package com.novel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.novel.model.Novel;

/**
 * 操作JDBC
 * 
 * @author Aroceee
 * 
 */
public class JDBC {
	
	static PreparedStatement ps = null;
	static Connection ct = null;
	static ResultSet rs = null;
	
	static Properties prop = PropertiesUtil.getPropertyFile("/prop.properties");

	public static void insert(List<List<String>> article, int start) throws Exception {
		
		try {
			initConnection(); // 初始化connection
			
			ct.setAutoCommit(false);
			
			int i = start;
			
			for (List<String> list : article) {
				for (String string : list) {
					ps = ct.prepareStatement("insert into paragraph(paragraph, sid) values(?,?)");
					
					ps.setString(1, string);
					ps.setInt(2, i);
					
					int line = ps.executeUpdate();
					
					if(line == 1) {
						System.out.println("成功添加一行");
					} else {
						System.out.println("添加失败");
					}
				}
				
				i ++;
			}
			ct.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(ps != null) ps.close();
			if(ct != null) ct.close();
		}
	}
	
	public static List<Novel> getAll() throws Exception {
		
		List<Novel> list = new ArrayList<>();
		
		try {
			initConnection(); // 初始化connection
			
			ps=ct.prepareStatement("SELECT t.id AS tid, a.id AS aid, s.id AS sid, p.id AS pid, p.paragraph, s.section, a.article, t.type " + 
									"FROM (((type AS t LEFT JOIN article AS a ON a.tid = t.id) " +  
									"LEFT JOIN section AS s ON s.aid = a.id)) " +  
									"LEFT JOIN paragraph AS p ON p.sid = s.id " + 
									"ORDER BY p.id ASC");
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Novel novel = new Novel();
				
				novel.setTid(rs.getInt(1));
				novel.setAid(rs.getInt(2));
				novel.setSid(rs.getInt(3));
				novel.setPid(rs.getInt(4));
				novel.setParagraph(rs.getString(5));
				novel.setSection(rs.getString(6));
				novel.setArticle(rs.getString(7));
				novel.setType(rs.getString(8));
				
				list.add(novel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	private static void initConnection() throws Exception {
		Class.forName(prop.getProperty("jdbc.driverClassName"));
		ct = DriverManager.getConnection(prop.getProperty("jdbc.url"), prop.getProperty("jdbc.user"), prop.getProperty("jdbc.password"));
	}
}
