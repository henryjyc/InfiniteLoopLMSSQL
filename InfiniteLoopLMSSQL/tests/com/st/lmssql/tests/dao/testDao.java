package com.st.lmssql.tests.dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.st.lmssql.dao.*;

public class testDao {

	@Before
	public void setUpConnection() {
		connectSQL.connect();
	}

	@Test
	public void testGenerateSELECT() {
		String expected = "SELECT * FROM library.tbl_book WHERE pubId=? AND bookId=? ";
		List<Integer> cols = new ArrayList<Integer>();
		cols.add(3);
		cols.add(0);
		String output = sqlTools.generateQuery(1, 2, cols);
		assertEquals(expected, output);
	}

	@Test
	public void testGenerateINSERT() {
		String expected = "INSERT INTO library.tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, ?, ?)";
		String output = sqlTools.generateQuery(2, 4, null);
		assertEquals(expected, output);
	}

	@Test
	public void testGenerateDELETE() {
		String expected = "DELETE FROM library.tbl_book WHERE bookId=? ";
		List<Integer> indexOfId = new ArrayList<Integer>();
		indexOfId.add(0);
		String output = sqlTools.generateQuery(4, 2, indexOfId);
		assertEquals(expected, output);
	}

	@Test
	public void testGenerateUPDATE() {
		String expected = "UPDATE library.tbl_borrower SET name=? WHERE cardNo=? ";
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(1);
		indices.add(0);
		String output = sqlTools.generateQuery(3, 5, indices);
		assertEquals(expected, output);
	}

	@Test
	public void testHasAuthorByID() {
		sqlDao dao = new sqlDao();
		try {
			assertTrue(dao.hasAuthor(3));
		} catch (SQLException e) {
			assert (false);
		}
	}

	@Test
	public void testHasAuthorByName() throws SQLException {
		sqlDao dao = new sqlDao();
		assertTrue(dao.hasAuthor("JK Rowlin"));
	}

	@Test
	public void testHasAuthorByName2() throws SQLException {
		sqlDao dao = new sqlDao();
		assertFalse(dao.hasAuthor("Smoothstack"));
	}

	@Test
	public void testHasBook() {
		sqlDao dao = new sqlDao();
		try {
			assertFalse(dao.hasBook(11));
		} catch (SQLException e) {
			assert (false);
		}
	}
}
