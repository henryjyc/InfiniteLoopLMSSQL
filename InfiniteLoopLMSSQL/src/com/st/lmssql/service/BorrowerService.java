package com.st.lmssql.service;

import java.sql.SQLException;
import java.util.List;

import com.st.lmssql.dao.borrowerDao;
import com.st.lmssql.models.tbl.*;

public class BorrowerService {
	borrowerDao dao;

	public BorrowerService() {
		dao = new borrowerDao();
	}

	// return a list of Library Branches
	// returns null of exception error
	// returns empty list if no data in table
	public List<LibraryBranch> getAllBranches() {
		try {
			return dao.getAllBranches();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Book> getAllBooks(int branchId) {
		try {
			return dao.getAllBooks(branchId);
		} catch (SQLException e) {
			return null;
		}
	}

	public List<Book> getLoanedBooks(int cardNo, int branchId) { // returns a list of books the borrower borrowed from
																	// the library
		try {
			return dao.getLoanedBooks(cardNo, branchId);
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean checkOutBook(int cardNo, int bookId, int branchId) throws SQLException {
		try {
			dao.checkOutBook(cardNo, bookId, branchId);
			dao.updateNumCopies(branchId, bookId, -1);
			dao.commit();
			return true;
		} catch (SQLException e) {
			dao.rollBack();
			return false;
		}
	}

	// simply removes the record with given cardNo and bookId from tbl_book_loans
	public boolean returnBook(int cardNo, int bookId, int branchId) throws SQLException {
		try {
			dao.returnBook(cardNo, bookId, branchId);
			dao.updateNumCopies(branchId, bookId, 1);
			dao.commit();
			return true;
		} catch (SQLException e) {
			dao.rollBack();
			return false;
		}
	}

	public boolean borCardNoExists(int cardNo) {
		try {
			if (dao.getBorrower(cardNo) == null)
				return false;
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public String getName(int cardNo) { // returns name associated with input cardNo
		try {
			Borrower borrower = dao.getBorrower(cardNo);
			if (borrower == null)
				return null;
			return borrower.getName();
		} catch (SQLException e) {
			return null;
		}
	}
}
