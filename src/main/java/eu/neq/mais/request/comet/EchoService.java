package eu.neq.mais.request.comet;

import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Configure;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;
import org.cometd.server.AbstractService;
import org.cometd.server.authorizer.GrantAuthorizer;

@Service("echoService")
public class EchoService {

	 @Session
     private ServerSession _session;

     @SuppressWarnings("unused")
     @Configure("/service/echo")
     private void configureEcho(ConfigurableServerChannel channel)
     {
         channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
     }

     @Listener("/service/echo")
     public void doEcho(ServerSession session, ServerMessage message)
     {
         Map<String,Object> data = message.getDataAsMap();
         System.out.println("ECHO from "+session+" "+data);
         session.deliver(_session, message.getChannel(), data, null);
     }


}
