package com.glassify.dao;

import java.util.List;

import com.glassify.domain.AuditTrailResult;
import com.glassify.util.AuditTrail;

public interface AuditTrailDao {

	void save(AuditTrail auditTrail) throws Exception;
	List<AuditTrail> get();
	AuditTrailResult getResultStats(); 
}
