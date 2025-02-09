/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


/**
 *
 * @author viren
 */
public class UserDaoImpl implements UserDao{
    public boolean isRegistered(String emailId){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn = DBUtil.provideConnection();
        boolean flag=false;
        try{
            ps = conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                flag=true;
            }
        }
        catch(SQLException ex){
            System.out.println("Error is isRegistered"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
         return flag;
    }
    
    public String registerUser(UserPojo user) {
       String status="Registration failed";
       boolean isUserRegistered=isRegistered(user.getUseremail());
       if(isUserRegistered){
           status="Email Already Registered. Try Again";
           return status;
       }
       Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("Insert into users values(?,?,?,?,?,?)");
           ps.setString(1,user.getUseremail());
           ps.setString(2,user.getUsername());
           ps.setString(3,user.getMobile());
           ps.setString(4,user.getAddress());
           ps.setInt(5,user.getPincode());
           ps.setString(6,user.getPassword());
           int count = ps.executeUpdate();
           if(count==1){
               status="Registration Successful";
               try {
                   // code to send email
                   MailMessage.registrationSuccess(user.getUseremail(), user.getUsername());
               } catch (MessagingException ex) {
                   Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, "Failed to send registration success email", ex);
               }
               System.out.println("Mail sent successfully");
           }
    }
       catch(SQLException ex){
           System.out.println("error in RegisterUser:"+ex);
           ex.printStackTrace();
       }
       DBUtil.closeStatement(ps);
       return status;
    }

     public String isValidCrendentials(String emailId, String password) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn = DBUtil.provideConnection();
        String status="Login Denied. Invalid Username or Password";
        try{
            ps = conn.prepareStatement("select * from users where useremail=? and password=?");
            ps.setString(1, emailId);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                status = "Login Successfull";
            }
        }
        catch(SQLException ex){
            status="Error."+ex.getMessage();
            System.out.println("Error is isValidCredentials"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
         return status;
    }

      public UserPojo getUserDetails(String emailId) {
          UserPojo user=null;
          PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn = DBUtil.provideConnection();
        try{
             ps = conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                user=new UserPojo();
                user.setUseremail(rs.getString("useremail"));
                user.setUsername(rs.getString("username"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPincode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getUserDetails:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return user;
    }

       public String getUserFirstName(String emailId) {
          String fName=null;
          PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn = DBUtil.provideConnection();
        try{
             ps = conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                String fullName=rs.getString(1);
                fName = fullName.split(" ")[0];   
            }  
        }
        catch(SQLException ex){
            System.out.println("Error in getUserFirstName:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return fName;
       }

    @Override
    public String getUserAddr(String emailId) {
                String address=null;
                PreparedStatement ps=null;
                ResultSet rs=null;
                Connection conn=DBUtil.provideConnection();
                
                try{
                    ps=conn.prepareStatement("select address from users where useremail=?");
                    ps.setString(1, emailId);
                    rs=ps.executeQuery();
                    if(rs.next()){
                        address=rs.getString(1);
                    }
                }
                catch(SQLException ex){
                    System.out.println("Error in getUserAddr: "+ex);
                    ex.printStackTrace();
                }
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(ps);
                return address;
                
    }
}



    