package com.poll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

import com.utility.DBClose;
import com.utility.DBOpen;

public class PollDAO {
	public int total(Map map) {
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from poll ");
		if(word.trim().length() > 0) {
			sql.append(" where " + col + " like concat('%', ?, '%') ");
		}
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			if(word.trim().length() > 0 ) {
				pstmt.setString(1, word);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return total;
	}
	
	public Vector<PollDTO> getList(Map map){
		Vector<PollDTO> list = new Vector<PollDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		int sno = (int)map.get("sno");
		int eno = (int)map.get("eno");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from poll ");
		if(word.trim().length() > 0) {
			sql.append(" where " + col + " like concat('%', ?, '%') ");
		}
		sql.append(" order by num desc ");
		sql.append(" limit ?, ? ");
		
		int i = 0;
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			if(word.trim().length() > 0) {
				pstmt.setString(++i, word); //1
			}
			pstmt.setInt(++i, sno); //1
			pstmt.setInt(++i, eno); //2
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PollDTO dto = new PollDTO();
				dto.setNum(rs.getInt("num"));
				dto.setQuestion(rs.getString("question"));
				dto.setSdate(rs.getString("sdate"));
				dto.setEdate(rs.getString("edate"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return list;
	}
	
	public boolean create(PollDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into poll(question,sdate,edate,wdate,type) ");
        sql.append(" values(?,?,?,now(),?) ");
        
        try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getQuestion());
			pstmt.setString(2, dto.getSdate());
            pstmt.setString(3, dto.getEdate());
            pstmt.setInt(4, dto.getType());
            
            int result = pstmt.executeUpdate();
            if(result > 0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}
	
	public int getMaxNum() {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sql = new StringBuffer();
        int maxNum = 0;
        
        
        try {
        	con = DBOpen.open();
            sql.append(" select max(num) from poll ");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
	        if(rs.next()) maxNum = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
        
        return maxNum;
	}
	
	public PollDTO read(int num) {
		PollDTO dto = null;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from Poll where num= ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new PollDTO();
				dto.setQuestion(rs.getString("question"));
				dto.setType(rs.getInt("type"));
				dto.setActive(rs.getInt("active"));
				dto.setEdate(rs.getString("edate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return dto;
	}
}
