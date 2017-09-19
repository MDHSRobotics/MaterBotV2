package org.usfirst.frc.team4141.MDRobotBase;



import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class MDCommandGroup extends CommandGroup {
	private MDRobotBase robot;

	public MDCommandGroup(MDRobotBase robot,String name) {
		super(name);
		this.robot=robot;
	}

	public void log(String methodName, String message) {
		getRobot().log(this.getClass().getSimpleName()+"."+methodName+"()", message);
		
	}
	public void log(Level level, String methodName, String message) {
		getRobot().log(level,this.getClass().getSimpleName()+"."+methodName+"()", message);
	}

	public MDRobotBase getRobot() {
		return robot;
	}

}
