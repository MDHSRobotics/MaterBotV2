package org.usfirst.frc.team4141.MDRobotBase.config;

import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;

public class StringConfigSetting implements ConfigSetting {
	private String value;
	private Type type;
	private String name;
	
	public StringConfigSetting(String value){
		this.value = value;
		this.type = Type.string;	
	}	

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
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
		throw new NotImplementedException("function getMin() on a StringConfigItem is not implemented.");
	}

	@Override
	public Object getMax() {
		throw new NotImplementedException("function getMax() on a StringConfigItem is not implemented.");
	}

	@Override
	public void setMin(Object min) {
		throw new NotImplementedException("function setMin() on a StringConfigItem is not implemented.");
	}

	@Override
	public void setMax(Object max) {
		throw new NotImplementedException("function setMax() on a StringConfigItem is not implemented.");
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof String){
			this.value = (String)value;
			System.out.printf("setting %s to %s\n",name,value);
		}
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

}
