package com.st.lmssql.dao;


import java.sql.SQLException;
import java.util.List;

import com.st.lmssql.models.tbl.Book;
import com.st.lmssql.models.tbl.BookCopies;
import com.st.lmssql.models.tbl.LibraryBranch;

//basic methods that all dao implementations should have
public interface daoInterface {
	public LibraryBranch getBranch(int branchId) throws SQLException;
	public List<LibraryBranch> getAllBranches() throws SQLException;
	public List<Book> getAllBooks(int branchId) throws SQLException;
	public void updateNumCopies(int branchId, int bookId, int addCopies) throws SQLException;
	public BookCopies getBookCopies(int branchId, int bookId) throws SQLException;
	public boolean hasBook(int bookId) throws SQLException;
	public boolean hasBook(String title) throws SQLException;
	public boolean hasAuthor(int authId) throws SQLException;
	public boolean hasAuthor(String name) throws SQLException;
	public boolean hasPublisher(int pubId) throws SQLException;
	public void commit() throws SQLException;
	public void rollBack() throws SQLException;
}
