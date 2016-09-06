
package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.RobotDiagnostics;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.config.BooleanConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.DiagnosticsSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;

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

	@Override
	protected void configureRobot() {

		//A commands needs to be configured for the autonomous mode.
		//In some cases it is desirable to have more than 1 auto command and make a decision at game time which command to use
		setAutonomousCommand(new MDCommand[]{
				new MDPrintCommand(this,"AutonomousCommand1","AutonomousCommand1 message"),
				new MDPrintCommand(this,"AutonomousCommand2","AutonomousCommand2 message"),
				new MDPrintCommand(this,"AutonomousCommand3","AutonomousCommand3 message"),
				new MDPrintCommand(this,"AutonomousCommand4","AutonomousCommand4 message")
			}
			, "AutonomousCommand4"  //specify the default
		);

		
		
		//A robot is composed of subsystems
		//A robot will typically have 1 drive system and several other fit to purpose subsystems		
		//The Drive system is a special subsystem in that it has specific logic handle the speed controllers
		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
				.add(MotorPosition.left, new Victor(0))
				.add(MotorPosition.right, new Victor(1))
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
				 .add("name",new StringConfigSetting("Mr. Roboto2"))					//go ahead name your robot
				 .add("autoCommand",new StringConfigSetting("AutonomousCommand1"))		//name of autoCommand you wish to start with
				 .configure()
		);
		
		//Subsystem to manage WebSocket Communications
		add( new WebSocketSubsystem(this, "WebSockets")
				 .add("enableWebSockets",new BooleanConfigSetting(true))
				 .configure()
		);



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
