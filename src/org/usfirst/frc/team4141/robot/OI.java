package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.ConsoleOI;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.OIBase;
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
		
		//Configure the joystick(s) here
		add(new MDJoystick(getRobot(), "joystick", 0)
			.whenPressed("A",3,new MDPrintCommand(getRobot(),"A Command","A Command message"))
			.whileHeld("leftBumper",6,new MDPrintCommand(getRobot(),"Left Bumper Command","Left Bumper Command message"))
			.configure()
		);

		//Configure the RioHID here
		// Uncomment the following to attach a command to the user button on the RoboRio
//		add(new RioHID(getRobot())
//			.whileHeld(new MDPrintCommand(getRobot(),"ExampleCommand1","ExampleCommand1 message"))
//			.configure()
//		);
		
		
		//Configure the MDConsole OI here		
		add(new ConsoleOI(getRobot())
				.whenPressed("Command1",0,new MDPrintCommand(getRobot(),"Command1","Command1 message"))
				.configure()
			);		
		
	}

}  


