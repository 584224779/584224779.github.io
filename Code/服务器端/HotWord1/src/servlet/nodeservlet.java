package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.gb.util.CheckIfExists;
import com.gb.pachong.BaikePaChong;
import com.gb.util.AddNum;
import com.google.gson.Gson;
@WebServlet("/nodeservlet")
public class nodeservlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public nodeservlet() 
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
	    PrintWriter out = response.getWriter();
	    String txt1=request.getParameter("txt1");
	    CheckIfExists checkIfExists=new CheckIfExists();
	    BaikePaChong pachong=new BaikePaChong();
		JSONObject obj = new JSONObject();
		if(txt1==null)
		{
			//��������Ϊ��
			obj.put("word", txt1);
			obj.put("source", "û���ҵ��ôʻ㶨��");
		}
		else 
		{

	    try
	    {
			if(checkIfExists.checkIfExists(txt1)==null)
			{
				//������ݿ���û�и��ȴʣ���ʼ��ȡ����
			    pachong.search(txt1);
			    obj.put("word", txt1);
				if(pachong.getRes()!=null)
				{
					//���������������
					obj.put("source", pachong.getRes());
				}
				else
					//��ȡ����
					obj.put("source", "û���ҵ��ôʻ㶨��");
			}
			else
			{
				//������ڣ���������+1�����ظ��ȴ���Ϣ
				AddNum st=new AddNum();
				st.CheckIfExists(txt1);
				obj.put("word", txt1);
				obj.put("source", checkIfExists.checkIfExists(txt1));
			}
		} 
	    catch (SQLException e1) 
	    {
			e1.printStackTrace();
		}
		
	}
		String json  = obj.toJSONString();
		try 
		{
			response.getWriter().write(json);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
