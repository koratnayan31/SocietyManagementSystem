package com.nayan.helper;

public class UploadMessage {
	private boolean isUploaded;
	private String uploadUrl;
	public boolean isUploaded() {
		return isUploaded;
	}
	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public UploadMessage(boolean isUploaded, String uploadUrl) {
		super();
		this.isUploaded = isUploaded;
		this.uploadUrl = uploadUrl;
	}
	public UploadMessage() {
		super();
	}
	
	
}
