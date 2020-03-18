import java.sql.*;

public class selectTest {
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
			
			String sql = "Select bizesId from bizes";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String bizesId = rs.getString(1);
				
				System.out.println(bizesId);
			}
			}catch(ClassNotFoundException e) {
				System.out.print("Driver Loading fail");
			}catch(SQLException e) {
				System.out.print("¿¡·¯: " + e);
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