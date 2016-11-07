package com.egen.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.egen.exception.UserAlreadyExistsException;
import com.egen.exception.UserNotFoundException;
import com.egen.model.UserModel;
import com.egen.service.UserService;
import com.egen.service.UserServiceImpl;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import static com.egen.utils.JsonUtil.*;

public class UserManagementController {
	
	 private final UserService<UserModel> userService;
	 private final Gson gson;
	 final static Logger logger = Logger.getLogger(UserManagementController.class);
	 
	 // Constructor - creating a DB, creating instance for user service.
	 public UserManagementController(String mongoURIString) throws IOException {
	        final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
	        final MongoDatabase userManagementDatabase = mongoClient.getDatabase("userManagementDB");
	        logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + userManagementDatabase);
	        gson = new Gson();
	        userService = new UserServiceImpl(userManagementDatabase);
	        initializeRoutes();
	 }

	// Routing to the service based on the given request
	private void initializeRoutes() throws IOException {
		
		// Handle to get all users request.
		Spark.get("/getAllUsers", new Route() {
		
			public Object handle(Request request, Response response) throws Exception {
				// TODO Auto-generated method stub
				logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + " calling getAllUsers");
				List<UserModel> allUsersList = userService.getAllUsers();
				return allUsersList;
			}
		}, json());
		
		// to handle create a user request.
		Spark.post("/createUser", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				// TODO Auto-generated method stub
				logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + " calling createUser");
				UserModel usermodel = gson.fromJson(request.body(), UserModel.class);
				logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + usermodel);
				return userService.createUser(usermodel);
				 
			}
		},json());
		
		// to handle update request.
		Spark.put("/updateUser", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				// TODO Auto-generated method stub
					logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + " calling updateUser");
					UserModel userModel = userService.updateUser(gson.fromJson(request.body(), UserModel.class));
					return userModel;
				
			}
		}, json());
		
		// To handle user not found exception.
		Spark.exception(UserNotFoundException.class, new ExceptionHandler() {
			
			@Override
			public void handle(Exception e, Request request, Response response) {
				// TODO Auto-generated method stub
				
				response.status(404);
				response.type("application/json");
				response.body(toJson(e.getMessage()));
			}
		});
		
		// To handle user already exists exception.
		Spark.exception(UserAlreadyExistsException.class, new ExceptionHandler() {
			
			@Override
			public void handle(Exception e, Request request, Response response) {
				// TODO Auto-generated method stub
				
				response.status(404);
				response.type("application/json");
				response.body(toJson(e.getMessage()));
			}
		});
	}
}
