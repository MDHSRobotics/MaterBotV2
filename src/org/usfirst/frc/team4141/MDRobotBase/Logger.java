package org.usfirst.frc.team4141.MDRobotBase;

import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotLogNotification;

public class Logger {
	

	public enum Level{
		INFO,
		WARNING,
		ERROR
	}
	private MDRobotBase robot;
	public MDRobotBase getRobot() {
		return robot;
	}
	public Logger(MDRobotBase robot){
		this.robot=robot;
	}
	public void log(Level level,String logOrigin, String message)
	{
		getRobot().post(new RobotLogNotification(level, logOrigin, message, false));
	}
	public void log(String logOrigin, String message)
	{
		log(Level.INFO,logOrigin,message);
	}

}
