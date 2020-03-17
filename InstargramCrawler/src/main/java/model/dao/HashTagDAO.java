package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import model.dto.CommentDTO;
import model.dto.ContentDTO;
import model.dto.HashTagDTO;
import model.dto.SubHashTagDTO;
import util.DBConnManager;
import util.MetaDataLoader;

public class HashTagDAO {
	
	public String checkHashTag(String hashTag) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("hashTag.checkHashTag");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rtn = null;		
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,hashTag);
			rs = ps.executeQuery();
			while(rs.next()) {
				rtn = rs.getString(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, rs);
		}
		return rtn;
	}
	public String checkConHashTag(String bizesId, String hashTag) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("bizesHashTagCon.checkConHashTag");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String rtn = null;		
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,bizesId);
			ps.setString(2, hashTag);
			rs = ps.executeQuery();
			while(rs.next()) {
				rtn = rs.getString(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, rs);
		}
		return rtn;
	}
	public int insertConHashTag(String bizesId, String hashTag) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("bizesHashTagCon.insertConHashTag");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,bizesId);
			ps.setString(2, hashTag);
			rtn = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			
		}catch (CommunicationsException e)  {
			System.err.println("[ DB ] DB와 잠시 연결이 끊겼습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, null);
		}
		return rtn;
	}

	public int insertHashTag(HashTagDTO hashTagDTO) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("hashTag.insertHashTag");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,hashTagDTO.getHashTagNm());
			ps.setString(2,hashTagDTO.getHashTagAdr());
			ps.setInt(3,hashTagDTO.getContentNum());
			rtn = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			
		}catch (CommunicationsException e)  {
			System.err.println("[ DB ] DB와 잠시 연결이 끊겼습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, null);
		}
		return rtn;
	}
	public int insertContent(ContentDTO content) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("content.insertContent");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,content.getContentAdr());
			ps.setString(2,content.getId());
			ps.setString(3,content.getContent());
			ps.setInt(4,content.getGood());
			ps.setInt(5,content.getCommentNum());
			ps.setDate(6,content.getCreationDate());
			
			rtn = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			
		}catch (CommunicationsException e)  {
			System.err.println("[ DB ] DB와 잠시 연결이 끊겼습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, null);
		}
		return rtn;
	}
	public int insertConContent(String hashTagAdr, String contentAdr) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("hashTagContentCon.insertConContent");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,hashTagAdr);
			ps.setString(2,contentAdr);
			rtn = ps.executeUpdate();
		}catch (SQLIntegrityConstraintViolationException e) {
			
		}catch (CommunicationsException e)  {
			System.err.println("[ DB ] DB와 잠시 연결이 끊겼습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, null);
		}
		return rtn;
	}
	public int insertComment(CommentDTO commentDTO) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("comment.insertComment");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,commentDTO.getId());
			ps.setString(2,commentDTO.getContent());
			ps.setDate(3,commentDTO.getCreationDate());
			ps.setString(4,commentDTO.getContentAdr());

			rtn = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			
		}catch (CommunicationsException e)  {
			System.err.println("[ DB ] DB와 잠시 연결이 끊겼습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, null);
		}
		return rtn;
	}
	public int insertSubHash(SubHashTagDTO subHashTagDTO) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("subHashTag.insertSubHashTag");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,subHashTagDTO.getHashTagNm());
			ps.setString(2,subHashTagDTO.getContentAdr());
			
			rtn = ps.executeUpdate();
		}catch (SQLIntegrityConstraintViolationException e) {
			
		}catch (CommunicationsException e)  {
			System.err.println("[ DB ] DB와 잠시 연결이 끊겼습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, null);
		}
		return rtn;
	}
}
 