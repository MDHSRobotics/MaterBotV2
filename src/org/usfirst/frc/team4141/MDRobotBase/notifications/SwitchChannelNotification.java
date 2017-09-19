package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;

public class SwitchChannelNotification extends RobotNotification {
	
	private MDRobotBase robot;

	public SwitchChannelNotification(MDRobotBase robot) {
		this(robot, true, WebSocketSubsystem.Remote.tegra.toString(), false);  //TODO: change showInConsole back to false after debugging
	}

	public SwitchChannelNotification(MDRobotBase robot, boolean showInConsole, String target, boolean record ) {
		super("SwitchChannelNotification", showInConsole, target, record);
		this.robot = robot;
	}	


	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
	}
	
	public MDRobotBase getRobot(){return robot;}
}
