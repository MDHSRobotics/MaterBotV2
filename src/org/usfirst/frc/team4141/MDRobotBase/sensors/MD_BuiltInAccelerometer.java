package org.usfirst.frc.team4141.MDRobotBase.sensors;


import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class MD_BuiltInAccelerometer extends BuiltInAccelerometer implements Sensor{
	SensorReading[] readings = new SensorReading[3];
	private String name;
	
	public MD_BuiltInAccelerometer(){
		this(true);
	}
	
	public MD_BuiltInAccelerometer(boolean observe){
		super();
		this.observe = observe;
		int i=0;
		readings[i++]=new AnalogSensorReading("Rio_AccelX", getX());
		readings[i++]=new AnalogSensorReading("Rio_AccelY", getY());
		readings[i++]=new AnalogSensorReading("Rio_AccelZ", getZ());
	}
	public void setName(String name){
		this.name = name;
	}	
	public void refresh(){
		int i=0;
		((AnalogSensorReading)readings[i++]).setValue(getX());
		((AnalogSensorReading)readings[i++]).setValue(getY());
		((AnalogSensorReading)readings[i++]).setValue(getZ());
	}

	@Override
	public String getName() {
		return name;
	}

	public SensorReading[] getReadings() {
		return readings;
	}

	private boolean observe;
	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}
}
