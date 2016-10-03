package org.usfirst.frc.team4141.MDRobotBase.eventmanager;



import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotConfigurationNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotNotification;


//TODO:  clean up dependencies.  Dependencies should be to Notification class, not RobotNotification class, see MSee
//TODO:  Refactor to remove session and manage dictionary of sockets instead, see MSee
/**
 * @author RobotC
 *
 */
public class EventManager {
	private final EventManager self = this;
	private long nextMessageID = 0;
	private MessageHandler handler;
	private ArrayBlockingQueue<RobotNotification>outbound= new ArrayBlockingQueue<RobotNotification>(100);
	private ArrayBlockingQueue<String>inbound= new ArrayBlockingQueue<String>(100);

	public EventManager(MessageHandler handler){
		this(handler,false);
	}
	public EventManager(MessageHandler handler,boolean enableWebSockets){
		this.enableWebSockets = enableWebSockets;
		sessions = new ArrayList<Session>();
		this.handler = handler;
	}
	

	public void start() throws Exception{
		if(enableWebSockets){
			int port = 5808;
			System.out.println("Initializing socket server at port "+port);
			Server server = new Server(port);
			WebSocketHandler wsHandler = new WebSocketHandler()
		    {
		        @Override
		        public void configure(WebSocketServletFactory factory)
		        {
		            factory.setCreator(new EventManagerWebSocketCreator(self));
		        }
		    };
			server.insertHandler(wsHandler);
			server.start();
		}

//		server.join();  //blocking all until server is stopped
	}
	public void enableWebSockets(){
		this.enableWebSockets = true;
	}
	
	public void disableWebSockets(){
		this.enableWebSockets = false;
	}	
	
	public synchronized void post(RobotNotification notification){
		try{
			outbound.add(notification);
//			System.out.printf("Message Added to outbound queue, now (%d)\n", outbound.size());
//			System.out.println(notification.toJSON());
		}
		catch(IllegalStateException e)
		{
			System.out.println("queue is full");
			System.out.println(e.getMessage());
		}
	}
	public synchronized  void process(){
		String message;
		while((message = inbound.poll())!=null){
//			System.out.println("received message "+message);
			if(handler!=null){
				handler.process(message);
			}
		}
	}
	public synchronized  void post(){
		RobotNotification notification;
		while((notification = outbound.poll())!=null){
//			System.out.println("outbound queue has a message to post");
//			System.out.println(notification.toJSON());
			
			notification.setMessageId(nextMessageID++);
			if(notification.showJavaConsole()){
				System.out.println(notification);
			}
			if(enableWebSockets){
				if(notification.broadcast()){
					try {
						for(Session session : sessions){
							if(session!=null && session.isOpen()){
//								System.out.printf("sending session %s message: \n%s\n",session.getRemoteAddress().toString(),notification.toJSON());
								session.getRemote().sendString(notification.toJSON());
							}
						}
					} catch (Exception e) {
						System.err.println("unable to post: "+notification.toJSON());
					}
				}	
			}
		}
	}

	private ArrayList<Session> sessions;
	private boolean enableWebSockets = false;
	public synchronized boolean addSession(Session session) {
		System.out.println("session added");
		return sessions.add(session);
	}
	public synchronized void removeSession(Session session) {
		if(session !=null && sessions!=null && sessions.contains(session)) sessions.remove(session);
	}

	public synchronized boolean isWebSocketsEnabled() {
		return this.enableWebSockets;
	}


	public  synchronized MessageHandler getHandler() {
		return this.handler;
	}
	public synchronized void connected() {
		//TODO:  refactor so that the code below is handled in the Message Handler's connect method, see MSee.
		System.out.printf("connected! %d sessions\n",sessions.size());
		post(new RobotConfigurationNotification(getHandler().geRobot(),true));
	}
	public  void process(String message) {
		try {
			inbound.put(message);
		} catch (InterruptedException e) {
			System.err.println("unable to receive: "+message);
		}
		
	}
	

}
