package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

public class Request {
	private EventManagerWebSocket socket;
	private String message;
	public String getMessage(){return  message;}
	public EventManagerWebSocket getSocket(){return  socket;}
	public Request(EventManagerWebSocket socket, String message){
		this.message = message;
		this.socket = socket;
	}
	@Override
	public String toString() {
		return message;
	}
}
