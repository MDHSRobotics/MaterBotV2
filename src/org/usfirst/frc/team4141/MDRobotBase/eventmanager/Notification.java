package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import java.util.Date;

public abstract class Notification {
	
	private long messageId = -1;
	private long messageTimeStamp = -1;
	private boolean console = false;
	private boolean record = false;
	private boolean display = false;
	private String notificationType;

	public String getNotificationType() {
		return notificationType;
	}
	public long getMessageTimeStamp() {
		return messageTimeStamp;
	}
	public boolean isConsole() {
		return console;
	}
	public boolean isRecord() {
		return record;
	}
	public boolean isDisplay() {
		return display;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId =  messageId;
	}
		
	public Notification(String notificationType,boolean display, boolean record, boolean console){
		this.notificationType = notificationType;
		this.display = display;
		this.record = record;
		this.console = console;
		messageTimeStamp = new Date().getTime();
		sb = new StringBuilder();
	}
	public Notification(String notificationType,boolean display,boolean record){
		this(notificationType,display,record,false);
	}
	public Notification(String notificationType,boolean display){
		this(notificationType,display,false,false);
	}
	public Notification(String notificationType){
		this(notificationType,false,false,false);
	}
	
	protected abstract void addJSONPayload();

	protected StringBuilder sb;
	public String toJSON() {
		sb = new StringBuilder();
		sb.append('{');
		if(getMessageId()>-1){
			sb.append(String.format("\"messageId\":%1$d",getMessageId()));
		}
		sb.append(", \"eventType\":\"");
		sb.append(getNotificationType());
		sb.append("\",\"isDisplay\":");
		sb.append(this.display);
		sb.append(",\"isRecord\":");
		sb.append(this.record);

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
