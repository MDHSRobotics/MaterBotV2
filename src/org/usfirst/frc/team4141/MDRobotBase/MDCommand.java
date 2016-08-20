package org.usfirst.frc.team4141.MDRobotBase;


import org.usfirst.frc.team4141.MDRobotBase.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;

public abstract class MDCommand extends Command {
	private MDRobotBase robot;

	public MDCommand(MDRobotBase robot,String name) {
		super(name);
		this.robot=robot;
	}

	public void log(String methodName, String message) {
		getRobot().log(this.getClass().getName()+"."+methodName+"()", message);
		
	}
	public void log(Level level, String methodName, String message) {
		getRobot().log(level,this.getClass().getName()+"."+methodName+"()", message);
		
	}
		
	public MDRobotBase getRobot() {
		return robot;
	}

	private boolean isInitialized = false;
	@Override
	protected void initialize() {
		if(!isInitialized){
			log("initialize",getName());
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		isInitialized =false;
	}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
	public MDCommand add(MDSubsystem subsystem) {
		// used to indicate a subsystem that the command requires
		requires(subsystem);
		return this;
	}
}
