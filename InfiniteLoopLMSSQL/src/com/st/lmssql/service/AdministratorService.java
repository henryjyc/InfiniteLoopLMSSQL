package com.st.lmssql.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.st.lmssql.dao.administratorDao;

public class AdministratorService {
	administratorDao dao;
	Map<String, Integer> converter = new HashMap<String, Integer>(); //contains the key to value mappings required to use the dao's queryGenerator (refer to sqlTools)
	
	public AdministratorService() { //creates instance of dao and fills the converter with mappings from table name inputs to value
		dao = new administratorDao();
		converter.put("author", 1);
		converter.put("auth", 1);
		converter.put("book", 2);
		converter.put("bk", 2);
		converter.put("patron", 5);
		converter.put("customer", 5);
		converter.put("borrower", 5);
		converter.put("bor", 5);
		converter.put("librarybranch", 6);
		converter.put("libranch", 6);
		converter.put("libbranch", 6);
		converter.put("library branch", 6);
		converter.put("library_branch", 6);
		converter.put("lb", 6);
		converter.put("library", 6);
		converter.put("branch", 6);
		converter.put("publisher", 7);
		converter.put("pub", 7);
	}
	
	/*
	 * only throws SQLException if rollback fails. to be handled by menu as critical error
	 * all methods require a table field, whose value determines which table to modify
	 * 	1) author, 2)book,5)borrower, 6)library branch, 7)publisher
	 *  table 4) book copies should never be entered for admin
	 *  table 5) has its own method called overrideDueDate
	 */
	
	public boolean add(String tableName, Map<String, String> data) throws SQLException{
		int table = converter.get(tableName);
		if(table == 2){
			if( !dao.hasAuthor( Integer.parseInt( data.get("authId") ) ) ) {
				return false;
			}
			else if(!dao.hasPublisher( Integer.parseInt( data.get("pubId") ) ) ) {
				return false;
			}
		}
		try {
			dao.add(table, data);
			dao.commit();
			return true;
		}catch(SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s) {
				throw s;
			}
		}
	}
	
	public boolean delete(String tableName, int idNo) throws SQLException {
		int table = converter.get(tableName);
		try {
			dao.delete(table, idNo);
			dao.commit();
			return true;
		} catch(SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s){
				throw s;
			}
		}
	}
	
	//updates the entry with given bookId with the params given
	//leaves the field/column as is if the values are null
	public boolean updateBook(int bookId, String title, String authId, String pubId) throws SQLException{
		int table = converter.get("book");
		try {
			if(!(title.isEmpty() || title==null))
				dao.update(table, bookId, 1, title);
			if(!(authId.isEmpty() || authId==null))
				dao.update(table, bookId, 2, authId);
			if(!(pubId.isEmpty() || pubId==null))
				dao.update(table, bookId, 3, pubId);
			dao.commit();
			return true;
		} catch (SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s ) {
				throw s;
			}
		}
	}
	public boolean updateAuthor(int authorId, String name) throws SQLException{
		int table = converter.get("author");
		try {
			if(!(name.isEmpty() || name==null))
				dao.update(table, authorId, 1, name);
			dao.commit();
			return true;
		} catch (SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s ) {
				throw s;
			}
		}
	}
	public boolean updatePublisher(int pubId, String pubName, String pubAddr, String pubPhone) throws SQLException{
		int table = converter.get("publisher");
		try {
			if(!(pubName.isEmpty() || pubName==null))
				dao.update(table, pubId, 1, pubName);
			if(!(pubAddr.isEmpty() || pubAddr==null))
				dao.update(table, pubId, 2, pubAddr);
			if(!(pubPhone.isEmpty() || pubPhone==null))
				dao.update(table, pubId, 3, pubPhone);
			dao.commit();
			return true;
		} catch (SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s ) {
				throw s;
			}
		}
	}
	public boolean updateBorrower(int borId, String borName, String borAddr, String borPhone) throws SQLException{
		int table = converter.get("borrower");
		try {
			if(!(borName.isEmpty() || borName==null))
				dao.update(table, borId, 1, borName);
			if(!(borAddr.isEmpty() || borAddr==null))
				dao.update(table, borId, 2, borAddr);
			if(!(borPhone.isEmpty() || borPhone==null))
				dao.update(table, borId, 3, borPhone);
			dao.commit();
			return true;
		} catch (SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s ) {
				throw s;
			}
		}
	}
	public boolean updateLibraryBranch(int branchId, String branchName, String branchAddr) throws SQLException{
		int table = converter.get("branch");
		try {
			if(!(branchName.isEmpty() || branchName==null))
				dao.update(table, branchId, 1, branchName);
			if(!(branchAddr.isEmpty() || branchAddr==null))
				dao.update(table, branchId, 2, branchAddr);
			dao.commit();
			return true;
		} catch (SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s ) {
				throw s;
			}
		}
	}
	public boolean changeDueDate(int bookId, int branchId, int cardNo, int daysToAdd) throws SQLException {
		try {
			dao.changeDueDate(bookId, branchId, cardNo, daysToAdd);
			dao.commit();
			return true;
		} catch(SQLException e) {
			try {
				dao.rollBack();
				return false;
			} catch (SQLException s) {
				throw s;
			}
		}
	}
}
