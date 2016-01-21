### Votum application
Voting system for deciding where to have lunch

### Launching the application

To launch the app from maven go to the application root directory (directory with pom.xml file) and run: mvn -DskipTests clean install exec:java -DmainClass="com.vbashur.votum.VotumApplication"

To build the package run: mvn -DskipTests clean package

To run unit tests: mvn clean test

### Credentials

The following login/password pairs are permitted to perform application operations:
user1/user1
user2/user2
user3/user3
user4/user4
user5/user5
admin/admin

api available for all users:
/api/list
/api/vote

api available for admin user only:
/api/collect
/api/top
/api/restaurant/update
/api/restaurant/delete
/api/restaurant/add

### Application usage

-- Get the list of restaurants with menu <br/>
curl 10.10.150.156:8080/api/list -u admin:admin -X GET

Response sample: <br/>
[{"restaurantId":1,"name":"Old good times","address":"Aschheim, Einsteinring 29","description":"Traditional german food for average price","email":"traditional@mail.de","meals":[{"mealId":2,"name":"Bayern sausages","price":3.0,"description":"Das ist lecker!!"},{"mealId":1,"name":"Pommes","price":1.0,"description":"Traditional german meal"}]},{"restaurantId":2,"name":"Exxxotica!","address":"Munich, Sohnenstrasse 15","description":"Fried insects, banana beer, sweet octopus and many more","email":"exxxotica@mail.de","meals":[{"mealId":4,"name":"kimchi","price":2.5,"description":"korean spicy cabbage"},{"mealId":3,"name":"bugs","price":7.15,"description":"deep fried"}]},{"restaurantId":3,"name":"Sir Karabas Barabas","address":"Augsburg, Rosenheimerplatz 2","description":"We bring our Gourmet Restaurant to your doorstep","email":"karabas@mail.de","meals":[{"mealId":5,"name":"carbonara de la penna","price":89.0,"description":"Made by Master Paolo from Florenzia with love"}]}]

-- Get to know about the restaurants and user votes<br/>
curl 10.10.150.184:8080/api/collect -u admin:admin -X GET

-- Find out the top voted restaurant<br/>
curl 10.10.150.156:8080/api/top -u admin:admin -X GET

-- Vote for the restaurant (pay attention to restaurant id and format of the vote message)<br/> 
curl 10.10.150.156:8080/api/vote -u admin:admin -X POST --data {\"restaurantId\":2} -H "Content-type:application/json"<br/>
curl 10.10.150.156:8080/api/vote -u user2:user2 -X POST --data {\"restaurantId\":3} -H "Content-type:application/json"

-- Delete restaurant<br/>
curl 10.10.150.156:8080/api/restaurant/delete -u admin:admin -X POST --data {\"restaurantId\":2} -H "Content-type:application/json"

-- Add restaurant<br/>
curl 10.10.150.156:8080/api/restaurant/delete -u admin:admin -X POST --data {\"name\":\"Karamba\",\"address\":\"Ottobrunn_Bahnstr_9\",\"description\":\"Cheap\",\"email\":\"none\",\"meals\":[{\"name\":\"Sausages\",\"price\":3,\"description\":\"Lecker\"},{\"name\":\"Pommes\",\"price\":10,\"description\":\"Cool\"}]} -H "Content-type:application/json"

-- Update restarurant<br/>
curl 10.10.150.156:8080/api/restaurant/update -u admin:admin -X POST --data {\"name\":\"Karamba\",\"address\":\"Beverly_Hills_90210\",\"description\":\"Expensive\",\"email\":\"none\",\"meals\":[{\"name\":\"Sausages\",\"price\":39,\"description\":\"Lecker\"},{\"name\":\"Pommes\",\"price\":180,\"description\":\"Cool\"}]} -H "Content-type:application/json"

-- set/change the voted restaurant: returns success status only for votes before 11:00 and firstly registered votes<br/>
curl 10.10.150.156:8080/api/vote -u user2:user2 -X POST --data {\"restaurantId\":3} -H "Content-type:application/json"

Response sample:<br/>
{"status":"Success","body":"User user2 has successfully voted for the restaurant with id=Sir Karabas Barabas"}<br/>

curl 10.10.150.156:8080/api/vote -u user2:user2 -X POST --data {\"restaurantId\":2} -H "Content-type:application/json"

Response sample:<br/>
{"status":"Success","body":"User user2 has successfully changed the restaurant, new restaurant id=Exxxotica!"}

### Used frameworks

Spring boot - application core
Maven - build tool

### Learn More

- [Maven] (https://maven.apache.org/)
- [Spring Boot] (http://projects.spring.io/spring-boot/)

Implemented by Victor Bashurov (http://vbashur.blogspot.com)

