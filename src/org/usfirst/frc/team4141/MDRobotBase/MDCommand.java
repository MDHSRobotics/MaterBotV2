package org.usfirst.frc.team4141.MDRobotBase;


import org.usfirst.frc.team4141.MDRobotBase.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;

public abstract class MDCommand extends Command {
	private MDRobotBase robot;
	private boolean isFinished = false;

	public MDCommand(MDRobotBase robot,String name) {
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

	@Override
	protected void initialize() {
		isFinished = false;
	}

	@Override
	protected void execute() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
		isFinished = true;
	}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }

	public void add(MDSubsystem subsystem) {
		requires(subsystem);		
	}

}
