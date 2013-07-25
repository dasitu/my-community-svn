package com.xiaoquyi.jsonelements;

import javax.xml.bind.annotation.*;


@XmlRootElement
public class Element {

	public Status status = null;
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
