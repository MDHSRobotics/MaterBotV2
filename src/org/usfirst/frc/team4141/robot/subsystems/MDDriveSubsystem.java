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
			if(getMotors()==null){
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}				
			if(getMotors().size()==2){
				if()!getMotors().containsKey(MotorPosition.left.toString()) || !getMotors().containsKey(MotorPosition.right.toString())){
					throw new IllegalArgumentException("Invalid motor configuration for TankDrive system with 2 motors.");
				}
				robotDrive = new RobotDrive(get(MotorPosition.left), get(MotorPosition.right));
			}
			else if(getMotors().size()==4){
				if(!getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
						  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
					throw new IllegalArgumentException("Invalid motor configuration for TankDrive system with 4 motors.");
				}
				robotDrive = new RobotDrive(new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearLeft), get(MotorPosition.frontLeft)}),
						new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearRight), get(MotorPosition.frontRight)}));
			}
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
	
	private double calculateMagnitude(double x,double y){
		//joystick will give x & y in a range of -1 <= 0 <= 1
		// the magnitude indicates how fast the robot shoudl be driving
		// use the distance formula:  s = sqrt(x^2 = y^2)
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	private double calculateDirection(double x, double y){
		private double calculateMagnitude(double x,double y){
			//joystick will give x & y in a range of -1 <= 0 <= 1
			// the direction indicates in what angle the robot should move. this is not rotation.
			// 0 degrees means go straight
			// 180 degrees means back up
			// 90 degrees means go to the right
			// use trigonometry.  Tangent (angle) = opposite (in our case x) / adjacent (in our case y)
			// we have x & y, solve for angle by taking the inverse tangent
			// angle = tangent^-1(x/y)
			// since this includes a division we need logic to handle things when x & y are 0
			double angle = 0;
			if(y==0){
				if(x>0) angle = Math.PI/2;
				if (x<0) angle = -Math.PI/2;
			}
			else if (x==0){
				if(y<0) angle = -Math.PI;
			}
			else{
				angle = Math.atan2(x, y);
			}
			return angle;  //range is -pi to +pi
			//TODO: Do we need to convert to degrees?
		}
	}
	
	public void arcadeDrive(Joystick joystick) {
		switch(type){
		case MecanumDrive:
			double magnitude= calculateMagnitude(joystick.getRawAxis(0),joystick.getRawAxis(1));
			double direction = calculateDirection(joystick.getRawAxis(0),joystick.getRawAxis(1));
			double rotation = joystick.getRawAxis(5);
			robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
			break;
		default:
			  double rightTriggerValue = joystick.getRawAxis(3);
			  double leftTriggerValue = -joystick.getRawAxis(2);
			  double forward = (rightTriggerValue+leftTriggerValue)*(1.0-(1.0-c));
		  	  double rotate = joystick.getRawAxis(0);
		  	  double[] speeds = interpolator.calculate(forward, rotate);
			  robotDrive.tankDrive(-speeds[0], speeds[1]);
		}
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
