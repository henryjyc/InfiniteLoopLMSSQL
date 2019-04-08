package com.st.lmssql.menus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import com.st.lmssql.dao.connectSQL;
import com.st.lmssql.utils.*;

public class Menu {
	private static Scanner scan = new Scanner(System.in);

	/*
	 * Only public method of the Menu class. First checks to see if it can connect
	 * to the SQL database. If connection is successful, loops Main Menu until it
	 * returns false via "EXIT" If unable to connect, the run() method terminates
	 * and returns to the main.
	 */
	public static void run() {
		if (connectSQL.connect()) {
			while (MainMenu())
				;
			getInput.close();
		} else {
			System.out.println("Unable to connect to database! Please contact the administrator!");
		}
	}

	/*
	 * method to run the Main Menu. returns true unless user picks the "EXIT" option
	 * in the main menu
	 */
	private static boolean MainMenu() {
		System.out.println("Welcome to the GCIT Library Management System.");
		System.out.println("Which of the following are you?");
		List<String> options = new ArrayList<String>();
		options.add("1) Librarian");
		options.add("2) Administrator");
		options.add("3) Borrower");
		options.add("4) EXIT");
		options.forEach(System.out::println);
		int input = getInput.getValidOption(options.size());
		switch (input) {
		case 1:
			while (LibrarianMenu.LibrarianMain())
				;
			return true;
		case 2:
			while (AdministratorMenu.AdministratorMain())
				;
			return true;
		case 3:
			while (BorrowerMenu.BorrowerMain())
				;
			return true;
		case 4:
			scan.close();
			System.out.println("Closing the app...");
			return false;
		default:
			return false;
		}
	}
}
