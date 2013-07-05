package com.xiaoquyi.jsonelements;
import javax.xml.bind.annotation.*;

@XmlRootElement   
public class Notice {

	public String title = ""; 
	public String content = "";
	public String publishTime = "";
	public String images = "";
	
	public Notice() {};
	
	public Notice(String title,
				  String content,
				  String publishTime,
				  String images) {
		
		this.title = title;
		this.content = content;
		this.publishTime = publishTime;
		this.images = images;
	}
	
	
}
