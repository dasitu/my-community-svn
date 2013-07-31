package com.xiaoquyi.jsonelements;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


//@XmlRootElement 
public class Elements extends Element {

	public List<Element> data = null;
	
	public Elements() {};
	
	public List<Element> addElement(Element item) {
		if (data == null) 
			data = new LinkedList<Element>();
		data.add(item);
		return data;
		
	}
	
	public void setStatus(Status status) {
		if (data == null) {
			super.setStatus(new Status(10000, -1, "No data!", 10000));
			return;
		}
		super.setStatus(status);
		
		for (Element i:data) {
				i.setStatus(null);
		}
	
	}
	
	public void setElements(List<Element> data) {
		this.data = data;
	}
		
}