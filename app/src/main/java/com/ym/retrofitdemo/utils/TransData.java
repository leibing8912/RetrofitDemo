package com.ym.retrofitdemo.utils;

/**
 * @className : TransData.java
 * @classDescription : Http请求返回信息类
 * @author : AIDAN SU
 * @createTime : 2014-4-1
 * 
 */
public class TransData {
	
	/**
	 * 接口返回响应代码
	 */
	private String code;
	/**
	 * 接口返回结果
	 */
	private String result;
	/**
	 * 接口返回错误信息
	 */
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
