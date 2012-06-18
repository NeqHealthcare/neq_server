package eu.neq.mais.request.comet;


import java.util.HashMap;


import javax.annotation.PostConstruct;
import javax.inject.Inject;


import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import org.cometd.java.annotation.Configure;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;

import org.cometd.server.authorizer.GrantAuthorizer;

import com.google.gson.Gson;

import eu.neq.mais.domain.ChartCoordinateFactory;

@Service("echoService")
public final class EchoService {

	@Inject
	private BayeuxServer server;
	@Session
	private ServerSession serverSession;

	private HashMap<String, Pulse> request = new HashMap<String, Pulse>();

	@PostConstruct
	void init() {
		server.addListener(new BayeuxServer.SubscriptionListener() {
			
			public void unsubscribed(ServerSession arg0, ServerChannel channel) {
				int subscribers = channel.getSubscribers().size();
//				System.out.println("UNsubscribed: "+channel.toString()+" - # of Subscriptions " + subscribers);
				
				if (subscribers == 0) {
//					System.out.println("--> last subscriber, stopping pulse thread for: "+channel.toString());
					
					request.get(channel.toString()).end();
					request.get(channel.toString()).interrupt();
					request.remove(channel.toString());		
				}
			}
			
			public void subscribed(ServerSession arg0, ServerChannel channel) {
//				System.out.println("subscribed: "+channel.toString() + " - # of Subscriptions: "+channel.getSubscribers().size()+ " - # of Listeners: "+channel.getListeners().size());
				
				if (channel.getSubscribers().size() == 1) {
					Pulse pulseThread = new Pulse(channel.toString()); 
					request.put(channel.toString(), pulseThread);
							
//					System.out.println("--> first subscriber, start pulse thread for channel "+channel.toString());
					pulseThread.start();
				}
				
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

	@Configure("/cometd/pulse")
	void pulse(ConfigurableServerChannel channel) {
		channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
	}

	@Listener("/cometd/echo")
	void echo(ServerSession remote, ServerMessage.Mutable message) {
		remote.deliver(serverSession, message.getChannel(), message.getData(),
				null);

	}


	class Pulse extends Thread {
		
		String channel;
		boolean active = true;
		
		public Pulse(String channel) {
			this.channel = channel;
		}
		
		public void run() {
			ChartCoordinateFactory ccf = new ChartCoordinateFactory();
			Heartbeat hb = new Heartbeat();
			int packetId = 0;

			
			while (active) {
				double y = hb.getNextY();
				String json = new Gson().toJson(ccf.getChartCoordinate(System.currentTimeMillis(), y));
							
				server.getChannel(channel).publish(serverSession, json, String.valueOf(packetId++));
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// intentioned exception -> initiates thread termination!
				} 
			}

		}
		
		public void end() {
			active = false;
		}
	};


}
