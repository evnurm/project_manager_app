package model;

import java.util.ArrayList;

/**
 * @author evnurm
 */
public class Project{

    String title;
    ArrayList<Task> tasks = new ArrayList<Task>();
    public Project(String name){
        title = name;
    }

    /** Adds a task into this project. */
    public void addTask(String name){
        tasks.add(new Task(name));
    }



}