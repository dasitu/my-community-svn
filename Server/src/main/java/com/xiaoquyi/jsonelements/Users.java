package com.xiaoquyi.jsonelements;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement   
public class Users  extends Status {

	public static class User {
		public String name = "";
		public String weibo = "";
		public String qq = "";
		public String email = "";
		public String lastAccess = "";
		public List<String> communities = null;

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
		
		public List<String> addCommunity(String community) {
			if (communities == null)
				communities = new LinkedList<String>();
			communities.add(community);
			return communities;
		}
	}
	@XmlElement
	public List<User> data = null;
	
	public Users() {};
	
	public List<User> addUser(User user) {
		if (data == null) 
			data = new LinkedList<User>();
		data.add(user);
		return data;
		
	}
}