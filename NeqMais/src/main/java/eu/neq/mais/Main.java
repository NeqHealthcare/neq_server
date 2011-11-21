package eu.neq.mais;

import java.io.File;
import java.net.URL;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
public class Main {
    private static final Logger LOG = Log.getLogger(Main.class);

        
      public static void main(String[] args) throws Exception
      {
  
          //The port that we should run on can be set into an environment variable
          //Look for that variable and default to 8080 if it isn't there.
          String port = System.getenv("PORT");
          if(port == null || port.isEmpty()) {
              port = "8080";
          }
          
    	  Server server = new Server(Integer.valueOf(port));
  
          ResourceHandler resource_handler = new ResourceHandler();
          resource_handler.setDirectoriesListed(true);
          resource_handler.setWelcomeFiles(new String[]{ "index.html" });
  
          resource_handler.setResourceBase(".");
          LOG.info("serving " + resource_handler.getBaseResource());
          
          HandlerList handlers = new HandlerList();
          handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
          server.setHandler(handlers);
  
          server.start();
          server.join();
      }
      

}
