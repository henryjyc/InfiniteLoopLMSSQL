package com.st.lmssql.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.st.lmssql.models.tbl.Book;
import com.st.lmssql.models.tbl.BookCopies;
import com.st.lmssql.models.tbl.LibraryBranch;

//parent class of all our dao implementations for each user.
//should implement methods that all users have access to
//methods that are unique to each user should be implemented in the subclass
public class sqlDao implements daoInterface{
	protected Connection conn;
	protected String sql;
	protected PreparedStatement prep;
	protected ResultSet results;
	
	public sqlDao() {
		conn = connectSQL.getConn();
	}
	
	//looks for a library branch with the following branch id from tbl_library_branch
	//returns the librarybranch object or null if not found
	@Override
	public LibraryBranch getBranch(int branchId) throws SQLException{
		sql = "SELECT * FROM library.tbl_library_branch WHERE branchId=?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1, branchId);
		results = prep.executeQuery();
		if(results.next())
			return new LibraryBranch(branchId, results.getString("branchName"), results.getString("branchAddress"));
		else
			return null;
	}
	
	//returns a list of all librarybranch objects in tbl_library_branch
	//returns empty list if table is empty
	@Override
	public List<LibraryBranch> getAllBranches() throws SQLException{
		List<LibraryBranch> libraries = new ArrayList<LibraryBranch>();
		conn = connectSQL.getConn();
		sql = "SELECT * FROM library.tbl_library_branch";
		prep = conn.prepareStatement(sql);
		results = prep.executeQuery();
		while (results.next()) {
			libraries.add(new LibraryBranch(results.getInt("branchId"), results.getString("branchName"), results.getString("branchAddress")));
		}
		return libraries;
	}
	
	@Override
	public List<Book> getAllBooks(int branchId) throws SQLException{
		List<Book> books = new ArrayList<Book>();
		sql = "SELECT * FROM library.tbl_book_copies JOIN library.tbl_book ON library.tbl_book_copies.bookId = library.tbl_book.bookId WHERE branchId=?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1,  branchId);
		results = prep.executeQuery();
		while (results.next()) {
			books.add(new Book(results.getInt("bookId"), results.getString("title"), results.getInt("authId"), results.getInt("pubId")));
		}
		return books;
	}
	
	/*adds the addCopies to current number of copies;
	*possible that addCopies could be negative in order to decrease the number of copies.
	*menu should never allow user to input a negative number for new number of copies
	*service should calculate the addCopies amount by subtracting current # of copies from new # of copies
	*hence, addCopies should never be a negative number greater than current # of copies
	*/
	@Override
	public void updateNumCopies(int branchId, int bookId, int addCopies) throws SQLException{
		BookCopies bc = this.getBookCopies(branchId, bookId);
		sql = "UPDATE library.tbl_book_copies SET noOfCopies=? WHERE branchId=? AND bookId=?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1, bc.getNoOfCopies()+addCopies);
		prep.setInt(2,  branchId);
		prep.setInt(3, bookId);
		prep.executeUpdate();
	}
	
	@Override
	public BookCopies getBookCopies(int branchId, int bookId) throws SQLException{
		sql = "SELECT * FROM library.tbl_book_copies WHERE branchId=? AND bookId=?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1,  branchId);
		prep.setInt(2,  bookId);
		results = prep.executeQuery();
		results.next();
		BookCopies bc = new BookCopies(bookId, branchId, results.getInt("noOfCopies"));
		return bc;
	}
	
	@Override
	public boolean hasAuthor(int authId) throws SQLException {
		List<Integer> colNameIndices = new ArrayList<Integer>();
		colNameIndices.add(0);
		sql = sqlTools.generateQuery(1, 1, colNameIndices);
		prep = conn.prepareStatement(sql);
		prep.setInt(1, authId);
		results = prep.executeQuery();
		if(results.next())
			return true;
		return false;
	}
	
	@Override
	public boolean hasAuthor(String name) throws SQLException {
		List<Integer> cols = new ArrayList<Integer>();
		cols.add(1);
		sql = sqlTools.generateQuery(1, 1, cols);
		prep = conn.prepareStatement(sql);
		prep.setString(1, name);
		results = prep.executeQuery();
		if(results.next())
			return true;
		return false;
	}
	
	@Override
	public boolean hasBook(int bookId) throws SQLException {
		List<Integer> colNameIndices = new ArrayList<Integer>();
		colNameIndices.add(0);
		sql = sqlTools.generateQuery(1, 2, colNameIndices);
		prep = conn.prepareStatement(sql);
		prep.setInt(1, bookId);
		results = prep.executeQuery();
		if(results.next())
			return true;
		return false;
	}
	
	@Override
	public boolean hasBook(String title) throws SQLException {
		List<Integer> colNameIndices = new ArrayList<Integer>();
		colNameIndices.add(0);
		sql = sqlTools.generateQuery(1, 2, colNameIndices);
		prep = conn.prepareStatement(sql);
		prep.setString(1, title);
		results = prep.executeQuery();
		if(results.next())
			return true;
		return false;
	}
	
	@Override
	public boolean hasPublisher(int pubId) throws SQLException {
		List<Integer> colNameIndices = new ArrayList<Integer>();
		colNameIndices.add(0);
		sql = sqlTools.generateQuery(1, 7, colNameIndices);
		prep = conn.prepareStatement(sql);
		prep.setInt(1, pubId);
		results = prep.executeQuery();
		if(results.next())
			return true;
		return false;
	}
	
	@Override
	public void commit() throws SQLException {
		conn.commit();
	}

	@Override
	public void rollBack() throws SQLException {
		conn.rollback();
	}
}
