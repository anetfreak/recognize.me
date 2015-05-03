package com.glassify.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.glassify.util.AuditTrail;

@Component
public class AuditTrailDaoImpl implements AuditTrailDao {

	@Autowired
	private DataSource dataSource;
	
	final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	@Override
	public void save(AuditTrail auditTrail) throws Exception {
		String query = "insert into audit_trail (type, email, latitude, longitude, fileSize, "
				+ "matched, brand, adFound, adText, GetCredential, adPosted, startTimeUpload, endTimeUpload"
				+ ", startTimeMatch, endTimeMatch, startTimeAd, endtimeAd) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] args = new Object[] { auditTrail.getType(), auditTrail.getEmail(), auditTrail.getLatitude(), auditTrail.getLongitude(),
				auditTrail.getFileSize(), auditTrail.isMatched(), auditTrail.getBrand(), auditTrail.isAdFound(), auditTrail.getAdText(),
				auditTrail.isGetCredential(), auditTrail.isAdPosted(), auditTrail.getStartTimeUpload(), auditTrail.getEndTimeUpload(),
				auditTrail.getStartTimeMatch(), auditTrail.getEndTimeMatch(), auditTrail.getStartTimeAd(), auditTrail.getEndtimeAd()};
		
		int out = jdbcTemplate.update(query, args);
	}

	@Override
	public List<AuditTrail> get() {
		String query = "select type, email, latitude, longitude, fileSize, "
				+ "matched, brand, adFound, adText, GetCredential, adPosted, startTimeUpload, endTimeUpload"
				+ ", startTimeMatch, endTimeMatch, startTimeAd, endtimeAd from audit_trail";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<AuditTrail> auditTrailList = new ArrayList<AuditTrail>();

		List<Map<String, Object>> auditTrailRows = jdbcTemplate.queryForList(query);

		for (Map<String, Object> auditTrailRow : auditTrailRows) {
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setType(String.valueOf(auditTrailRow.get("type")));
			auditTrail.setEmail(String.valueOf(auditTrailRow.get("email")));
			auditTrail.setLatitude(String.valueOf(auditTrailRow.get("latitude")));
			auditTrail.setLongitude(String.valueOf(auditTrailRow.get("longitude")));
			auditTrail.setFileSize(Long.parseLong(String.valueOf(auditTrailRow.get("fileSize"))));
			auditTrail.setMatched(Boolean.parseBoolean(String.valueOf(auditTrailRow.get("matched"))));
			auditTrail.setBrand(String.valueOf(auditTrailRow.get("brand")));
			auditTrail.setAdFound(Boolean.parseBoolean(String.valueOf(auditTrailRow.get("adFound"))));
			auditTrail.setAdText(String.valueOf(auditTrailRow.get("adText")));
			auditTrail.setGetCredential(Boolean.parseBoolean(String.valueOf(auditTrailRow.get("GetCredential"))));
			auditTrail.setAdPosted(Boolean.parseBoolean(String.valueOf(auditTrailRow.get("adPosted"))));
			try {
				auditTrail.setStartTimeUpload(dateFormat.parse(String.valueOf(auditTrailRow.get("startTimeUpload"))));
				auditTrail.setEndTimeUpload(dateFormat.parse(String.valueOf(auditTrailRow.get("endTimeUpload"))));
				auditTrail.setStartTimeMatch(dateFormat.parse(String.valueOf(auditTrailRow.get("startTimeMatch"))));
				auditTrail.setEndTimeMatch(dateFormat.parse(String.valueOf(auditTrailRow.get("endTimeMatch"))));
				auditTrail.setStartTimeAd(dateFormat.parse(String.valueOf(auditTrailRow.get("startTimeAd"))));
				auditTrail.setEndtimeAd(dateFormat.parse(String.valueOf(auditTrailRow.get("endtimeAd"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			auditTrailList.add(auditTrail);
		}
		return auditTrailList;
	}

}
