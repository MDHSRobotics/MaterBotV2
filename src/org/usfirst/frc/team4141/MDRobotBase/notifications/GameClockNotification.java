package org.usfirst.frc.team4141.MDRobotBase.notifications;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class GameClockNotification extends RobotNotification {
	//{"eventType": "GameClockNotification", "messageId":22, "timestamp": 1456619634747, "dsMatchTime":15,"timerMatchTime":0}

	
	private double timerMatchTime;
	private double driverStationMatchTime;
	private boolean isFMSAttached;
	private boolean isAutonomous;

	public double getTimerMatchTime() {
		return timerMatchTime;
	}


	public double getDriverStationMatchTime() {
		return driverStationMatchTime;
	}

	public GameClockNotification(boolean display) {
		this(display,true, false);
	}
	public GameClockNotification(boolean display,boolean record,boolean broadcast) {
		super("GameClockNotification", display,record, broadcast);
		this.timerMatchTime = Timer.getMatchTime();
		this.driverStationMatchTime = DriverStation.getInstance().getMatchTime();
		this.isFMSAttached = DriverStation.getInstance().isFMSAttached();
		this.isAutonomous = DriverStation.getInstance().isAutonomous();
	}


	public boolean isFMSAttached() {
		return isFMSAttached;
	}


	public boolean isAutonomous() {
		return isAutonomous;
	}


	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		sb.append(", \"timerMatchTime\":");
		sb.append(getTimerMatchTime());
		sb.append(", \"dsMatchTime\":");
		sb.append(getDriverStationMatchTime());
		sb.append(", \"FMSAttached\":");
		sb.append(isFMSAttached);
		sb.append(", \"Autonomous\":");
		sb.append(isAutonomous());
	}

}
