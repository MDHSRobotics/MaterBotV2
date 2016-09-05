package org.usfirst.frc.team4141.MDRobotBase;

import java.util.Hashtable;

import org.usfirst.frc.team4141.MDRobotBase.notifications.ConsoleRumbleNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.HeartbeatNotification;

import edu.wpi.first.wpilibj.Joystick;


public class ConsoleOI extends MDGenericHID {
	//ConsoleOI is a Human Interface Device built into MDConsole as an extension to a joystick
	//it defines a button for each command that are added to it


	private boolean isConfigured;
	private Hashtable<Integer,MDConsoleButton> buttons;

	public ConsoleOI(MDRobotBase robot) {
		this(robot,"ConsoleOI");
	}
	
	public ConsoleOI(MDRobotBase robot, String name) {
		super(robot,name);
		this.buttons = new Hashtable<Integer,MDConsoleButton>();
		
		System.out.println("ConsoleOI created");
	}
	
	public Hashtable<Integer,MDConsoleButton> getButtons() { return buttons;}


	@Override
	public boolean getRawButton(int button) {
		Integer id = new Integer(button);
		if(buttons.containsKey(id)){
			return buttons.get(id).get();
		}
		return false;
	}


	public ConsoleOI cancelWhenPressed(String buttonName,int buttonNumber,String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			MDConsoleButton button = new MDConsoleButton(this, buttonName, buttonNumber);
			button.cancelWhenPressed(getRobot().getCommands().get(commandName));
			buttons.put(new Integer(buttonNumber), button);
		}
		return this;
	}

	public ConsoleOI toggleWhenPressed(String buttonName,int buttonNumber,String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			MDConsoleButton button = new MDConsoleButton(this, buttonName, buttonNumber);
			button.toggleWhenPressed(getRobot().getCommands().get(commandName));
			buttons.put(new Integer(buttonNumber), button);
		}
		return this;
	}
	
	public ConsoleOI whenPressed(String buttonName,int buttonNumber,String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			MDConsoleButton button = new MDConsoleButton(this, buttonName, buttonNumber);
			button.whenPressed(getRobot().getCommands().get(commandName));
			buttons.put(new Integer(buttonNumber), button);
		}
		return this;
	}

	public ConsoleOI whenReleased(String buttonName,int buttonNumber,String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			MDConsoleButton button = new MDConsoleButton(this, buttonName, buttonNumber);
			button.whenReleased(getRobot().getCommands().get(commandName));
			buttons.put(new Integer(buttonNumber), button);
		}
		return this;
	}

	public ConsoleOI whileHeld(String buttonName,int buttonNumber,String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			MDConsoleButton button = new MDConsoleButton(this, buttonName, buttonNumber);
			button.whileHeld(getRobot().getCommands().get(commandName));
			buttons.put(new Integer(buttonNumber), button);
		}
		return this;
	}
	
	public ConsoleOI configure(){
		isConfigured = true;
		return this;
	}
	public boolean isConfigured(){ return isConfigured;}

	public void setRumble(Joystick.RumbleType hand,double value) {
		getRobot().post(new ConsoleRumbleNotification(getRobot(),hand,value,true,true));
	}
}
