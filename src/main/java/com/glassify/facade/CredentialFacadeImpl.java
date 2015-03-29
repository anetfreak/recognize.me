package com.glassify.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glassify.dao.CredentialDao;
import com.glassify.domain.MyCredential;

@Component
public class CredentialFacadeImpl implements CredentialFacade {
	
	@Autowired
	private CredentialDao credentialDao;

	@Override
	public void saveCredential(MyCredential credential) {
		credentialDao.saveCredential(credential);
	}

	@Override
	public List<MyCredential> getCredentialForUser(String userId) {
		return credentialDao.getCredentialForUser(userId);
	}

}
