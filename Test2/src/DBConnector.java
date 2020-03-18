import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnector {
	public static void main(String args[]) {
		Connection conn =null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url ="jdbc:mysql://220.67.115.29:3388/dongdang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			
			conn = DriverManager.getConnection(url,"dongdang","h0t$ix");
			System.out.print("Sucess");
			
		}catch(ClassNotFoundException e) {
			System.out.print("Driver Loading fail");
		}catch(SQLException e) {
			System.out.print("¿¡·¯: " + e);
		}
		finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
			
		
	}
}
