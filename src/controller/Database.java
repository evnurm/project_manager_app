package controller;
import java.sql.*;

/**
 * Created by Viljami on 19.6.2017.
 */
public class Database {

    public Database(){
        // JDBC driver name and database URL
         final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
         final String DB_URL = "jdbc:mysql://localhost/project_manager";

        //  Database credentials
         final String USER = "client";
         final String PASS = "client";


         Connection conn  = null;
         Statement statement = null;
         try {
             Class.forName("com.mysql.jdbc.Driver");

             System.out.println("Connecting to database...");
             conn = DriverManager.getConnection(DB_URL,USER,PASS);

             System.out.println("Creating statement...");
             statement = conn.createStatement();
             String sql;
             sql = "SELECT * FROM Users";
             ResultSet rs = statement.executeQuery(sql);

             while(rs.next()) {
                 //Retrieve by column name
                 int id = rs.getInt("user_id");
                 String user = rs.getString("username");
                 String first = rs.getString("first_name");
                 String last = rs.getString("last_name");
                 String email = rs.getString("email");
                 //Display values
                 System.out.println("ID: " + id);
                 System.out.println("Username: " + user);
                 System.out.println("First name: " + first);
                 System.out.println("Last name: " + last);
                 System.out.println("Email: " + email);

                 rs.close();
                 statement.close();
                 conn.close();
             }

         }catch(ClassNotFoundException e){
             System.out.println("Class not found.");

         }catch (SQLException e){
             System.out.println("SQL Exception");
         }

    }
}
