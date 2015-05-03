package com.glassify.facade;

import java.util.List;

import com.glassify.util.AuditTrail;

public interface AuditTrailFacade {

	public void saveAuditTrail(AuditTrail auditTrail) throws Exception;
	public List<AuditTrail> getAllAuditTrail();
}
