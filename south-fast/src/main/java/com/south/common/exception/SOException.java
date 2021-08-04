/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.common.exception;

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SOException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public SOException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public SOException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public SOException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public SOException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public SOException(BizCodeEnume enume) {
		super(enume.getMsg());
		this.msg = enume.getMsg();
		this.code = enume.getCode();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
