package com.gb.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.gb.util.GBUtil;
public class UpdateType 
{
	//手动分类
	public void UpdateType1(String inputWordName,String x) throws SQLException
	{
		Connection connection=null;
		connection = GBUtil.get();
		Statement stmt=connection.createStatement();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "update HotWord set type = '"+x+"' where name = '"+inputWordName+"'";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.executeUpdate();
		GBUtil.close(resultSet);
		GBUtil.close(preparedStatement);
		GBUtil.close(connection);
		stmt.close();
		connection.close();
	}
}
