
package com.iqbal.db;

import java.sql.*;
import java.util.Map;

import com.iqbal.tools.Utils;

public class DB {

    private String dbName = "";
    private String dbUser = "";
    private String dbPass = "";
    private String dbHost = "";
    private String dbPort = "";
    private String connString = "";
    private Connection dbConnection;
    private int lastRowCount = 0;

    public DB(Map<String, String> conf) {
        if (conf == null) {
            return;
        }

        this.dbName = conf.containsKey("dbName") ? conf.get("dbName") : "javaassignmentdb";
        this.dbUser = conf.containsKey("dbUser") ? conf.get("dbUser") : "root";
        this.dbPass = conf.containsKey("dbPass") ? conf.get("dbPass") : "root";
        this.dbHost = conf.containsKey("dbHost") ? conf.get("dbHost") : "localhost";
        this.dbPort = conf.containsKey("dbPort") ? conf.get("dbPort") : "3306";

        this.connString = String.format(
                "jdbc:mysql://%s:%s/%s?user=%s&password=%s",
                this.dbHost,
                this.dbPort,
                this.dbName,
                this.dbUser,
                this.dbPass
        );
    }

    public int getLastRowCount() {
        return this.lastRowCount;
    }

    public boolean connectDB() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(this.connString, this.dbUser, this.dbPass);
            this.dbConnection = con;
            Utils.stdout(con);
        } catch (Exception e) {
            System.out.println("ERROR in connecting to database: " + e.getMessage());
            this.dbConnection = null;
            return false;
        }

        return true;
    }

    public String getVar(String sql) throws SQLException {
        String result = "";
        Statement s = this.dbConnection.createStatement();
        s.executeQuery(sql);
        ResultSet res = s.getResultSet();
        if (res.next());
        result = res.getString(1);

        return result;
    }

    public int nonTransactQuery(String sql) throws SQLException {
        Statement s = this.dbConnection.createStatement();
        int res = s.executeUpdate(sql);

        return res;
    }

    public dbRow getRow(String sql) throws SQLException {
        dbRow result = new dbRow();
        Statement s = this.dbConnection.createStatement();
        s.executeQuery(sql);
        ResultSet res = s.getResultSet();
        if (res.next()) {
            result = this.constructRow(res);
        }

        return result;
    }

    private dbRow constructRow(ResultSet res) throws SQLException {
        ResultSetMetaData meta = res.getMetaData();
        int columnCount = meta.getColumnCount();
        dbRow row = new dbRow();
        String field;
        String val;
        for (int i = 1; i <= columnCount; i++) {
            field = meta.getColumnName(i);
            val = res.getString(i);
            row.put(field, val);
        }

        return row;
    }

    public dbList getList(String sql) throws SQLException {
        dbList result = new dbList();
        Statement s = null;
        ResultSet res = null;
        int rowCount = 0;

        s = this.dbConnection.createStatement();

        s.executeQuery(sql);
        res = s.getResultSet();
        while (res.next()) {
            dbRow row = this.constructRow(res);
            result.put(rowCount, row);

            rowCount++;
        }

        this.lastRowCount = rowCount;
        return result;
    }

    public static String buildQueryClause(dbRow cond) {
        return buildQueryClause(cond, "AND");
    }

    public static String buildQueryClause(dbRow cond, String glue) {
        String cl = "";
        if (cond == null || cond.size() < 1) {
            return cl;
        }
        String token;
        for (String k : cond.keySet()) {
            token = String.format("%s = '%s'",
                    k, Utils.escapeSQLVar(cond.get(k)));
            if (cl.equals("")) {
                cl = token;
            } else {
                cl += " " + glue + " " + token;
            }
        }

        return cl;
    }

    public static String buildBetweenClause(String field, String from, String to) {
        return String.format("%s BETWEEN '%s' AND '%s'",
                Utils.escapeSQLVar(field), Utils.escapeSQLVar(from), Utils.escapeSQLVar(to));
    }

    public static String buildInsertSQL(String table, dbRow data) {
        String token, fields = "", values = "";
        for (String field : data.keySet()) {
            if (fields.equals("")) {
                fields = field;
            } else {
                fields += "," + field;
            }

            token = data.get(field);
            if (values.equals("")) {
                values = "'" + Utils.escapeSQLVar(token) + "'";
            } else {
                values += ",'" + Utils.escapeSQLVar(token) + "'";
            }
        }

        return String.format("INSERT INTO %s( %s ) VALUES( %s )", table, fields, values);
    }

    public static String buildUpdateSQL(String table, dbRow data, dbRow constrain) {
        String fields = "", dataString = "", value = "";
        String whereClause = "";
        if (constrain.size() > 0) {
            whereClause = " WHERE " + DB.buildQueryClause(constrain);
        }

        for (String field : data.keySet()) {
            value = Utils.escapeSQLVar(data.get(field));
            if (dataString.equals("")) {
                dataString = String.format("%s = '%s'", field, value);
            } else {
                dataString += ", " + String.format("%s = '%s'", field, value);
            }
        }

        return String.format("UPDATE %s SET %s %s", table, dataString, whereClause);
    }

    public int save(String table, String keyfield, dbRow data) {
        return save(table, keyfield, data, true);
    }

    public int save(String table, String keyfield, dbRow data, boolean updateIfIDExists) {
        String sql = "";
        if (!updateIfIDExists || !data.containsKey(keyfield) || data.get(keyfield).equals("0")) {
            if (updateIfIDExists) {
                data.remove(keyfield);
            }
            sql = DB.buildInsertSQL(table, data);
        } else {
            dbRow constrain = new dbRow();
            constrain.put(keyfield, data.get(keyfield));
            data.remove(keyfield);
            sql = DB.buildUpdateSQL(table, data, constrain);
        }

        Utils.stdout(sql);
        try {
            return nonTransactQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.alert("Failed to save data\r\n"
            		+ ": \n" + e.getMessage());
            return 0;
        }
    }

    public dbList loadData(dbRow filter, String tableName) {
        return loadData(filter, tableName, "1");
    }

    public dbList loadData(dbRow filter, String tableName, String orderField) {
        String sql = "SELECT * FROM " + tableName + " ORDER BY " + orderField + " ASC";
        if (filter != null && filter.size() > 0) {
            sql = String.format("SELECT * FROM %s WHERE %s ORDER BY %s ASC",
                    tableName, DB.buildQueryClause(filter), orderField);
        }
        try {
            dbList prods = this.getList(sql);
            return prods;
        } catch (Exception e) {
            e.printStackTrace();
            Utils.alert("Failed to fetch data");
            return null;
        }
    }

    public dbRow fetchByID(String id, String tableName, String keyField) {
        return fetchByID(id, tableName, keyField, true);
    }

    public dbRow fetchByID(String id, String tableName, String keyField, boolean alert) {
        try {
            return this.getRow(String.format("SELECT * FROM %s WHERE %s = '%s'",
                    tableName, keyField, Utils.escapeSQLVar(id + "")));
        } catch (Exception e) {
            e.printStackTrace();
            if (alert) {
                Utils.alert("Data not found: " + e.getMessage());
            }
            return null;
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            connected = dbConnection != null && getVar("SELECT 1").equals("1");
            if (connected) {
                return connected;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!connected) {
            connected = connectDB();
        }

        return connected;
    }
}
