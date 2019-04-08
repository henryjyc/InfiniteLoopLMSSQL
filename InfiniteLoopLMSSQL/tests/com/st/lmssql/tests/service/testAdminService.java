package com.st.lmssql.tests.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.st.lmssql.dao.*;
import com.st.lmssql.service.AdministratorService;

public class testAdminService {
	sqlDao dao;
	AdministratorService service;
	@Before
	public void setUpConnection() {
		connectSQL.connect();
		dao = new administratorDao();
		service = new AdministratorService();
	}
	
//	@Test
//	public void testAddAuthor() throws SQLException{
//		Map<String, String> data = new HashMap<String, String>();
//		String testName = "ham";
//		data.put("name", testName);
//		service.add("author", data);
//		assert(dao.hasAuthor(testName));
//	}
//	
//	@Test
//	public void testDeleteAuthor() throws SQLException{
//		service.delete("author", 7);
//		assertFalse(dao.hasAuthor("ham"));
//	}
	
	@Test
	public void testUpdateAuthor() throws SQLException{
		service.updateAuthor(2, "JK Rowling");
		assert(dao.hasAuthor("JK Rowling"));
	}
}
