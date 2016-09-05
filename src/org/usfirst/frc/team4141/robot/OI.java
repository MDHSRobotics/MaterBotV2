package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.OIBase;
import org.usfirst.frc.team4141.MDRobotBase.RioHID;


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
//		add(new MDJoystick(getRobot(), "joystick1", 0)
//			.whenPressed("A",3,"autonomousCommand")
//			.whileHeld("leftBumper",6,"ExampleCommand1")
//		);
		//Configure the RioHID here
		add(new RioHID(getRobot())
			.whileHeld("ExampleCommand1")
		);
		
		//Configure console here
	}


}  


