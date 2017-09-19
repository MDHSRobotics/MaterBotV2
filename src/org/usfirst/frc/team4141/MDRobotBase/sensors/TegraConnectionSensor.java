package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

public class TegraConnectionSensor implements Sensor{
   
	StringSensorReading reading;
	private String name;
	private boolean observe;
	private MDSubsystem subsystem;


	public TegraConnectionSensor(){
		this(null);
	}
	
	public TegraConnectionSensor(MDSubsystem subsystem){
		this(null, true);
	}
	
	public TegraConnectionSensor(MDSubsystem subsystem, boolean observe){
		this.observe = observe;
		this.subsystem = subsystem;
		reading=new StringSensorReading(this,"tegra", "");
	}

	public void set(String tegraAddress){
		reading.setValue(tegraAddress);
	}
	
	public String get(){
		return reading.getValue();
	}
	
	public void setName(String name){
		this.name = name;
	}	

	public void refresh(){
	}

	@Override
	public String getName() {
		return name;
	}

	public SensorReading[] getReadings() {
		return new SensorReading[] {reading};
	}


	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}

	@Override
	public MDSubsystem getSubsystem() {
		return subsystem;
	}
	@Override
	public Sensor setSubsystem(MDSubsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}
}
