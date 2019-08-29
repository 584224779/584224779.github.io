package com.gb.pachong;
import java.sql.SQLException;
import com.gb.util.AddNum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
public class BaikePaChong implements PageProcessor 
{
	private static String key;
	public static String res=null;
	// ץȡ��վ��������ã��������롢���Դ�����ץȡ��� 
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public void run(String key) 
	{
		this.key = key;
		//Page������ǵ�ǰ��ȡ��ҳ�棬getUrl()���Ի�õ�ǰurl��addTargetRequests()���ǰ����ӷ���ȴ���ȡ��getHtml()���ҳ���htmlԪ��
		//��������
		Spider.create(new BaikePaChong()).addUrl("https://baike.baidu.com/item/" + key).thread(5).run();
	}
	@Override
	public Site getSite() 
	{
		return site;
	}
	@Override
	public void process(Page page) 
	{
		//��ȡҳ������
		res = page.getHtml().xpath("//meta[@name='description']/@content").toString();
		//�Ѱ���������ӵ����ݿ�ķ�������ʵ�����ɶ���
		AddNum addNum=new AddNum();
		try 
		{
			//������ӽ����ݿ�
			addNum.store(key, res);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void search(String string) 
	{
		//ʵ����һ������
		BaikePaChong baikePaChong = new BaikePaChong();
		//����
		baikePaChong.run(string);
	}
	public String getRes()
	{
		return res;
	}
}
