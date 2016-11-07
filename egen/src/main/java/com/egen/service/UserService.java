package com.egen.service;

import java.util.List;

import com.egen.exception.UserAlreadyExistsException;
import com.egen.exception.UserNotFoundException;

public interface UserService<T> {
	
	public T createUser(T t) throws UserAlreadyExistsException;
	public List<T> getAllUsers();
	public T updateUser(T t) throws UserNotFoundException;
	
}
