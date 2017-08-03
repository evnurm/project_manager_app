package controller;

/**
 * This class is designed to be holder of relevant information regarding getting user and project information.
 */

public class Session {

    private String userId;
    private String projectId;

    public Session(String userId, String projectId){
        this.userId = userId;
        this.projectId = projectId;

    }

    public String getUserId() {
        return userId;
    }
    public String getProjectId(){
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
