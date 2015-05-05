package com.glassify.domain;

import java.util.List;

public class AuditTrailResult {
	
	private List<AuditTrailSuccess> success;
	private List<AuditTrailFailure> failures;
	
	public List<AuditTrailSuccess> getSuccess() {
		return success;
	}
	public void setSuccess(List<AuditTrailSuccess> success) {
		this.success = success;
	}
	public List<AuditTrailFailure> getFailures() {
		return failures;
	}
	public void setFailures(List<AuditTrailFailure> failures) {
		this.failures = failures;
	}
}
