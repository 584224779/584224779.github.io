package servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.gb.model.baike;
import com.gb.util.CheckIfExists;
import com.gb.model.Bigtype;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
@WebServlet("/linksservlet")
//关系图
public class linksservlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public linksservlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		//获取操作信息
		String method = request.getParameter("method");
		if(method.equals("links")) 
		{
			CheckIfExists checkIfExists=new CheckIfExists();
			JSONObject json = new JSONObject();
			List<JSONObject> jsons1 = new ArrayList<>();
			JSONObject json11 = null;
			JSONObject json12 = null;
			List<baike> list=new ArrayList<>();
			try 
			{
				list=checkIfExists.loadAll();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			List<Bigtype> list2=new ArrayList<>();
			try 
			{
				list2=checkIfExists.loadAllType();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			//创建热词节点
			for(baike baike:list)
			{
				json11=new JSONObject();
				json11.put("name", baike.getName());
				json11.put("draggable", "true");
				json11.put("symbolSize", new int[] {50,50});
				json11.put("itemStyle", new JSONObject().put("color", "#000"));
				json11.put("category", "");
				jsons1.add(json11);
			}
			//创建父类节点
			for(Bigtype bigtype:list2)
			{
				json12=new JSONObject();
				json12.put("name", bigtype.getFather());
				json12.put("draggable", "true");
				json12.put("symbolSize", new int[] {100,100});
				json12.put("itemStyle", new JSONObject().put("color", "#000"));
				json12.put("category", "");
				jsons1.add(json12);
			}
			System.out.println("数据读取完毕");
			
			//链条数据
			List<JSONObject> jsons2 = new ArrayList<>();
			JSONObject json13 = new JSONObject();
			json13.put("name", "0");
			jsons2.add(json13);
			
			//链接数据
			List<JSONObject> jsons3 = new ArrayList<>();
			JSONObject json14 = null;
			for(Bigtype bigtype:list2)
			{
				for(baike baike:list)
				{
					if(bigtype.getType().equals(baike.getType()))
					{
						json14=new JSONObject();
						json14.put("target", bigtype.getFather());
						json14.put("source", baike.getName());
						json14.put("category", "");
						jsons3.add(json14);
					}
				}
			}
			System.out.println("相互关联成功！");
			//打包数据返回
			json.put("data", jsons1);
			json.put("categories", jsons2);
			json.put("links", jsons3);
			
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toJSONString());
		}
	}

}
