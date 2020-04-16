package com.example.video.util;


public class HTTPStatusResponse {

	private int statusCode;
	private String description;
	private Object data;

	public HTTPStatusResponse(int statusCode, String description, Object data) {
		super();
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getDescription() {
		return description;
	}

	public Object getData() {
		return data;
	}

	public HTTPStatusResponse(int statusCode, String description) {
		this.statusCode = statusCode;
		this.description = description;
	}

}
