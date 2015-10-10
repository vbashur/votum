## Data Access Application

Application demonstrates the basic spring security functionality based on storing user information into database. 

### Features:
 - user authentication: simple login/password form
 - user authorization: user role defines visible UI areas and data operations
 - show information about registered users 
 - user management: create, delete, change password (for admin only)
 - failed login attempts logging

### Launching the application

 Launch command line interface in the directory with project's pom.xml file, type 'mvn clean install package jetty:run'
 Open web browser and type in the address bar 'http://localhost:8080/datapine-test-app/'
  
### Credentials:
 By default there are following users available
 Administrator: login - admin@domain.me, password - admin
 User: login - user@domain.me, password - user
 
### Restrictions and known issues
 - Users created by Administrator has 'ROLE_USER' permissions by default - was implemented in that way for the sake of simplicity

Implemented by Victor Bashurov (http://vbashur.blogspot.com)