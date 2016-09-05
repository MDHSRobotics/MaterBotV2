package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class ConsoleRumbleNotification extends RobotNotification {
	
	private MDRobotBase robot;
	private double value;
	private RumbleType hand;

	public ConsoleRumbleNotification(MDRobotBase robot,Joystick.RumbleType hand,double value) {
		this(robot, hand, value, false,false,true,true);
	}
	public ConsoleRumbleNotification(MDRobotBase robot,Joystick.RumbleType hand,double value, boolean record) {
		this(robot, hand, value, false,false,true,record);		
	}
	public ConsoleRumbleNotification(MDRobotBase robot,Joystick.RumbleType hand,double value, boolean showJavaConsole, boolean showMDConsole ) {
		this(robot, hand, value, showJavaConsole,showMDConsole,true,true);
	}
	public ConsoleRumbleNotification(MDRobotBase robot,Joystick.RumbleType hand,double value, boolean showJavaConsole, boolean showMDConsole, boolean broadcast, boolean record ) {
		super("ConsoleRumbleNotification", showJavaConsole, showMDConsole, broadcast, record);
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
		sb.append(hand.value);
		sb.append("\", \"value\":");
		sb.append(value);
	}
}
