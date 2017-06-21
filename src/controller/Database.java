package controller;
import javax.xml.bind.DatatypeConverter;
import javax.xml.transform.Result;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

/**
 * Created by Viljami on 19.6.2017.
 */
public class Database {

    Connection conn;
    Statement statement;
    public Database(){

         final String databaseURL = "jdbc:mysql://localhost/project_manager";

        //  Database credentials
         final String mysqlUsername = "client";
         final String mysqlPassword = "client";




         try {
             Class.forName("com.mysql.jdbc.Driver");

             System.out.println("Connecting to database...");
             conn = DriverManager.getConnection(databaseURL, mysqlUsername, mysqlPassword);



         }catch(ClassNotFoundException e){
             System.out.println("Class not found.");

         }catch (SQLException e){
             System.out.println("SQL Exception");
         }

    }

    public void signIn(String username, String passwordText){
        try {
            // Hashing the password using SHA-256 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = md.digest(passwordText.getBytes("UTF-8"));
            String hashString = DatatypeConverter.printHexBinary(passwordHash).toLowerCase();

            statement = conn.createStatement();
            String sql = "SELECT user_id FROM Users WHERE username = '" +username + "' AND password  = '" +hashString +"';";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("user_id");
                System.out.println(id);
            }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
