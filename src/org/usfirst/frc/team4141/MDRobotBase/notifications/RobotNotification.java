package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Notification;

import edu.wpi.first.wpilibj.Timer;


public abstract class RobotNotification extends Notification {
	private double fpgaTime;
	public double getFpgaTime() {
		return fpgaTime;
	}
	public RobotNotification(String notificationType,boolean display, boolean record, boolean broadcast){
		super(notificationType,display,record,broadcast);
		this.fpgaTime = Timer.getFPGATimestamp();
	}
	public RobotNotification(String notificationType, boolean record, boolean broadcast){
		this(notificationType,false,record,broadcast);
	}
	public RobotNotification(String notificationType, boolean broadcast){
		this(notificationType,false,false,broadcast);
	}
	public RobotNotification(String notificationType){
		this(notificationType,false,false,false);
	}

/*
{"eventType": "targetAcquiredNotification", "messageId":7, "timestamp": 1456619634747, "targetAcquired":true}
{"eventType": "goodShotNotification", "messageId":9, "timestamp": 1456619634747, "GoodShot":true}
 */
	@Override
	public Notification parse(String arg0) {
		throw new NotImplementedException();
	}
}
