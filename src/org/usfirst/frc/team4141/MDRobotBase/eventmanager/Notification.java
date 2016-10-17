package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

public abstract class Notification {
	
	private long messageId = -1;
	private boolean display = false;
	private boolean record = false;
	private boolean broadcast = false;
	private boolean showJavaConsole = false;
	private String notificationType;

	public String getNotificationType() {
		return notificationType;
	}

	public boolean showJavaConsole() {
		return showJavaConsole;
	}
	public boolean dislay() {
		return display;
	}
	public boolean record() {
		return record;
	}
	public boolean broadcast(){
		return broadcast;
	}
	
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId =  messageId;
	}
		
	public Notification(String notificationType,boolean showJavaConsole,boolean broadcast,boolean record,boolean display){
		this.notificationType = notificationType;
		this.showJavaConsole = showJavaConsole;
		this.broadcast = broadcast;
		this.display = display;
		this.record = record;
		sb = new StringBuilder();
	}
	public Notification(String notificationType){
		this(notificationType,false,true,false,true);
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
		sb.append(", \"display\":");
		sb.append(dislay());
		sb.append(", \"record\":");
		sb.append(record());
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
