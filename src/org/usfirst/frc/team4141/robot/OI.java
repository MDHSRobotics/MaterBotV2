package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.ConsoleOI;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.OIBase;
import org.usfirst.frc.team4141.MDRobotBase.RioHID;
import org.usfirst.frc.team4141.robot.commands.MDMoveCommand;
import org.usfirst.frc.team4141.robot.commands.MDMoveCommand.Direction;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends OIBase{
	
	public OI(MDRobotBase robot) {
		super(robot);
		System.out.println("OI created");
	}


	public void configureOI(){
		//A robot should have 1 or more operator interfaces (OI)
		//Typically, a robot will have at least 1 joystick and 1 console
		
		//if using gamepad, the buttons are generally as follows:
		// x is button 1
		// a is button 2
		// b is button 3
		// y is button 4
		
		
		
		//Configure the joystick(s) here
		add(new MDJoystick(getRobot(), "joystick", 0)
			.whenPressed("rightBumper",5,new MDPrintCommand(getRobot(),"Right Bumper Command","Right Bumper Command message"))
			.whileHeld("leftBumper",6,new MDPrintCommand(getRobot(),"Left Bumper Command","Left Bumper Command message"))
		    //the following commands are test move commands useful in testing drive configuration and set up
		    //comment out and replace as needed
			.whenPressed("X",1,new MDMoveCommand(getRobot(),"left command",Direction.left))
			.whenPressed("A",2,new MDMoveCommand(getRobot(),"reverse command",Direction.reverse))
			.whenPressed("B",3,new MDMoveCommand(getRobot(),"right command",Direction.right))
			.whenPressed("Y",4,new MDMoveCommand(getRobot(),"forward command",Direction.forward))
			.configure()
		);

		//Configure the RioHID here
		// Uncomment the following to attach a command to the user button on the RoboRio
		add(new RioHID(getRobot())
			.whileHeld(new MDPrintCommand(getRobot(),"ExampleCommand1","ExampleCommand1 message"))
			.configure()
		);
		
		
		//Configure the MDConsole OI here		
		add(new ConsoleOI(getRobot())
				.whenPressed("Command1",0,new MDPrintCommand(getRobot(),"Command1","Command1 message"))
				.configure()
			);		
		
	}

}  


