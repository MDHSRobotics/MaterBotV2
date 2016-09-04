package org.usfirst.frc.team4141.MDRobotBase.config;

import edu.wpi.first.wpilibj.command.Subsystem;

public interface ConfigSetting {
	String getName();
	void setName(String name);
	Subsystem getSubsystem();
	void setSubsystem(Subsystem subsystem);
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
		binary,
		integer,
		decimal,
		string
	}
	String getPath();
	String toJSON();
}
