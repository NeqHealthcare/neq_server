package eu.neq.mais.request.comet;

import org.cometd.bayeux.Bayeux;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.java.annotation.AnnotationCometdServlet;
import org.cometd.server.CometdServlet;
import org.cometd.server.DefaultSecurityPolicy;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class HelloWorld {

	public static void main(String[] args) throws Exception {

		int port = 8081;
		Server server = new Server();
				
		QueuedThreadPool qtp = new QueuedThreadPool();
		qtp.setMinThreads(5);
		qtp.setMaxThreads(200);
		server.setThreadPool(qtp);
		
		SelectChannelConnector connector=new SelectChannelConnector();
		connector.setPort(port);
        connector.setPort(port);
        connector.setMaxIdleTime(120000);
        connector.setLowResourcesMaxIdleTime(60000);
        connector.setLowResourcesConnections(20000);
        connector.setAcceptQueueSize(5000);
        server.addConnector(connector);
        SocketConnector bconnector=new SocketConnector();
        bconnector.setPort(port+1);
        server.addConnector(bconnector);
        server.addConnector(connector);
        
       
       
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);      
		ServletContextHandler context = new ServletContextHandler(contexts,
				"/", ServletContextHandler.SESSIONS);
		server.setHandler(context);
		
		ServletHolder dftServlet = context.addServlet(DefaultServlet.class, "/");
        dftServlet.setInitOrder(1);
		
		
		
        CometdServlet cometdServlet = new AnnotationCometdServlet();
        ServletHolder comet = new ServletHolder(cometdServlet);
        comet.setInitParameter("timeout", "200000");
        comet.setInitParameter("interval", "100");
        comet.setInitParameter("maxInterval", "100000");
        comet.setInitParameter("multiFrameInterval", "1500");
        comet.setInitParameter("directDeliver", "true");
        comet.setInitParameter("logLevel", "1");
       comet.setInitParameter("services","eu.neq.mais.request.comet.EchoService");
        comet.setInitParameter("transports","org.cometd.websocket.server.WebSocketTransport");
        comet.setInitOrder(2);
        context.addServlet(comet, "/cometd/*");
        
//        ServletHolder demo=context.addServlet(CometdDemoServlet.class, "/demo/*");
//        demo.setInitOrder(3);
		
//		context.addServlet(ConfigurationServlet.class, "");
	//	server.addEventListener(new BayeuxInitializer());
		
		server.start();
		
		 BayeuxServer bayeux = cometdServlet.getBayeux();
	     bayeux.setSecurityPolicy(new DefaultSecurityPolicy());
	
		
	}

	public HelloWorld() throws Exception {

	}

}