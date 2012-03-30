package eu.neq.mais.request.comet;

import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.DefaultSecurityPolicy;

public class BayeuxAuthenticator extends DefaultSecurityPolicy implements ServerSession.RemoveListener {
	
	@Override
    public boolean canHandshake(BayeuxServer server, ServerSession session, ServerMessage message)          
    {
        if (session.isLocalSession())                                                                       
            return true;

        Map<String, Object> ext = message.getExt();
        if (ext == null)
            return false;

        Map<String, Object> authentication = (Map<String, Object>)ext.get("authentication");
        if (authentication == null)
            return false;

        Object authenticationData = verify(authentication);                                                 
        if (authenticationData == null)
            return false;

        // Authentication successful                                      

        // Link authentication data to the session                                                          

        // Be notified when the session disappears
        session.addListener(this);                                                                          

        return true;
    }

    private Object verify(Map<String, Object> authentication) {
		return true;
	}

	public void removed(ServerSession session, boolean expired)                                             
    {
        // Unlink authentication data from the remote client                                                
    }
}
