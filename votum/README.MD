-- Get the list of restaurants with menu
curl 10.10.150.184:8080/api/list -u admin:admin -X GET

[{"restaurantId":1,"name":"Old good times","address":"Aschheim, Einsteinring 29","description":"Traditional german food for average price","email":"traditional@mail.de","meals":[{"mealId":2,"name":"Bayern sausages","price":3.0,"description":"Das ist lecker!!"},{"mealId":1,"name":"Pommes","price":1.0,"description":"Traditional german meal"}]},{"restaurantId":2,"name":"Exxxotica!","address":"Munich, Sohnenstrasse 15","description":"Fried insects, banana beer, sweet octopus and many more","email":"exxxotica@mail.de","meals":[{"mealId":4,"name":"kimchi","price":2.5,"description":"korean spicy cabbage"},{"mealId":3,"name":"bugs","price":7.15,"description":"deep fried"}]},{"restaurantId":3,"name":"Sir Karabas Barabas","address":"Augsburg, Rosenheimerplatz 2","description":"We bring our Gourmet Restaurant to your doorstep","email":"karabas@mail.de","meals":[{"mealId":5,"name":"carbonara de la penna","price":89.0,"description":"Made by Master Paolo from Florenzia with love"}]}]

-- Get to know about the restaurants and user votes
curl 10.10.150.184:8080/api/collect -u admin:admin -X GET


-- Find out the top voted restaurant
curl 10.10.150.184:8080/api/top -u admin:admin -X GET

