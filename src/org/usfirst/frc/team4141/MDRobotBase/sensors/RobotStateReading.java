package org.usfirst.frc.team4141.MDRobotBase.sensors;

import edu.wpi.first.wpilibj.RobotState;

public class RobotStateReading extends StringSensorReading {

	public enum lifecycleState{
		Disabled,
		Autonomous,
		Teleop,
		Test
	}
	public RobotStateReading(String name) {
		super(name,null);	
	}

	public void refresh() {
		if(!RobotState.isEnabled()) setValue(lifecycleState.Disabled.toString());
		else if(RobotState.isDisabled()) setValue(lifecycleState.Disabled.toString());
		else if(RobotState.isOperatorControl()) setValue(lifecycleState.Teleop.toString());
		else if(RobotState.isAutonomous()) setValue(lifecycleState.Autonomous.toString());
		else if(RobotState.isTest()) setValue(lifecycleState.Test.toString());
	}
}
