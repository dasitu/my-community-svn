package com.xiaoquyi.jsonelements;


import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Status {

	public int errcode = 0;
	public int ret = 0;
	public String msg = "ok";
	public int seqid = 0;

	public Status() {};
	
	public void setStatus(int errcode,
			int ret,
			String msg,
			int seqid) {

		this.errcode = errcode;
		this.ret = ret;
		this.msg = msg;
		this.seqid = seqid;
	}
}
