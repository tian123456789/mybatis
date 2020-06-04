package com.tgr.springbootmybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.tgr.springbootmybatis.entity.User;

public interface TestJdbc {
	/*public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			//通过驱动管理类获取连接
			connection = 
					DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/mybatis?"
							+ "useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","root");
			//定义sql语句
			String sql = "select * from user where name = ?";
			//获取预处理statement
			preparedStatement = connection.prepareStatement(sql);
			//设置参数 第一个参数为sql语句中参数的序号(从1开始) 第二个参数为设置的参数的值
			preparedStatement.setString(1, "王小一");
			//项数据库发出sql执行查询 查询出结果集
			resultSet = preparedStatement.executeQuery();
			
			List<User> userList = new ArrayList<User>();
			
			//遍历查询结果集
			while(resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("userId"));
				user.setName(resultSet.getString("name"));
				userList.add(user);
			}
			System.out.println("userList::"+userList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
}
