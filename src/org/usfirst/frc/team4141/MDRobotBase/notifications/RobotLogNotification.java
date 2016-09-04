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
	
	public RobotLogNotification(Level level,String logOrigin, String message,boolean showJavaConsole, boolean showMDConsole, boolean broadcast, boolean record) {
		super("RobotLogNotification",showJavaConsole,showMDConsole,broadcast, record);
		this.level = level;
		this.source = logOrigin;
		this.message = message;
	}
	public RobotLogNotification(Level level,String logOrigin, String message,boolean showMDConsole) {
		this(level,logOrigin,message,false,showMDConsole,true,true);
	}
	public RobotLogNotification(Level level,String logOrigin, String message,boolean showMDConsole,boolean record) {
		this(level,logOrigin,message,false,showMDConsole,true,record);
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
