package com.glassify.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class MyLogger {
	private static PrintWriter writer = null;
/*	private static LogManager lm = LogManager.getLogManager();
	private static Logger logger = Logger.getLogger("RecognizeMe Logs");;
	private static FileHandler fh = null;
	
	public static Logger getLogger(){
		try {
			//if(fh == null){
				logger.setUseParentHandlers(false);
				 fh = new FileHandler("/tmp/Application_log.txt");
				 lm.addLogger(logger);
				 logger.setLevel(Level.INFO);
				 fh.setFormatter(new SimpleFormatter());
				 logger.addHandler(fh);
			//}
			return logger;
		} catch (Exception e) {
	        System.out.println("Exception thrown: " + e);
	        e.printStackTrace();
	    }
		return logger;
	}*/
	public static PrintWriter getWriter() {
		try {
			if(writer == null){ 
				File file = new File("/tmp/recognizeme.log");
				writer = new PrintWriter(file, "UTF-8");
				//writer.println("The first line");
				//writer.println("The second line");
				//writer.close();
				return writer;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	}
	
}