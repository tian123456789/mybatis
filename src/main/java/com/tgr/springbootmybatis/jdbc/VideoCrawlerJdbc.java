package com.tgr.springbootmybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.attribute.ResolutionSyntax;

public class VideoCrawlerJdbc {

	private static Connection connection = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet result = null;
	
	public static void main(String[] args) throws SQLException {
		//定义sql语句
		String sql = "select count(*) from episode";
		result = executeQuery(sql);
		if(result.next()) {
			System.out.println(result.getInt(1));
		}
		
		try {
			
		} catch (Exception e) {
			
		}
		
	}
	
	public static ResultSet executeQuery(String sql) {
		try {
			//加载数据驱动
			Class.forName("com.mysql.jdbc.Driver");
			//通过驱动管理类获取连接
			connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/videocrawler?"
							+ "useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","root");
			//通过连接获取预编译处理平台
			preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setLong(1, 1L);
			//执行sql 获取结果
			result = preparedStatement.executeQuery();
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {connection.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
}
