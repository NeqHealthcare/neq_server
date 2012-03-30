package eu.neq.mais.request.comet;

import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.AbstractService;


public class EchoService extends AbstractService {

	public EchoService(BayeuxServer bayeux) {
		super(bayeux, "echo");
		addService("/echo", "processEcho");
	}
	
	public void processEcho(ServerSession remote, Map<String, Object> data) {
		remote.deliver(getServerSession(), "/echo", data, null);
	}

}
