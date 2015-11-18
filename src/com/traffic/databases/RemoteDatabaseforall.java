package com.traffic.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RemoteDatabaseforall {

	 public String jdbcDriver = "com.mysql.jdbc.Driver";
		 public  String jdbcurl = "";
		 public  String userName = "remote";
		 public  String password = "Iknowthat";
	public RemoteDatabaseforall(String host,String database,String userName,String password) {
		this.jdbcurl = "jdbc:mysql://"+host+"/"+database;
		this.userName = userName;
		this.password = password;
	}
	
	public  Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(jdbcurl, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Statement st, Connection conn) {
		try {
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * return the article ID.
	 * 
	 * @TODO 
	 *       .ARTICLE_CATEGORY_ID
	 * @param title
	 * @param fulltext
	 * @param introText
	 * @return
	 * @throws SQLException 
	 */

	public ArrayList<Object[]> selectall(String sql) throws Exception {
		ArrayList<Object[]> data = new ArrayList<>();
		Connection conn = createConnection();
		Statement statment = null;
		ResultSet rst = null;
		try {
			statment = conn.createStatement();
			statment.setQueryTimeout(300);
			rst = statment.executeQuery(sql);
			ResultSetMetaData rstmd = rst.getMetaData();
			// 列数
			int count = rstmd.getColumnCount();
			while (rst.next()) {
				Object[] row = new Object[count];

				for (int i = 0; i < count; i++) {
					row[i] = rst.getObject(i + 1);
				}
				data.add(row);
			}
		}finally {
			close(rst,statment, conn);
		}
		return data;
	}
	
	public void select(String sql) {
		Connection conn = createConnection();
		Statement statment = null;
		try {
			
			statment = conn.createStatement();
			statment.setQueryTimeout(300);
		}catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e1){
		}
		finally {
		}
	}
	
	public void inserAll(String SQLtemp) {
		Connection conn = createConnection();
		Statement statment = null;
		try {
			statment = conn.createStatement();
			statment.executeUpdate(SQLtemp);
		} catch (SQLException e) {
			System.out.println(SQLtemp);
			e.printStackTrace();
		} finally {
			close(statment, conn);
		}
	}


	public void executeBatch(List<String> sqlList) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = createConnection();
			st = con.createStatement();
			for (String s : sqlList) {
				st.addBatch(s);
			}
			st.executeBatch();
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, st, con);
		}
	}
	
}
