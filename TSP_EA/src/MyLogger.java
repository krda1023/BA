

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public void setLogger(){

		Handler fileHandler = null;
		Formatter simpleFormatter = null;
		try{
			Calendar c=Calendar.getInstance();
			Date d= c.getTime();
			DateFormat df= new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
			String s =df.format(d);
			// Creating FileHandler
			fileHandler = new FileHandler("./logfile "+s+".log");
			
			// Creating SimpleFormatter
			simpleFormatter = new SimpleFormatter();
			
			// Assigning handler to logger
			LOGGER.addHandler(fileHandler);
			
			// Logging message of Level info (this should be publish in the default format i.e. XMLFormat)
			LOGGER.info("Finnest message: Logger with DEFAULT FORMATTER");
			
			// Setting formatter to the handler
			fileHandler.setFormatter(simpleFormatter);
			
			// Setting Level to ALL
			fileHandler.setLevel(Level.ALL);
			LOGGER.setLevel(Level.ALL);
			
			// Logging message of Level finest (this should be publish in the simple format)
		
			}
		catch(IOException exception){
			LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
		}
	}
	
	public Logger getLogger() {
		return LOGGER;
	}
	
	public void writeInfo(Object o, Object p) {
		LOGGER.info(String.valueOf(o)+"     "+String.valueOf(p));
	}

}
