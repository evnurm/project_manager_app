package model;

/**
 * @author evnurm
 */
public class Member{
    private String firstName;
    private String lastName;
    private String email;

    public Member(User user){
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
