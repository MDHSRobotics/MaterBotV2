package org.usfirst.frc.team4141.MDRobotBase;

import java.util.Hashtable;

public abstract class OIBase {
	public abstract void configureOI();
	private MDRobotBase robot;
	Hashtable<String, MDJoystick> joysticks;
	
	public OIBase(MDRobotBase robot){
		this.robot = robot;
		this.joysticks = new Hashtable<String, MDJoystick>();
	}	
	
	public void add(MDJoystick joystick) {
		joysticks.put(joystick.getName(),joystick);
	}
	

	public MDRobotBase getRobot() {
		return robot;
	}
	
	public Hashtable<String, MDJoystick> getJoysticks() {
		return joysticks;
	}
}
