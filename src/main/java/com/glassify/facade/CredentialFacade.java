package com.glassify.facade;

import java.util.List;

import com.glassify.domain.MyCredential;

public interface CredentialFacade {
	
	void saveCredential(MyCredential credential);
	List<MyCredential> getCredentialForUser(final String userId);
}
