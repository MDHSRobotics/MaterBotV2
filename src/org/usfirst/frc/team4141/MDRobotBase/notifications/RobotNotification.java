package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Notification;

import edu.wpi.first.wpilibj.Timer;



public abstract class RobotNotification extends Notification {
	private double fpgaTime;
	private boolean showMDConsole = false;
	private boolean broadcast = false;
	private boolean record = false;
	public double getFpgaTime() {
		return fpgaTime;
	}
	public RobotNotification(String notificationType){
		this(notificationType,false);
	}
	public RobotNotification(String notificationType,boolean showJavaConsole){
		this(notificationType,showJavaConsole,false,false,false);
	}
	public RobotNotification(String notificationType,boolean showJavaConsole,boolean showMDConsole,boolean broadcast,boolean record){
		super(notificationType,showJavaConsole);
		this.fpgaTime = Timer.getFPGATimestamp();
		this.broadcast = broadcast;
		this.showMDConsole = showMDConsole;
		this.record = record;
	}

	public boolean showMDConsole(){
		return showMDConsole;
	}
	public boolean record(){
		return record;
	}
	public boolean broadcast(){
		return broadcast;
	}

/*
{"eventType": "targetAcquiredNotification", "messageId":7, "timestamp": 1456619634747, "targetAcquired":true}
{"eventType": "goodShotNotification", "messageId":9, "timestamp": 1456619634747, "GoodShot":true}
 */
	@Override
	public Notification parse(String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public String toJSON() {
		sb = new StringBuilder();
		sb.append('{');
		if(getMessageId()>-1){
			sb.append(String.format("\"messageId\":%1$d, ",getMessageId()));
		}
		sb.append("\"eventType\":\"");
		sb.append(getNotificationType());
		sb.append("\",\"dislay\":");
		sb.append(this.showMDConsole);
		sb.append(",\"record\":");
		sb.append(this.record);
		addJSONPayload();
		sb.append('}');
		return sb.toString();
	}		
		
}
