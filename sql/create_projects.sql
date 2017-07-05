CREATE TABLE Projects(

	project_id CHAR(8),
	owner_id CHAR(6),
    name VARCHAR(30),
    description VARCHAR(150),
    created DATE,
    deadline DATE,
    PRIMARY KEY (project_id),
    FOREIGN KEY (owner_id) REFERENCES Users(user_id)
    
    
);