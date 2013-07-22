package com.xiaoquyi.jsonelements;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement   
public class User  extends Status {

	public String name = "";
	public String weibo = "";
	public String qq = "";
	public String email = "";
	public String lastAccess = "";
	
	public User() {};
	
	public User(String name,
				  String weibo,
				  String qq,
				  String email,
				  String lastAccess) {
		
		this.name = name;
		this.weibo = weibo;
		this.qq = qq;
		this.email = email;
		this.lastAccess = lastAccess;
	}
	
	
}