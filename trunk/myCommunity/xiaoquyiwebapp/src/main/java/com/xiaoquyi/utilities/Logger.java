package com.xiaoquyi.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {

	private static File logFile = null;
	private static loggingLevel ll = loggingLevel.DEBUG;
	private final static String INFOR_PROMPT = "[INFO]:\n";
	private final static String ERROR_PROMPT = "[ERROR]:\n";
	private final static String WARNING_PROMPT = "[WARNING]:\n";
	private final static String DEBUG_PROMPT = "[DEBUG]:\n";
	private final static String LOG_FILE_NAME = "API_access.log";
	
	private static boolean setLogFile() {
		logFile = new File(LOG_FILE_NAME);
		try {
			if (!logFile.exists())
				logFile.createNewFile();
			return true;
		}catch(IOException ie) {
			System.out.println(ie.getMessage());
			return false;
		}
	}
	
	private static FileWriter getFileWriter() {
		
		try {
			return new FileWriter(logFile,true);
		}catch(IOException ie) {
			System.out.println(ie.getMessage());
			return null;
		}
	}
	
	private static void doLogWritting(String prompt,String content) throws IOException {
		setLogFile();
		writeDateTime();
		FileWriter fw = getFileWriter();
		fw.write(prompt);
		if (content.toCharArray()[0] != '\t')
			content = "\t" + content;
		if (!content.endsWith("\n"))
			content += "\n";
		fw.write(content);
		fw.close();
		
	}
	
	private static void writeDateTime() throws IOException {
		Date dt = new Date();
		FileWriter fw = getFileWriter();
		fw.write(String.format("[%s]\n",dt.toString()));
		fw.close();
	}
	
	public enum loggingLevel {
		NOTHING,
		ERROR,
		WARNING,
		INFO,
		DEBUG
	}
	
	public void setLogLevel(loggingLevel ll) {
		Logger.ll = ll;
	}
	
	
	
	public static void infoWritting(String tw) throws IOException {
		
		if (ll.ordinal() < loggingLevel.INFO.ordinal()) {
			return;
		}
		
		doLogWritting(INFOR_PROMPT,tw);
	}
	
	public static void errorWritting(String tw) throws IOException {
		
		if (ll.ordinal() < loggingLevel.ERROR.ordinal()) {
			return;
		}
		
		doLogWritting(ERROR_PROMPT,tw);
		
	}
	
	public static void debugWritting(String tw) throws IOException {
		
		if (ll.ordinal() < loggingLevel.DEBUG.ordinal()) {
			return;
		}
		
		doLogWritting(DEBUG_PROMPT,tw);
		
	}
	
	public static void warningWritting(String tw) throws IOException {
		
		if (ll.ordinal() < loggingLevel.WARNING.ordinal()) {
			return;
		}
		
		doLogWritting(WARNING_PROMPT,tw);
	}

}
