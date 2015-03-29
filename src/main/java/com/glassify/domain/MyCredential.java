package com.glassify.domain;

import com.google.api.client.auth.oauth2.Credential;

public class MyCredential {

	private String userId;
	private Credential credential;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Credential getCredential() {
		return credential;
	}
	public void setCredential(Credential credential) {
		this.credential = credential;
	}
}
