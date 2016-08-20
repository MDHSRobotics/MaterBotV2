package org.usfirst.frc.team4141.MDRobotBase;

import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;


public class MDDriveSubsystem extends MDSubsystem {
	public enum Type{
		TankDrive,
		FourWheelDrive,
		MecanumDrive
	}
	public enum MotorPosition{
		left,
		right,
		frontLeft,
		rearLeft,
		frontRight,
		rearRight
	}
	
	private RobotDrive robotDrive;
	private Type type;
	
	public MDDriveSubsystem(MDRobotBase robot, String name, Type type) {
		super(robot, name);
		this.type = type;
	}
	
	public MDDriveSubsystem add(MotorPosition position,SpeedController speedController){
		if(speedController instanceof PWM){
			super.add(position.toString(),(PWM)speedController);
		}
		else
		{
			throw new NotImplementedException("input is not a PWM");
		}
		return this;
	}

	public MDDriveSubsystem add(String name,Sensor sensor){
		super.add(name,sensor);
		sensor.setName(name);
		return this;
	}
	
	public SpeedController get(MotorPosition position){
		PWM motor = getMotors().get(position.toString());
		if(motor instanceof SpeedController){
			return (SpeedController) motor;
		}
		return null;
	}
	
	public MDSubsystem configure(){
		super.configure();
		switch(type){
		case TankDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.left) || !getMotors().containsKey(MotorPosition.right)){
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}				
			robotDrive = new RobotDrive(get(MotorPosition.left), get(MotorPosition.right));
			break;
		case FourWheelDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.rearLeft) || !getMotors().containsKey(MotorPosition.frontLeft)
									  || !getMotors().containsKey(MotorPosition.rearRight) || !getMotors().containsKey(MotorPosition.frontRight)){
				throw new IllegalArgumentException("Invalid motor configuration for FourWheelDrive system.");
			}		
			robotDrive = new RobotDrive(get(MotorPosition.rearLeft), get(MotorPosition.frontLeft),
					get(MotorPosition.rearRight), get(MotorPosition.frontRight));
			break;
		case MecanumDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.rearLeft) || !getMotors().containsKey(MotorPosition.frontLeft)
									  || !getMotors().containsKey(MotorPosition.rearRight) || !getMotors().containsKey(MotorPosition.frontRight)){
				throw new IllegalArgumentException("Invalid motor configuration for MecanumDrive system.");
			}	
			robotDrive = new RobotDrive(get(MotorPosition.rearLeft), get(MotorPosition.frontLeft),
					get(MotorPosition.rearRight), get(MotorPosition.frontRight));
			break;
		default:
			throw new NotImplementedException("drive of type "+type.toString()+" is not supported.");
		}
		robotDrive.stopMotor();
		return this;
	}
	public Type getType(){ return type;}

	@Override
	protected void initDefaultCommand() {
		//set up default command, as needed
	}

	@Override
	protected void setUp() {
		//called after configuration is completed
		
	}
}
