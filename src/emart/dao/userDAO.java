/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.userPojo;
import emart.pojo.userProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author suraj
 */
public class userDAO {
    
      public static boolean validteUser(userPojo user)throws SQLException
      {
          Connection conn = DBConnection.getConnection();
          PreparedStatement ps = conn.prepareStatement("select * from users where userid=? and password=? and usertype=?");
          ps.setString(1,user.getUserid());
          ps.setString(2, user.getPassword());
          ps.setString(3, user.getUsertype());
          
          ResultSet rs = ps.executeQuery();
          if(rs.next())
          {
              String username = rs.getString(5);
              userProfile.setUsername(username);
              return true;
          }
          
          return false;
          
      }
      
      public static boolean isUserPresent(String empid)throws SQLException
      {
          Connection conn = DBConnection.getConnection();
          PreparedStatement ps = conn.prepareStatement("select 1 from users where empid=?");
          ps.setString(1,empid);
          ResultSet rs = ps.executeQuery();
          return rs.next();
      }
}
