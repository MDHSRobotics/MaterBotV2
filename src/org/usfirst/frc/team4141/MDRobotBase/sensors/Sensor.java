package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

public interface Sensor {
	String getName();
	SensorReading[] getReadings();
	MDSubsystem getSubsystem();
	Sensor setSubsystem(MDSubsystem subsystem);
	void refresh();
	void setName(String name);
	boolean observe();
}
