package org.usfirst.frc.team4141.MDRobotBase.sensors;


import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;

public class MDDigitalInput extends DigitalInput implements Sensor {

	SensorReading[] readings = new SensorReading[1];
	private String name;
	private MDSubsystem subsystem;	
	public MDDigitalInput(MDSubsystem subsystem, String name, int channel) {
		this(subsystem,name, channel,true);
	}
	public MDDigitalInput(MDSubsystem subsystem, String name, int channel,boolean observe) {
		super(channel);
		this.name = name;
		this.observe = observe;
		this.subsystem = subsystem;
		readings[0] = new DigitalSensorReading(this,name, get());
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"channel\":");
		sb.append(readings[0].toJSON());
		sb.append("}");
		return sb.toString();
	}

	@Override
	public SensorReading[] getReadings() {
		return readings;
	}

	@Override
	public void refresh() {
		((DigitalSensorReading)readings[0]).setValue(get());
	}
	
	private boolean observe;
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
