CREATE TABLE Member(
	project_id CHAR(8),
    user_id CHAR(6),
    PRIMARY KEY(project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES Projects(project_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
    
);