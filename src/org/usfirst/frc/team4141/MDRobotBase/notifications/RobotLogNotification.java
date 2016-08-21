package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.Logger.Level;

public class RobotLogNotification extends RobotNotification {
	//{"eventType": "LogNotification", "messageId":1, "timestamp": 1456619634747, "level":"INFO","source":"source","message":"message"}


	private String message;
	private String source;
	private Level level;
	
	public String getMessage() {
		return message;
	}
	public String getSource() {
		return source;
	}
	public Level getLevel() {
		return level;
	}
	
	public RobotLogNotification(Level level,String logOrigin, String message,boolean display,boolean record,boolean console) {
		super("RobotLogNotification",display,record,console);
		this.level = level;
		this.source = logOrigin;
		this.message = message;
	}
	public RobotLogNotification(Level level,String logOrigin, String message,boolean display) {
		this(level,logOrigin,message,display,false,true);
	}
	
	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		if(level!=null){
			sb.append(", \"level\":\"");
			sb.append(level.toString());
			sb.append("\"");
		}
		if(source!=null){
			sb.append(", \"source\":\"");
			sb.append(source.toString());
			sb.append("\"");
		}
		if(message!=null){
			sb.append(", \"message\":\"");
			sb.append(message.toString());
			sb.append("\"");
		}
	}

}
