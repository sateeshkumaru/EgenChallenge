package com.egen.dao;

import java.util.List;

public interface UserDAO<T> {

	public List<T> getAllObjects();

	public T getObject(T object);

	public T saveObject(T object);
	
	public T updateObject(T object);
}
