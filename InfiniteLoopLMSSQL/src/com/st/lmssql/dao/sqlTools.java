package com.st.lmssql.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * enum to create the first portion of the sql query
 * can generate queries using the keywords select, insert into, update, and delete
 */
enum Action {
	SELECT("SELECT * FROM "), INSERT("INSERT INTO "), UPDATE("UPDATE "), DELETE("DELETE FROM ");

	private final String action;

	private Action(String action) {
		this.action = action;
	}

	public String toString() {
		return this.action;
	}
}

enum Table {
	AUTHOR("library.tbl_author", "authorId|authorName"), BOOK("library.tbl_book", "bookId|title|authId|pubId"),
	BOOKCOPIES("library.tbl_book_copies", "bookId|branchId|noOfCopies"),
	BOOKLOANS("library.tbl_book_loans", "bookId|branchId|cardNo|dateOut|dueDate"),
	BORROWER("library.tbl_borrower", "cardNo|name|address|phone"),
	LIBRARYBRANCH("library.tbl_library_branch", "branchId|branchName|branchAddress"),
	PUBLISHER("library.tbl_publisher", "publisherId|publisherName|publisherAddress|publisherPhone");

	private final String table;
	private final String colNames;

	private Table(String table, String colNames) {
		this.table = table;
		this.colNames = colNames;
	}

	public String table() {
		return this.table;
	}

	public String colNames() {
		return this.colNames;
	}

	public List<String> colList() {
		String[] parts = this.colNames.split("\\|");
		List<String> list = new ArrayList<String>();
		for (String part : parts)
			list.add(part);
		return list;
	}
}

/*
 * generates a partial query depending on the desired action and table actions:
 * 1) select 2) insert 3) update 4) delete tables: 1) author 2) book 3) book
 * copies 4) book loans 5) borrower 6) library branch 7) publisher cols stores
 * the indices of the tables starting from 0(left) to x(right) where the
 * generator injects colNames into the query in the order listed in cols for
 * example, if the action is update and cols.get(0) is 2, it'll set the 3rd
 * column from the left.
 */
public class sqlTools {
	public static String generateQuery(int action, int table, List<Integer> colNameIndices) {
		// String output = "";
		StringBuilder output = new StringBuilder();
		List<String> colNames = null;
		boolean isUpdate = false;
		boolean isInsert = false;
		// concat the action to be taken
		switch (action) {
		case 1:
			output.append(Action.SELECT.toString());
			break;
		case 2:
			output.append(Action.INSERT.toString());
			isInsert = true;
			break;
		case 3:
			output.append(Action.UPDATE.toString());
			isUpdate = true;
			break;
		case 4:
			output.append(Action.DELETE.toString());
			break;
		}

		// concat the table to choose from
		switch (table) {
		case 1:
			output.append(Table.AUTHOR.table());
			colNames = Table.AUTHOR.colList();
			break;
		case 2:
			output.append(Table.BOOK.table());
			colNames = Table.BOOK.colList();
			break;
		case 3:
			output.append(Table.BOOKCOPIES.table());
			colNames = Table.BOOKCOPIES.colList();
			break;
		case 4:
			output.append(Table.BOOKLOANS.table());
			colNames = Table.BOOKLOANS.colList();
			break;
		case 5:
			output.append(Table.BORROWER.table());
			colNames = Table.BORROWER.colList();
			break;
		case 6:
			output.append(Table.LIBRARYBRANCH.table());
			colNames = Table.LIBRARYBRANCH.colList();
			break;
		case 7:
			output.append(Table.PUBLISHER.table());
			colNames = Table.PUBLISHER.colList();
			break;
		}
		if (isInsert) {
			output.append(" (");

			// if adding copes or loans, we need all columns
			// other tables don't require the id field since it's auto incremented
			int startIndex;
			if (output.indexOf("copies") > 0 || output.indexOf("loans") > 0) {
				startIndex = 0;
			} else
				startIndex = 1;
			for (int i = startIndex; i < colNames.size(); i++) {
				output.append(colNames.get(i));
				if ((i + 1) < colNames.size())
					output.append(", ");
			}
			output.append(") VALUES (");
			for (int i = startIndex; i < colNames.size(); i++) {
				output.append("?");
				if ((i + 1) < colNames.size())
					output.append(", ");
			}
			output.append(")");
		} else {
			if (isUpdate) {
				output.append(" SET ");
				output.append(colNames.get(colNameIndices.get(0)));
				output.append("=?");
			}
			output.append(" WHERE ");
			for (int i = 0; i < colNameIndices.size(); i++) {
				if (isUpdate) {
					i++;
					if (i >= colNameIndices.size())
						break;
					isUpdate = false;
				}
				output.append(colNames.get(colNameIndices.get(i)));
				output.append("=? ");
				if ((i + 1) < colNameIndices.size())
					output.append("AND ");
			}
		}
		return output.toString();
	}
}
