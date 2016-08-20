package org.usfirst.frc.team4141.MDRobotBase.config;


public class DoubleConfigSetting implements ConfigSetting {
	private Double min;
	private Double max;
	private Double value;
	private Type type;
	private String name;
	
	
	public DoubleConfigSetting(Double min, Double max, Double value){
		this.min = min;
		this.max = max;
		this.value = value;
		this.type = Type.doubleNumber;	
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
	}

	@Override
	public void setMax(Object max) {
		if(max instanceof Double){
			this.max = (Double)max;
		}
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof Double){
			this.value = (Double)value;
			System.out.printf("setting %s to %f\n",name,value);
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

}
