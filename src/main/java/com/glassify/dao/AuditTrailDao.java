package com.glassify.dao;

import java.util.List;

import com.glassify.util.AuditTrail;

public interface AuditTrailDao {

	public void save(AuditTrail auditTrail) throws Exception;
	public List<AuditTrail> get();
}
