package com.gb.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class GBUtil 
{
	//数据库连接工具类
	public static Connection get() 
	{
		//驱动
		String SQLDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String JDBC= "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=HotWord";
		try
		{
			Class.forName(SQLDriver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("驱动数据库失败！");
			System.exit(0);
		}     
		System.out.println("驱动成功!");
		Connection connection=null;
		try
		{
			String username="sa";
			String password="242772";
			connection=DriverManager.getConnection(JDBC,username,password);
			System.out.println("数据库连接成功");
			java.sql.Statement stmt=connection.createStatement();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return connection;
	}
	public static void close(Connection connection ) 
	{
		try 
		{
			if (connection != null) 
			{
				connection.close();
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement preparedStatement ) 
	{
		try 
		{
			if (preparedStatement != null) 
			{
				preparedStatement.close();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public static void close(ResultSet resultSet ) 
	{
		try 
		{
			if (resultSet != null) 
			{
				resultSet.close();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}