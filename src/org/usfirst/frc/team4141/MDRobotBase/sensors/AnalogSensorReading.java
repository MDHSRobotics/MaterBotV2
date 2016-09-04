package org.usfirst.frc.team4141.MDRobotBase.sensors;

public class AnalogSensorReading extends ReadingBase  {

	private double value;
	
	public AnalogSensorReading(Sensor sensor,String name, double value, boolean observe,boolean show){
		super(sensor,name,Type.analog,observe,show);
		this.value = value;
	}
	
	public AnalogSensorReading(Sensor sensor,String name, double value){
		this(sensor,name,value,true,true);
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
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
		sb.append(Double.toString(getValue()));
		sb.append("}");
		return sb.toString();
	}
}

