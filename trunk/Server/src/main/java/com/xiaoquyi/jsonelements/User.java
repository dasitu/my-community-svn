package com.xiaoquyi.jsonelements;

import java.util.LinkedList;
import java.util.List;

public class User extends Element{
	public String name = "";
	public String weibo = "";
	public String qq = "";
	public String email = "";
	public String lastAccess = "";
	public List<Element> communities = null;

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

	public List<Element> addCommunity(Community community) {
		if (communities == null)
			communities = new LinkedList<Element>();
		communities.add(community);
		return communities;
	}

	public void setCommunities(List<Element> communities) {
		this.communities = communities;
	}
}
