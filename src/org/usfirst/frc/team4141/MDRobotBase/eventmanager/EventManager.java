package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * @author RobotC
 *
 */
public class EventManager {
	private final EventManager self = this;
	private long nextMessageID = 0;
	private MessageHandler handler;
	private ArrayBlockingQueue<Notification>outbound= new ArrayBlockingQueue<Notification>(100);
	private ArrayBlockingQueue<String>inbound= new ArrayBlockingQueue<String>(100);
	private Hashtable<String,EventManagerWebSocket> remotes;
	private int port = 5808;
	
	public EventManager(MessageHandler handler){
		this(handler,false);
	}
	public EventManager(MessageHandler handler,boolean enableWebSockets){
		this.enableWebSockets = enableWebSockets;
		remotes = new Hashtable<String,EventManagerWebSocket>();
		this.handler = handler;
	}
	

	public void start() throws Exception{
		if(enableWebSockets){
			
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
	
	public synchronized void post(Notification notification){
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
			if(handler!=null){
				handler.process(message);
			}
		}
	}
	
	public synchronized  void post(){
		Notification notification;
//		System.out.printf("checking outbound queue for messages (%d)\n",outbound.size());
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
						for(EventManagerWebSocket socket : remotes.values()){
							if(socket!=null && socket.getSession() !=null && socket.getSession().isOpen()){
								socket.getSession().getRemote().sendString(notification.toJSON());
							}
						}
					} catch (Exception e) {
						System.err.println("unable to post: "+notification.toJSON());
					}
				}	
			}
		}
	}

	private boolean enableWebSockets = false;

	public synchronized void removeSocket(EventManagerWebSocket socket) {
		if(socket!=null && remotes.containsKey(socket.toString())){remotes.remove(socket.toString());}
	}

	public synchronized boolean isWebSocketsEnabled() {
		return this.enableWebSockets;
	}


	public  synchronized MessageHandler getHandler() {
		return this.handler;
	}

	public synchronized void connected(EventManagerWebSocket socket) {
		System.out.printf("connected socket local address: %s\n",socket.getSession().getLocalAddress().getHostString());
		System.out.printf("connected socket remote address: %s\n",socket.getSession().getRemoteAddress().getHostString());
		remotes.put(socket.toString(),socket);
		System.out.printf("connected! %d remotes\n",remotes.size());
		handler.connect(socket);
	}
	
	public  void process(String message) {
		try {
			inbound.put(message);
		} catch (InterruptedException e) {
			System.err.println("unable to receive: "+message);
		}	
	}
	public int getPort(){return port;}
}