package org.usfirst.frc.team4141.MDRobotBase.sensors;

public interface SensorReading {
	public enum Type{
		analog,  //double
		digital,  //boolean
		string
	}
	Type getType();
	String getName();
	String toJSON();
	boolean observe();
	boolean show();
}
