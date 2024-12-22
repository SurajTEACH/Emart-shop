
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author suraj
 */
public class DBConnection {
    private static Connection conn;
    static
    {  
        try
        {
            
             Class.forName("oracle.jdbc.OracleDriver");
             conn = DriverManager.getConnection("jdbc:oracle:thin:@//surajBook3:1521/XE","emart","emart");
             JOptionPane.showMessageDialog(null,"Connection Opened Successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
        
        }
        catch(ClassNotFoundException  ex)
        {
            JOptionPane.showMessageDialog(null,"Error in loading the driver","Driver Error!",JOptionPane.INFORMATION_MESSAGE); 
            ex.printStackTrace();
            System.exit(1);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in opaneing connection","DBSQL Error!",JOptionPane.INFORMATION_MESSAGE); 
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static void closeConnection(){
        
        try{
            
            conn.close();
            JOptionPane.showMessageDialog(null,"Connection Close Successfully!","Close",JOptionPane.INFORMATION_MESSAGE);
        
        }
         catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in closing connection","DBSQL Error!",JOptionPane.INFORMATION_MESSAGE); 
            ex.printStackTrace();
        }
    }
    
}
