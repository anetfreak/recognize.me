package com.glassify.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class AuditTrail implements Serializable {
	private final static long serialVersionUID = -62818723;

	String type;
	String email;
	String latitude;
	String longitude;
	long fileSize;
	boolean matched;
	String brand;
	boolean adFound;
	String adText;
	boolean getCredential;
	boolean adPosted;
	Date startTimeUpload;
	Date endTimeUpload;
	Date startTimeMatch;
	Date endTimeMatch;
	Date startTimeAd;
	Date endtimeAd;

	public AuditTrail() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		type = "";
		email = "";
		latitude = "";
		longitude = "";
		fileSize = 0;
		matched = false;
		brand = "";
		adFound = false;
		adText = "";
		try {
			startTimeUpload = dateFormat.parse("01/01/1997 00:00:00");
			endTimeUpload = dateFormat.parse("01/01/1997 00:00:00");
			startTimeMatch = dateFormat.parse("01/01/1997 00:00:00");
			endTimeMatch = dateFormat.parse("01/01/1997 00:00:00");
			startTimeAd = dateFormat.parse("01/01/1997 00:00:00");
			endtimeAd = dateFormat.parse("01/01/1997 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		getCredential = false;
		adPosted = false;
	}

	public boolean isAdPosted() {
		return adPosted;
	}

	public void setAdPosted(boolean isAdPosted) {
		this.adPosted = isAdPosted;
	}
	
	public boolean isGetCredential() {
		return getCredential;
	}

	public void setGetCredential(boolean GetCredential) {
		this.getCredential = GetCredential;
	}

	//@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartTimeUpload() {
		return startTimeUpload;
	}

	//@JsonDeserialize(using = JsonDateDeSerializer.class)
	public void setStartTimeUpload(Date startTimeUpload) {
		this.startTimeUpload = startTimeUpload;
	}

	//@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndTimeUpload() {
		return endTimeUpload;
	}

	//@JsonDeserialize(using = JsonDateDeSerializer.class)
	public void setEndTimeUpload(Date endTimeUpload) {
		this.endTimeUpload = endTimeUpload;
	}

	//@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartTimeMatch() {
		return startTimeMatch;
	}

	//@JsonDeserialize(using = JsonDateDeSerializer.class)
	public void setStartTimeMatch(Date startTimeMatch) {
		this.startTimeMatch = startTimeMatch;
	}

	//@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndTimeMatch() {
		return endTimeMatch;
	}

	//@JsonDeserialize(using = JsonDateDeSerializer.class)
	public void setEndTimeMatch(Date endTimeMatch) {
		this.endTimeMatch = endTimeMatch;
	}

	//@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartTimeAd() {
		return startTimeAd;
	}

	//@JsonDeserialize(using = JsonDateDeSerializer.class)
	public void setStartTimeAd(Date startTimeAd) {
		this.startTimeAd = startTimeAd;
	}

	//@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndtimeAd() {
		return endtimeAd;
	}

	//@JsonDeserialize(using = JsonDateDeSerializer.class)
	public void setEndtimeAd(Date endtimeAd) {
		this.endtimeAd = endtimeAd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public boolean isAdFound() {
		return adFound;
	}

	public void setAdFound(boolean adFound) {
		this.adFound = adFound;
	}

	public String getAdText() {
		return adText;
	}

	public void setAdText(String adText) {
		this.adText = adText;
	}

	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public long getFileSize() {
		return fileSize;
	}

	/*file size in KB*/
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        mapper.configure(Feature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        mapper.setDateFormat(df);  // this works for outbounds but has no effect on inbounds
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        mapper.getDeserializationConfig().withDateFormat(df); // Gave this a shot but still does not sniff strings for a format that we declare should be treated as java.util.Date                           
        
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
}