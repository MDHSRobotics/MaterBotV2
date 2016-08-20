package org.usfirst.frc.team4141.MDRobotBase.sensors;

public class AnalogSensorReading implements SensorReading {

	private String name;
	private double value;
	private Type type;
	private boolean observe;

	
	public AnalogSensorReading(String name, double value, boolean observe){
		this.name = name;
		this.value = value;
		this.type = Type.analog;
		this.observe = observe;
	}
	
	public AnalogSensorReading(String name, double value){
		this(name,value,true);
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
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
		sb.append(Double.toString(getValue()));
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

