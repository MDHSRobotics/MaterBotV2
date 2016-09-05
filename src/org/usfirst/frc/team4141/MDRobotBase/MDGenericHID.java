package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.GenericHID;

public class MDGenericHID extends GenericHID {
	private MDRobotBase robot;
	private String name;
	public String getName() {return name;}
	public MDRobotBase getRobot() {return robot;}
	public MDGenericHID(MDRobotBase robot, String name){
		this.robot = robot;
		this.name = name;
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
	public double getZ(Hand hand) {
		return super.getZ();
	}
	@Override
	public double getTwist() {
		return 0;
	}
	@Override
	public double getThrottle() {
		return 0;
	}
	@Override
	public double getRawAxis(int which) {
		return 0;
	}
	@Override
	public boolean getTrigger(Hand hand) {
		return super.getTrigger();
	}
	@Override
	public boolean getTop(Hand hand) {
		return super.getTop();
	}
	@Override
	public boolean getBumper(Hand hand) {
		return super.getBumper();
	}
	@Override
	public boolean getRawButton(int button) {
		return false;
	}
	@Override
	public int getPOV(int pov) {
		return super.getPOV();
	}	
}
