package org.usfirst.frc.team4141.MDRobotBase.config;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DoubleConfigSetting implements ConfigSetting {
	private Double min;
	private Double max;
	private Double value;
	private Type type;
	private String name;
	private Subsystem subsystem;
	
	
	public DoubleConfigSetting(Double min, Double max, Double value){
		this.min = min;
		this.max = max;
		this.value = value;
		this.type = Type.decimal;	
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
		if(min instanceof Double){
			this.min = (Double)min;
		}
		else if (min instanceof Integer){
			this.min = new Double((Integer)min);
		}
		else if (min instanceof String){
			this.min = Double.valueOf((String)min);
		}
		else if (min instanceof Boolean){
			this.min = (((Boolean)min).booleanValue()?1.0:0.0);
		}
	}

	@Override
	public void setMax(Object max) {
		if(max instanceof Double){
			this.max = (Double)max;
		}
		else if (max instanceof Integer){
			this.max = new Double((Integer)max);
		}
		else if (max instanceof String){
			this.max = Double.valueOf((String)max);
		}
		else if (max instanceof Boolean){
			this.max = (((Boolean)max).booleanValue()?1.0:0.0);
		}
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof Double){
			this.value = (Double)value;
//			System.out.printf("setting %s to %f\n",name,value);
		}
		else if (value instanceof Integer){
			this.value = new Double((Integer)value);
		}
		else if (value instanceof String){
			this.value = Double.valueOf((String)value);
		}
		else if (value instanceof Boolean){
			this.value = (((Boolean)value).booleanValue()?1.0:0.0);
		}
	}


	@Override
	public int getInt() {
		return value.intValue();
	}

	@Override
	public double getDouble() {
		return value.doubleValue();
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
		sb.append(", \"min\":");
		sb.append(getMin().toString());
		sb.append(", \"max\":");
		sb.append(getMax().toString());
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String getPath() {
		return getSubsystem().getName()+'.'+getName();
	}

	@Override
	public boolean getBoolean() {
		if (value == 0.0) return false;
		return true;
	}
}
