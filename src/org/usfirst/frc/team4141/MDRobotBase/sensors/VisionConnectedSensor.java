package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

public class VisionConnectedSensor implements Sensor {

	private String name;
	private DigitalSensorReading reading;
	private MDSubsystem subsystem;
	private boolean observe;

	public VisionConnectedSensor(){
		this(null,null,true);
	}
	
	public VisionConnectedSensor(MDSubsystem subsystem, String name, boolean observe){
		this.name = name;
		this.observe = observe;
		this.subsystem = subsystem;
		reading = new DigitalSensorReading(this, "isVisionConnected", false);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public SensorReading[] getReadings() {
		return new SensorReading[] {reading};
	}

	@Override
	public MDSubsystem getSubsystem() {
		return subsystem;
	}

	@Override
	public Sensor setSubsystem(MDSubsystem subsystem) {
		this.subsystem  = subsystem;
		return this;
	}

	@Override
	public void refresh() {
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean observe() {
		return observe;
	}

	public void set(boolean connected){
		reading.setValue(connected);
	}
	
	public boolean get(){
		return reading.getValue();
	}
}
