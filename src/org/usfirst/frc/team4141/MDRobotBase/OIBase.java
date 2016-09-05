package org.usfirst.frc.team4141.MDRobotBase;

import java.util.Hashtable;


public abstract class OIBase {
	public abstract void configureOI();
	private MDRobotBase robot;
	private RioHID rioHID;
	private ConsoleOI console;
	Hashtable<String, MDJoystick> joysticks;
	
	public OIBase(MDRobotBase robot){
		this.robot = robot;
		this.joysticks = new Hashtable<String, MDJoystick>();
	}	
	
	public MDRobotBase getRobot() {
		return robot;
	}
	
	public RioHID getRioHID() {
		return rioHID;
	}
	public ConsoleOI getConsole() {
		return console;
	}

	public Hashtable<String, MDJoystick> getJoysticks() {
		return joysticks;
	}	
	
	protected OIBase add(RioHID rioHID) {
		this.rioHID = rioHID;
		return this;
	}

	protected OIBase add(ConsoleOI console) {
		this.console = console;
		return this;
	}
	
	protected OIBase add(MDJoystick joystick) {
		joysticks.put(joystick.getName(),joystick);
		return this;		
	}
	
}
