package eu.neq.mais.request.comet;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.sun.servicetag.SystemEnvironment;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Configure;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;
import org.cometd.server.AbstractService;
import org.cometd.server.authorizer.GrantAuthorizer;

@Service("echoService")
public final class EchoService {

	@Inject private BayeuxServer server;
	@Session private ServerSession serverSession;
	
	@PostConstruct
	void init() {
		server.addListener(new BayeuxServer.SessionListener() {
			
			public void sessionRemoved(ServerSession session, boolean arg1) {
				//System.out.println("SESSION REMOVED");
				server.getChannel("/echo").publish(session, "disconnected !", null);
			}
			
			public void sessionAdded(ServerSession session) {
				//System.out.println("SESSION ADDED");
				server.getChannel("/echo").publish(session, "connected !", null);
			}
		});
		
		server.addListener(new BayeuxServer.ChannelListener() {
			
			public void configureChannel(ConfigurableServerChannel arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void channelRemoved(String arg0) {
				System.out.println("SESSION REMOVED");
				
			}
			
			public void channelAdded(ServerChannel arg0) {
				
			
			}
		});
	}
	
	@Configure("/**")
	void any(ConfigurableServerChannel channel) {
		channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
	}
	
	@Configure("/cometd/echo")
	void echo(ConfigurableServerChannel channel) {
		channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
	}
	
	@Listener("/cometd/echo")
	void echo(ServerSession remote, ServerMessage.Mutable message) {
		System.out.println("echo");
        System.out.println(message);
		remote.deliver(serverSession, message.getChannel(), message.getData(), null);

	}


}
