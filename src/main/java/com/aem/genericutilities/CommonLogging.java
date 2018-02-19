package com.aem.genericutilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import automation.ConfigurationRead.ConfigurationReader;

/**
 * 
 * @author mkarthik
 * 
 *         This class should have all the functions which are related to logging
 *         using log4j
 * 
 *         please add description for all the methods defined and declared in
 *         this class
 * 
 */
public class CommonLogging {
	public static Logger glb_Logger_commonFunctionLog = null;
	static Properties m_Properties_props = null;
	
	public static Logger getLogObj(Class className) throws Exception {
		glb_Logger_commonFunctionLog = Logger.getLogger(className);
		m_Properties_props = new Properties();
		m_Properties_props.setProperty("log4j.appender.File.file",System.getProperty("user.dir")+ ConfigurationReader.getValue("log_path"));
		m_Properties_props.setProperty("log4j.appender.Html.File",System.getProperty("user.dir")+ ConfigurationReader.getValue("html_log_path"));
		try {
			m_Properties_props.load(new FileInputStream(System.getProperty("user.dir")+ ConfigurationReader.getValue("log_properties_Path")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(m_Properties_props);
		return glb_Logger_commonFunctionLog;
	}
	

	/**
	 * @author Rajdeep 
	 * @date: 13/10/2017
	 * @Description: This function is used to print stack trace in console and execution logs
	 */
	public static void throwableHandler(Logger glb_Logger_commonlogs, Throwable t, String message) {
		//Print Stack Trace in console
		t.printStackTrace();
		
		//Print Stack trace in logs
		glb_Logger_commonlogs.error(message + "  " + t);
		
		//remove timestamp from log to print exception stack trace
		m_Properties_props.setProperty("log4j.appender.File.layout.ConversionPattern", "%m%n");
		PropertyConfigurator.configure(m_Properties_props);
		
		StackTraceElement[] s = t.getStackTrace();
		for(StackTraceElement e : s){
			glb_Logger_commonlogs.error("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t at " + e);
		}
		
		//revert back timestamp in log
		m_Properties_props.setProperty("log4j.appender.File.layout.ConversionPattern", "[%d] [%-5p] [%c %x] - %m%n");		
		PropertyConfigurator.configure(m_Properties_props);
	}
}