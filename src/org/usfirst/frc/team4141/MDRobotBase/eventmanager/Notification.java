package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import java.util.Date;

public abstract class Notification {
	
	private long messageId = -1;
	private boolean showJavaConsole = false;
	private String notificationType;

	public String getNotificationType() {
		return notificationType;
	}

	public boolean showJavaConsole() {
		return showJavaConsole;
	}

	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId =  messageId;
	}
		
	public Notification(String notificationType,boolean showJavaConsole){
		this.notificationType = notificationType;
		this.showJavaConsole = showJavaConsole;
		sb = new StringBuilder();
	}
	public Notification(String notificationType){
		this(notificationType,false);
	}
	
	protected abstract void addJSONPayload();

	protected StringBuilder sb;
	public String toJSON() {
		sb = new StringBuilder();
		sb.append('{');
		if(getMessageId()>-1){
			sb.append(String.format("\"messageId\":%1$d, ",getMessageId()));
		}
		sb.append("\"eventType\":\"");
		sb.append(getNotificationType());
		sb.append("\"");
		addJSONPayload();
		sb.append('}');
		return sb.toString();
	}		
	
	@Override
	public String toString() {
		return toJSON();
	}
	
	public abstract Notification parse(String notification);
}
