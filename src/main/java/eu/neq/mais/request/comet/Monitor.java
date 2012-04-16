package eu.neq.mais.request.comet;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;

@Service("monitor")
public class Monitor
{
    @Listener("/meta/subscribe")
    public void monitorSubscribe(ServerSession session, ServerMessage message)
    {
        System.out.println("Monitored Subscribe from "+session+" for "+message.get(Message.SUBSCRIPTION_FIELD));
    }

    @Listener("/meta/unsubscribe")
    public void monitorUnsubscribe(ServerSession session, ServerMessage message)
    {
    	System.out.println("Monitored Unsubscribe from "+session+" for "+message.get(Message.SUBSCRIPTION_FIELD));
    }

    @Listener("/meta/*")
    public void monitorMeta(ServerSession session, ServerMessage message)
    {
    	System.out.println(message.toString());
    }
}
