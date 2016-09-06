package org.usfirst.frc.team4141.MDRobotBase;

import java.util.Hashtable;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team4141.MDRobotBase.Logger.Level;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigPreferenceManager;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotNotification;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;
import org.usfirst.frc.team4141.robot.OI;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public abstract class MDRobotBase extends IterativeRobot{

	private OI oi;
	private Logger logger;
	private Hashtable<String,MDSubsystem> subsystems;
	private Hashtable<String,MDCommand> commandChooser;

	private String name;
	
	private Hashtable<String,SensorReading> sensorReadingsDictionary;
	private Hashtable<String,Sensor> sensorsDictionary;

	//function prototype that implementing robot must implement
    protected abstract void configureRobot();
    
    //Constructors
	public MDRobotBase() {
		this(null);
	}
	public MDRobotBase(String name) {
		this.name = name;
		sensorReadingsDictionary = new Hashtable<String,SensorReading>();
		sensorsDictionary = new Hashtable<String,Sensor>();
	}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}

	
	//register subsystems
    public void add(MDSubsystem subsystem){
    	System.out.println("adding subsystem "+subsystem.getName());
    	this.subsystems.put(subsystem.getName(),subsystem);
    }
	public Hashtable<String, MDSubsystem> getSubsystems() {
		return subsystems;
	}
	
	//register sensors

    public Hashtable<String, SensorReading> getSensorReadingsDictionary() {
		return sensorReadingsDictionary;
	}	
    public Hashtable<String, Sensor> getSensorsDictionary() {
		return sensorsDictionary;
	}	
    
    public Hashtable<String, MDCommand> getCommandChooser() {
		return commandChooser;
	}
	
	
	//Log helper methods
	public void log(String logOrigin, String message) {
		logger.log(logOrigin, message);		
	}
	public void log(Level level, String logOrigin, String message) {
		logger.log(level,logOrigin, message);		
	}

//	public Logger getLogger() {
//		return logger;
//	}

    protected Command autonomousCommand=null;
	
    // Operator Interface
    public OI getOi() {
		return oi;
	}

    
	//Robot Licycle Methods.  These are called by the FRC system
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	
    	this.logger=new Logger(this);
    	this.subsystems=new Hashtable<String,MDSubsystem>();
    	this.commandChooser=new Hashtable<String,MDCommand>();
    	oi = new OI(this);
//    	ConfigPreferenceManager.clearPreferences();
    	configureRobot();  
    	oi.configureOI();
    	System.out.println("RobotInit completed");
    	
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
	public void disabledInit(){
    	System.out.println("disabledInit()");
    }
    
    /**
     * This function is called periodically while the robot is in disabled state
     */
    @Override
	public void disabledPeriodic() {

		Scheduler.getInstance().run();
	}    
	

    /**
     * This function is called once right before the robot goes into autonomous mode.
     */    
    @Override
	public void autonomousInit() {
    	if (autonomousCommand != null) autonomousCommand.start();
    	else{
    		System.out.println("autonomousCommand is unexpectedly null");
    	}
    }

    /**
     * This function is called periodically during autonomous mode
     */
    @Override
   	public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }  

    
    /**
     * This function is called once right before the robot goes into teleop mode.
     */    
    @Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }   

    /**
     * This function is called once right before the robot goes into test mode.
     */  	@Override
	public void testInit() {
	}
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
        LiveWindow.run();
    }

	public void add(SensorReading reading) {
		if(sensorReadingsDictionary!=null){
			sensorReadingsDictionary.put(reading.getName(), reading);
//			System.out.println("registering sensor reading: "+reading.getName());
		}
	}
	
	
	public void add(Sensor sensor) {
		if(sensorsDictionary!=null){
				sensorsDictionary.put(sensor.getName(), sensor);
		}
	}
	public void post(RobotNotification notification){
		if(getSubsystems()!=null && getSubsystems().containsKey("WebSockets")){
			((WebSocketSubsystem)(getSubsystems().get("WebSockets"))).post(notification);
		}
	}
	
	protected void setAutonomousCommand(MDCommand[] commands) {
		for(MDCommand command : commands){
			commandChooser.put(command.getName(), command);
			if(autonomousCommand==null){
				System.out.println("defaulting autoCommand to "+command.getName());
				autonomousCommand = command;
			}
		}
	}

	protected void setAutonomousCommand(MDCommand[] commands, String defaultCommandName) {
		for(MDCommand command : commands){
			commandChooser.put(command.getName(), command);
			if(defaultCommandName!=null && defaultCommandName.equals(command.getName())){
				System.out.println("defaulting autoCommand to "+command.getName());
				autonomousCommand = command;
			}
		}
	}
	
	protected void setAutonomousCommand(MDCommand command) {
		setAutonomousCommand(new MDCommand[]{command});
	}
	
	public void setAutoCommand(String commandName) {
		if(commandChooser.containsKey(commandName)){
			autonomousCommand = commandChooser.get(commandName);
		}
	}
}
