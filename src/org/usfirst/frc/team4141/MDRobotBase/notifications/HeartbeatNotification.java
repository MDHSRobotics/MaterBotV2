package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;

public class HeartbeatNotification extends RobotNotification {
	
	private MDRobotBase robot;

	public HeartbeatNotification(MDRobotBase robot) {
		this(robot, false, WebSocketSubsystem.Remote.console.toString(), true);
	}
	
	public HeartbeatNotification(MDRobotBase robot, boolean showInConsole, boolean record) {
		this(robot, false, WebSocketSubsystem.Remote.console.toString(), record);
	}
	
	public HeartbeatNotification(MDRobotBase robot, boolean showInConsole, String target, boolean record ) {
		super("Heartbeat", showInConsole, target, record);
		this.robot = robot;
		for(Sensor sensor : robot.getSensorsDictionary().values()){
			sensor.refresh();
		}
	}	


	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		sb.append(", \"sensors\":{");
		boolean isFirst = true;
		for(SensorReading reading : robot.getSensorReadingsDictionary().values()){
			if(isFirst) isFirst = false;
			else sb.append(", ");
			sb.append("\"");
			sb.append(reading.getName());
			sb.append("\":");
			sb.append(reading.toJSON());
		}
		sb.append("}");	}
}
