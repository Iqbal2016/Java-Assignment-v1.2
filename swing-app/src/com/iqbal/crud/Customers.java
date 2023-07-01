
package com.iqbal.crud;

import com.iqbal.db.dbList;
import com.iqbal.db.dbRow;
import com.iqbal.tools.Utils;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Customers {
    public static dbList loadData(dbRow filter){
        try {
            return Launcher.getDatabaseConnection().loadData(filter, "customer", "CustomerId");
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static dbRow getMember(String id){
        try {
            return Launcher.getDatabaseConnection().fetchByID(id, "customer", "id");
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static dbList searchByName(String name){
        try {
            String sql = String.format(
                    "SELECT * FROM customer WHERE LOWER(%s) LIKE '%%%s%%' ORDER BY %s ASC",
                    "CustomerId",
                    Utils.escapeSQLVar(name).toLowerCase(),
                    "CustomerId"
            );
            return Launcher.getDatabaseConnection().getList(sql);
        } catch(Exception e){
            e.printStackTrace();
            return new dbList();
        }
    }
    
    public static boolean delete(String id){
        try {
            String sql = String.format("DELETE FROM customer WHERE id = '%s'", 
                    Utils.escapeSQLVar(id)
            );
            return Launcher.getDatabaseConnection().nonTransactQuery(sql) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
