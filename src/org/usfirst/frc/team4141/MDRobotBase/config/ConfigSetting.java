package org.usfirst.frc.team4141.MDRobotBase.config;


public interface ConfigSetting {
	String getName();
	Type getType();
	Object getValue();
	Object getMin();
	Object getMax();
	void setMin(Object min);
	void setMax(Object max);
	void setValue(Object value);
	int getInt();
	double getDouble();
	String getString();
	public enum Type {
		integer,
		doubleNumber,
		string
	}
}
