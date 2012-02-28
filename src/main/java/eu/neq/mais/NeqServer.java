package eu.neq.mais;

import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.Monitor;
import eu.neq.mais.technicalservice.Settings;

/**
 * 
 * @author Jan Gansen
 *
 */
public class NeqServer implements Runnable {
	
	private static NeqServer instance = null;
	
	private Server server = null;
	
	public static NeqServer getInstance(){
		if(instance == null){
			instance = new NeqServer();
		}
		return instance;
	}
	
	public NeqServer(){
		
	}
	
	public void run() {
		this.stop();
		
		Logger logger = Logger.getLogger("eu.neq.mais.Main");
		logger.addHandler(FileHandler.getLogFileHandler(Settings.LOG_FILE_MAIN));
		
		logger.info("Setting up Server - Port 8080");
		server = new Server(8080); 
		
		ServletHolder servletHolder = new ServletHolder(ServletContainer.class); 
		
		
		servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", 
		        "com.sun.jersey.api.core.PackagesResourceConfig"); 
		servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "eu.neq.mais.request");
		
		
		
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS); 
		context.addServlet(servletHolder, "/*"); 
	
		
		logger.info("starting server");
		try {
			//Starts the NEQ MAIS
			server.start();
			
			
			//Starts the Monitoring Activities
			Thread monitoringThread = new Thread( new Monitor() ); 
			monitoringThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		logger.info("joining server");
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
	
	public void stop(){
		if(server != null){
			try {
				server.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[]  args){
		NeqServer server = NeqServer.getInstance();
		server.run();
		
	}
}