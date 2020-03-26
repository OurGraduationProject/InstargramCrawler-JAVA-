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
	/**
	 * <pre>해시태그테이블 해시태그 존재유무 체크</pre>
	 * @param hashTag
	 * @return
	 */
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

	/**
	 * 
	 * <pre>해시태그 삽입</pre>
	 * @param hashTagDTO
	 * @return
	 */
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

	/**
	 * 
	 * <pre>게시글 삽입</pre>
	 * @param content
	 * @return
	 */
	public int insertContent(ContentDTO content) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("content.insertContent");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,content.getContentAdr());
			ps.setString(2,content.getContent());
			ps.setInt(3,content.getGood());
			ps.setInt(4,content.getCommentNum());
			ps.setDate(5,content.getCreationDate());
			
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

	/**
	 * 
	 * <pre>연결 테이블(해시-게시글) 삽입</pre>
	 * @param hashTagAdr
	 * @param contentAdr
	 * @return
	 */
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

	/**
	 * 
	 * <pre>댓글 삽입</pre>
	 * @param commentDTO
	 * @return
	 */
	public int insertComment(CommentDTO commentDTO) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("comment.insertComment");
		Connection con = null;
		PreparedStatement ps = null;
		int rtn = 0;	
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,commentDTO.getContent());
			ps.setDate(2,commentDTO.getCreationDate());
			ps.setString(3,commentDTO.getContentAdr());

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

	/**
	 * 
	 * <pre>서브해시 삽입</pre>
	 * @param subHashTagDTO
	 * @return
	 */
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
 