package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends MDRobotBase {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	@Override
	protected void configureRobot() {

		//A commands needs to be configured for the autonomous mode.
		//In some cases it is desirable to have more than 1 auto command and make a decision at game time which command to use
//		setAutonomousCommand(new MDCommand[]{
//				new MDPrintCommand(this,"AutonomousCommand","AutonomousCommand message")
//			}, "AutonomousCommand"  //specify the default
//		);

		//Subsystem to manage robot wide config settings
		add( new CoreSubsystem(this, "core")
				 .add("name",new StringConfigSetting("MaterBot"))					//go ahead name your robot
				 .add("autoCommand",new StringConfigSetting("AutonomousCommand"))		//name of autoCommand you wish to start with
				 .configure()
		);		
		
		//A robot is composed of subsystems
		//A robot will typically have 1 drive system and several other fit to purpose subsystems		
		//The Drive system is a special subsystem in that it has specific logic handle the speed controllers
		//We have 2 types of drive systems, tank drive and mecanum drive
		//uncomment the desired drive system and adjust the motor configuration as needed
		//Mecanum example :
		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
				.add("accelerometer", new MD_BuiltInAccelerometer())
				.add("IMU", new MD_IMU())
				.add(MotorPosition.frontLeft, new CANTalon(3))
				.add(MotorPosition.frontRight, new CANTalon(4))
				.add(MotorPosition.rearLeft, new CANTalon(5))
				.add(MotorPosition.rearRight, new CANTalon(6))
				.add("Drive-F", new DoubleConfigSetting(0.0, 1.0, 0.0))
		 	    .add("Drive-P", new DoubleConfigSetting(0.0, 1.0, 0.1))
				.add("Drive-I", new DoubleConfigSetting(0.0, 1.0, 0.8))
				.add("Drive-D", new DoubleConfigSetting(0.0, 1.0, 0.1))
				.add("a", new DoubleConfigSetting(0.0, 1.0, 0.25)) //High Speed - Turn Factor
		 	    .add("b", new DoubleConfigSetting(0.0, 1.0, 0.4)) //Slow Speed - Turn Factor
				.add("c", new DoubleConfigSetting(0.0, 1.0, 1.0)) //Speed Governor
				.configure()
		);	
		
		//TankDrive with 2 motors example:
//		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
//				.add(MotorPosition.right, new Victor(0))
//				.add(MotorPosition.left, new Victor(1))
//				.add("accelerometer", new MD_BuiltInAccelerometer())
//				.add("IMU", new MD_IMU())
//				.configure()
//		);	
		//TankDrive with 4 motors example:
//		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
//				.add(MotorPosition.frontRight, new Victor(0))
//				.add(MotorPosition.rearRight, new Victor(1))
//				.add(MotorPosition.frontLeft, new Victor(2))
//				.add(MotorPosition.rearLeft, new Victor(3))
//				.add("accelerometer", new MD_BuiltInAccelerometer())
//				.add("IMU", new MD_IMU())
//				.configure()
//		);	
		

	}



	
	//Override lifecycle methods, as needed
	//	@Override
	//	public void teleopPeriodic() {
	//		super.teleopPeriodic();
	//		...
	//	}
	//	@Override
	//	public void autonomousPeriodic() {
	//		super.autonomousPeriodic();
	//		...
	//	}	
	
		
		//Event manager WebSocket related methods
		//Override as needed
	//	@Override
	//	public void onConnect(Session session) {
	//		super.onConnect(session);
	//		...
	//	}
		
}
