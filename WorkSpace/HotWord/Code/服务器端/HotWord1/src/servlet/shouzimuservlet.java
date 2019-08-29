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
//����ĸ����
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
		//����������������
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		//�õ��ͻ��˴��������ݣ��ж�ȷ��������ĸ�������������в���
		String method = request.getParameter("method");
		System.out.println("method");
	if(method.equals("shouzimu")) 
	{
		//���������ȡ���ݿ����ݵķ�������
		CheckIfExists checkIfExists=new CheckIfExists();
		//jsonObject
		//��ʼ��
		JSONObject json = new JSONObject();
		List<JSONObject> jsons = new ArrayList<>();
		List<baike> list=new ArrayList<>();
		try 
		{
			//��ȡȫ������
			list=checkIfExists.loadAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		String string;
		String str;
		JSONObject json1=null;
		//ѭ����������ȡ�����ݼ����е�ÿ�����ݽ��в���
		for(baike baike:list)
		{
			//�����ַ���Ϊ�ö�������
			string= baike.getName();
			//����HeadUtil�������еķ�����ȡ���Ƶ�����ĸ������ȡ�õ���ÿ���ֵ�����ĸ�����磺��ĸ���õ��ľ���ZM��
			string=HeadUtil.getPYIndexStr(string,true);
			//�ַ�����ȡ�õ���һ����Ϊ����ĸ
			str=string.substring(0, 1);
			json1 = new JSONObject();
			//��json�з�������
			json1.put("zimu",str);
			json1.put("name",baike.getName());
			//��json�����з���json����
			jsons.add(json1);
			System.out.println(json1.get("zimu"));
			System.out.println(json1.get("name"));
		}
		System.out.println(jsons);
		response.setCharacterEncoding("utf-8");
		//�������
		response.getWriter().write(jsons.toString());
	}
}
}
