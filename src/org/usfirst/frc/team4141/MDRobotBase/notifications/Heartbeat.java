package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;

public class Heartbeat extends RobotNotification {
	
	private MDRobotBase robot;

	public Heartbeat(MDRobotBase robot, boolean display) {
		this(robot,display,true, false);
	}
	public Heartbeat(MDRobotBase robot, boolean display,boolean record,boolean broadcast) {
		super("Heartbeat", display,record, broadcast);
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
		sb.append(", \"sensors\":[");
		boolean isFirst = true;
		for(SensorReading reading : robot.getSensorReadingsDictionary().values()){
			if(isFirst) isFirst = false;
			else sb.append(",");
			sb.append(reading.toJSON());
		}
		sb.append("]");	}
}
