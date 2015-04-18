package com.glassify.util;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	private static LogManager lm = LogManager.getLogManager();
	private static Logger logger = Logger.getLogger("RecognizeMe Logs");;
	private static FileHandler fh = null;
	public static Logger getLogger(){
		try {
			if(fh == null){
				 fh = new FileHandler("Application_log.txt");
				 lm.addLogger(logger);
				 logger.setLevel(Level.INFO);
				 fh.setFormatter(new SimpleFormatter());
				 logger.addHandler(fh);
			}
			return logger;
		} catch (Exception e) {
	        System.out.println("Exception thrown: " + e);
	        e.printStackTrace();
	      }
		return logger;
	}
}