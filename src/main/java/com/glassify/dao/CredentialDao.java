package com.glassify.dao;

import java.util.List;

import com.glassify.domain.MyCredential;

public interface CredentialDao {

	void saveCredential(MyCredential credential);
	List<MyCredential> getCredentialForUser(final String userId);
}
