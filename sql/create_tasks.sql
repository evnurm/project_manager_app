CREATE TABLE Tasks(

	task_id CHAR(10) PRIMARY KEY,
    project_id CHAR(8),
    name VARCHAR(30),
    description VARCHAR(150),
    created DATE,
    deadline DATE,
    
    FOREIGN KEY(project_id) REFERENCES Projects(project_id)
    
);