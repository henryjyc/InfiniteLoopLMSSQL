package com.st.lmssql.dao;

import java.sql.SQLException;

import com.st.lmssql.models.tbl.LibraryBranch;

public class librarianDao extends sqlDao{
	
	public void updateLibrary(int branchId, LibraryBranch library) throws SQLException{
		sql = "UPDATE library.tbl_library_branch SET branchName=?, branchAddress=? WHERE branchId=?";
		prep = conn.prepareStatement(sql);
		prep.setString(1, library.getBranchName());
		prep.setString(2,  library.getBranchAddress());
		prep.setInt(3, branchId);
		prep.executeUpdate();
	}
}
