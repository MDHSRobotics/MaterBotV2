package org.usfirst.frc.team4141.MDRobotBase.config;

import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;

import edu.wpi.first.wpilibj.command.Subsystem;

public class StringConfigSetting implements ConfigSetting {
	private String value;
	private String min;
	private String max;
	private Type type;
	private String name;
	private Subsystem subsystem;
	
	public StringConfigSetting(String value){
		this(value,null,null);
	}	
	
	public StringConfigSetting(String value, String min, String max){
		this.value = value;
		this.min= min;
		this.max = max;
		this.type = Type.string;	
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
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
		this.min = min.toString();
	}

	@Override
	public void setMax(Object max) {
		this.max = max.toString();
	}

	@Override
	public void setValue(Object value) {
		this.value = (String)value;
	}


	@Override
	public int getInt() {
		throw new NotImplementedException("function getInt() on a StringConfigItem is not implemented.");
	}

	@Override
	public double getDouble() {
		throw new NotImplementedException("function getDouble() on a StringConfigItem is not implemented.");
	}

	@Override
	public String getString() {
		return value;
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
		sb.append("\", \"value\":\"");
		sb.append(getValue().toString());
		if(this.min!=null){
			sb.append("\", \"min\":\"");
			sb.append(getMin().toString());
		}
		if(this.max!=null){
			sb.append("\", \"max\":\"");
			sb.append(getMax().toString());
		}
		sb.append("\"}");
		return sb.toString();
	}

	@Override
	public String getPath() {
		return getSubsystem().getName()+'.'+getName();
	}
	@Override
	public boolean getBoolean() {
		return Boolean.parseBoolean(value);
	}
}
