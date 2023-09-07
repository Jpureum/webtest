package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.utility.DBClose;
import com.utility.DBOpen;

public class BbsDAO {
	
	public int total(String col, String word) {
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from bbs ");
		
		if (word.trim().length() > 0 && col.equals("title_content")) {
		      sql.append(" where title like concat('%' , ? , '%')  ");
		      sql.append(" or content like concat('%' , ? , '%')  ");
	    } else if (word.trim().length() > 0) {
	      sql.append(" where " + col + " like concat('%' , ? , '%')  ");
	    }
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			
			if (word.trim().length() > 0 && col.equals("title_content")) {
			    pstmt.setString(1, word);
			    pstmt.setString(2, word);
			} else if (word.trim().length() > 0) {
			  pstmt.setString(1, word);
			}
			
			rs = pstmt.executeQuery();
			
//			if(rs.next()) {
//				total = rs.getInt(1)
//			}
			
			rs.next();
			total = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return total;
	}
	
	public List<BbsDTO> list(Map map){
		List<BbsDTO> list = new ArrayList<BbsDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT bbsno, wname, title, wdate, grpno, indent, ansnum, viewcnt ");
		sql.append(" FROM bbs ");
		if (word.trim().length() > 0 && col.equals("title_content")) {
		      sql.append(" where title like concat('%' , ? , '%')  ");
		      sql.append(" or content like concat('%' , ? , '%')  ");
	    } else if (word.trim().length() > 0) {
	      sql.append(" where " + col + " like concat('%' , ? , '%')  ");
	    }
		
		sql.append(" ORDER BY grpno DESC, ansnum ASC ");
		sql.append(" limit ?, ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			int i = 0;
			
			if (word.trim().length() > 0 && col.equals("title_content")) {
			    pstmt.setString(++i, word);
			    pstmt.setString(++i, word);
			} else if (word.trim().length() > 0) {
			  pstmt.setString(++i, word);
			}
			pstmt.setInt(++i, sno);
			pstmt.setInt(++i, eno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BbsDTO dto = new BbsDTO();
				dto.setBbsno(rs.getInt("bbsno"));
				dto.setWname(rs.getString("wname"));
				dto.setTitle(rs.getString("title"));
				dto.setWdate(rs.getString("wdate"));
				dto.setGrpno(rs.getInt("grpno"));
				dto.setIndent(rs.getInt("indent"));
				dto.setAnsnum(rs.getInt("ansnum"));
				dto.setViewcnt(rs.getInt("viewcnt"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return list;
	}
	
	public boolean create(BbsDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into bbs(wname, title, content, passwd, wdate, grpno) ");
        sql.append(" values(?, ?, ?, ?, sysdate(), ");
        sql.append(" (select ifnull(max(grpno),0) + 1 FROM bbs b) ) ");
        
        try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getWname());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getPasswd());
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}
	
	public BbsDTO read(int bbsno) {
		BbsDTO dto = null;
	    Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    StringBuffer sql = new StringBuffer();
        sql.append(" select bbsno, wname, title, content, viewcnt, wdate ");
        sql.append(" from bbs ");
        sql.append(" where bbsno = ?  ");
	    
	    try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bbsno);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
		        dto = new BbsDTO();
		        dto.setBbsno(rs.getInt("bbsno"));
		        dto.setWname(rs.getString("wname"));
		        dto.setTitle(rs.getString("title"));
		        dto.setContent(rs.getString("content"));
		        dto.setViewcnt(rs.getInt("viewcnt"));
		        dto.setWdate(rs.getString("wdate"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return dto;
	}
	
	public void upViewcnt(int bbsno) {
		Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append(" update bbs ");
	    sql.append(" set viewcnt = viewcnt + 1 ");
	    sql.append(" where bbsno = ? ");
	    
	    try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bbsno);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
	}
	
	public boolean update(BbsDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append(" update bbs ");
	    sql.append(" set wname = ? , ");
	    sql.append(" 	 title = ? , ");	    
	    sql.append(" 	 content = ?  ");	    
	    sql.append(" where bbsno = ? ");	    
		
	    try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getWname());
			pstmt.setString(2, dto.getTitle());
	        pstmt.setString(3, dto.getContent());
	        pstmt.setInt(4, dto.getBbsno());
	        
	        int cnt = pstmt.executeUpdate();
	        if(cnt > 0) flag = true;
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		return flag;
	}
	
	public boolean passCheck(Map map) {
		boolean flag = false;
		Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    int bbsno = (Integer)map.get("bbsno");
	    String passwd = (String)map.get("passwd");
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append(" select count(bbsno) as cnt ");
	    sql.append(" from bbs ");
	    sql.append(" where bbsno = ? and passwd = ? ");
	    
	    try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bbsno);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			int cnt = rs.getInt("cnt");
			if(cnt > 0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return flag;
	}
	
	public boolean delete(int bbsno) {
		boolean flag = false;
		Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append(" delete from bbs ");
	    sql.append(" where bbsno = ? ");
	    
	    try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bbsno);
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}
	
	public BbsDTO readReply(int bbsno) {
		BbsDTO dto = null;
	    Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    StringBuffer sql = new StringBuffer();
        sql.append(" select bbsno, title, grpno, indent, ansnum ");
        sql.append(" from bbs ");
        sql.append(" where bbsno = ?  ");
        
        try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bbsno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new BbsDTO();
				dto.setBbsno(rs.getInt("bbsno"));
				dto.setTitle(rs.getString("title"));
				dto.setGrpno(rs.getInt("grpno"));
				dto.setIndent(rs.getInt("indent"));
				dto.setAnsnum(rs.getInt("ansnum"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
        
		return dto;
	}
	
	public void upAnsnum(Map map) {
		Connection con = DBOpen.open();
	    PreparedStatement pstmt = null;
	    
	    StringBuffer sql = new StringBuffer();
	    int grpno = (Integer) map.get("grpno");
	    int ansnum = (Integer) map.get("ansnum");
	    sql.append(" update bbs ");
	    sql.append(" set ansnum = ansnum + 1 ");
	    sql.append(" where grpno = ?  ");
	    sql.append(" and ansnum > ?  ");
	    
	    try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, grpno);
			pstmt.setInt(2, ansnum);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
	}
	
	public boolean createReply(BbsDTO dto) {
		boolean flag = false;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into bbs(wname, title, content, passwd, wdate, grpno, indent, ansnum) ");
        sql.append(" values(?, ?, ?, ?, sysdate(), ?, ?, ?) ");
        
        try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getWname());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getPasswd());
			pstmt.setInt(5, dto.getGrpno());
			pstmt.setInt(6, dto.getIndent() + 1);
			pstmt.setInt(7, dto.getAnsnum() + 1);
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}
}
