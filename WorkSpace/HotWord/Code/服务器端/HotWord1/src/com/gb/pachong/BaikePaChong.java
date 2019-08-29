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
	// 抓取网站的相关配置，包括编码、重试次数、抓取间隔 
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public void run(String key) 
	{
		this.key = key;
		//Page对象就是当前获取的页面，getUrl()可以获得当前url，addTargetRequests()就是把链接放入等待爬取，getHtml()获得页面的html元素
		//启动爬虫
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
		//获取页面内容
		res = page.getHtml().xpath("//meta[@name='description']/@content").toString();
		//把包含数据添加到数据库的方法的类实例化成对象
		AddNum addNum=new AddNum();
		try 
		{
			//数据添加进数据库
			addNum.store(key, res);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void search(String string) 
	{
		//实例化一个对象
		BaikePaChong baikePaChong = new BaikePaChong();
		//搜索
		baikePaChong.run(string);
	}
	public String getRes()
	{
		return res;
	}
}
