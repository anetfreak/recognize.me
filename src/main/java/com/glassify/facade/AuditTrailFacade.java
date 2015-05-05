package com.glassify.facade;

import java.util.List;

import com.glassify.domain.AuditTrailResult;
import com.glassify.util.AuditTrail;

public interface AuditTrailFacade {

	void saveAuditTrail(AuditTrail auditTrail) throws Exception;
	List<AuditTrail> getAllAuditTrail();
	AuditTrailResult getResultStats();
}
