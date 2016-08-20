package org.usfirst.frc.team4141.MDRobotBase;

import java.util.Hashtable;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.eclipse.jetty.websocket.api.Session;
import org.usfirst.frc.team4141.MDRobotBase.Logger.Level;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManager;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManagerCallBack;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Notification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.GameClockNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotConfigurationNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotStateNotification;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;
import org.usfirst.frc.team4141.robot.OI;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public abstract class MDRobotBase extends IterativeRobot implements EventManagerCallBack{
	public enum RobotState{
		RobotInit,
		DisabledInit,
		DisabledPeriodic,
		AutonomousInit,
		AutonomousPeriodic,
		TeleopInit,
		TeleopPeriodic,
		TestInit,
		TestPeriodic
	}
	private EventManager eventManager;
	private OI oi;
	private Logger logger;
	private Hashtable<String,MDSubsystem> subsystems;
	private Hashtable<String,MDCommand> commands;

	private String name;
	
	private Hashtable<String,SensorReading> sensorReadingsDictionary;
	private Hashtable<String,Sensor> sensorsDictionary;

	//function prototype that implementing robot must implement
    protected abstract void configureRobot();
    
    //Constructors
	public MDRobotBase(String name) {
		this.name = name;
    	this.eventManager = new EventManager(this);
		sensorReadingsDictionary = new Hashtable<String,SensorReading>();
	}

	public String getName(){return name;}
	//methods to configure WebSockets
	public void enableWebSockets(){
		if(this.eventManager!=null) eventManager.setEnableWebSockets(true);
	}
	public void disableWebSockets(){
		if(this.eventManager!=null) eventManager.setEnableWebSockets(false);
	}
	
	//register subsystems
    public void add(MDSubsystem subsystem){
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
    
	//register commands
    public void add(MDCommand command){
    	this.commands.put(command.getName(),command);
    }
    public Hashtable<String, MDCommand> getCommands() {
		return commands;
	}
	
    //
    //EventManager helper methods
	public void post(Notification notification){
		eventManager.post(notification);
	}
	
	
//	@Override
//	public void setEventManager(EventManager eventManager) {
//		this.eventManager = eventManager;
//	}
//	
//	@Override
//	public EventManager getEventManager() {
//		return eventManager;
//	}
	
	
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

    //Variables used in subsampling telemetry
    private int i=0;
    private int rate = 5;  // e.g. 5 means 5 times per second
    private int factor = 50 / rate;    // assume sample 50x / secs 

    
	//Robot Licycle Methods.  These are called by the FRC system
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	
    	this.logger=new Logger(this);
    	this.subsystems=new Hashtable<String,MDSubsystem>();
    	this.commands=new Hashtable<String,MDCommand>();
    	oi = new OI(this);
    	configureRobot();
    	try {
    		eventManager.start();
		} catch (Exception e) {
			System.err.println("Unable to start eventManager due to error: "+e.getMessage());
			e.printStackTrace(System.err);
		}
    	periodicFirst = false;
    	post(new RobotStateNotification(RobotState.RobotInit,false));
    }
    
	boolean periodicFirst = false;   
	
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
	public void disabledInit(){
    	post(new RobotStateNotification(RobotState.DisabledInit,false));
		periodicFirst = true;
    }
    /**
     * This function is called periodically while the robot is in disabled state
     */    
    @Override
	public void disabledPeriodic() {
		if(periodicFirst){
	    	post(new RobotStateNotification(RobotState.DisabledPeriodic,false));
	    	periodicFirst = false;
		}
		//TODO refactor the telemetry sampling approach
		if(i==0){ //subsamples the readings 
//			RobotMap.IMU.read(sensors);
//			RobotMap.builtInAccelerometer.read(sensors);
			post(new GameClockNotification(false));
//			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

		}
		i++;
		i = i%factor;
		Scheduler.getInstance().run();
	}    
	

    /**
     * This function is called once right before the robot goes into autonomous mode.
     */    
    @Override
	public void autonomousInit() {
		periodicFirst = true;
    	post(new RobotStateNotification(RobotState.AutonomousInit,false));
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous mode
     */
    @Override
   	public void autonomousPeriodic() {
   		if(periodicFirst){
   	    	post(new RobotStateNotification(RobotState.AutonomousPeriodic,false));
   	    	periodicFirst = false;
   		}
   		if(i==0){ //subsamples the readings 
//   			RobotMap.IMU.read(sensors);
//   			RobotMap.builtInAccelerometer.read(sensors);
   			post(new GameClockNotification(false));
//   			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

   		}
   		i++;
   		i = i%factor;
//   		double now = Timer.getFPGATimestamp();
//   		System.out.println("delta="+(now - priorAutoPeriod));
//   		priorAutoPeriod = now;
           Scheduler.getInstance().run();
       }  

    
    /**
     * This function is called once right before the robot goes into teleop mode.
     */    
    @Override
	public void teleopInit() {
    	post(new RobotStateNotification(RobotState.TeleopInit,false));
		periodicFirst = true;
    	
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
		if(periodicFirst){
	    	post(new RobotStateNotification(RobotState.TeleopPeriodic,false));
	    	periodicFirst = false;
		}
		if(i==0){ //subsamples the readings 
//			RobotMap.IMU.read(sensors);
//			RobotMap.builtInAccelerometer.read(sensors);			
			post(new GameClockNotification(false));
//			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));
		}
		i++;
		i = i%factor;
        Scheduler.getInstance().run();
    }   



    
    /**
     * This function is called once right before the robot goes into test mode.
     */  	@Override
	public void testInit() {
    	post(new RobotStateNotification(RobotState.TestInit,false));
		periodicFirst = true;
	}
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
		if(periodicFirst){
	    	post(new RobotStateNotification(RobotState.TestPeriodic,false));
	    	periodicFirst = false;
		}
		if(i==0){ //subsamples the readings 
//			RobotMap.IMU.read(sensors);
//			RobotMap.builtInAccelerometer.read(sensors);
//			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

		}
		i++;
		i = i%factor;		
        LiveWindow.run();
    }
 
	
	//WebSocket handlers
	@Override
	public void onConnect(Session session) {
		System.out.println("connected!");
		//TODO refactor configuration communications to console
		post(new RobotConfigurationNotification(this,true));
	}
	@Override
	public void onClose(Session session, int closeCode, String closeReason) {
		System.out.printf("disconnected code[%d] reason:%s",closeCode,closeReason);
	}
	@Override
	public void onError(Session session, Throwable err) {
		System.err.println("error: "+err.getMessage());
	}
	@Override
	public void onBinary(Session session, byte[] bytes, int arg2, int arg3) {
		throw new NotImplementedException("Event manager does not yet support binary messages");
	}

	@Override
	public void onText(Session session, String event) {
		//TODO Refactor UI sending to robot
		//this should be built into the base
		//initiate a command on demand
		//set a configuration setting
		//Code to process message
		System.out.printf("message received from UI\n\t%s\n",event);
		
//		boolean isCommand = false;
//		boolean isSystem = false;
//		
//		Map map= decode(event);
//		if(map==null) return;
//
//		String eventType=null;
//		
//		
//		String key = "eventType";
//		if(map.containsKey(key)){		
//			eventType = (String) map.get(key);
//			
//		}
//		if(eventType!=null){
//			if(eventType.equals("configItemNotification")){
//				String commandName=null;
//				String systemName=null;
//				String itemName=null;
//				String value=null;
//				key = "name";
//				if(map.containsKey(key)){
//					itemName = (String) map.get(key);
//					
//				}
//				key = "systemName";
//				if(map.containsKey(key)){
//					systemName=(String) map.get(key);
//					
//					isSystem = true;
//				}
//				key = "commandName";
//				if(map.containsKey(key)){
//					commandName=(String) map.get(key);
//					
//					isCommand = true;
//				}
//				key = "value";
//				if(map.containsKey(key)){
//					value = (String) map.get(key);
//					
//				}
//				if(itemName!=null && value!=null){
//					ConfigItem item = null;
//					if(isCommand && commandName!=null){
//						item = ConfigManager.getItem(getCommands().get(commandName), itemName);
//					}
//					if(isSystem && systemName!=null){
//						item = ConfigManager.getItem(getSubsystems().get(systemName), itemName);
//					} 
//					if(item!=null){
//						switch(item.getType()){
//						case doubleNumber:
//							try{
//								Double d = Double.valueOf(value);
//								if(d!=null){
//									item.setValue(d);
//								}
//							}
//							catch(NumberFormatException nfe){							
//							}						
//							break;
//						case integer:
//							try{
//								Integer i = Integer.valueOf(value);
//								if(i!=null){
//									item.setValue(i);
//								}
//							}
//							catch(NumberFormatException nfe){							
//							}	
//							break;
//						case string:
//							item.setValue(value);
//							break;
//						}
//					}
//				}				
//			}
//		}

	}

	public void add(SensorReading reading) {
		if(sensorReadingsDictionary!=null){
			sensorReadingsDictionary.put(reading.getName(), reading);
		}
	}
	
	
	public void add(Sensor sensor) {
		if(sensorsDictionary!=null){
				sensorsDictionary.put(sensor.getName(), sensor);
		}
	}

}
