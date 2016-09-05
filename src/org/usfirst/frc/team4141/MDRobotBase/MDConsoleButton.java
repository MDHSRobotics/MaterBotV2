package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.buttons.InternalButton;

public class MDConsoleButton extends InternalButton {

	private ConsoleOI consoleOI;
	private int buttonNumber;
	private MDCommand command;
	private String name;

	public MDConsoleButton(ConsoleOI consoleOI, String name, int buttonNumber) {
		this.consoleOI = consoleOI;
		this.name=name;
		this.buttonNumber=buttonNumber;
	}

	public ConsoleOI getConsoleOI(){ return consoleOI;}
	public int getButtonNumber(){ return buttonNumber;}
	public String getName(){ return name;}
	
	
	public void cancelWhenPressed(MDCommand command){
		super.cancelWhenPressed(command);
		this.command = command;
	}

	public void toggleWhenPressed(MDCommand command){
		super.toggleWhenPressed(command);
		this.command = command;
	}
	
	public void whenPressed(MDCommand command){
		super.whenPressed(command);
		this.command = command;
	}

	public void whenReleased(MDCommand command){
		super.whenReleased(command);
		this.command = command;
	}

	public void whileHeld(MDCommand command){
		super.whileHeld(command);
		this.command = command;
	}
	
	public MDCommand getCommand(){ return command;}
	
}
