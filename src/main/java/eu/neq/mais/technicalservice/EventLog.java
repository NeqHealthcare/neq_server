package eu.neq.mais.technicalservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Later, we may want to write the event log to a file or something, not the system output stream :)
 * @author seba
 *
 */
public abstract class EventLog {
		
	private static PrintStream eventLog = System.out;
	
	
	public static PrintStream getEventLog() {
		return eventLog;
	}

	public static void setEventLog(PrintStream eventLog) {
		EventLog.eventLog = eventLog;
		init();
	}
	
	public static void setEventLog(String fileName) {
		try {
			EventLog.eventLog = new PrintStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		init();
	}
	
	private static void init() {
		System.setOut(eventLog);
	}
	
	
	

}
