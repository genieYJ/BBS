package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;

public class JDBConnect {
	
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	
	public JDBConnect(String driver, String url, String user, String pwd) {
		
		try {
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB 연결1 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public JDBConnect(ServletContext application) {
		try {
			String driver = application.getInitParameter("OracleDriver");
			Class.forName(driver);
			
			String url = application.getInitParameter("OracleDraiver");
			String user = application.getInitParameter("OracleId");
			String pwd = application.getInitParameter("OraclePwd");
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB 연결2 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void close() {
		try {
			if(rs != null ) rs.close();
			if(stmt != null ) stmt.close();
			if(psmt != null ) psmt.close();
			if(con != null ) con.close();
			System.out.println("DB 연결 해제");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
