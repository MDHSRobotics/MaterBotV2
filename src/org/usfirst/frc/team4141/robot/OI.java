package org.usfirst.frc.team4141.robot;

import java.util.Hashtable;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.JoystickButton;





/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	//// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);


    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


	private MDRobotBase robot;
	private Hashtable<String, JoystickButton> buttons;
	
  

	public MDRobotBase getRobot() {
		return robot;
	}

	Joystick joystick = new Joystick(0);
	public Joystick getJoystick() {
		return joystick;
	}
  
	public OI(MDRobotBase robot) {
 
	  this.robot = robot;
	  this.buttons=new Hashtable<String, JoystickButton>();
	}

	public Hashtable<String, JoystickButton> getButtons() {
		return buttons;
	}
	
	public void add(String name,JoystickButton button){
		buttons.put(name, button);
	}

}  

