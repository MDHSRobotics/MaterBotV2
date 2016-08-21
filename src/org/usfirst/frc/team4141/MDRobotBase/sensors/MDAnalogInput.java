package org.usfirst.frc.team4141.MDRobotBase.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class MDAnalogInput extends AnalogInput implements Sensor {
	SensorReading[] readings = new SensorReading[1];
	private String name;	
	public MDAnalogInput(String name, int channel) {
		this(name,channel,true);
	}
	public MDAnalogInput(String name, int channel,boolean observe) {
		super(channel);
		this.observe = observe;
		this.name = name;
		readings[0] = new AnalogSensorReading(name, getVoltage());
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
		((AnalogSensorReading)readings[0]).setValue(getVoltage());
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
