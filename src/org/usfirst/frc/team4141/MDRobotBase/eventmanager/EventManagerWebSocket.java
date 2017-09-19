package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

	
@WebSocket
public class EventManagerWebSocket{
	
	private EventManager eventManager;
	private Session session;
	private String name;
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}

	public EventManagerWebSocket(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public Session getSession(){ return session;}
	
	@OnWebSocketConnect
	public void onConnect(Session session){
		this.session = session;
        eventManager.connected(this);
	}

	@OnWebSocketClose
	public void onClose(Session session, int closeCode, String closeReason){
		
        eventManager.removeSocket(this);
        System.out.println("socketOnClose: "+this.name);
	}
	
    @OnWebSocketMessage
    public void onText(Session session, String message) {
//    	System.out.printf("message received: %s\n",message);
    	eventManager.process(new Request(this,message));
//        if (session.isOpen()) {
//        	String response = "{\"eventType\": \"RobotStateNotification\", \"messageId\":25, \"timestamp\": 1458450677922, \"state\":\"AutonomousPeriodic\"}";
//            System.out.printf("response: %s\n", response);
//            session.getRemote().sendString(response, null);
//            
//        }
        
    }

    @OnWebSocketMessage
    public void onBinary(Session session, byte[] buffer, int offset, int length) {
    	System.out.printf("binary message received\n");
    }
    
    @OnWebSocketError
    public void onError(Session session,Throwable err){
    	System.out.printf("socket error: %s\n",err.getMessage());
    }
}