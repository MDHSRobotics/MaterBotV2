package org.usfirst.frc.team4141.MDRobotBase.sensors;

public interface Sensor {
	String getName();
	SensorReading[] getReadings();
	void refresh();
	void setName(String name);
	boolean observe();
}
