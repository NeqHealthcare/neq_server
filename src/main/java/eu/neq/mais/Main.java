package eu.neq.mais;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;


import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.Settings;


public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Logger logger = Logger.getLogger("eu.neq.mais.Main");
		logger.addHandler(FileHandler.getLogFileHandler(Settings.LOG_FILE_MAIN));
		
		logger.info("Setting up Server - Port 8080");
		Server server = new Server(8080); 
		
		ServletHolder servletHolder = new ServletHolder(ServletContainer.class); 
		
		
		servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", 
		        "com.sun.jersey.api.core.PackagesResourceConfig"); 
		servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "eu.neq.mais.request");
		
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS); 
		context.addServlet(servletHolder, "/*"); 
		
		logger.info("starting server");
		server.start(); 
		
		logger.info("joining server");
		server.join();

	}
}