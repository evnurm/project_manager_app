package model;

/**
 * @author evnurm
 */
public class Task implements ListableItem{

    private String title;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return "";
    }

    public Task(String name){
        title = name;
    }

}
