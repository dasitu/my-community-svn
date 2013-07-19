package com.xiaoquyi.jsonelements;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement  
public class Status  extends Element  {

	public int errcode;
	public int ret;
	public String msg;
	public int seqid;

	public Status() {};

	public Status(int errcode,
			int ret,
			String msg,
			int seqid) {

		this.errcode = errcode;
		this.ret = ret;
		this.msg = msg;
		this.seqid = seqid;
	}
}
