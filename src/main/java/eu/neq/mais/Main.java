package eu.neq.mais;

import org.eclipse.jetty.server.Server;


import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;


public class Main {

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080); 
		
		ServletHolder servletHolder = new ServletHolder(ServletContainer.class); 
		
		servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", 
		        "com.sun.jersey.api.core.PackagesResourceConfig"); 
		servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "eu.neq.mais");
		
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS); 
		context.addServlet(servletHolder, "/rest/*"); 
		
		// INIT THE CONNECTOR
		
		Connector backEndConnector = ConnectorFactory.createConnector();
		
		
		
		server.start(); 
		server.join();
		
		
		/*tryton remote call - test*/
		
		

	}
}