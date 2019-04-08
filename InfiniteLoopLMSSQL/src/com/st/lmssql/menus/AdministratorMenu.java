package com.st.lmssql.menus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.st.lmssql.service.AdministratorService;
import com.st.lmssql.utils.getInput;

public class AdministratorMenu {
	private final static String CRITICAL_ERROR = "CRITICAL ERROR! DATABASE INTEGRITY AT RISK! PLEASE CONTACT THE IT DEPARTMENT!";
	public static boolean AdministratorMain() {
		System.out.println("Hello administrator. Please choose one of the options below.");
		AdministratorService service = new AdministratorService();
		
		List<String> options = new ArrayList<String>();
		options.add("1) Add a new entry");
		options.add("2) Update an existing entry");
		options.add("3) Delete an existing entry");
		options.add("4) Override current due date");
		options.forEach(System.out::println);
		int action = getInput.getValidOption(options.size());
		if(action != 4) {
			
			System.out.println("Which data would you like to make changes to?");
			List<String> tables = new ArrayList<String>();
			tables.add("1) Books");
			tables.add("2) Authors");
			tables.add("3) Publishers");
			tables.add("4) Library Branches");
			tables.add("5) Borrowers");
			tables.forEach(System.out::println);
			int table = getInput.getValidOption(tables.size());
			switch(action) {
				case 1: //add a new entry
					Map<String, String> data = new HashMap<String, String>();
					switch(table) {
						case 1: //add a book
							System.out.println("Please enter the book's title.");
							data.put("title", getInput.getName());
							System.out.println("Please enter the book's author's Id.");
							data.put("authorId", ""+getInput.getPosInt());
							System.out.println("Please enter the book's publisher's Id.");
							data.put("publisherId", ""+getInput.getPosInt());
							try {
								if(service.add("book", data)) {
									System.out.println("The book has been added to the database!");
									return true;
								}
								else {
									System.out.println("Failed to add book to the database! Please check that both the author and publisher are in the database.");
									return true;
								}
							} catch(Exception e) {
									System.out.println(CRITICAL_ERROR);
									return false;
							}
						case 2: //add an author
							System.out.println("Please enter the author's name.");
							data.put("name", getInput.getName());
							try {
								if(service.add("author", data)) {
									System.out.println("The author has been added to the database!");
									return true;
								}
								else {
									System.out.println("Failed to add the record to the database! Please contact the administrator if this problem persists.");
									return true;
								}
							} catch (SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 3: //add publisher
							System.out.println("Please enter the publisher's name.");
							data.put("name", getInput.getName());
							System.out.println("Please enter the publisher's address.");
							data.put("address", getInput.getAddr());
							System.out.println("Please enter the publisher's phone number.");
							data.put("phone", ""+getInput.getPosInt());
							try {
								if(service.add("publisher", data)) {
									System.out.println("The new entry has been added to the database!");
									return true;
								}
								else {
									System.out.println("Failed to add the record to the database! Please contact the administrator if this problem persists.");
									return true;
								}
							} catch (SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 4: //add library branch
							System.out.println("Please enter the library's name.");
							data.put("name", getInput.getName());
							System.out.println("Please enter the library's address.");
							data.put("address", getInput.getAddr());
							try {
								if(service.add("library", data)) {
									System.out.println("The new entry has been added to the database!");
									return true;
								}
								else {
									System.out.println("Failed to add the record to the database! Please contact the administrator if this problem persists.");
									return true;
								}
							} catch (SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 5: //add borrower
							System.out.println("Please enter the borrower's name.");
							data.put("name", getInput.getName());
							System.out.println("Please enter the borrower's address.");
							data.put("address", getInput.getAddr());
							System.out.println("Please enter the borrower's phone number.");
							data.put("phone", ""+getInput.getPosInt());
							try {
								if(service.add("borrower", data)) {
									System.out.println("The new entry has been added to the database!");
									return true;
								}
								else {
									System.out.println("Failed to add the record to the database! Please contact the administrator if this problem persists.");
									return true;
								}
							} catch (SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
					}
				case 2: //update an existing entry
					switch(table) {
						case 1: //update book
							System.out.println("Please enter the book's id number, or leave field blank to return to the menu.");
							int bookId = getInput.getPosInt();
							if(bookId < 0) {
								System.out.println("Returning to the menu...");
								return true;
							}
							System.out.println("For each field, please enter the publisher's new information, or leave it blank to keep it as is.");
							System.out.print("The book's title:");
							String title = getInput.getName();
							System.out.print("The book's author's Id");
							String authorId = ""+getInput.getPosInt();
							if(authorId.equals("-1"))
								authorId = null;
							System.out.print("The book's publisher's Id");
							String publisherId = ""+getInput.getPosInt();
							if(publisherId.equals("-1"))
								publisherId = null;
							try {
								service.updateBook(bookId, title, authorId, publisherId);
								System.out.println("The book has been updated.");
								return true;
							} catch(SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 2: //update author
							System.out.println("Please enter the author's id number, or leave field blank to return to the menu.");
							int authId = getInput.getPosInt();
							if(authId < 0) {
								System.out.println("Returning to the menu...");
								return true;
							}
							System.out.println("For each field, please enter the publisher's new information, or leave it blank to keep it as is.");
							System.out.print("Name:");
							String authName = getInput.getName();
							try {
								service.updateAuthor(authId, authName);
								System.out.println("The author has been updated.");
								return true;
							} catch(SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 3: //update publisher
							System.out.println("Please enter the publisher's id number, or leave field blank to return to the menu.");
							int pubId = getInput.getPosInt();
							if(pubId < 0) {
								System.out.println("Returning to the menu...");
								return true;
							}
							System.out.println("For each field, please enter the publisher's new information, or leave it blank to keep it as is.");
							System.out.print("The publisher's name:");
							String pubName = getInput.getName();
							System.out.print("The publisher's address:");
							String pubAddr = getInput.getAddr();
							System.out.print("The publisher's phone number:");
							String pubPhone = ""+getInput.getValidPhone();
							if(pubPhone.equals("-1"))
								pubPhone = null;
							try {
								service.updatePublisher(pubId, pubName, pubAddr, pubPhone);
								System.out.println("The book has been updated.");
								return true;
							} catch(SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 4: //update library branch
							System.out.println("Please enter the library's branch id number, or leave field blank to return to the menu.");
							int branchId = getInput.getPosInt();
							if(branchId < 0) {
								System.out.println("Returning to the menu...");
								return true;
							}
							System.out.println("For each field, please enter the library's new information, or leave it blank to keep it as is.");
							System.out.print("The library's name:");
							String branchName = getInput.getName();
							System.out.print("The library's address:");
							String branchAddr = getInput.getAddr();
							try {
								service.updateLibraryBranch(branchId, branchName, branchAddr);
								System.out.println("The book has been updated.");
								return true;
							} catch(SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
						case 5: //update borrower
							System.out.println("Please enter the borrower's id number, or leave field blank to return to the menu.");
							int borId = getInput.getPosInt();
							if(borId < 0) {
								System.out.println("Returning to the menu...");
								return true;
							}
							System.out.println("For each field, please enter the borrower's new information, or leave it blank to keep it as is.");
							System.out.print("The borrower's name:");
							String borName = getInput.getName();
							System.out.print("The borrower's address:");
							String borAddr = getInput.getAddr();
							System.out.print("The borrower's phone number:");
							String borPhone = ""+getInput.getValidPhone();
							if(borPhone.equals("-1"))
								borPhone = null;
							try {
								service.updateBorrower(borId, borName, borAddr, borPhone);
								System.out.println("The book has been updated.");
								return true;
							} catch(SQLException e) {
								System.out.println(CRITICAL_ERROR);
								return false;
							}
					}
				case 3: //delete an entry
					int idNo;
					System.out.println("Please enter the id number of the item you would like to delete, or leave field blank to return to the menu.");
					idNo = getInput.getPosInt();
					if(idNo < 0) {
						System.out.println("Returning to the menu...");
						return true;
					}
					switch(table) {
						case 1: //delete book
							try {
								service.delete("book", idNo);
								System.out.println("The book has been deleted!");
								return true;
							} catch (SQLException e) {
								System.out.println("CRITICAL_ERROR");
							}
						case 2: //delete author
							try {
								service.delete("author", idNo);
								System.out.println("The author has been deleted!");
								return true;
							} catch (SQLException e) {
								System.out.println("CRITICAL_ERROR");
							}
						case 3: //delete publisher
							try {
								service.delete("publisher", idNo);
								System.out.println("The publisher has been deleted!");
								return true;
							} catch (SQLException e) {
								System.out.println("CRITICAL_ERROR");
							}
						case 4: //delete library branch
							try {
								service.delete("library", idNo);
								System.out.println("The library has been deleted!");
								return true;
							} catch (SQLException e) {
								System.out.println("CRITICAL_ERROR");
							}
						case 5: //delete borrower
							try {
								service.delete("borrower", idNo);
								System.out.println("The borrower has been deleted!");
								return true;
							} catch (SQLException e) {
								System.out.println("CRITICAL_ERROR");
							}
					}
					
			}
		}
		else { //override the due date.
			System.out.println("Please enter the book's Id");
			int bookId = getInput.getPosInt();
			System.out.println("Please enter the branch's Id");
			int branchId = getInput.getPosInt();
			System.out.println("Please enter the borrower's card number");
			int cardNo = getInput.getPosInt();
			System.out.println("How many days would you like to extend the due date by?");
			int days = getInput.getPosInt();
			if(bookId>0 && branchId > 0 && cardNo>0 && days>0) {
				try {
					service.changeDueDate(bookId, branchId, cardNo, days);
					System.out.println("Due date has been extended...");
					return true;
				} catch(SQLException e) {
					System.out.println("CRITICAL_ERROR");
					return false;
				}
			}
		}
		return false;
	}
}
