package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class MDJoystick extends Joystick {
	private String name;
	private MDRobotBase robot;
	private boolean isConfigured;

	public MDJoystick(MDRobotBase robot, String name,int port){
		super(port);
		this.name = name;
		this.robot = robot;
	}
	
	public String getName(){
		return name;
	}
	public MDRobotBase getRobot(){
		return robot;
	}

	public MDJoystick cancelWhenPressed(String buttonName,int buttonNumber,String commandName){
		if(robot.getCommands().containsKey(commandName)){
			JoystickButton button = new JoystickButton(this, buttonNumber);
			button.cancelWhenPressed(robot.getCommands().get(commandName));
		}
		return this;
	}

	public MDJoystick toggleWhenPressed(String buttonName,int buttonNumber,String commandName){
		if(robot.getCommands().containsKey(commandName)){
			JoystickButton button = new JoystickButton(this, buttonNumber);
			button.toggleWhenPressed(robot.getCommands().get(commandName));
		}
		return this;
	}
	
	public MDJoystick whenPressed(String buttonName,int buttonNumber,String commandName){
		if(robot.getCommands().containsKey(commandName)){
			JoystickButton button = new JoystickButton(this, buttonNumber);
			button.whenPressed(robot.getCommands().get(commandName));
		}
		return this;
	}

	public MDJoystick whenReleased(String buttonName,int buttonNumber,String commandName){
		if(robot.getCommands().containsKey(commandName)){
			JoystickButton button = new JoystickButton(this, buttonNumber);
			button.whenReleased(robot.getCommands().get(commandName));
		}
		return this;
	}

	public MDJoystick whileHeld(String buttonName,int buttonNumber,String commandName){
		if(robot.getCommands().containsKey(commandName)){
			JoystickButton button = new JoystickButton(this, buttonNumber);
			button.whileHeld(robot.getCommands().get(commandName));
		}
		return this;
	}

	public MDJoystick configure() {
		isConfigured = true;
		return this;
	}
	
	public boolean isCOnfigured(){ return isConfigured;}
}
