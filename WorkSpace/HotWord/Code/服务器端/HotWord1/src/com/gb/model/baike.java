package com.gb.model;
public class baike 
{
	//热词名称
	private String name;
	//热词相关内容
	private String content;
	//热词类型
	private String type;
	//该热词被搜索的次数
	private String num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
