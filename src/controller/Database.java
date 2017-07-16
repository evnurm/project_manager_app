package controller;
import com.sun.org.apache.regexp.internal.RE;
import model.Project;
import view.Main;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


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

    public String createID(int length) {

        Random rand = new Random();
        char[] base64 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '-', '/',};
        char[] id = new char[length];

        for (int i = 1; i <= length; i++) {
            id[i - 1] = base64[rand.nextInt(64)];

        }

        return new String(id);
    }

    public String signIn(String username, String passwordText){
        String id = "";
        try {
            // Hashing the password using SHA-256 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = md.digest(passwordText.getBytes("UTF-8"));
            String hashString = DatatypeConverter.printHexBinary(passwordHash).toLowerCase();

            statement = conn.createStatement();
            String sql = "SELECT user_id FROM Users WHERE username = '" +username + "' AND password  = '" +hashString +"';";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                id = rs.getString("user_id");

            }



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
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

    public boolean usernameIsTaken(String username) throws SQLException {
        statement = conn.createStatement();
        ResultSet rs =statement.executeQuery("SELECT COUNT('user_id') AS 'num'  FROM Users WHERE username = '" + username+ "'");
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

    /**
     * Finds all the projects where the given user is the owner of the project.
     * @param id The id of the username.
     * @return A project ArrayList consisting of all the users projects.
     * @throws SQLException
     */
    public ArrayList<Project> getProjects(String id) throws SQLException {
        String projectId;
        String ownerId;
        String name;
        String desc;
        Date created;
        Date deadline;

        statement = conn.createStatement();


        String sql = "SELECT * FROM Projects WHERE owner_id ='" +id +"';";

        ResultSet rs  = statement.executeQuery(sql);
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            projectId = rs.getString("project_id");
            ownerId = rs.getString("owner_id");
            name = rs.getString("name");
            desc = rs.getString("description");
            created = rs.getDate("created");
            deadline = rs.getDate("deadline");




           projects.add(new Project(projectId, ownerId, name, desc, created, deadline));


        }

        sql = "SELECT * FROM Projects WHERE project_id IN(SELECT project_id FROM Member WHERE user_id = '" + id + "')";
        rs = statement.executeQuery(sql);
        while(rs.next()){
            projectId = rs.getString("project_id");
            ownerId = rs.getString("owner_id");
            name = rs.getString("name");
            desc = rs.getString("description");
            created = rs.getDate("created");
            deadline = rs.getDate("deadline");




            projects.add(new Project(projectId, ownerId, name, desc, created, deadline));

        }

        return projects;




    }

    public boolean projectIdIsTaken(String id) throws SQLException {

        statement = conn.createStatement();
        ResultSet rs =statement.executeQuery("SELECT COUNT('project_id') AS 'num'  FROM Projects WHERE project_id = '" + id + "'");
        int num = 0;
        while(rs.next()){
            num = rs.getInt("num");
        }
        return num != 0;





    }
    /**
     * Creates a new database entry for a new project.
     * @param project
     * @return
     */
    public boolean addProject(String name, String desc, String deadline) throws SQLException {

        statement = conn.createStatement();

        String projectId = createID(8);
        String ownerID = Main.userid;

        SimpleDateFormat SQLDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String created = SQLDateFormat.format(new Date());

        while(projectIdIsTaken(projectId)){
            projectId = createID(8);
        }


        String insertionQuery  = "INSERT INTO Projects VALUES('" +projectId +"', '" +ownerID+"', '" + name +"', '" + desc +"', '" + created+"', '" +deadline+"');";
        return statement.execute(insertionQuery);



    }

    /**
     *  Deletes the project with the given id from the database.
     *  @param projectId    The id of project to be deleted.
     */
    public boolean deleteProject(String projectId) throws SQLException {

        statement = conn.createStatement();

        String deletionQuery = "DELETE FROM `Projects` WHERE `project_id` =  '"+ projectId + "';" ;
        return statement.execute(deletionQuery);

    }

}


