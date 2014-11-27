DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id INT(10) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,    
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS User_roles;
CREATE TABLE User_roles (
  user_role_id INT(10) NOT NULL,
  email VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL
);

-- In fact there should be one more table which is a key holder for user_roles and User, within this table no need to store email in
-- User_roles, User_roles should contain only a unique set of roles and its primary keys


-- Commented to simplify test app
-- DROP TABLE IF EXISTS authorize_pair;
-- CREATE TABLE authorize_pair ( 
-- 	U_Id INT(10),
-- 	R_Id INT(10),
-- 	FOREIGN KEY (U_Id) REFERENCES User(id),
-- FOREIGN KEY (R_Id) REFERENCES User_roles(user_role_id),
-- );


