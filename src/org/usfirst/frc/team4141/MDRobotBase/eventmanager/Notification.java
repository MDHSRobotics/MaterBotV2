package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

public abstract class Notification {
	
	private long messageId = -1;
	private boolean record = false;
	private String target;
	private boolean showInConsole = false;
	private String notificationType;

	public String getNotificationType() {
		return notificationType;
	}

	public boolean showInConsole() {
		return showInConsole;
	}

	public boolean record() {
		return record;
	}
	public String getTarget(){
		return target;
	}
	
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId =  messageId;
	}
		
	public Notification(String notificationType,boolean showInConsole,String target,boolean record){
		this.notificationType = notificationType;
		this.showInConsole = showInConsole;
		this.target = target;
		this.record = record;
		sb = new StringBuilder();
	}
	public Notification(String notificationType){
		this(notificationType,false,null,false);
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
		if(getTarget()!=null){
			sb.append(", \"target\":\"");
			sb.append(getTarget());
			sb.append("\"");
		}
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
