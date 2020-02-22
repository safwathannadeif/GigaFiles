//Config Logging in Details
//http://www.javapractices.com/topic/TopicAction.do?Id=143
//https://www.logicbig.com/tutorials/core-java-tutorial/logging/loading-properties.html
package com.shd.commonref ;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerRef {

		//private final static String nameId="ServDisply" ;
		private  static  Logger servLogDisply = null ;

		public  static synchronized Logger  makeLogRefInit(Level l) 
		{
			if(servLogDisply != null ) return servLogDisply ;
				
			String logFile = "C:/GigaFiles/LogDir/log.log" ;
			Integer logFileSize=1024*1024 ;
			// Create a FileHandler with 1MB file size and a single log file. 
	        // make the handler to append the log message.
			//servLogDisply = Logger.getLogger(nameId); 
			servLogDisply = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			//Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			Objects.requireNonNull(servLogDisply, "NULL!! for servLogDisply" ) ;
			//assert loggerToUse != null ;
			servLogDisply.setUseParentHandlers(false) ;//No Console
			Handler[] handlers = servLogDisply.getHandlers();
			for(Handler handler : handlers) {
				servLogDisply.removeHandler(handler);
			}
			LogFormat logFrmt =new LogFormat() ;
			Handler  consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(logFrmt);
			//consoleHandler.setLevel(Level.ALL);
			consoleHandler.setLevel(l);
			
			System.out.println("makeLogRefInit Done with Level=" + l);
			servLogDisply.addHandler(consoleHandler);
			//
			FileHandler fileTxt = null;
			try {
				fileTxt = new FileHandler(logFile,logFileSize,2, false);
			} catch (SecurityException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Problems with creating the log files:\n" + e);
			}
			
	        fileTxt.setFormatter(logFrmt);
	        fileTxt.setLevel(l);
	   
			servLogDisply.addHandler(fileTxt) ;
			//
			return servLogDisply ;


		}
	public  static Logger makeLogRef()
	{
		//if (servLogDisply== null) makeLogRefInit(Level.INFO) ;
		return servLogDisply ;
	}

	}