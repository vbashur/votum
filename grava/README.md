## Grava Hal - Bikini Bottom Edition

Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit, his Grava Hal. In each of the six round pits are put six stones when the game starts.

Grava Hal Rules
Game Play
The player who begins with the first move picks up all the stones in anyone of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own Grava Hal. No stones are put in the opponents' Grava Hal. If the player's last stone lands in his own Grava Hal, he gets another turn. This can be repeated several times before it's the other player's turn.

Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other players' pit) and puts them in his own Grava Hal.

The Game Ends
The game is over as soon as one of the sides run out of stones. The player who still has stones in his pits keeps them and puts them in his/hers Grava Hal. Winner of the game is the player who has the most stones in his Grava Hal

### Launching the application

To launch the app from maven go to the application root directory (directory with pom.xml file) and run: mvn -DskipTests clean install exec:java -DmainClass="com.vbashur.grava.GhalApplication"
Then go to http://localhost:8080 in your favorite browser, you can play with your partner if your computers are in the same network, the following address should be used by your partner http://<YOUR_IP_ADDRESS>:8080

To build the package run: mvn -DskipTests clean package
To run unit tests: mvn clean test

### Application architecture

Information about two gameboards and corresponding players is stored in the spring application context.
Player's gameboards exchange the information about their state using spring application events.
UI components change their state in accordance with gameboards using Vaadin broadcast events.

### Used frameworks

Spring boot - application core
Vaadin - user interface
Maven - build tool

### Learn More

- [Maven] (https://maven.apache.org/)
- [Spring Boot] (http://projects.spring.io/spring-boot/)
- [Spring Events] (https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2)
- [Vaadin] (https://vaadin.com/learn)

Implemented by Victor Bashurov (http://vbashur.blogspot.com)