package eu.neq.mais.request.comet;

import org.cometd.bayeux.Bayeux;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.java.annotation.AnnotationCometdServlet;
import org.cometd.server.CometdServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class HelloWorld {

	public static void main(String[] args) throws Exception {

		Server server = new Server();
		QueuedThreadPool qtp = new QueuedThreadPool();
		qtp.setMinThreads(5);
		qtp.setMaxThreads(200);
		server.setThreadPool(qtp);
		
		SelectChannelConnector connector=new SelectChannelConnector();
		connector.setPort(8081);
        server.addConnector(connector);
        
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

		ServletContextHandler context = new ServletContextHandler(contexts,
				"/", ServletContextHandler.SESSIONS);
		context.setInitParameter("async-enabled", "true");
		context.setAttribute("async-enabled", "true");

		CometdServlet cometdServlet = new AnnotationCometdServlet();
		ServletHolder cometd_holder = new ServletHolder(cometdServlet);
		cometd_holder.setAsyncSupported(true);
		cometd_holder.setInitParameter("timeout", "200000");
		cometd_holder.setInitParameter("interval", "100");
		cometd_holder.setInitParameter("maxInterval", "100000");
		cometd_holder.setInitParameter("multiFrameInterval", "1500");
		cometd_holder.setInitParameter("directDeliver", "true");
		cometd_holder.setInitParameter("logLevel", "0");
		cometd_holder.setInitOrder(1);
		cometd_holder.setInitParameter("services","eu.neq.mais.request.comet.EchoService");
		cometd_holder.setInitParameter("transports","org.cometd.websocket.server.WebSocketTransport");
		context.addServlet(cometd_holder, "/cometd/*");
			
		
//		ServletHolder config = new ServletHolder(ConfigurationServlet.class);
//		config.setInitOrder(2);
//		context.addServlet(config, "");
		
		server.start();
		

	}

	public HelloWorld() throws Exception {

	}

}