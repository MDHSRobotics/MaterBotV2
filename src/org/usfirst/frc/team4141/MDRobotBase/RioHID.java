package org.usfirst.frc.team4141.MDRobotBase;



import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.GenericHID.HIDType;
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
		super(robot,name,-2,HIDType.kXInputUnknown);
		this.userButton = new InternalButton();
		this.agent = new Notifier(new UserButtonAgent(userButton));
		agent.startPeriodic(0.2);
		debug("RioHID created");
	}

	@Override
	public int getPOV() {
		return (userButton.get()?1:0);
	}


	@Override
	public boolean getRawButton(int button) {
		return userButton.get();
	}


	public RioHID cancelWhenPressed(MDCommand command){
			userButton.cancelWhenPressed(command);
		return this;
	}

	public RioHID toggleWhenPressed(MDCommand command){
			userButton.toggleWhenPressed(command);
		return this;
	}
	
	public RioHID whenPressed(MDCommand command){
			userButton.whenPressed(command);
		return this;
	}

	public RioHID whenReleased(MDCommand command){
			userButton.whenReleased(command);
		return this;
	}

	public RioHID whileHeld(MDCommand command){
			userButton.whileHeld(command);
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
