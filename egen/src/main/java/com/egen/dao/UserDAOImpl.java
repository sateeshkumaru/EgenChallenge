package com.egen.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.egen.model.UserModel;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class UserDAOImpl implements UserDAO<UserModel> {

	 public static final String collectionName = "users";
	 private final MongoCollection<Document> usersCollection;
	 private final Gson gson;
	 final static Logger logger = Logger.getLogger(UserDAOImpl.class);
	
	 public UserDAOImpl(MongoDatabase userManagementDatabase) {
		 usersCollection = userManagementDatabase.getCollection(collectionName);
		 logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + usersCollection);
		 gson = new Gson();
	    }
	
	@Override
	public List<UserModel> getAllObjects() {
		// TODO Auto-generated method stub
		List<UserModel> allUsersList = new ArrayList<UserModel>();
		 FindIterable<Document> find = usersCollection.find();
		 logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + "find: " + find);
		 MongoCursor<Document> allUsersCusor = find.iterator();
			try {
			    while(allUsersCusor.hasNext()) {
			    	Document doc = allUsersCusor.next();
			    	allUsersList.add(gson.fromJson(doc.toJson(), UserModel.class));   	
			    }
			 } finally {
				 allUsersCusor.close();
			 }
			return allUsersList;
		
	}

	@Override
	public UserModel getObject(UserModel userModel) {
		// TODO Auto-generated method stub
		logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + userModel);
		Document doc = usersCollection.find(eq("_id",userModel.get_id())).first();
		if(doc==null)
		{
			return null;
		}
		logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + " doc: " + doc );
		return gson.fromJson(doc.toJson(), UserModel.class);
	}

	@Override
	public UserModel saveObject(UserModel userModel) {
		// TODO Auto-generated method stub
		logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + userModel);
		Document doc = Document.parse(gson.toJson(userModel));
		logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + "doc: " + doc );
		usersCollection.insertOne(doc);	
		return userModel;
	}

	@Override
	public UserModel updateObject(UserModel userModel) {
		// TODO Auto-generated method stub
		Bson filter = new Document("_id",userModel.get_id());
		Document doc = Document.parse(gson.toJson(userModel));
		usersCollection.replaceOne(filter, doc);
		return userModel;
	}
}
