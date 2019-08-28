package com.gb.util;
import com.gb.util.GBUtil;
import com.gb.model.Bigtype;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.gb.model.baike;
public class CheckIfExists 
{
	public String checkIfExists(String inputWord) throws SQLException
	{
		Connection connection=null;
		connection = GBUtil.get();
		//����SQL�������
		Statement stmt=connection.createStatement();
		//��ȡ����
		String sql="select * from HotWord";
		ResultSet rs=stmt.executeQuery(sql);
		String name;
		String content;
	    //ѭ�����ÿһ����¼
		while(rs.next())
		{
			name=rs.getString("name").trim();
			if(name.equals(inputWord))
			{
				content=rs.getString("content").trim();
				return content;
			}
		}	
		stmt.close();
		connection.close();
		return null;
	}
	public List<Bigtype> loadAllType() throws SQLException
	{
		//��type���еõ������list
		List<Bigtype> list=new ArrayList<>();
		Connection connection=null;
		connection = GBUtil.get();
		Statement stmt=connection.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM type");
		String father;
		String type;
		while(rs.next())
		{
			father=rs.getString("father").trim();
			type=rs.getString("type").trim();
			Bigtype bigtype=new Bigtype();
			bigtype.setFather(father);
			bigtype.setType(type);
			list.add(bigtype);
		}	
		stmt.close();
		connection.close();
		return list;
	}
	public List<baike> loadAll() throws SQLException
	{
		//��HotWord���еõ������ȴ�����
		List<baike> list=new ArrayList<>();
		Connection connection=null;
		connection = GBUtil.get();
		Statement stmt=connection.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM HotWord");
		String name;
		String content;
		String type;
		String num;
		while(rs.next())
		{
			name=rs.getString("name").trim();
			content=rs.getString("content").trim();
			type=rs.getString("type").trim();
			num=rs.getString("num").trim();
			baike baike=new baike();
			baike.setName(name);
			baike.setContent(content);
			baike.setType(type);
			baike.setNum(num);
			list.add(baike);
		}	
		stmt.close();
		connection.close();
		return list;
	}
	public List<String> checkIfExists() throws SQLException
	{
		List<String> list=new ArrayList<>();
		Connection connection=null;
		connection = GBUtil.get();
		Statement stmt=connection.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM HotWord");
		String name;
		while(rs.next())
		{
			name=rs.getString("name").trim();
			if(name==null)
				break;
			list.add(name);
			name=rs.getString("num").trim();
			list.add(name);
		}
		stmt.close();
		connection.close();
		return list;
	}
}
