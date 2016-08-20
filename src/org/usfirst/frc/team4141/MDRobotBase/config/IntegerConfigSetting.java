package org.usfirst.frc.team4141.MDRobotBase.config;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntegerConfigSetting implements ConfigSetting {
	private Integer min;
	private Integer max;
	private Integer value;
	private Type type;
	private String name;
	private Subsystem subsystem;
	
	public IntegerConfigSetting(Integer min, Integer max, Integer value){
		this.min = min;
		this.max = max;
		this.value = value;
		this.type = Type.integer;		
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
		if(min instanceof Integer){
			this.min = (Integer)min;
		}
	}

	@Override
	public void setMax(Object max) {
		if(max instanceof Integer){
			this.max = (Integer)max;
		}
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof Integer){
			this.value = (Integer)value;
			System.out.printf("setting %s to %d\n",name,value);
		}
	}


	@Override
	public int getInt() {
		return value.intValue();
	}

	@Override
	public double getDouble() {
		return value.intValue();
	}

	@Override
	public String getString() {
		return value.toString();
	}
	@Override
	public String getPath() {
		return subsystem.getName()+"."+getName();
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"path\":\"");
		sb.append(getPath());
		sb.append("\", \"name\":\"");
		sb.append(getName());
		sb.append("\", \"type\":\"");
		sb.append(getType().toString());
		sb.append("\", \"value\":");
		sb.append(getValue().toString());
		sb.append(", \"min\":");
		sb.append(getMin().toString());
		sb.append(", \"max\":");
		sb.append(getMax().toString());
		sb.append("}");
		return sb.toString();
	}
}
