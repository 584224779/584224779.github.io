package servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gb.util.HeadUtil;
import com.alibaba.fastjson.JSONObject;
import com.gb.model.baike;
import com.gb.util.CheckIfExists;
@WebServlet("/shouzimuservlet")
//首字母索引
public class shouzimuservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public shouzimuservlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//添加允许跨域访问语句
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		//得到客户端传来的数据，判断确认是首字母索引，继续进行操作
		String method = request.getParameter("method");
		System.out.println("method");
	if(method.equals("shouzimu")) 
	{
		//引入包含调取数据库数据的方法的类
		CheckIfExists checkIfExists=new CheckIfExists();
		//jsonObject
		//初始化
		JSONObject json = new JSONObject();
		List<JSONObject> jsons = new ArrayList<>();
		List<baike> list=new ArrayList<>();
		try 
		{
			//调取全部数据
			list=checkIfExists.loadAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		String string;
		String str;
		JSONObject json1=null;
		//循环遍历，对取得数据集合中的每个数据进行操作
		for(baike baike:list)
		{
			//定义字符串为该对象名称
			string= baike.getName();
			//调用HeadUtil工具类中的方法调取名称的首字母（这样取得的是每个字的首字母，例如：字母，得到的就是ZM）
			string=HeadUtil.getPYIndexStr(string,true);
			//字符串截取得到第一个即为首字母
			str=string.substring(0, 1);
			json1 = new JSONObject();
			//向json中放入数据
			json1.put("zimu",str);
			json1.put("name",baike.getName());
			//向json数组中放入json对象
			jsons.add(json1);
			System.out.println(json1.get("zimu"));
			System.out.println(json1.get("name"));
		}
		System.out.println(jsons);
		response.setCharacterEncoding("utf-8");
		//输出数据
		response.getWriter().write(jsons.toString());
	}
}
}
