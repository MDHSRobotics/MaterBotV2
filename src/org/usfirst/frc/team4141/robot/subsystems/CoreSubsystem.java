package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

public class CoreSubsystem extends MDSubsystem {

	public CoreSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
	}

	@Override
	protected void initDefaultCommand() {
	}

	@Override
	protected void setUp() {
		if(getConfigSettings().containsKey("name")){
			getRobot().setName(getConfigSettings().get("name").getString());
		}
	}
}
