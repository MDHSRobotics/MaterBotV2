package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Notification;

import edu.wpi.first.wpilibj.Timer;

//TODO:Refactor broadcast to Notification class so that dependency is cleaner.  see MSee

public abstract class RobotNotification extends Notification {
	private double fpgaTime;
	public double getFpgaTime() {
		return fpgaTime;
	}

	public RobotNotification(String notificationType,boolean showInConsole,String target,boolean record){
		super(notificationType,showInConsole,target,record);
		this.fpgaTime = Timer.getFPGATimestamp();
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
		sb.append("\"");
		if(getTarget()!=null){
			sb.append(", \"target\":\"");
			sb.append(getTarget());
			sb.append("\"");
		}
		sb.append(",\"record\":");
		sb.append(this.record());
		addJSONPayload();
		sb.append('}');
		return sb.toString();
	}		
		
}
