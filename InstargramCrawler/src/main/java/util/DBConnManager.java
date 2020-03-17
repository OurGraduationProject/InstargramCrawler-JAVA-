package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


//DB 연결 매니터
public class DBConnManager {
	
	static String driverName;
	static String dbServerAddr;
	static String dbName; // 디비명
	static String user; // 아이디
	static String pswd; // 비밀번호
		
	public static void dbLoading(Properties proFile) {
		  driverName = proFile.getProperty("driverName"); 
		  dbServerAddr = proFile.getProperty("url");
		  dbName = proFile.getProperty("dbName"); 
		  user = proFile.getProperty("userName"); 
		  pswd = proFile.getProperty("userPass");
		  try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  }
	 
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName+"?characterEncoding=UTF-8&serverTimezone=UTC", user, pswd);
	}
	
	public static Connection getConnection(String dbName) throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	// DB ���� ����
	public static void dbClose(Connection con, Statement st, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();	rs=null;
			}
			if(st != null) {
				st.close();	st=null;
			}
			if(con != null) {
				con.close();	con=null;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
