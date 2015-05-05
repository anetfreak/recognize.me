package com.glassify.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glassify.dao.AuditTrailDao;
import com.glassify.domain.AuditTrailResult;
import com.glassify.util.AuditTrail;

@Component
public class AuditTrailFacadeImpl implements AuditTrailFacade{
	
	@Autowired
	private AuditTrailDao auditTrailDao;

	@Override
	public List<AuditTrail> getAllAuditTrail() {
		return auditTrailDao.get();
	}

	@Override
	public void saveAuditTrail(AuditTrail auditTrail) throws Exception {
		auditTrailDao.save(auditTrail);
	}
	
	@Override
	public AuditTrailResult getResultStats() {
		return auditTrailDao.getResultStats();
	}
}
