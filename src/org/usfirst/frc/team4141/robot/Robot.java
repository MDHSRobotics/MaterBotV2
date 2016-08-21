
package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.MDDriveSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.MDRobotBase.MDDriveSubsystem.Type;
import org.usfirst.frc.team4141.MDRobotBase.MDPrintCommand;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MDAnalogInput;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.RobotDiagnostics;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
import org.usfirst.frc.team4141.robot.commands.ExampleCommand;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.DiagnosticsSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;

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

//TODO figure out why the rumbling was needed and refactor into OI
//	private boolean rumbling = false;
//	private double rumblestart;
	
	@Override
	protected void configureRobot() {
		
		//A robot is composed of subsystems
		//A robot will typically have 1 drive system and several other fit to purpose subsystems
		
		
		//The Drive system is a special subsystem in that it has specific logic handle the speed controllers
		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
				.add(MotorPosition.left, new Victor(0))
				.add(MotorPosition.right, new Victor(1))
				.add("distanceSensor",new MDAnalogInput(0))
				.add("accelerometer", new MD_BuiltInAccelerometer())
				.configure()
		);	

		
		//Special Subsystem used for RobotDiagnostics
		add( new DiagnosticsSubsystem(this, "diagnosticsSubsystem")
				 .add("diagnosticsSensor",new RobotDiagnostics())
				 .add("diagnosticsScanPeriod",new DoubleConfigSetting(0.02, 20.0, 0.1))
			     .configure()
		);
		
		//Subsystem to manage robot wide config settings
		add( new CoreSubsystem(this, "core")
				 .add("name",new StringConfigSetting("Mr. Roboto"))//go ahead name your robot
				 .configure()
		);
		
		//Subsystem to manage WebSocket Communications
		add( new WebSocketSubsystem(this, "WebSockets")
				 .add("enableWebSockets",new StringConfigSetting("Mr. Roboto"))
				 .configure()
		);
		
		//A robot will define several commands
		add(new MDPrintCommand(this,"command1","command1 message")
				.add(getSubsystems().get("diagnosticsSubsystem")));
		add(new ExampleCommand(this,"exampleCommand"));


		autonomousCommand=getCommands().get("AutonomousCommand");

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
