package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;

public class ConsoleRumbleNotification extends RobotNotification {
	
	private MDRobotBase robot;
	private double value;
	private RumbleType hand;

	public ConsoleRumbleNotification(MDRobotBase robot,RumbleType hand,double value) {
		this(robot, hand, value, true, WebSocketSubsystem.Remote.console.toString(), false);  //TODO: change showInConsole back to false after debugging
	}

	public ConsoleRumbleNotification(MDRobotBase robot,RumbleType hand,double value, boolean showInConsole, String target, boolean record ) {
		super("ConsoleRumbleNotification", showInConsole, target, record);
		this.robot = robot;
		this.value = value;
		this.hand = hand;
	}	


	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		sb.append(", \"hand\":\"");
		sb.append(hand.toString());
		sb.append("\", \"value\":");
		sb.append(value);
	}
	
	public MDRobotBase getRobot(){return robot;}
}
