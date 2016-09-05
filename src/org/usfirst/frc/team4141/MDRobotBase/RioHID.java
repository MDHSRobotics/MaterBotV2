package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.hal.HALUtil;

public class RioHID extends GenericHID {
	//The RoboRio comes with a built-in user button
	//This class implements a Human Interface Device for this button	
	//This button is mapped to the trigger and to raw button index 0
	private String name;
	private MDRobotBase robot;
	private InternalButton userButton;
	private Notifier agent;

	public RioHID(MDRobotBase robot) {
		this(robot,"RioHID");
	}
	public RioHID(MDRobotBase robot, String name) {
		this.robot = robot;
		this.name=name;
		this.userButton = new InternalButton();
		this.agent = new Notifier(new UserButtonAgent(userButton));
		agent.startPeriodic(0.2);
		System.out.println("RioHID created");
	}
	public String getName(){
		return name;
	}
	public MDRobotBase getRobot(){
		return robot;
	}
	@Override
	public double getX(Hand hand) {
		return 0;
	}

	@Override
	public double getY(Hand hand) {
		return 0;
	}

	@Override
	public double getZ(Hand hand) {
		return 0;
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
		return HALUtil.getFPGAButton();
	}

	@Override
	public boolean getTop(Hand hand) {
		return false;
	}

	@Override
	public boolean getBumper(Hand hand) {
		return false;
	}

	@Override
	public boolean getRawButton(int button) {
		if(button==0) return HALUtil.getFPGAButton();
		return false;
	}

	@Override
	public int getPOV(int pov) {
		return 0;
	}
	public RioHID cancelWhenPressed(String commandName){
		if(robot.getCommands().containsKey(commandName)){
			userButton.cancelWhenPressed(robot.getCommands().get(commandName));
		}
		return this;
	}

	public RioHID toggleWhenPressed(String commandName){
		if(robot.getCommands().containsKey(commandName)){
			userButton.toggleWhenPressed(robot.getCommands().get(commandName));
		}
		return this;
	}
	
	public RioHID whenPressed(String commandName){
		System.out.println("configuring when pressed to fire command "+commandName);
		if(robot.getCommands().containsKey(commandName)){
			System.out.println("found "+commandName);
			userButton.whenPressed(robot.getCommands().get(commandName));
		}
		return this;
	}

	public RioHID whenReleased(String commandName){
		if(robot.getCommands().containsKey(commandName)){
			userButton.whenReleased(robot.getCommands().get(commandName));
		}
		return this;
	}

	public RioHID whileHeld(String commandName){
		if(robot.getCommands().containsKey(commandName)){
			userButton.whileHeld(robot.getCommands().get(commandName));
		}
		return this;
	}
}
