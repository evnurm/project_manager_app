package controller;

import model.Member;
import model.Project;
import model.Task;
import model.User;
import view.Main;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


/**
 * Created by Viljami on 19.6.2017.
 */
public class Database {

    Connection conn;
    PreparedStatement statement;



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
             e.printStackTrace();
         }

    }


        public String createID(int length) {

            Random rand = new Random();
            char[] base64 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '-', '/'};
            String id = "";
            for (int i = 1; i <= length; i++) {
                id =  id + base64[rand.nextInt(64)];

            }

            return id;
    }

    public User signIn(String username, String passwordText){
        String id = "";
        String fname;
        String lname;
        String email;
        User user = new User("", "", "", "");
        try {
            // Hashing the password using SHA-256 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = md.digest(passwordText.getBytes("UTF-8"));
            String hashString = DatatypeConverter.printHexBinary(passwordHash).toLowerCase();


            statement = conn.prepareStatement("SELECT * FROM Users WHERE username COLLATE utf8_bin = ? AND password  = ?;");
            statement.setString(1, username);
            statement.setString(2, hashString);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                id = rs.getString("user_id");
                fname = rs.getString("first_name");
                lname = rs.getString("last_name");
                email = rs.getString("email");
                user = new User(id, fname, lname, email);

            }



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean idIsTaken(String id) throws SQLException {

            statement = conn.prepareStatement("SELECT COUNT('user_id') AS 'num' FROM Users WHERE user_id = ?");
            statement.setString(1,id);


            ResultSet rs = statement.executeQuery();
            int num = 0;
                    while(rs.next()){
                        num = rs.getInt("num");
                    }
                    return num != 0;

    }

    public boolean usernameIsTaken(String username) throws SQLException {
        statement = conn.prepareStatement("SELECT COUNT('user_id') AS 'num'  FROM Users WHERE username = ?");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
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
            statement = conn.prepareStatement("INSERT INTO Users VALUES(?,?,?,?,?,?)");
            statement.setString(1,userId);
            statement.setString(2,username);
            statement.setString(3,hashString);
            statement.setString(4,fname);
            statement.setString(5, lname);
            statement.setString(6, email);

            statement.execute();
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

        /*
         * Fetching the projects the current user has created themselves
         */

        statement = conn.prepareStatement("SELECT * FROM Projects WHERE owner_id = ? ;");
        statement.setString(1,id);
        ResultSet rs  = statement.executeQuery();
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            projectId = rs.getString("project_id");
            ownerId = rs.getString("owner_id");
            name = rs.getString("name");
            desc = rs.getString("description");
            created = rs.getDate("created");
            deadline = rs.getDate("deadline");


            Project project = new Project(projectId, ownerId, name, desc, created, deadline);

           projects.add(project);


        }

        statement = conn.prepareStatement("SELECT * FROM Projects WHERE project_id IN(SELECT project_id FROM Member WHERE user_id = ?)");
        statement.setString(1,id);
        rs = statement.executeQuery();
        while(rs.next()){
            projectId = rs.getString("project_id");
            ownerId = rs.getString("owner_id");
            name = rs.getString("name");
            desc = rs.getString("description");
            created = rs.getDate("created");
            deadline = rs.getDate("deadline");


            Project project = new Project(projectId, ownerId, name, desc, created, deadline);



            projects.add(project);

        }

        /*
         * Finding the tasks and members of the project and adding them to project instance at hand.
         */
        ArrayList<Task> tasks;
        ArrayList<Member> members;
        for(Project pr: projects){
            tasks = getTasks(pr.getId());
            members = getMembers(pr.getId());


            for(Task task: tasks){

                pr.addTask(task);
            }
            for(Member m : members){
                pr.addMember(m);
            }

        }

        return projects;




    }

    public boolean projectIdIsTaken(String id) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT('project_id') AS 'num'  FROM Projects WHERE project_id = ?;");
        stmt.setString(1,id);
        ResultSet rs = stmt.executeQuery();

        int num = 0;
        while(rs.next()){
            num = rs.getInt("num");
        }

        return num != 0;
    }
    /**
     * Creates a new database entry for a new project.

     * @return
     */
    public boolean addProject(String name, String desc, String deadline) throws SQLException {

        statement = conn.prepareStatement( "INSERT INTO Projects VALUES (?, ?, ?, ?, ?, ?);");
        String projectId = createID(8);
        String ownerID = Main.getSession().getUser().getId();

        SimpleDateFormat SQLDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String created = SQLDateFormat.format(new Date());

        while(projectIdIsTaken(projectId)){
            projectId = createID(8);
        }

        statement.setString(1, projectId);
        statement.setString(2, ownerID);
        statement.setString(3, name);
        statement.setString(4, desc);
        statement.setString(5, created);
        statement.setString(6, deadline);

        return statement.execute();
    }

    /**
     *  Deletes the project with the given id from the database.
     *  @param projectId    The id of project to be deleted.
     */
    public boolean deleteProject(String projectId) throws SQLException {

        statement = conn.prepareStatement("DELETE FROM `Projects` WHERE `project_id` = ?;");
        statement.setString(1, projectId);
        return statement.execute();

    }

    private boolean taskIdIsTaken(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(task_id) AS num FROM Tasks WHERE task_id = '" +id +"'");
        ResultSet rs = stmt.executeQuery();
        int num = 0;
        while(rs.next()){
            num = rs.getInt("num");

        }
        return num != 0;


    }

    public boolean addTask(String projectId, String name, String description, String deadline) throws SQLException {

        statement = conn.prepareStatement("INSERT INTO Tasks VALUES(?,?,?,?,?,?);");

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String created = sdf.format(today);

        String id = createID(10);

        while(taskIdIsTaken(id)){
            id = createID(10);
        }

        statement.setString(1, id);
        statement.setString(2, projectId);
        statement.setString(3, name);
        statement.setString(4, description);
        statement.setString(5, created);
        statement.setString(6, deadline);
        return statement.execute();
    }

    public ArrayList<Task> getTasks(String projectId) throws SQLException {

        ArrayList<Task> tasks = new ArrayList<>();

        statement = conn.prepareStatement("SELECT * FROM Tasks WHERE project_id = ?;");

        String projectID;
        String taskID;
        String name;
        String desc;
        Date created;
        Date dl;

        statement.setString(1,projectId);
        ResultSet rs = statement.executeQuery();

        while(rs.next()){

            projectID = rs.getString("project_id");
            taskID = rs.getString("task_id");
            name = rs.getString("name");
            desc = rs.getString("description");
            created = rs.getDate("created");
            dl = rs.getDate("deadline");

            Task task = new Task(taskID, projectID, name, desc, created, dl);
            tasks.add(task);

        }
        return tasks;


    }

    /**
     * Deletes the project from the database
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean deleteTask(String id) throws SQLException {

        statement = conn.prepareStatement("DELETE FROM Tasks WHERE task_id = ?;");
        statement.setString(1, id);
        return statement.execute();
    }

    /**
     * Gets the description of the given project
     * @param projectId the id of the project
     * @return The description of the project or "Description not found" if descriptino not available
     * @throws SQLException
     */
    public String getProjectDescription(String projectId) throws SQLException {
        statement = conn.prepareStatement("SELECT description FROM Projects WHERE project_id = ?;");
        statement.setString(1, projectId);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            String description = rs.getString("description");
            return description;
        }
        else {
            return "Description not found";
        }
    }

    /**
     * Finds the members of the given project
     * @param projectId the id of the project whose members are wanted to get
     * @return an ArrayList containing the members of the project
     */
    public ArrayList<Member> getMembers(String projectId){

        ArrayList<Member> members = new  ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE user_id IN( SELECT user_id FROM Member WHERE project_id = ?)");
            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String id = rs.getString("user_id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String email = rs.getString("email");
                User user = new User(id, fname, lname, email);
                members.add(new Member(user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    /**
     * Finds the users whose names resemble the search query and returns them in an ArrayList
     * and an empty ArrayList if no users match the query
     * @param searchQuery
     * @return
     * @throws SQLException
     */
    public ArrayList<User> searchUsers(String searchQuery, String projectId) throws SQLException {
        ArrayList<User> matchedUsers = new ArrayList<>();
        statement = conn. prepareStatement("SELECT * FROM Users WHERE first_name LIKE ? OR last_name LIKE ? AND user_id NOT IN(" +
                "SELECT user_id FROM Member WHERE project_id = ?)");
        statement.setString(1, searchQuery + "%");
        statement.setString(2, searchQuery + "%");
        statement.setString(3, projectId);

        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String id = rs.getString("user_id");
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
            String email = rs.getString("email");

            matchedUsers.add(new User(id, fname, lname, email));

        }
        return matchedUsers;
    }

    private boolean isMember(final String projectId, final String userId) throws SQLException {
        statement = conn.prepareStatement("SELECT COUNT(*) FROM member WHERE project_id=? AND user_id=?");
        statement.setString(1, projectId);
        statement.setString(2, userId);
        ResultSet rs = statement.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }

    public void addMember(final String projectId, final String userId) throws SQLException {
        if (!this.isMember(projectId, userId)) {
            statement = conn.prepareStatement("INSERT INTO Member VALUES (?, ?)");
            statement.setString(1, projectId);
            statement.setString(2, userId);
            statement.execute();
        }
    }

}


