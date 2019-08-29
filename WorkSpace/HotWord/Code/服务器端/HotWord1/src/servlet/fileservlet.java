package servlet;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.gb.model.baike;
import com.gb.util.CheckIfExists;
import com.gb.util.GBUtil;
@WebServlet("/fileservlet")
//导出到excel
public class fileservlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public fileservlet() 
    {
        super();
    }

    public void init() throws ServletException {
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        GBUtil dbUtil = new GBUtil();
        Connection conn = null;
        try 
        {
        	// 获得数据库连接
            conn = GBUtil.get();
            Workbook wb = new HSSFWorkbook();
            //规定标题栏
            String headers[] = { "名称", "类型","内容","搜索热度" };// 标题
            CheckIfExists checkIfExists=new CheckIfExists();
            List<baike> list=new ArrayList<>();
    		try 
    		{
    			//从HotWord表中取出所有数据
    			list=checkIfExists.loadAll();
    		} 
    		catch (SQLException e) 
    		{
    			e.printStackTrace();
    		}
    		ResultSet rs = hotwordList(conn);
    		//将数据填入Excel
            fillExcelData(rs, wb, headers);
            //输出一个excel表
            export(response, wb, "NewFile.xls");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                GBUtil.close(conn);;
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    public ResultSet hotwordList(Connection con) throws Exception 
    {
    	//返回查询之后的结果集
        StringBuffer sb = new StringBuffer("select * from HotWord");
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }
    public void fillExcelData(ResultSet rs, Workbook wb, String[] headers) throws Exception {
    	// 第一行
        int rowIndex = 0; 
     // 创建sheet页
        Sheet sheet = wb.createSheet(); 
        Row row = sheet.createRow(rowIndex++);
        // 创建标题单元格
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        // 导出数据库中的数据
        while (rs.next()) {
            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < headers.length; i++) {
                row.createCell(i).setCellValue(rs.getObject(i + 1).toString());
                //rs.getObject(i + 1)得到一个对象，即数据库中一行的结果，每一列就是属性凑成一行变成对象。因为id是从1开始，所以要+1。
            }
        }
    }
    public void export(HttpServletResponse response, Workbook wb,String fileName) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"), "iso8859-1"));
        // 设置头信息
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        // 进行输出，下载到本地
        wb.write(out);
        out.flush();
        out.close();
    }
}