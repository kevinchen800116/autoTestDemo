package com.demo.utils;

public enum ViewEnum {
	
	WebView("WEBVIEW"),
	
	NativeView("NATIVE_APP");
	
	
	private String viewName;
	
	ViewEnum(String string) {
		this.viewName = string;
	}
	
	public String getViewName() {
		return viewName;
	}
	
}
