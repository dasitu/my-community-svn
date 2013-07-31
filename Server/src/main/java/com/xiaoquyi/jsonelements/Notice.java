package com.xiaoquyi.jsonelements;

import java.util.LinkedList;
import java.util.List;

public class Notice extends Element{
	public String title = "";
	public String content = "";
	public String publishTime = "";
	public String communityName = "";
	public List<String> images = null;
	public String poster = ""; 
	
	public Notice() {};

	public Notice(String title,
				  String content,
				  String poster,
				  String publishTime,
				  String communityName) {
		
		this.title = title;
		this.content = content;
		this.publishTime = publishTime;
		this.poster = poster;
		this.communityName = communityName;
	}
	
	public void addImage(String image){
		if (this.images == null)
			this.images = new LinkedList<String>();
		this.images.add(image);
		
	}
}
