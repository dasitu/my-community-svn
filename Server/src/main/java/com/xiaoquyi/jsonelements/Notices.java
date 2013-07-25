package com.xiaoquyi.jsonelements;
import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement   
public class Notices extends Element {
	
//	@XmlRootElement
//	static public class Notice extends Element{
//		public String title = "";
//		public String content = "";
//		public String publishTime = "";
//		public List<String> images = null;
//		public String poster = ""; 
//		
//		public Notice() {};
//	
//		public Notice(String title,
//					  String content,
//					  String poster,
//					  String publishTime) {
//			
//			this.title = title;
//			this.content = content;
//			this.publishTime = publishTime;
//			this.poster = poster;
//		}
//		
//		public void addImage(String image){
//			if (this.images == null)
//				this.images = new LinkedList<String>();
//			this.images.add(image);
//			
//		}
//		
//	}
//	@XmlElement
	public List<Notice> data = null;
	
	
	public Notices() {};
	
	public void addNotice(Notice item) {
		if (this.data == null) {
			this.data = new LinkedList<Notice>();
		}
		this.data.add(item);
		
	}
	
	public void setStatus(Status status) {
		if (data == null) {
			super.setStatus(new Status(10000, -1, "No data!", 10000));
			return;
		}
		super.setStatus(status);
		
		for (Notice i:data) {
			i.setStatus(null);
		}
	
	}
}
