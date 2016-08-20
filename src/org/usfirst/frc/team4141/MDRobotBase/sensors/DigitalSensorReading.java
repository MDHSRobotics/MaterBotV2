package org.usfirst.frc.team4141.MDRobotBase.sensors;


public class DigitalSensorReading implements SensorReading {

	private String name;
	private boolean value;
	private Type type;
	private boolean observe;
	
	public DigitalSensorReading(String name, boolean value, boolean observe){
		this.name = name;
		this.value = value;
		this.type = Type.analog;
		this.observe = observe;

	}
	
	public DigitalSensorReading(String name, boolean value){
		this(name,value,true);
	}	

	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}


	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"type\":\"");
		sb.append(type.toString());
		sb.append("\", \"name\":\"");
		sb.append(name);
		sb.append("\", \"value\":");
		sb.append(Boolean.toString(getValue()));
		sb.append("}");
		return sb.toString();
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}
	@Override
	public boolean observe() {
		return observe;
	}
}

