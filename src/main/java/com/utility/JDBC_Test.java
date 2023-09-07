package com.utility;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
/**
 * JAVA Client Test
 */
public class JDBC_Test {
 
    public static void main(String[] args) {
 
        Connection con = DBOpen.open();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        // 테이블의 갯수 리턴
        String sql="SELECT COUNT(*) cnt FROM information_schema.tables WHERE table_schema = 'webtest';";
        
        try{
            pstmt = con.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            if (rs.next() == true){
                System.out.println("현재 webtest DB에 생성된 테이블 갯수: " + rs.getInt("cnt"));
            }
        }catch(Exception e){
            System.out.println(e);
        }finally{
            DBClose.close(rs, pstmt, con);
        }
    }
}