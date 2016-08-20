
package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;


/**
 *
 */
public class DiagnosticsSubSystem extends MDSubsystem {
    
    // Special purpose subsystem that contains sensors related to the internals of the RoboRio
	// Used for diagnostics

    public DiagnosticsSubSystem(MDRobotBase robot, String name) {
		super(robot, name);
	}
    

	public void initDefaultCommand() {
    }
}

