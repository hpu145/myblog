package com.kaishengit.entity;

public class ResultJson {
	public static final String JSON_STATE_SUCCESS = "success";
	public static final String JSON_STATE_ERROR = "error";

	private String state;
	private String message;
	private Object data;
	
	public ResultJson() {
		
	}
	public ResultJson(Object data) {
		this.state = JSON_STATE_SUCCESS;
		this.data = data;
	}
	
	public ResultJson(String message) {
		this.state = JSON_STATE_ERROR;
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
