package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.MultiSpeedController;
import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.TankDriveInterpolator;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.robot.commands.ArcadeDriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;


public class MDDriveSubsystem extends MDSubsystem {
	public enum Type{
		TankDrive,
		ToteDrive,
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
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.left.toString()) || !getMotors().containsKey(MotorPosition.right.toString())){
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}				
			robotDrive = new RobotDrive(get(MotorPosition.left), get(MotorPosition.right));
			break;
		case ToteDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
									  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
				throw new IllegalArgumentException("Invalid motor configuration for Tote system.");
			}		

			robotDrive = new RobotDrive(new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearLeft), get(MotorPosition.frontLeft)}),
					new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearRight), get(MotorPosition.frontRight)}));

			break;
		case MecanumDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
									  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
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
		setDefaultCommand(new ArcadeDriveCommand(getRobot()));
	}
	public void arcadeDrive(Joystick joystick) {
	  double rightTriggerValue = joystick.getRawAxis(3);
	  double leftTriggerValue = -joystick.getRawAxis(2);
	  double forward = (rightTriggerValue+leftTriggerValue)*(1.0-(1.0-c));
  	  double rotate = joystick.getRawAxis(0);
  	  double[] speeds = interpolator.calculate(forward, rotate);
	
	  robotDrive.tankDrive(speeds[0], speeds[1]);
	}
	
	public void stop(){
		robotDrive.stopMotor();
	}	
	
	private double c = 1.0;
	private TankDriveInterpolator interpolator = new TankDriveInterpolator();
	@Override
	protected void setUp() {
		//called after configuration is completed
		if(getConfigSettings().containsKey("c")) c = getConfigSettings().get("c").getDouble();
		if(getConfigSettings().containsKey("a")) interpolator.setA(getConfigSettings().get("a").getDouble());
		if(getConfigSettings().containsKey("b")) interpolator.setB(getConfigSettings().get("b").getDouble());
	}
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("c")) c = changedSetting.getDouble();
		if(changedSetting.getName().equals("a")) interpolator.setA(changedSetting.getDouble());
		if(changedSetting.getName().equals("b")) interpolator.setB(changedSetting.getDouble());
		//method to listen to setting changes
	}
}
