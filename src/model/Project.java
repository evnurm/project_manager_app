package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author evnurm
 */
public class Project{

    private String title;
    private String description;
    private Date deadline;
    private Date beginning;
    private ArrayList<Member> members = new ArrayList<Member>();
    public ArrayList<Task> tasks = new ArrayList<Task>();


    public Project(String name, Date beginning, Date DL){
        title = name;
        this.beginning = beginning;
        deadline = DL;
    }

    /** Adds a task into this project. */
    public void addTask(Task task){
        tasks.add(task);
    }

    /** Removes the given task from this project. */
    public void removeTask(Task task){
        tasks.remove(task);
    }

    /** Adds the given member into this project. */
    public void addMember(Member member){ members.add(member); }

    /** Removes the given member from this project. */
    public void removeMember(Member member){ members.remove(member);}

    /** Sets the deadline of the project to the given date. */
    public void setDL(Date date){ deadline = date; }

    /** Sets the beginning date of the project to the given date. */
    public void setBeginning(Date date){beginning = date;}
}