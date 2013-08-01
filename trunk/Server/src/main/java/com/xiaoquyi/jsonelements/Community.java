package com.xiaoquyi.jsonelements;


public class Community extends Element{
	
	public String address = "";
	public String name = "";
	public int active = -1;
	public String lastAcess = "";
	
	public Community() {};
	
	public Community(
				  String address,
				  String name,
				  int active,
				  String lastAcess) {
		
		this.address = address;
		this.name = name;
		this.active = active;
		this.lastAcess = lastAcess;
	}
}