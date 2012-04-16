//package eu.neq.mais.request.comet;
//
//import java.io.IOException;
//
//import javax.servlet.GenericServlet;
//import javax.servlet.ServletContextAttributeEvent;
//import javax.servlet.ServletContextAttributeListener;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.cometd.bayeux.Bayeux;
//import org.cometd.bayeux.server.BayeuxServer;
//
//public class BayeuxInitializer implements ServletContextAttributeListener {
//
//	 public void attributeAdded(ServletContextAttributeEvent event)
//	    {
//	        System.out.println(event.getName());
//	        {
//	            // Grab the Bayeux object
//	            BayeuxServer bayeux = (BayeuxServer)event.getValue();
//	            BayeuxAuthenticator authenticator = new BayeuxAuthenticator();
//	            bayeux.setSecurityPolicy(authenticator);
//	            
//	            EchoService e = new EchoService(bayeux);
//	            
//	            // Create other services here
//
//	            // This is also the place where you can configure the Bayeux object
//	            // by adding extensions or specifying a SecurityPolicy
//	        }
//	    }
//
//
//
//		public void attributeRemoved(ServletContextAttributeEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void attributeReplaced(ServletContextAttributeEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//}
