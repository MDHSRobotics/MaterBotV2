package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

public abstract class ReadingBase implements SensorReading{
	private Sensor sensor;
	private String name;
	private Type type;
	private boolean observe;
	private boolean show;


	public ReadingBase(Sensor sensor, String name, Type type,boolean observe,boolean show){
		this.sensor = sensor;
		this.name = name;
		this.type = type;
		this.observe = observe;
		this.show = show;
	}
	
	public Sensor getSensor(){return sensor;}
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}
	@Override
	public boolean observe() {
		return observe;
	}
	public void setShow(boolean show){
		this.show = show;
	}
	@Override
	public boolean show() {
		return show;
	}
	public MDSubsystem getSubsystem(){
		if(sensor!=null){
			return sensor.getSubsystem();
		}
		return null;
	}
}
