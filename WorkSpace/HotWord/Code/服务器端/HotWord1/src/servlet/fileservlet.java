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
//������excel
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
        	// ������ݿ�����
            conn = GBUtil.get();
            Workbook wb = new HSSFWorkbook();
            //�涨������
            String headers[] = { "����", "����","����","�����ȶ�" };// ����
            CheckIfExists checkIfExists=new CheckIfExists();
            List<baike> list=new ArrayList<>();
    		try 
    		{
    			//��HotWord����ȡ����������
    			list=checkIfExists.loadAll();
    		} 
    		catch (SQLException e) 
    		{
    			e.printStackTrace();
    		}
    		ResultSet rs = hotwordList(conn);
    		//����������Excel
            fillExcelData(rs, wb, headers);
            //���һ��excel��
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
    	//���ز�ѯ֮��Ľ����
        StringBuffer sb = new StringBuffer("select * from HotWord");
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }
    public void fillExcelData(ResultSet rs, Workbook wb, String[] headers) throws Exception {
    	// ��һ��
        int rowIndex = 0; 
     // ����sheetҳ
        Sheet sheet = wb.createSheet(); 
        Row row = sheet.createRow(rowIndex++);
        // �������ⵥԪ��
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        // �������ݿ��е�����
        while (rs.next()) {
            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < headers.length; i++) {
                row.createCell(i).setCellValue(rs.getObject(i + 1).toString());
                //rs.getObject(i + 1)�õ�һ�����󣬼����ݿ���һ�еĽ����ÿһ�о������Դճ�һ�б�ɶ�����Ϊid�Ǵ�1��ʼ������Ҫ+1��
            }
        }
    }
    public void export(HttpServletResponse response, Workbook wb,String fileName) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"), "iso8859-1"));
        // ����ͷ��Ϣ
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        // ������������ص�����
        wb.write(out);
        out.flush();
        out.close();
    }
}