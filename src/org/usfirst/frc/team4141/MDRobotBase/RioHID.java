package org.usfirst.frc.team4141.MDRobotBase;



import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.buttons.InternalButton;

public class RioHID extends MDGenericHID {
	//The RoboRio comes with a built-in user button
	//This class implements a Human Interface Device for this button	
	//This button is mapped to the trigger and to raw button index 0
	private InternalButton userButton;
	private Notifier agent;
	private boolean isConfigured;

	public RioHID(MDRobotBase robot) {
		this(robot,"RioHID");
	}
	public RioHID(MDRobotBase robot, String name) {
		super(robot,name);
		this.userButton = new InternalButton();
		this.agent = new Notifier(new UserButtonAgent(userButton));
		agent.startPeriodic(0.2);
		System.out.println("RioHID created");
	}


	@Override
	public boolean getTrigger(Hand hand) {
		return userButton.get();
	}


	@Override
	public boolean getRawButton(int button) {
		if(button==0) return userButton.get();
		return false;
	}


	public RioHID cancelWhenPressed(String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			userButton.cancelWhenPressed(getRobot().getCommands().get(commandName));
		}
		return this;
	}

	public RioHID toggleWhenPressed(String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			userButton.toggleWhenPressed(getRobot().getCommands().get(commandName));
		}
		return this;
	}
	
	public RioHID whenPressed(String commandName){
		System.out.println("configuring when pressed to fire command "+commandName);
		if(getRobot().getCommands().containsKey(commandName)){
			System.out.println("found "+commandName);
			userButton.whenPressed(getRobot().getCommands().get(commandName));
		}
		return this;
	}

	public RioHID whenReleased(String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			userButton.whenReleased(getRobot().getCommands().get(commandName));
		}
		return this;
	}

	public RioHID whileHeld(String commandName){
		if(getRobot().getCommands().containsKey(commandName)){
			userButton.whileHeld(getRobot().getCommands().get(commandName));
		}
		return this;
	}
	public RioHID configure() {
		isConfigured = true;
		return this;
	}
	public boolean isConfigured() {
		return isConfigured;
	}

}
