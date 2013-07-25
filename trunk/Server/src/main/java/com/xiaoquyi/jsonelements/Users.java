package com.xiaoquyi.jsonelements;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement   
public class Users  extends Element {

	public static class User {
		public String name = "";
		public String weibo = "";
		public String qq = "";
		public String email = "";
		public String lastAccess = "";
		public List<Community> communities = null;

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
		
		public List<Community> addCommunity(Community community) {
			if (communities == null)
				communities = new LinkedList<Community>();
			communities.add(community);
			return communities;
		}
		
		public void setCommunities(List<Community> communities) {
			this.communities = communities;
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