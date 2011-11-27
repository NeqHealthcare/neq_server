package eu.neq.mais.technicalservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Logger;

/**
 * Later, we may want to write the event log to a file or something, not the system output stream :)
 * @author seba
 *
 */
public abstract class EventLogger extends Logger {
		
	protected EventLogger(String arg0, String arg1) {
		super("","");
		
	}

	private static PrintStream eventLog = System.out;
	
	
	public static PrintStream getEventLog() {
		return eventLog;
	}

	public static void setEventLog(PrintStream eventLog) {
		EventLogger.eventLog = eventLog;
		init();
	}
	
	public static void setEventLog(String fileName) {
		try {
			EventLogger.eventLog = new PrintStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		init();
	}
	
	private static void init() {
		System.setOut(eventLog);
	}
	
	
	

}
