package com.xiaoquyi.jsonelements;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement   
public class Users  extends Element {

	public List<User> data = null;
	
	public Users() {};
	
	public List<User> addUser(User user) {
		if (data == null) 
			data = new LinkedList<User>();
		data.add(user);
		return data;
		
	}
	
	public void setStatus(Status status) {
		if (data == null) {
			super.setStatus(new Status(10000, -1, "No data!", 10000));
			return;
		}
		super.setStatus(status);
		
		for (User i:data) {
			i.setStatus(null);
		}
	
	}
}