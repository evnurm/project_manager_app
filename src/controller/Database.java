package controller;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;






/**
 * Created by Viljami on 19.6.2017.
 */
public class Database {

    Connection conn;
    Statement statement;
    public Database(){

         final String databaseURL = "jdbc:mysql://localhost/project_manager?useSSL=false";

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
                String id = rs.getString("user_id");
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

    public boolean idIsTaken(String id) throws SQLException {

            statement = conn.createStatement();
            ResultSet rs =statement.executeQuery("SELECT COUNT('user_id') AS 'num'  FROM Users WHERE user_id = '" + id + "'");
            int num = 0;
                    while(rs.next()){
                        num = rs.getInt("num");
                    }
                    return num != 0;





    }

    public void insertUser(String userId, String username,  String password, String fname, String lname, String email) throws SQLException {

        // Hashing the password first.
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            String hashString = DatatypeConverter.printHexBinary(hash).toLowerCase();

            // Now trying to insert the values into database.
            statement = conn.createStatement();
            statement.execute("INSERT INTO Users VALUES('" + userId +"', '" +username +"', '" + hashString +"', '" + fname +"', '" +lname +"', '" + email+"')");
        }catch(NoSuchAlgorithmException e){
            System.out.println("No Such Algorithm...");
        }

    }
}
