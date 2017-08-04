package model;

public class User {

    private String id;
    private String firstName;
    private  String lastName;
    private String email;


    public User(String id, String fname, String lname, String email){
        this.id = id;
        firstName = fname;
        lastName = lname;
        this.email = email;

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
