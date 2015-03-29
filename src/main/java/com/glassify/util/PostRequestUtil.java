package com.glassify.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class PostRequestUtil {
	private String strUrl;
	private String strValues;
	
  	public Boolean post() throws Exception {
	    URL url = new URL(strUrl);
	    URLConnection conn = url.openConnection();
	    conn.setDoOutput(true);
	    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	
	    writer.write(strValues);
	    writer.flush();
	    String line;
	    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    while ((line = reader.readLine()) != null) {
	      System.out.println(line);
	    }
	    writer.close();
	    reader.close();
	    return true;
  	}

	public String getUrl() {
		return strUrl;
	}

	public void setUrl(String strUrl) {
		this.strUrl = strUrl;
	}

	public String getStrValues() {
		return strValues;
	}

	public void setStrValues(String strValues) {
		this.strValues = strValues;
	}
}