package org.usfirst.frc.team4141.robot.subsystems;

import java.util.Date;

import javax.print.attribute.standard.Media;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.MultiSpeedController;
import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.TankDriveInterpolator;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;


public class MDDriveSubsystem extends MDSubsystem {
	public enum Type{
		TankDrive,
		MecanumDrive
	}
	

	public enum MotorPosition{
		left,
		right,
		secondRight,
		secondLeft,
		frontLeft,
		rearLeft,
		frontRight,
		rearRight
	}
	
	// ------------------------------------------------ //
	
	private RobotDrive robotDrive;
	private Type type;
	private boolean isFlipped = false;
	private boolean resettingGyro = false;
	private long gyroResetStart;
	private long gyroResetDuration = 150;
	private double speed = 0;
	private double c = 1.0;
	private MD_IMU imu;
	private TankDriveInterpolator interpolator = new TankDriveInterpolator();
	
//	private double F=0.0;
//	private double P=0.0;
//	private double I=0.1;
//	private double D=0.0;
//	private double rpm=1.0;
//	rearLeftTalon.setPID(p, i, d, f);//chapter 12
//	private CANTalon rearRightTalon;
//	private CANTalon frontLeftTalon;
//	private CANTalon frontRightTalon;

	
	// ------------------------------------------------ //
	
	/**
	 * The method is used to hold the parameters robot, name, and type.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @param name the default name used after the string in the constructor
	 * @param type is used to determine the type of driveTrain
	 */
	public MDDriveSubsystem(MDRobotBase robot, String name, Type type) {
		super(robot, name);
		this.type = type;
	}
	
	/**
	 * This method is used to hold the parameters position and speedController.
	 * Inside the method is a check to see if the speedController is a PWM or 
	 * a CANTALON in which it will add a position and a speedController. And if it is not a 
	 * PWM or a CANTALON it will return that the input is not a PWM.
	 * 
	 * @param position is used to set the different positions 
	 * @param speedController is used to set the motor to a speed controller
	 * @return true if the PWM or a CANTALON found, else Input is not a PWM.
	 */
	public MDDriveSubsystem add(MotorPosition position,SpeedController speedController){
		if(speedController instanceof PWM || speedController instanceof CANTalon){
			super.add(position.toString(),(SpeedController)speedController);
		}
		else
		{
			throw new NotImplementedException("Input is not a PWM");
		}
		return this;
	}


	public MDDriveSubsystem add(String name,Sensor sensor){
		super.add(name,sensor);
		return this;
	}
	
	// ------------------------------------------------ //
	
	/**
	 * This method is used to map a SpeedController motor to a position on the robot, 
	 * and if the robot is a instance of of a speed controller return the speed controller
	 * as a motor.
	 * 
	 * @param position the mapping of a SpeedController to a motor on the robot
	 * @return a speedController motor, else return nothing.
	 */
	public SpeedController get(MotorPosition position){
		SpeedController motor = getMotors().get(position.toString());
		if(motor instanceof SpeedController){
			return (SpeedController) motor;
		}
		return null;
	}
	
	/**
	 * This method is used as a fail-safe to check that all the motors and sensors used in the
	 * selected drive train is connected and ready to be used. If none of the items are connected 
	 * the Robot will not enable.
	 * 
	 * @return true unless there is a item not connected, and so stop the motors. 
	 */
	public MDSubsystem configure(){
		super.configure();
		switch(type){
		case TankDrive:
			if(getMotors()==null){
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}				
			if(getMotors().size()==2){
				if(!getMotors().containsKey(MotorPosition.left.toString()) || !getMotors().containsKey(MotorPosition.right.toString())){
					throw new IllegalArgumentException("Invalid MDDriveSubsystem TankDrive configuraton, missing motors.");
				}
				robotDrive = new RobotDrive(get(MotorPosition.left), get(MotorPosition.right));
			}
			else if(getMotors().size()==4){
				if(!getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
						  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
					throw new IllegalArgumentException("Invalid MDDriveSubsystem TankDrive configuraton, missing motors.");
				}
				robotDrive = new RobotDrive(new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearLeft), get(MotorPosition.frontLeft)}),
						new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearRight), get(MotorPosition.frontRight)}));
			}
			
//			if(getSolenoids()==null 
//					|| !getSolenoids().containsKey(rightShiftSolenoidName) || !(getSolenoids().get(rightShiftSolenoidName) instanceof Solenoid)) {
//					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid.");
//			}	
//			rightShiftSolenoid=(Solenoid) getSolenoids().get(rightShiftSolenoidName);

//			if(getSolenoids()==null 
//					|| !getSolenoids().containsKey(leftShiftSolenoidName) || !(getSolenoids().get(leftShiftSolenoidName) instanceof Solenoid)) {
//					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid1.");
//			}	
//			leftShiftSolenoid=(Solenoid) getSolenoids().get(leftShiftSolenoidName);
//			
			if(getSensors()==null && !getSensors().containsKey("IMU")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing IMU.");
			}
		    imu=(MD_IMU) getSensors().get("IMU");
		    gyroReset();
		    if(getSensors()==null && !getSensors().containsKey("High Gear")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing Gear Shift Sensors.");
			}
//		    rearLeftTalon = (CANTalon)(getMotors().get(MotorPosition.rearLeft));
//		    rearRightTalon = (CANTalon)(getMotors().get(MotorPosition.rearRight));
//		    frontLeftTalon = (CANTalon)(getMotors().get(MotorPosition.frontLeft));
//		    frontRightTalon = (CANTalon)(getMotors().get(MotorPosition.frontRight));
			
			
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
	
	/**
	 * This method is used to find the type of drive train selected.
	 * 
	 * @return the type of drive train selected.
	 */
	public Type getType() { 
		return type;
	}

	/**
	 * This method holds the default command used in a subsystem.
	 * The command that it is doing here is stopping the motors from moving, so that
	 * the robot does not drive away when we connect.
	 */
	@Override
	protected void initDefaultCommand() {
		robotDrive.stopMotor();
		//set up default command, as needed
		//setDefaultCommand(new ArcadeDriveCommand(getRobot()));
	}
	
	// ------------------------------------------------ //

	/**
	 * This method holds the amount of power given to the motors
	 * when the driver moves the joystick in the x or y direction. 
	 * 
	 * @return the input of the joystick and output power
	 */
	private double calculateMagnitude(double x,double y){
		//joystick will give x & y in a range of -1 <= 0 <= 1
		// the magnitude indicates how fast the robot shoudl be driving
		// use the distance formula:  s = sqrt(x^2 = y^2)
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	/**
	 * This method takes into account the direction that the driver has the joystick in
	 * and then applies the speed accordingly towards the desired direction.
	 *  
	 * @return the input of the joystick and output direction
	 */
	private double calculateDirection(double x, double y){
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
				if(x>0) angle = 90;
				if (x<0) angle = -90;
			}
			else if (x==0){
				if(y<0) angle = 180;
			}
			else{
				angle = Math.atan2(x, y)*180/Math.PI;
			}
			return angle;  
	}
	
	/**
	 * This method holds the parameter joystick.
	 * This method connects the axis of the joystick to the different 
	 * values of magnitude, direction, and rotation for MecanumDrive or
	 * speeds 0 and 1 for Tank Drive.
	 *  
	 * @param joystick to be used as a variable calling the actual Joystick
	 * @return the input of the joystick and output direction
	 */
	public void arcadeDrive(Joystick joystick) {
		switch(type){
		case MecanumDrive:
			double magnitude= calculateMagnitude(joystick.getRawAxis(0),joystick.getRawAxis(1));
			double direction = calculateDirection(-joystick.getRawAxis(0),-joystick.getRawAxis(1));
			double rotation = joystick.getRawAxis(1);
			robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
			break;
		default:
		 // double rightTriggerValue = joystick.getRawAxis(3);
		 //	double leftTriggerValue = -joystick.getRawAxis(2);
			double forwardAxisValue = -joystick.getRawAxis(1);
			double forward = (forwardAxisValue)*(1.0-(1.0-c));
		  	double rotate = -joystick.getRawAxis(0); //(Changed to accompass shifting w/controller and deadzoned)
	  	  //debug("forward = " + forward + ", rotate = " + rotate);
		  	double[] speeds = interpolator.calculate(forward, rotate);
		    //debug("left: "+speeds[0]+", right: "+speeds[1]);
			robotDrive.tankDrive(-speeds[0], -speeds[1]);
		}
	}
	
	// ------------------------------------------------ //

	/**
	 * This method calls all the drive motors that are being used 
	 * to come to a complete stop.
	 */
	public void stop(){
//		debug("motors stopped");
		robotDrive.stopMotor();
		speed = 0;
	}	
	
	/**
	 * This method is used for defining the variable c, a, and b to show up 
	 * on the MDConsole. C being the governor of the speed, a being the speed, 
	 * and be being the torque of the motors.
	 */
	@Override
	protected void setUp() {
		//called after configuration is completed
		if(getConfigSettings().containsKey("c")) c = getConfigSettings().get("c").getDouble();
		if(getConfigSettings().containsKey("a")) interpolator.setA(getConfigSettings().get("a").getDouble());
		if(getConfigSettings().containsKey("b")) interpolator.setB(getConfigSettings().get("b").getDouble());
//		if(getConfigSettings().containsKey("F")) F = getConfigSettings().get("F").getDouble();
//		if(getConfigSettings().containsKey("P")) P = getConfigSettings().get("P").getDouble();
//		if(getConfigSettings().containsKey("I")) I = getConfigSettings().get("I").getDouble();
//		if(getConfigSettings().containsKey("D")) D = getConfigSettings().get("D").getDouble();
//		if(getConfigSettings().containsKey("RPM")) rpm = getConfigSettings().get("RPM").getDouble();//*1000;
	}
	
	/**
	 * This method allows us to change the values of the variable on the fly, 
	 * without going and re-deploying the code every time we want to change the value.
	 * Instead we can now do it with the new and improved MDConsole.
	 */
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("c")) c = changedSetting.getDouble();
		if(changedSetting.getName().equals("a")) interpolator.setA(changedSetting.getDouble());
		if(changedSetting.getName().equals("b")) interpolator.setB(changedSetting.getDouble());
//		if(changedSetting.getName().equals("F")) F = changedSetting.getDouble();
//		if(changedSetting.getName().equals("P")) P = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("I")) I = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("D")) D = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("RPM")) rpm = changedSetting.getDouble();//*1000;
		//method to listen to setting changes
	}

	// ------------------------------------------------ //

	/**
	 * This method calls the robot to make a right turn, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void right(double speed) {
		this.speed = speed;
		
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("right");
		double direction = -90;
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			robotDrive.tankDrive(this.speed, this.speed/10);
		}
	}

	/**
	 * This method calls the robot to make a left turn, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void left(double speed) {
		this.speed = speed;
		
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("left");
		double direction = 90;
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			robotDrive.tankDrive(this.speed/10, this.speed);
		}
	}

	/**
	 * This method calls the robot to go backwards, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void reverse(double speed) {
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("reverse");
		double direction = 180;
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			robotDrive.tankDrive(-this.speed, this.speed);
		}
	}

	/**
	 * This method calls the robot to go fowards, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void forward(double speed) {
	//	debug("forward"); 	
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		
		double direction = 0;
		
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			debug("speed =" + speed);
			robotDrive.tankDrive(this.speed, this.speed);
		}
	}
	
	// ------------------------------------------------ //

	/**
	 * This method calls the robot to flip its motors opposite of 
	 * its current direction. 
	 */
	public void flip() {
		
		if (speed != 0) return;
		isFlipped = !isFlipped;
		debug("flip. isFlipped now sent to " + isFlipped + ". speed = " + speed);
	}
	
	
	/**
	 * This method calls that if the robot receives a go ahead 
	 * from the gyroReset method, then set the time back to 0 until 
	 * the current time is greater than the gyroResetDuration. 
	 * Else the gyro has not been rested.
	 * 
	 * @return the z angle of the imu.
	 */
	public double getAngle() {
		if (resettingGyro) { 
			long now = (new Date()).getTime();
			if (now - gyroResetStart <= gyroResetDuration) return 0;
			else resettingGyro = false;
		}

		return imu.getAngleZ();
//		return imu.getAngleX();
	}

	// ------------------------------------------------ //

	/**
	 * This method calls the reset of the current angle 
	 * values on the imu back to 0, and then states that the 
	 * gyro has been reseted. It also grabs a new time stamp.
	 * 
	 * 
	 * @return the z angle of the imu.
	 */
	public void gyroReset() {
		imu.reset();
		resettingGyro = true;
	    gyroResetStart = (new Date()).getTime();
	}

	/**
	 * This method corrects the robot for when the direction is flipped,
	 * so that a strait direction can be achieved when the speed is flipped.
	 * 
	 * @param speed to be used for the amount of power
	 * @param angle to be used for the directional adjustments 
	 */
	public void move(double speed, double angle) {
		if(speed == 0) {stop();return;}
		double correction = angle/180.00;
  	  	debug("speed = " + speed + ", angle = " + angle+ ", correction = "+correction+", isFlipped = "+ isFlipped);
	  	//double[] speeds = interpolator.calculate(speed, correction, isFlipped);
		double[] speeds = new double[2];
		if(angle>=0){
			if(angle>10) angle = 10;
			speeds[1]=speed;
			speeds[0]=speed*(1.0 - angle/10.0);
		}
		else{
			if(angle<-10) angle = -10;
			speeds[1]=speed*(1.0 + angle/10.0);
			speeds[0]=speed;
		}
		robotDrive.tankDrive(speeds[0], speeds[1]);
	}
	
	/**
	 * This method is still in the works.
	 */
	//TODO 
	}

	// boolean isOn = false; // Why is this here? What is this? It doesn't link to anything.;

