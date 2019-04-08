package com.st.lmssql.service;

import java.sql.SQLException;
import java.util.List;

import com.st.lmssql.dao.daoInterface;
import com.st.lmssql.dao.librarianDao;
import com.st.lmssql.models.*;
import com.st.lmssql.models.tbl.Book;
import com.st.lmssql.models.tbl.BookCopies;
import com.st.lmssql.models.tbl.LibraryBranch;

public class LibrarianService {
	librarianDao dao;
	public LibrarianService() {
		dao = new librarianDao();
	}
	
	//return a list of Library Branches
	//returns null of exception error
	//returns empty list if no data in table
	public List<LibraryBranch> getAllBranches(){ 
		try {
			return dao.getAllBranches();
		} catch (SQLException e) {
			System.out.println("To IT: SQLException error in getAllBranches()");
			return null;
		}
	} 
	
	//returns the LibraryBranch object of given branchId
	//returns null if no LibraryBranch with given id.
	public LibraryBranch getBranch(int branchId) {
		LibraryBranch library;
		try {
			library = dao.getBranch(branchId);
		} catch (SQLException e) {
			library = null;
		}
		return library;
	}
	
	/*
	 * If the update was successful, returns true;
	 * If the update wasn't successful, but rollback was successful, returns false;
	 * If the method throws an SQLException, rollback was unsuccessful. CRITICAL ERROR
	 */
	public boolean update(int branchId, String branchName, String branchAddr) throws SQLException{
		LibraryBranch library = new LibraryBranch(branchId, branchName, branchAddr);
		try{
			dao.updateLibrary(branchId, library);
			dao.commit();
			return true;
		} catch (SQLException e) {
			dao.rollBack();
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * If the update was successful, returns true;
	 * If the update wasn't successful, but rollback was successful, returns false;
	 * If the method throws an SQLException, rollback was unsuccessful. CRITICAL ERROR
	 */
	public boolean updateNumCopies(int branchId, int bookId, int addCopies) throws SQLException{
		try {
			dao.updateNumCopies(branchId, bookId, addCopies);
			dao.commit();
			return true;
		} catch(SQLException e) {
			dao.rollBack();
			e.printStackTrace();
			return false;
		}
	}
	
	//return # of copies from branch or -1 if record not found
	public BookCopies getBookCopies(int branchId, int bookId) {
		try {
			return dao.getBookCopies(branchId, bookId);
		}catch (SQLException e) {
			return null;
		}
	}
	
	//returns a list containing all books owned by the library
	//returns null of sqlexception
	public List<Book> getAllBooks(int branchId){ 
		try {
			return dao.getAllBooks(branchId);
		} catch(SQLException e) {
			return null;
		}
	}
}
