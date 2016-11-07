package com.egen.main;

import java.io.IOException;

import com.egen.controller.UserManagementController;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args == null || args.length == 0) {
			new UserManagementController("mongodb://localhost");
		} else {
			new UserManagementController(args[0]);
		}
	}
}
