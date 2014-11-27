README

Datapine Test Application

Launch:
 - Launch command line interface in the directory with project's pom.xml file, type 'mvn clean install package jetty:run'
 - Open web browser and type in the address bar 'http://localhost:8080/datapine-test-app/'
 

Features:
 - user authentication: simple login/password form
 - user authorization: user role defines visible UI areas and data operations
 - show information about registered users 
 - user management: create, delete, change password (for admin only)
 - failed login attempts logging
 
Credentials:
 By default there are following users available
 Administrator: login - admin@domain.me, password - admin
 User: login - user@domain.me, password - user
 
Restrictions and known issues
 1. Users created by Administrator has 'ROLE_USER' permissions by default - was implemented in that way for the sake of simplicity
 2. Changing the password for user doesn't take an effect, by default the new password will be 'password'
 3. Confirming user password change creates new browser window
 Points 2 and 3 are not business layer problems but UI implementation
 