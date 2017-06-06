package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author evnurm
 */
public class Project{

    private String title;
    private Date deadline;
    private Date beginning;
    private ArrayList<Member> members = new ArrayList<Member>();
    private ArrayList<Task> tasks = new ArrayList<Task>();


    public Project(String name){
        title = name;
    }

    /** Adds a task into this project. */
    public void addTask(Task task){
        tasks.add(task)
    }

    /** Removes the given task from this project. */
    public removeTask(Task task){
        tasks.remove(task);
    }

    /** Adds the given member into this project. */
    public void addMember(Member member){ members.add(member) }

    /** Removes the given member from this project. */
    public void removeMember(Member member){ members.remove(member)}

    /** Sets the deadline of the project to the given date. */
    public void setDL(Date date){ deadline = date }

    /** Sets the beginning date of the project to the given date. */
    public void setBeginning(Date date){beginning = date}
}