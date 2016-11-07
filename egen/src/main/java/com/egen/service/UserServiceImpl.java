package com.egen.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.egen.dao.UserDAO;
import com.egen.dao.UserDAOImpl;
import com.egen.exception.UserAlreadyExistsException;
import com.egen.exception.UserNotFoundException;
import com.egen.model.UserModel;
import com.mongodb.client.MongoDatabase;

public class UserServiceImpl implements UserService<UserModel>
{
	
	final static Logger logger = Logger.getLogger(UserServiceImpl.class);
	public final UserDAO<UserModel> userDao;

	public UserServiceImpl(MongoDatabase userManagementDatabase) {
		// TODO Auto-generated constructor stub
		userDao = new UserDAOImpl(userManagementDatabase);
	}
	
	@Override
	public UserModel createUser(UserModel userModel) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName() + userModel);
		if (userDao.getObject(userModel)==null)
		{
			
			return userDao.saveObject(userModel);
		}
		else {
			throw new UserAlreadyExistsException("User already exists..!!");
		}
	}

	@Override
	public List<UserModel> getAllUsers() {
		// TODO Auto-generated method stub
		logger.debug(Thread.currentThread().getStackTrace()[2].getMethodName());
		return userDao.getAllObjects();
		
	}

	@Override
	public UserModel updateUser(UserModel userModel) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if (userDao.getObject(userModel)==null)
		{
			throw new UserNotFoundException("User Not Found..!!");
		}
		else
		{
			return userDao.updateObject(userModel);
		}
	}
	
}