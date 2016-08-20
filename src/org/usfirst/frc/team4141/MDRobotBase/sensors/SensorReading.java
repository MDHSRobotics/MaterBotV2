package org.usfirst.frc.team4141.MDRobotBase.sensors;

public interface SensorReading {
	public enum Type{
		analog,  //double
		digital  //boolean
	}
	Type getType();
	String getName();
	String toJSON();
	boolean observe();
}
