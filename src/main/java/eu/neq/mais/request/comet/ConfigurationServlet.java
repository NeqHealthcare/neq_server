//package eu.neq.mais.request.comet;
//
//import java.io.IOException;
//
//import javax.servlet.GenericServlet;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.cometd.bayeux.server.BayeuxServer;
//
//public class ConfigurationServlet extends GenericServlet
//{
//    public void init() throws ServletException
//    {
//        // Grab the Bayeux object
//        BayeuxServer bayeux = (BayeuxServer)getServletContext().getAttribute(BayeuxServer.ATTRIBUTE);
//        new EchoService(bayeux);
//        // Create other services here
//
//        // This is also the place where you can configure the Bayeux object
//        // by adding extensions or specifying a SecurityPolicy
//    }
//
//    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
//    {
//        throw new ServletException();
//    }
//}