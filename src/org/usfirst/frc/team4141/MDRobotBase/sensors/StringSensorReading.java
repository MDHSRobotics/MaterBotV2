package org.usfirst.frc.team4141.MDRobotBase.sensors;

public class StringSensorReading implements SensorReading {

	private String name;
	private String value;
	private Type type;
	private boolean observe;

	public StringSensorReading(String name, String value, boolean observe){
		this.name = name;
		this.value = value;
		this.type = Type.analog;
		this.observe = observe;
	}
	
	public StringSensorReading(String name, String value){
		this(name,value,true);
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
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"type\":\"");
		sb.append(type.toString());
		sb.append("\", \"name\":\"");
		sb.append(name);
		sb.append("\", \"value\":\"");
		sb.append(value);
		sb.append("\"}");
		return sb.toString();
	}

	@Override
	public boolean observe() {
		return observe;
	}
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
