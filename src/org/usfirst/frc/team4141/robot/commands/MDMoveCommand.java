package org.usfirst.frc.team4141.robot.commands;

import java.util.Date;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.commands.MDMoveCommand.Direction;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

public class MDMoveCommand extends MDCommand {
	// The purpoise of this command is to do basic motion testing
	// four directions are defined, see Direction enum
	// the duration is defaulted to 3 seconds
	// speed is defaulted to 0.5
	private double duration = 3000; //3 seconds in milliseconds
	private double speed = 0.6;
	private Direction direction;
	private long start;
	private MDDriveSubsystem driveSystem;
	public enum Direction{
		left,
		reverse,
		right,
		forward
	}

	// ------------------------------------------------ //

	public MDMoveCommand(MDRobotBase robot, String name, Direction direction) {
		super(robot,name);
		this.direction = direction;
	}

	// ------------------------------------------------ //
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {
		if(!getRobot().getSubsystems().containsKey("driveSystem")){
			log(Level.ERROR, "initialize()",  "drive system not found");
		}
		driveSystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem"); 
		start =(new Date()).getTime();
	}
	
	@Override
	protected boolean isFinished() {
		long now = (new Date()).getTime();
		return  (now >=(start+duration));
	}
	
	@Override
	protected void execute() {
		switch(direction){
		case right:
			driveSystem.right(speed);
			break;
		case left:
			driveSystem.left(speed);
			break;
		case reverse:
			driveSystem.reverse(speed);
			break;
		default: //default to forward
			driveSystem.forward(speed);
		}
	}
	
	@Override
	protected void end() {
		driveSystem.stop();
	}
}
