package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;

public class HeartbeatNotification extends RobotNotification {
	
	private MDRobotBase robot;

	public HeartbeatNotification(MDRobotBase robot) {
		this(robot,false,false,true,true);
	}
	public HeartbeatNotification(MDRobotBase robot, boolean record) {
		this(robot,false,false,true,record);		
	}
	public HeartbeatNotification(MDRobotBase robot, boolean showJavaConsole, boolean showMDConsole ) {
		this(robot,showJavaConsole,showMDConsole,true,true);
	}
	public HeartbeatNotification(MDRobotBase robot, boolean showJavaConsole, boolean showMDConsole, boolean broadcast, boolean record ) {
		super("Heartbeat", showJavaConsole, showMDConsole, broadcast, record);
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
