package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;

public class ConsoleConnectionNotification extends RobotNotification {
	
	private MDRobotBase robot;
	private String consoleAddress;

	public ConsoleConnectionNotification(MDRobotBase robot,String consoleAddress) {
		this(robot, consoleAddress, true, WebSocketSubsystem.Remote.tegra.toString(), false);  //TODO: change showInConsole back to false after debugging
	}

	public ConsoleConnectionNotification(MDRobotBase robot, String consoleAddress, boolean showInConsole, String target, boolean record ) {
		super("ConsoleConnectionNotification", showInConsole, target, record);
		this.robot = robot;
		this.consoleAddress = consoleAddress;
	}	


	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		sb.append(", \"consoleAddress\":\"");
		sb.append(consoleAddress);
		sb.append("\"");
	}
	
	public MDRobotBase getRobot(){return robot;}
}
