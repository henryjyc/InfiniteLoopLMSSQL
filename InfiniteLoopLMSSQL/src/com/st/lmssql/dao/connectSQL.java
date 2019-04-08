package com.st.lmssql.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class connectSQL {
	private static Connection conn = null;
	
	public static boolean connect() {
		try {
			FileInputStream fin = new FileInputStream("./resources/.config");
			Properties props = new Properties();
			props.load(fin);
			String url = props.getProperty("url");
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			conn = DriverManager.getConnection(url, user, password);
//			BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
//			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library", buffReader.readLine(), buffReader.readLine());
			conn.setAutoCommit(false);
//			buffReader.close();
			return true;
		} catch (IOException | SQLException e) {
			return false;
		}
	}
	
	public static Connection getConn() {
		return conn;
	}
}
