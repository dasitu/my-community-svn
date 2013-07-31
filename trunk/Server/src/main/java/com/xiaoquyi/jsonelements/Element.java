package com.xiaoquyi.jsonelements;


import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonTypeInfo(use=JsonTypeInfo.Id.NONE, include=JsonTypeInfo.As.PROPERTY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Element {

	public Status status = null;
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
