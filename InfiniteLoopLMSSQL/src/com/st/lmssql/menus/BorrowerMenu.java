package com.st.lmssql.menus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.st.lmssql.models.tbl.Book;
import com.st.lmssql.models.tbl.LibraryBranch;
import com.st.lmssql.service.BorrowerService;
import com.st.lmssql.utils.getInput;

public class BorrowerMenu {
	private static BorrowerService service = new BorrowerService();
	public static boolean BorrowerMain() {
		System.out.println("Hello Borrower. Please input your card number or enter a blank field to return to the Main Menu.");
		int cardNo = getInput.getPosInt();
		if(cardNo == -1) {
			System.out.println("Returning to Main Menu...");
			return false;
		}
		
		else if(service.borCardNoExists(cardNo)) {
			while(LoggedIn(cardNo)) {}
			return false;
		}
		else {
			System.out.println("That is not a registered number!");
			return true;
		}
	}
			
			
		public static boolean LoggedIn(int cardNo) {
			
			List<String> options = new ArrayList<String>();
			options.add("1) Check out a book.");
			options.add("2) Return a book.");
			options.add("3) Go back to Main Menu.");
			System.out.println("Hello, " + service.getName(cardNo) + ". Please choose one from the options below.");
			options.forEach(System.out::println);
			int input = getInput.getValidOption(options.size());
			switch(input) {
				case 1:
					CheckOutMenu(cardNo, service);
					return true;
				case 2:
					ReturnMenu(cardNo, service);
					return true;
				case 3:
					System.out.println("Returning to the Main Menu...");
					return false;
				default:
					return false;
			}
		}
	
	
	private static void CheckOutMenu(int cardNo, BorrowerService service) {
		
		List<LibraryBranch> libraries = service.getAllBranches();
		System.out.println("Which library branch would you like to check out the book from?");
		for(int i = 0; i < libraries.size(); i++) {
			System.out.println((i+1) + ") " + libraries.get(i).getBranchName());
		}
		int branchId = libraries.get(getInput.getValidOption(libraries.size())-1).getBranchId();
		List<Book> books = service.getAllBooks(branchId);
		System.out.println("Which of the following books would you like to borrow?");
		for(int i = 0; i < books.size(); i++) {
			System.out.println((i+1) + ") " + books.get(i).getTitle());
		}
		int bookId = books.get(getInput.getValidOption(books.size())-1).getBookId();
		try {
			if(service.checkOutBook(cardNo, bookId, branchId)) {
				System.out.println("Book is now checked out.");
			}
			else
				System.out.println("Unable to check out book. Please contact the librarian for assistance.");
		}
		catch (SQLException e){
			System.out.println("CRITICAL ERROR! PLEASE CONTACT ADMINISTRATOR ASAP");
			e.printStackTrace();
		}
	}
	
	private static void ReturnMenu(int cardNo, BorrowerService service) {
		List<LibraryBranch> libraries = service.getAllBranches();
		System.out.println("Which library branch would you like to return the book to?");
		for(int i = 0; i < libraries.size(); i++) {
			System.out.println((i+1) + ") " + libraries.get(i).getBranchName());
		}
		int branchId = libraries.get(getInput.getValidOption(libraries.size())-1).getBranchId();
		List<Book> books = service.getLoanedBooks(cardNo, branchId);
		System.out.println("Which of the following books would you like to return?");
		for(int i = 0; i < books.size(); i++) {
			System.out.println((i+1) + ") " + books.get(i).getTitle());
		}
		int bookId = books.get(getInput.getValidOption(books.size())-1).getBookId();
		try {
			service.returnBook(cardNo, bookId, branchId);
		} catch(SQLException e) {
			System.out.println("CRITICAL ERROR! PLEASE CONTACT ADMINISTRATOR ASAP");
			e.printStackTrace();
		}
	}
}
