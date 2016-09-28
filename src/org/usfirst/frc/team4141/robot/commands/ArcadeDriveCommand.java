package org.usfirst.frc.team4141.robot.commands;


import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;


public class ArcadeDriveCommand extends MDCommand {
	MDDriveSubsystem driveSys;
	public ArcadeDriveCommand(MDRobotBase robot) {
		super(robot,"ArcadeDriveCommand");
		MDSubsystem sys = robot.getSubsystems().get("driveSystem");
		requires(sys);
		driveSys = (MDDriveSubsystem)sys;
    }
	
	private MDJoystick joystick = null;
	@Override
	protected void initialize() {
		super.initialize();
		joystick = getRobot().getOi().getJoysticks().get("joystick");
	}

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	driveSys.arcadeDrive(joystick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	super.end();
    	driveSys.stop();
    }

}