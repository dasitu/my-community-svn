package com.xiaoquyi.jsonelements;
import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement   
public class Notices extends Element {
	
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
