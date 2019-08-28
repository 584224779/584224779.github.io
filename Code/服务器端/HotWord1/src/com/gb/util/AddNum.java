package com.gb.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gb.util.GBUtil;
public class AddNum 
{
	//����������ȴ��Ѵ��ڣ�������������
	public String CheckIfExists(String inputWordName) throws SQLException
	{
		Connection connection=null;
		connection = GBUtil.get();
		Statement stmt=connection.createStatement();
		//��ѯ�Ƿ��Ѵ��ڸ��ȴ�
		String sql="select *from HotWord";
		ResultSet rs=stmt.executeQuery(sql);
		String name;
		String num = null;
		int a=1;
		while(rs.next())
		{
			name=rs.getString("name").trim();
			if(name.equals(inputWordName))
			{
				num=rs.getString("num").trim();
				break;
			};
		}
		a=Integer.parseInt(num);
		//���Ѵ��ڣ�������������
		a++;
		num = String.valueOf(a);
		sql = "select count(*) from HotWord where name = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		sql = "update HotWord set num = '"+num+"' where name = '"+inputWordName+"'";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.executeUpdate();
		//�ͷ���Դ
		GBUtil.close(resultSet);
		GBUtil.close(preparedStatement);
		GBUtil.close(connection);
		stmt.close();
		connection.close();
		return null;
	}
	public void store(String inputWordName,String inputWordContent) throws SQLException
	{
		Connection connection=null;
		connection = GBUtil.get();
		String sql = "select count(*) from HotWord where name = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//���������ȴʲ�����ʱ�����һ���µ����ݣ�Ĭ�Ϸ���Ϊ������������������Ϊ1��
		sql = "insert into HotWord(name,type,content,num) values(?,?,?,?)";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, inputWordName);
		preparedStatement.setString(2, "4");
		preparedStatement.setString(3, inputWordContent);
		preparedStatement.setString(4, "1");
		preparedStatement.executeUpdate();
		//�ͷ���Դ
		GBUtil.close(resultSet);
		GBUtil.close(preparedStatement);
		GBUtil.close(connection);
	}
}
