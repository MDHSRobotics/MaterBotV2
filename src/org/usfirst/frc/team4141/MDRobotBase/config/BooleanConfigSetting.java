package org.usfirst.frc.team4141.MDRobotBase.config;

import edu.wpi.first.wpilibj.command.Subsystem;

public class BooleanConfigSetting implements ConfigSetting {
	private Boolean min;
	private Boolean max;
	private Boolean value;
	private Type type;
	private String name;
	private Subsystem subsystem;
	
	
	public BooleanConfigSetting(Boolean value){
		this.min = false;
		this.max = true;
		this.value = value;
		this.type = Type.binary;	
	}	

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	@Override
	public Subsystem getSubsystem() {
		return subsystem;
	}

	@Override
	public void setSubsystem(Subsystem subsystem){
		this.subsystem = subsystem;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Object getMin() {
		return min;
	}

	@Override
	public Object getMax() {
		return max;
	}

	@Override
	public void setMin(Object min) {
	}

	@Override
	public void setMax(Object max) {
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof Boolean){
			this.value = (Boolean)value;
			System.out.printf("setting %s to %s\n",name,value.toString());
		}
		else if (value instanceof Integer){
			this.value = (((Integer)value).intValue()==0?false:true);
		}
		else if (value instanceof Double){
			this.value = (((Double)value).doubleValue()==0.0?false:true);
		}
		else if (value instanceof String){
			this.value = Boolean.valueOf((String)value);
		}

	}


	@Override
	public int getInt() {
		return (value.booleanValue()?1:0);
	}

	@Override
	public double getDouble() {
		return (value.booleanValue()?1.0:0.0);
	}

	@Override
	public String getString() {
		return value.toString();
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"subsystem\":\"");
		sb.append(getSubsystem().getName());
		sb.append("\", \"name\":\"");
		sb.append(getName());
		sb.append("\", \"type\":\"");
		sb.append(getType().toString());
		sb.append("\", \"value\":");
		sb.append(getValue().toString());
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String getPath() {
		return getSubsystem().getName()+'.'+getName();
	}

	@Override
	public boolean getBoolean() {
		return value;
	}
}
