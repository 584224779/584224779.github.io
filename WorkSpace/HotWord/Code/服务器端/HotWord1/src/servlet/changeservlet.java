package servlet;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.gb.util.UpdateType;
@WebServlet("/changeservlet")
public class changeservlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public changeservlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//�������
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String name=request.getParameter("name");
		String newtype=request.getParameter("changetype");
		JSONObject obj = new JSONObject();
		if(name==null||newtype==null)
		{
			
		}
		else 
		{	
			UpdateType updateType=new UpdateType();
			try 
			{
				//���ȴʸ��ķ���
				updateType.UpdateType1(name, newtype);
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			//�޸ĳɹ�
			obj.put("success", "�޸ĳɹ���");
			String json  = obj.toJSONString();
			try 
			{
				//��json���ݷ���
				response.getWriter().write(json);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

}
