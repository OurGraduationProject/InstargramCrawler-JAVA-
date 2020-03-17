package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.dto.KewordDTO;
import util.DBConnManager;
import util.MetaDataLoader;

public class KewordDAO {
	/**
	 * 키워드 생성 메소드
	 * @param ldongList
	 * @return
	 */
	public List<KewordDTO> getKeword(List<String> ldongList) {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("search.selectAll");
		for(int i=0; i<ldongList.size();i++) {
			query += "\"" + ldongList.get(i) + "\"";
			if(i!=ldongList.size()-1) {
				query += ",";
			}else {
				query +=") order by bizesId asc;";
			}
		}
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<KewordDTO> kewordList=null;		
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			kewordList = new LinkedList<KewordDTO>();
			while(rs.next()) {
				kewordList.add(new KewordDTO(rs.getString("bizesId"),rs.getString("ldongNm"),rs.getString("bizesNm")));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, rs);
		}
		return kewordList;
	}
	/**
	 * 한 지역 법정동리스트 검색
	 * @return
	 */
	public List<String> getLdongList() {
		String query = MetaDataLoader.getDbCmdProfile().getProperty("search.ldongList");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> ldongList=null;		
		try {
	
			con = DBConnManager.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			ldongList = new ArrayList<String>();
			while(rs.next()) {
				ldongList.add(rs.getString("ldongNm"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnManager.dbClose(con, ps, rs);
		}
		return ldongList;
	}
}