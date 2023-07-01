
package com.iqbal.crud;

import com.iqbal.db.DB;
import com.iqbal.tools.Utils;

import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

/**
 *
 * @author user
 */
public class Launcher {
    private static DB databaseConnection;
    public static DB getDatabaseConnection() {
        return databaseConnection;
    }
    
    private static MainFrame frame;
    public static MainFrame getFrame(){
        return frame;
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            
        }
        
        // database connection information
        Map<String, String> dbInfo = new HashMap<String, String>();
        
        //dbInfo.put("dbHost", "localhost");
        //dbInfo.put("dbPort", "3306");
        //dbInfo.put("dbUser", "root");
        //dbInfo.put("dbPass", "root");
        dbInfo.put("dbName", "javaassignmentdb");
        
        databaseConnection = new DB(dbInfo);
        if(!databaseConnection.connectDB()){
            Utils.alert("Connection to database failed. The application cannot run.\r\n"
            		+ "");
            return;
        }
        
        frame = new MainFrame();
        frame.setVisible(true);
        
        frame.setContent(new CustomerList(), "Data Customer");
    }
}
