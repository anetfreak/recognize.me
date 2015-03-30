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
	public MyCredential getCredentialForUser(String userId) {
		List<MyCredential> myCredential = credentialDao.getCredentialForUser(userId);
		if(!myCredential.isEmpty())
			return myCredential.get(0);
		else
			return null;
	}

}
