package board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import common.JDBConnect;

public class BoardDAO extends JDBConnect {
	
	public BoardDAO (ServletContext application) {
		super(application);
	}
	
	// Board List Count
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM board";
		if (map.get("searchWord") != null) {
			query += "WHERE" + map.get("searchField") + " LIKE '%" +map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		return totalCount;
	}
	
	// Board List Search
	public List<BoardDTO> selectList(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		String query = "SELECT * FROM board";
		if (map.get("searchWord") != null) {
			query += "WHERE" + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%'";
		}
		query += " ORDER BY num DESC";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		return bbs;
	}

}
