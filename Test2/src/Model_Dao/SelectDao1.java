package Model_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectDao1 {
	public static void main(String args[]) {
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url ="jdbc:mysql://220.67.115.29:3388/dongdang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			
			conn = DriverManager.getConnection(url,"dongdang","h0t$ix");
			System.out.print("Sucess");
			
			stmt = conn.createStatement();
			
			String sql = "SELECT hash_tag.hash_tagNm FROM hash_tag";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String bizesId = rs.getString(1);
				
				System.out.println(bizesId);
			}
			}catch(ClassNotFoundException e) {
				System.out.print("Driver Loading fail");
			}catch(SQLException e) {
				System.out.print("Error: " + e);
			}
			finally {
				try {
					if(conn != null && !conn.isClosed()) {
						conn.close();
						
					if( stmt != null && !stmt.isClosed()){
	                    stmt.close();
	                }
	                if( rs != null && !rs.isClosed()){
	                    rs.close();
	                }
				  }
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
}
