package eu.neq.mais.request.comet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletResponse;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.java.annotation.ServerAnnotationProcessor;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.authorizer.GrantAuthorizer;

public class CometdDemoServlet extends GenericServlet {

	@Override
    public void init() throws ServletException
    {
		super.init();
		final BayeuxServerImpl bayeux=(BayeuxServerImpl)getServletContext().getAttribute(BayeuxServer.ATTRIBUTE);
		
		if (bayeux==null)
            throw new UnavailableException("No BayeuxServer!");
		
        bayeux.createIfAbsent("/**",new ServerChannel.Initializer()
        {
            public void configureChannel(ConfigurableServerChannel channel)
            {
                channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
            }
        });
        
        bayeux.getChannel(ServerChannel.META_HANDSHAKE).addAuthorizer(GrantAuthorizer.GRANT_ALL);
		
        ServerAnnotationProcessor processor = new ServerAnnotationProcessor(bayeux);
        processor.process(new EchoService());
        processor.process(new Monitor());
    }
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		Enumeration<String> x = arg0.getAttributeNames();
		
		((HttpServletResponse)arg1).sendError(593);
		
	}
	
    @Override
    public void destroy()
    {
        super.destroy();
    }

}
