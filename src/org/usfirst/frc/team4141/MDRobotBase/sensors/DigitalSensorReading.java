package org.usfirst.frc.team4141.MDRobotBase.sensors;


public class DigitalSensorReading extends ReadingBase {

	private boolean value;

	
	public DigitalSensorReading(Sensor sensor, String name, boolean value, boolean observe,boolean show){
		super(sensor,name,Type.digital,observe,show);
		this.value = value;
	}
	
	public DigitalSensorReading(Sensor sensor, String name, boolean value){
		this(sensor,name,value,true,true);
	}	

	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}



	@Override
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		sb.append("{");
		if(getSubsystem()!=null && getSubsystem().getName()!=null){
			sb.append("\"subsystem\":\"");
			sb.append(getSubsystem().getName());
			sb.append("\"");
			isFirst= false;
		}
		if(getSensor()!=null && getSensor().getName()!=null){
			if(!isFirst){
				sb.append(", ");
			}
			sb.append("\"sensor\":\"");
			sb.append(getSensor().getName());
			sb.append("\"");
			isFirst= false;
		}
		if(!isFirst){
			sb.append(", ");
		}
		sb.append("\"name\":\"");
		sb.append(getName());
		sb.append("\", \"type\":\"");
		sb.append(getType().toString());
		sb.append("\", \"observe\":");
		sb.append(Boolean.toString(observe()).toString());
		sb.append(", \"show\":");
		sb.append(Boolean.toString(show()).toString());
		sb.append(", \"value\":");
		sb.append(Boolean.toString(getValue()));
		sb.append("}");
		return sb.toString();
	}

}

