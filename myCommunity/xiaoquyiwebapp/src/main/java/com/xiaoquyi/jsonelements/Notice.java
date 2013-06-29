package com.xiaoquyi.jsonelements;
import javax.xml.bind.annotation.*;

@XmlRootElement   

public class Notice {

	
	
	public Notice() {};
	
	public String title;
	public String content;
	public String publishTime;
	public String images;
}
