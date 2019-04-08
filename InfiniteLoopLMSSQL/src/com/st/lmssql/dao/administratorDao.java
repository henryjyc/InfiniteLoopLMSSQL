package com.st.lmssql.dao;

import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.st.lmssql.models.tbl.*;

/*
 * for reference, the value of "table" in the params equate to the following list
 * 1) author 2) book 3) book copies 4) book loans 5) borrower 6) library branch 7) publisher
 */
public class administratorDao extends sqlDao{
	
	/*
	 * performs the add action (2 in the query generator)
	 * The data contains map of data of the object to add
	 */
	public void add(int table, Map<String,String> data) throws SQLException{
		sql = sqlTools.generateQuery(2, table, null);
		prep = conn.prepareStatement(sql);
		switch(table) {
			case 1://add author
				prep.setString(1, data.get("name"));
				break;
			case 2://add book
				prep.setString(1, data.get("title"));
				prep.setInt(2, Integer.parseInt(data.get("authId")));
				prep.setInt(3, Integer.parseInt(data.get("pubId")));
				break;
			case 3://add book copies
				prep.setInt(1, Integer.parseInt(data.get("bookId")));
				prep.setInt(2, Integer.parseInt(data.get("branchId")));
				prep.setInt(3, Integer.parseInt(data.get("noOfCopies")));
				break;
			case 4://add book loans
				prep.setInt(1, Integer.parseInt(data.get("bookId")));
				prep.setInt(2, Integer.parseInt(data.get("branchId")));
				prep.setInt(3, Integer.parseInt(data.get("cardNo")));
				prep.setDate(4, java.sql.Date.valueOf(data.get("dateOut")));
				prep.setDate(5, java.sql.Date.valueOf(data.get("dueDate")));
				break;
			case 5://add borrower
				prep.setString(1, data.get("name"));
				prep.setString(2, data.get("address"));
				prep.setString(3, data.get("phone"));
				break;
			case 6: //library branch
				prep.setString(1, data.get("name"));
				prep.setString(2, data.get("address"));
				break;
			case 7: //publisher
				prep.setString(1, data.get("name"));
				prep.setString(2, data.get("address"));
				prep.setString(3, data.get("phone"));
				break;
		}
			prep.executeUpdate();
	}
	/*
	 * handles all of the updates for administrator
	 * (action # = 3 in the query generator)
	 * takes in the table, Id No, colNo (0 starting from the left), and a string containing the update data
	 */
	public void update(int table, int idNo, int updateCol, String data) throws SQLException{
		//first case statement for the table
		//second case statement for the columns
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(updateCol);
		indices.add(0); //id field
		sql = sqlTools.generateQuery(3, table, indices);
		prep = conn.prepareStatement(sql);
		switch(table) {
			case 1: case 5: case 6: case 7://update author, borrower, library branch, or publisher
				prep.setString(1, data);
				break;
			case 2://update book
				switch(updateCol) {
					case 1: //update title
						prep.setString(1, data);
						break;
					case 2: case 3: //update authId or pubId
						prep.setInt(1, Integer.parseInt(data));
						break;
				}
				break;
		}
		prep.executeUpdate();
	}
	/*
	 * handles all of the deletes for administrator
	 * (action # = 4 in the query generator)
	 */
	public void delete(int table, int idNo) throws SQLException{
		List<Integer> indexOfId = new ArrayList<Integer>();
		indexOfId.add(0); //for the id column
		sql = sqlTools.generateQuery(4, table, indexOfId);
		prep = conn.prepareStatement(sql);
		prep.setInt(1, idNo);
		prep.executeUpdate();
	}
	
	/*
	 * adds a number of days to current due date.
	 */
	public void changeDueDate(int bookId, int branchId, int cardNo, int daysToAdd) throws SQLException{
		List<Integer> cols = new ArrayList<Integer>();
		cols.add(0);
		cols.add(1);
		cols.add(2);
		sql = sqlTools.generateQuery(1, 4, cols);
		prep = conn.prepareStatement(sql);
		prep.setInt(1, bookId);
		prep.setInt(2, branchId);
		prep.setInt(3, cardNo);
		results = prep.executeQuery();
		LocalDate dueDate = results.getDate("dueDate").toLocalDate();
		dueDate = dueDate.plusDays(daysToAdd);
		cols.add(0, 0); //set the due date as the first param for update's set
		sql = sqlTools.generateQuery(3, 4, cols);
		prep = conn.prepareStatement(sql);
		prep.setDate(1, java.sql.Date.valueOf(dueDate));
		prep.setInt(1, bookId);
		prep.setInt(2, branchId);
		prep.setInt(3, cardNo);
		prep.executeUpdate();
	}
}
