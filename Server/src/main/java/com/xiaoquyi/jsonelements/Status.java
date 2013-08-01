package com.xiaoquyi.jsonelements;



public class Status {

	public int errcode = 0;
	public int ret = 0;
	public String msg = "ok";
	public int seqid = 0;

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
