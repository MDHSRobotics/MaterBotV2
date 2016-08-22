package org.usfirst.frc.team4141.MDRobotBase;

import org.usfirst.frc.team4141.MDRobotBase.notifications.HeartbeatNotification;

public class DiagnosticScan implements Runnable {
	
	
	private MDRobotBase robot;
	public DiagnosticScan(MDRobotBase robot) {
		this.robot = robot;
	}
	
	@Override
	public void run() {
		System.out.println("heartbeat");
		robot.post(new HeartbeatNotification(robot, true));
	}

}
