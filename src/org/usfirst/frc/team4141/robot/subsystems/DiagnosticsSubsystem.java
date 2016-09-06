
package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.DiagnosticScan;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

import edu.wpi.first.wpilibj.Notifier;


/**
 *
 */
public class DiagnosticsSubsystem extends MDSubsystem {
    
    // Special purpose subsystem that contains sensors related to the internals of the RoboRio
	// Used for diagnostics
	
	// Depends on the RobotDiagnostics sensor
	// Schedules periodic heartbeat
	Notifier scan;
	
	private double scanPeriod = 0.1;  //0.1 seconds

    public DiagnosticsSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	} 


	@Override
	protected void setUp() {
		if(getConfigSettings()!=null && getConfigSettings().containsKey("diagnosticsScanPeriod")){
			scanPeriod = getConfigSettings().get("diagnosticsScanPeriod").getDouble();
		}	
	}
	
	@Override
	protected void initDefaultCommand() {
		System.out.println("initDefaultCommand()");
		scan = new Notifier(new DiagnosticScan(getRobot()));
		scan.startPeriodic(scanPeriod);
	}
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(this.scan!=null && changedSetting.getName().equals("diagnosticsScanPeriod")){
			scanPeriod = getConfigSettings().get("diagnosticsScanPeriod").getDouble();
			System.out.println("changing scan period to "+scanPeriod);
			scan.startPeriodic(scanPeriod);
//			scan.stop();
//			scan = new Notifier(new DiagnosticScan(getRobot()));
//			scan.startPeriodic(scanPeriod);
		}
	}
}