CREATE TABLE Projects(

	project_id INT,
	owner_id INT,
    name VARCHAR(30),
    description VARCHAR(150),
    created TIMESTAMP,
    deadline TIMESTAMP,
    PRIMARY KEY (project_id),
    FOREIGN KEY (owner_id) REFERENCES Users(user_id)
    
    
);