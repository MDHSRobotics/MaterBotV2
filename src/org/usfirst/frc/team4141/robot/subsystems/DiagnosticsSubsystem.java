
package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.DiagnosticScan;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

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
	} 

	public void initDefaultCommand() {
		scan = new Notifier(new DiagnosticScan());
		scan.startPeriodic(scanPeriod);
	}

	@Override
	protected void setUp() {
		//see if the scan period has been configured
		if(getConfigSettings()!=null && getConfigSettings().containsKey("diagnosticsScanPeriod")){
			scanPeriod = getConfigSettings().get("diagnosticsScanPeriod").getDouble();
		}
	}
}