package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author evnurm
 */
public class Task implements ListableItem{

    private String title;
    private String taskId;
    private String projectId;


    private String description;
    private Date created;
    private Date deadline;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return "";
    }

    public Task(String taskId, String projectId, String name, String desc, Date created,  Date dl){
        title = name;
        this.taskId = taskId;
        this.projectId = projectId;
        this.description = desc;
        this.created = created;
        this.deadline = dl;


    }


    public String getTaskId(){
        return taskId;
    }
    public String getProjectId(){
        return projectId;
    }

    public String getCreated(){
        SimpleDateFormat sqlFormat = new SimpleDateFormat("YYYY-MM-dd");
        return sqlFormat.format(created);
    }
    public String getDeadline(){
        SimpleDateFormat sqlFormat = new SimpleDateFormat("YYYY-MM-dd");
        return sqlFormat.format(deadline);
    }
}
