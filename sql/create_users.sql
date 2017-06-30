CREATE TABLE USERS(
    user_id CHAR(6) PRIMARY KEY,
    username VARCHAR(20),
    password CHAR(64),
    first_name VARCHAR(20),
    last_name VARCHAR(30),
    email VARCHAR(50)

);