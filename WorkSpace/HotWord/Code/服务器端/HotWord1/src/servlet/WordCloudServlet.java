package servlet;
import java.io.IOException;
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
@WebServlet("/WordCloudServlet")
//字符云
public class WordCloudServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public WordCloudServlet() 
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("utf-8");
		//允许跨域访问
		response.setHeader("Access-Control-Allow-Origin","*");
		String method = request.getParameter("method");
		if(method.equals("wordCloud")) 
		{
			JSONObject json = new JSONObject();
			List<JSONObject> jsons = new ArrayList<>();
			CheckIfExists checkIfExists=new CheckIfExists();
			List<String> list = null;
			try 
			{
				//获取全部热词
				list = checkIfExists.checkIfExists();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			for(int n=0;n<list.size();n+=2)
			{
				JSONObject json1 = new JSONObject();
				json1.put("name",list.get(n));
				//字符云的显示大小根据搜索次数来决定
				json1.put("value",Integer.parseInt(list.get(n+1))*100);
				jsons.add(json1);
			}
			json.put("data", jsons);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toJSONString());
		}
	}
}
