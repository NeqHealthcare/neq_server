package eu.neq.mais;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.eclipse.jetty.http.ssl.SslContextFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ConnectHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
//import org.eclipse.jetty.util.ssl.SslContextFactory;

//import org.eclipse.jetty.util.ssl.SslContextFactory;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.request.comet.CometdServer;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.Monitor;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.Settings;

/**
 * 
 * @author Jan Gansen
 * 
 */
public class NeqServer implements Runnable {

	private static NeqServer instance = null;
	private static SessionStore sessionStore = new SessionStore();
	private static int port = 8080;
	private static Logger logger = null;

	private Server server = null;

	public static NeqServer getInstance() {
		if (instance == null) {
			instance = new NeqServer();
		}
		return instance;
	}

	public NeqServer() {

	}

	public void initLogger() {
		logger = Logger.getLogger("eu.neq.mais.Main");
		logger.addHandler(FileHandler.getLogFileHandler(Settings.LOG_FILE_MAIN));

	}

	public void initHttpServer() {
		server = new Server();

		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);

		// default connector
		connector.setMaxIdleTime(30000);
		connector.setRequestHeaderSize(8192);

		// possible solution of error with to many patients
		connector.setRequestBufferSize(128000);
		connector.setResponseBufferSize(128000);

		// secure connector
		SslSelectChannelConnector sec_connector = new SslSelectChannelConnector();
		sec_connector.setMaxIdleTime(30000);
		sec_connector.setRequestHeaderSize(8192);
		sec_connector.setRequestBufferSize(128000);
		sec_connector.setResponseBufferSize(128000);
		sec_connector.setPort(8081);

		sec_connector.setKeystore(Settings.SSL_KEYFILE);

		// Only used for development purposes - Does not have to be removed
		sec_connector.setKeyPassword("qiQ6SLs6oIe2sDXoqPiE");
		sec_connector.setTrustPassword("qiQ6SLs6oIe2sDXoqPiE");

		server.setConnectors(new Connector[] { connector, sec_connector });

		ServletHolder servletHolder = new ServletHolder(ServletContainer.class);

		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.packages",
				"eu.neq.mais.request");

		ServletContextHandler context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);
		context.addServlet(servletHolder, "/*");
	}

	public void initConnectors() {
		Map<String, Backend> configuredBackends = null;
		
		try {
			configuredBackends = FileHandler.getBackendMap();
		} catch (IOException e) {
			logger.info("Cannot find backend configuration");
			e.printStackTrace();
		}
		
		for (String b : configuredBackends.keySet()) {
			try {
				eu.neq.mais.connector.Connector x = ConnectorFactory.getConnector(b);
				x.init();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

	public void run() {
		this.stop();

		initLogger();
		initConnectors();
		
		initHttpServer();

		CometdServer.start();

		try {
			// Starts the NEQ MAIS
			server.start();

			// Starts the Monitoring Activities
			Thread monitoringThread = new Thread(new Monitor());
			monitoringThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		if (server != null) {
			try {
				server.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		NeqServer server = NeqServer.getInstance();
		server.run();

	}

	public static SessionStore getSessionStore() {
		return sessionStore;
	}

	public static void setSessionStore(SessionStore sessionStore) {
		NeqServer.sessionStore = sessionStore;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		NeqServer.port = port;
	}

}