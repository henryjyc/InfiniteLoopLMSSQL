package com.st.lmssql.menus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.st.lmssql.models.tbl.Book;
import com.st.lmssql.models.tbl.BookCopies;
import com.st.lmssql.models.tbl.LibraryBranch;
import com.st.lmssql.service.LibrarianService;
import com.st.lmssql.utils.getInput;

public class LibrarianMenu {
	public static boolean LibrarianMain() {
		LibrarianService service = new LibrarianService();
		System.out.println("Hello librarian. Please choose one of the options below.");
		List<String> options = new ArrayList<String>();
		options.add("1) Enter the your library's branch.");
		options.add("2) Go back to the Main Menu.");
		options.forEach(System.out::println);
		int input = getInput.getValidOption(options.size());
		switch(input) {
			case 1:
				List<LibraryBranch> libraries = service.getAllBranches();
				if(libraries == null) {
					System.out.println("An error occurred when fetching data! Please contact the administrator if this problem persists!");
					return false;
				}
				else if(libraries.isEmpty()) {
					System.out.println("There are no library branches stored in the database. Please contact the administrator!");
					return false;
				}
				else {
					for(int i = 0; i < libraries.size(); i++) {
						System.out.println((i+1) + ") " + libraries.get(i).getBranchName());
					}
					int branch = getInput.getValidOption(libraries.size())-1;
					while(ManageLibrary(libraries.get(branch).getBranchId(), service));
				}
				return true;
			case 2:
				System.out.println("Returning to the Main Menu...");
				return false;
			default:
				return false;
		}
	}
	
	private static boolean ManageLibrary(int branchId, LibrarianService service) {
		LibraryBranch library = service.getBranch(branchId);
		if(library == null) {
			System.out.println("No such library in the database. Returning to Librarian Menu...");
			return false;
		}
		System.out.println("Managing " + library.getBranchName() + " Library.");
		System.out.println("What would you like to do? Please choose one of the options below.");
		List<String> options = new ArrayList<String>();
		options.add("1) Update library's information.");
		options.add("2) Update the number of copies of a book owned by the library.");
		options.add("3) Go back to the Librarian Menu.");
		options.forEach(System.out::println);
		
		int input = getInput.getValidOption(options.size());
		switch(input) {
			case 1:
				System.out.println("Updating library's information...");
				
				System.out.println("Please enter the library's new name or leave field blank to use old name.");
				String libName = getInput.getName();
				if(libName.isEmpty())
					libName = library.getBranchName();
				
				System.out.println("Please enter the library's new address or leave field blank to use old address.");
				String libAddr = getInput.getAddr();
				if(libAddr.isEmpty())
					libAddr = library.getBranchAddress();
				try{
					if(service.update(branchId, libName, libAddr))
						System.out.println("The library has been updated with the new information.");
					else
						System.out.println("Unable to update library! Please contact the administrator if problem persists.");
				} catch(SQLException e) {
					System.out.println("CRITICAL ERROR! PLEASE CONTACT THE ADMINISTRATOR ASAP!!!");
					return false;
				}
				System.out.println("Returning to the Branch Management Menu...");
				return true;
				
			case 2:
				List<Book> books = service.getAllBooks(branchId);
				if(books == null) {
					System.out.println("Failed to retrieve books from database. Returning to Branch Management Menu...");
					return false;
				}
				System.out.println("Which of the following books would you like to update?");
				for(int i = 0; i < books.size(); i++) {
					System.out.println((i+1) + ") " + books.get(i).getTitle());
				}
				int bookId = books.get(getInput.getValidOption(books.size())-1).getBookId();
				BookCopies bc = service.getBookCopies(branchId, bookId);
				if(bc.getNoOfCopies() < 0) {
					System.out.println("Unable to locate book in database. Returning to Branch Management Menu...");
					return false;
				}
				System.out.println("Existing number of copies: " + bc.getNoOfCopies());
				System.out.println("Please enter the new number of copies there are, or enter a blank field to leave as is.");
				int newCopies = getInput.getPosInt();
				if(newCopies > 0) {
					try {
						if(service.updateNumCopies(branchId, bookId, newCopies-bc.getNoOfCopies())) {
							System.out.println("Update successful!!");
						}
						else
							System.out.println("Update was unsuccessful. Please contact Administrator if problem persists.");
					} catch(SQLException e) {
						System.out.println("CRITICAL ERROR!!! PLEASE CONTACT ADMINISTRATOR ASAP!");
						return false;
					}
				}
				System.out.println("Returning to the Branch Management Menu...");
				return true;
				
			case 3:
				System.out.println("Returning to the Librarian Menu...");
				return false;
			default:
				System.out.println("Returning to the Librarian Menu...");
				return false;
		}
	}
}
