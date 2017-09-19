package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.GenericHID;

//TODO Fixed a error by copping some code
public class MDGenericHID extends GenericHID {
	private MDRobotBase robot;
	private String name;
	private HIDType type;
	public String getName() {return name;}
	public MDRobotBase getRobot() {return robot;}
	public MDGenericHID(MDRobotBase robot, String name, int port, HIDType type){
		super(port);
		this.robot = robot;
		this.name = name;
		this.type = type;
	}
	@Override
	public double getX(Hand hand) {
		return super.getX();
	}
	@Override
	public double getY(Hand hand) {
		return super.getY();
	}
	@Override
	public double getRawAxis(int which) {
		return 0;
	}
	@Override
	public boolean getRawButton(int button) {
		return false;
	}
	
	public void debug(String message) {
		getRobot().debug(message);		
	}

	@Override
	public HIDType getType() {
		return type;
	}
	@Override
	public void setOutput(int outputNumber, boolean value) {
	}

	@Override
	public void setOutputs(int value) {
	}

	@Override
	public void setRumble(RumbleType type, double value) {
	}
	@Override
	public int getPOVCount() {
		return 0;
	}
	@Override
	public int getPOV(int pov) {
		return super.getPOV();
	}
}
