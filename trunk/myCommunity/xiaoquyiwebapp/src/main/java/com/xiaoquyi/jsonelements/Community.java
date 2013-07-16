package com.xiaoquyi.jsonelements;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement   
public class Community extends Element{

	public String province = "";
	public String city = "";
	public String area = "";
	public String address = "";
	public String name = "";
	public int active = -1;
	public String lastAcess = "";
	
	public Community() {};
	
	public Community(String province,
				  String city,
				  String area,
				  String address,
				  String name,
				  int active,
				  String lastAcess) {
		
		this.province = province;
		this.city = city;
		this.area = area;
		this.address = address;
		this.name = name;
		this.active = active;
		this.lastAcess = lastAcess;
	}
	
	
}