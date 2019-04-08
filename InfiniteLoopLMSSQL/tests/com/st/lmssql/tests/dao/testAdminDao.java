package com.st.lmssql.tests.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import org.junit.Before;
import org.junit.Test;

import com.st.lmssql.dao.administratorDao;
import com.st.lmssql.dao.connectSQL;
import com.st.lmssql.dao.sqlTools;


public class testAdminDao {
	Connection conn;
	administratorDao dao;
	@Before
	public void setUpConnection() {
		connectSQL.connect();
		conn = connectSQL.getConn();
		dao = new administratorDao();
	}
	
	@Test
	public void testUpdate() throws SQLException{
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(1); //name field
		indices.add(0); //id field
		String sql = sqlTools.generateQuery(3, 1, indices);
		PreparedStatement prep = conn.prepareStatement(sql);
		prep.setInt(1, 9);
		prep.setString(1, "HamFanatic");
		prep.executeUpdate();
		assert(dao.hasAuthor("HamFanatic"));
	}
}
