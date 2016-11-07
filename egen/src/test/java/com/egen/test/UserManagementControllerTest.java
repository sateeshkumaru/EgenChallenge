package com.egen.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.egen.main.Main;
import com.google.gson.Gson;

import spark.Spark;
import spark.utils.IOUtils;

public class UserManagementControllerTest {

	@BeforeClass
	public static void beforeClass() throws IOException {
		Main.main(null);
	}
	
	@AfterClass
	public static void afterClass() {
		Spark.stop();
	}
	
	@Test
	public void createNewUserTest() {
		String input = "{\"_id\":\"2016\",\"firstName\":\"undra\",\"lastName\":\"javarapu\",\"email\":\"undra.javarapu@gmail.com\",\"address\":{\"street\":\"417 s,warrensburg,Apt#C\",\"city\":\"warrens\",\"zip\":\"64093\",\"state\":\"MO\",\"country\":\"US\"},\"dateCreated\":\"2016-11-06\",\"company\":{\"name\":\"University of Central Missouri\",\"website\":\"http://ucmo.com\"},\"profilePic\":\"http://lorempixel.com/640/480/people\"}";
		TestResponse res = request("POST", "http://localhost:4567/createUser", input);
		Map<String, String> output = res.json();
		assertEquals(200, res.status);
		assertEquals("undra", output.get("firstName"));
		assertEquals("undra.javarapu@gmail.com", output.get("email"));
	}

	@Test
	public void userAlreadyExitsExceptionTest() {
		String input = "{\"_id\":\"2000\",\"firstName\":\"undra\",\"lastName\":\"javarapu\",\"email\":\"undra.javarapu@gmail.com\",\"address\":{\"street\":\"417 s,warrensburg,Apt#C\",\"city\":\"warrens\",\"zip\":\"64093\",\"state\":\"MO\",\"country\":\"US\"},\"dateCreated\":\"2016-11-06\",\"company\":{\"name\":\"University of Central Missouri\",\"website\":\"http://ucmo.com\"},\"profilePic\":\"http://lorempixel.com/640/480/people\"}";
		TestResponse res = request("POST", "http://localhost:4567/createUser", input);
		assertEquals(404, res.status);
		assertEquals("\"User already exists..!!\"", res.body);
		
	}

	@Test
	public void updateUserTest() {
		String input = "{\"_id\":\"2001\",\"firstName\":\"undra1\",\"lastName\":\"javarapu1\",\"email\":\"undra.javarapu@gmail.com\",\"address\":{\"street\":\"417 s,warrensburg,Apt#C\",\"city\":\"warrens\",\"zip\":\"64093\",\"state\":\"MO\",\"country\":\"US\"},\"dateCreated\":\"2016-11-06\",\"company\":{\"name\":\"University of Central Missouri\",\"website\":\"http://ucmo.com\"},\"profilePic\":\"http://lorempixel.com/640/480/people\"}";
		TestResponse res = request("PUT", "http://localhost:4567/updateUser", input);
		Map<String, String> output = res.json();
		assertEquals(200, res.status);
		assertEquals("undra1", output.get("firstName"));
		assertEquals("javarapu1", output.get("lastName"));

	}
	@Test
	public void userNotFoundExceptionTest() {
		String input = "{\"_id\":\"2007\",\"firstName\":\"undra\",\"lastName\":\"javarapu\",\"email\":\"undra.javarapu@gmail.com\",\"address\":{\"street\":\"417 s,warrensburg,Apt#C\",\"city\":\"warrens\",\"zip\":\"64093\",\"state\":\"MO\",\"country\":\"US\"},\"dateCreated\":\"2016-11-06\",\"company\":{\"name\":\"University of Central Missouri\",\"website\":\"http://ucmo.com\"},\"profilePic\":\"http://lorempixel.com/640/480/people\"}";
		TestResponse res = request("PUT", "http://localhost:4567/updateUser", input);
		System.out.println(res.body);
		assertEquals(404, res.status);
		assertEquals("\"User Not Found..!!\"", res.body);
	}
	
	@Test
	public void getAllUsersTest()
	{
		TestResponse res = request("GET", "http://localhost:4567/getAllUsers", null);
		List<String> output = res.list();
		assertEquals(4, output.size());
	}
	
	private TestResponse request(String method, String urlPath, String input) {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty( "Content-Type", "application/json" );
			if(method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))
			{
			  connection.setDoOutput(true);
			  OutputStream os = connection.getOutputStream();
			  os.write(input.getBytes());
			  os.flush();
			}
            InputStream inputStream = null;
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

			String body = IOUtils.toString(inputStream);
			
			return new TestResponse(connection.getResponseCode(), body);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	private static class TestResponse {

		public final String body;
		public final int status;

		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

		public Map<String,String> json() {
			return new Gson().fromJson(body, HashMap.class);
		}
		
		public List<String> list()
		{
			return new Gson().fromJson(body, ArrayList.class);
		}
	}
}
