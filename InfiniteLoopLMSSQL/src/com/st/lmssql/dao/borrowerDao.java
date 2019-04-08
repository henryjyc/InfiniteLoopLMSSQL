package com.st.lmssql.dao;

import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.st.lmssql.models.tbl.Book;
import com.st.lmssql.models.tbl.Borrower;
import com.st.lmssql.models.tbl.LibraryBranch;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class borrowerDao extends sqlDao{

	
	public List<Book> getLoanedBooks(int cardNo, int branchId) throws SQLException{
		conn = connectSQL.getConn();
		List<Book> books = new ArrayList<Book>();
		sql = "SELECT * FROM library.tbl_book_loans JOIN library.tbl_book ON library.tbl_book_loans.bookId = library.tbl_book.bookId WHERE cardNo=? AND branchId =?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1,  cardNo);
		prep.setInt(2, branchId);
		results = prep.executeQuery();
		while (results.next()) {
			books.add(new Book(results.getInt("bookId"), results.getString("title"), results.getInt("authId"), results.getInt("pubId")));
		}
		return books;
	}
	
	//does not check if the book has already been checked out
	public void checkOutBook(int cardNo, int bookId, int branchId) throws SQLException {
		conn = connectSQL.getConn();
		LocalDate date = LocalDate.now();
		LocalDate dueDate = date.plusWeeks(1);
		sql = "INSERT INTO library.tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, ?, ?)";
		prep = conn.prepareStatement(sql);
		prep.setInt(1, bookId);
		prep.setInt(2, branchId);
		prep.setInt(3, cardNo);
		prep.setDate(4, Date.valueOf(date));
		prep.setDate(5,  Date.valueOf(dueDate));
		prep.executeUpdate();
	}
	
	public void returnBook(int cardNo, int bookId, int branchId) throws SQLException {
//		DELETE FROM `library`.`tbl_book_loans` WHERE (`bookId` = '5') and (`branchId` = '1') and (`cardNo` = '3');
		conn = connectSQL.getConn();
		sql = "DELETE FROM library.tbl_book_loans WHERE bookId=? AND branchId=? AND cardNo=?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1, bookId);
		prep.setInt(2, bookId);
		prep.setInt(3,  cardNo);
		prep.executeUpdate();
	}
	
	public Borrower getBorrower(int cardNo) throws SQLException{
		conn = connectSQL.getConn();
		sql = "SELECT * FROM library.tbl_borrower WHERE cardNo=?";
		prep = conn.prepareStatement(sql);
		prep.setInt(1, cardNo);
		results = prep.executeQuery();
		if(results.next()) {
			return new Borrower(cardNo, results.getString("name"), results.getString("address"), results.getString("phone"));
		}
		else
			return null;
	}
}
