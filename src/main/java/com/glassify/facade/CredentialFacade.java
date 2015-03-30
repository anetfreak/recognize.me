package com.glassify.facade;

import com.glassify.domain.MyCredential;

public interface CredentialFacade {
	
	void saveCredential(MyCredential credential);
	MyCredential getCredentialForUser(final String userId);
}
