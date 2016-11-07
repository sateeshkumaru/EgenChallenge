# EgenChallenge
Creating a restful web service for User Management application using Spark Java and Mongodb.

-> Developed below 3 services using Spark Java and MongoDB
1)createUser -
  . Takes input as JSON.
  . Creates the user if he is not already available in the data store.
  . if user already exists, send back a 404 response saying user already exists.

2) getAllUsers -
  . Gives the list of all users that are in the data store.
  
3) updateUser -
    . Takes input as JSON.
    . Finds the user from the data store.
    . if found, updates the required fields. If not found, send back a 404 response saying user not found.

-- Implemented JUnit test cases to test the services.

Steps:-
-------
1) Run Main.java to launch the application.
2) Use a rest client(Postman) to send requests(POST, GET, PUT)           
                 OR
 Test your web services using UserManagementControllerTest.java (Junit test cases).            
                 
                 
                 
                 
      
                  
