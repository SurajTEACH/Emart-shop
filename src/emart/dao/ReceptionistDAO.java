
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ReceptionistPojo;
import emart.pojo.userPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceptionistDAO {

    public static Map<String, String> getNonRegisteredReceptionists() throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        
     
        ResultSet rs = st.executeQuery("SELECT empid, empname FROM employees WHERE job='Receptionist' "
                                       + "AND empid NOT IN (SELECT empid FROM users WHERE usertype='Receptionist')");
        
        HashMap<String, String> receptionistList = new HashMap<>();
        
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            receptionistList.put(id, name);
        }
        
        return receptionistList;
    }

    public static boolean addReceptionist(userPojo user) throws SQLException {
        Connection con = DBConnection.getConnection();
        
       
        PreparedStatement ps = con.prepareStatement("INSERT INTO users (userid, empid, password, usertype, username) VALUES (?, ?, ?, ?, ?)");
        
        ps.setString(1, user.getUserid());
        ps.setString(2, user.getEmpid());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getUsertype());
        ps.setString(5, user.getUsername());
        
        int result = ps.executeUpdate();
        return result == 1;
    }
    
    public static List<ReceptionistPojo> getAllReceptionistDetails() throws SQLException
    {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
    
   
         ResultSet rs = st.executeQuery("SELECT users.empid, empname, userid, job, salary FROM users, employees WHERE usertype='Receptionist' AND users.empid = employees.empid");
    
        ArrayList<ReceptionistPojo> al = new ArrayList<>();
    
        while (rs.next())
        {
            ReceptionistPojo recep = new ReceptionistPojo();
            recep.setEmpid(rs.getString(1));
            recep.setEmpname(rs.getString(2));
            recep.setUserid(rs.getString(3));
            recep.setJob(rs.getString(4));
            recep.setSalary(rs.getDouble(5));
        
            al.add(recep);
        }
    
        return al;
    }
    
    public static Map<String ,String> getAllReceptionistId()throws SQLException
    {
         Connection con = DBConnection.getConnection();
         Statement st = con.createStatement();
        
        
        ResultSet rs = st.executeQuery("SELECT userid,username from users WHERE usertype='Receptionist'");
        
        HashMap<String, String> receptionistList = new HashMap<>();
        
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            receptionistList.put(id, name);
        }
        
        return receptionistList;
    }
    
   
    public static boolean updatePassword(String userid, String pwd) throws SQLException 
    {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE userid = ?");
        ps.setString(1, pwd);
        ps.setString(2, userid);
        return ps.executeUpdate() == 1;
    }
    
    public static List<String> getAllReceptionistUserId()throws SQLException
    {
         Connection con = DBConnection.getConnection();
         Statement st = con.createStatement();
        
        
        ResultSet rs = st.executeQuery("SELECT userid from users WHERE usertype='Receptionist'");
        
        List<String> receptionistList = new ArrayList<>();
        
        while (rs.next()) {
            String id = rs.getString(1);
            receptionistList.add(id);
        }
        
        return receptionistList;  
    }
    
    public static boolean deleteReceptionist(String userid)throws SQLException
    {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE from users WHERE userid = ?");
        ps.setString(1, userid);
        return ps.executeUpdate() == 1;  
    }

    
}
