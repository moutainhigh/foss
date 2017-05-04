package com.deppon.foss.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jLevelChanger {
	public void setLogLevel(String loggerName, String level) {
		if ("debug".equalsIgnoreCase(level)) {
			Logger.getLogger(loggerName).setLevel(Level.DEBUG);
		} else if ("info".equalsIgnoreCase(level)) {
			Logger.getLogger(loggerName).setLevel(Level.INFO);
		} else if ("error".equalsIgnoreCase(level)) {
			Logger.getLogger(loggerName).setLevel(Level.ERROR);
		} else if ("fatal".equalsIgnoreCase(level)) {
			Logger.getLogger(loggerName).setLevel(Level.FATAL);
		} else if ("warn".equalsIgnoreCase(level)) {
			Logger.getLogger(loggerName).setLevel(Level.WARN);
		}
	}
}
