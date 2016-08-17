
package org.usfirst.frc.team4141.robot;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import team4141.eventmanager.EventManager;
import team4141.eventmanager.EventManagerCallBack;
import team4141.eventmanager.Notification;

import org.eclipse.jetty.websocket.api.Session;
import org.usfirst.frc.team4141.robot.Logger.Level;
import org.usfirst.frc.team4141.robot.commands.MDCommand;
import org.usfirst.frc.team4141.robot.notifications.GameClockNotification;
import org.usfirst.frc.team4141.robot.notifications.RobotConfigurationNotification;
import org.usfirst.frc.team4141.robot.notifications.RobotStateNotification;
import org.usfirst.frc.team4141.robot.notifications.RobotStateNotification.RobotState;
import org.usfirst.frc.team4141.robot.notifications.TelemetryNotification;
import org.usfirst.frc.team4141.robot.subsystems.MDSubsystem;

import com.google.gson.Gson;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public abstract class MDRobotBase extends IterativeRobot implements EventManagerCallBack{

	private static final String dirPath = "/media/sda1";
	private static final String extension = ".dat";
	private static File logFile;

	private static final Object LOCK_1 = new Object() {};
	private static boolean recordingEnabled = false;
	
	public static void log(Notification notification) {
		if(recordingEnabled){
		    synchronized(LOCK_1) {
		    	if(logFile == null){
		    		File dir = new File(dirPath);
		    		if(!dir.exists() || !dir.isDirectory()){
		    			System.err.println("Unable to open location "+dirPath);
		    			recordingEnabled = false;
		    		}
		    		else{
		    			logFile = new File(dir,new Date().getTime()+extension);
		    			try {
							FileWriter fw = new FileWriter(logFile);
							fw.write("");
							fw.flush();
							fw.close();
						} catch (IOException e) {
							System.err.println("unable to write to file "+ logFile.getAbsolutePath());
//							e.printStackTrace(System.err);
						}
		    		}
		    	}
		    	try {
		    		if(logFile!=null && logFile.toPath()!=null && notification!=null && notification.toJSON()!=null){
						Files.write(logFile.toPath(), (notification.toJSON()+"\n").getBytes(), StandardOpenOption.APPEND);
		    		}
				} catch (IOException e) {
					System.err.println("unable to write to file "+ logFile.getAbsolutePath());
//					e.printStackTrace(System.err);
				}
		    }
	    }
	}
	
	public void post(Notification notification){
		eventManager.post(notification);
		log(notification);
	}
	
	private EventManager eventManager;
	public MDRobotBase(boolean enableLiveWindow, boolean enableSmartDashboard) {
		this.enableLiveWindow = enableLiveWindow;
		this.enableSmartDashboard = enableSmartDashboard;
		this.eventManager = new EventManager(this);
    	try {
    		eventManager.start();
		} catch (Exception e) {
			System.err.println("Unable to start eventManager due to error: "+e.getMessage());
			e.printStackTrace(System.err);
		}
    	gson = new Gson(); 
	}

	

	private OI oi;
	private Logger logger;
	private boolean enableLiveWindow = true;
	public boolean isLiveWindowEnabled(){
		return enableLiveWindow;
	}
	public void setEnableLiveWindow(boolean enableLiveWindow){
		this.enableLiveWindow = enableLiveWindow;
	}
	private boolean enableSmartDashboard = false;
	public boolean isSmartDashboardEnabled(){
		return enableSmartDashboard;
	}
	public void setSmartDashboardEnabled(boolean enableSmartDashboard){
		this.enableSmartDashboard = enableSmartDashboard;
	}
	public Logger getLogger() {
		return logger;
	}

	private Hashtable<String,MDSubsystem> subsystems;
	public Hashtable<String, MDSubsystem> getSubsystems() {
		return subsystems;
	}
	private Hashtable<String,Telemetry> sensors;
	public Hashtable<String, Telemetry> getSensors() {
		return sensors;
	}
//	public void observe(Telemetry sensor){
//		sensors.put(sensor.getName(), sensor);
//	}

	public Hashtable<String, Command> getCommands() {
		return commands;
	}

	private Hashtable<String,Command> commands;
    protected Command autonomousCommand=null;
	

    public OI getOi() {
		return oi;
	}
    
    public void add(String name,MDSubsystem subsystem){
    	subsystem.setName(name);
    	this.subsystems.put(name,subsystem);
    }
    public void add(String name, Command command){
    	if(command instanceof MDCommand) ((MDCommand)command).setName(name);
    	this.commands.put(name,command);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	
    	this.logger=new Logger(this);
    	System.out.println("robotInit enableLiveWindow = "+enableLiveWindow);
    	RobotMap.init(enableLiveWindow);
    	this.subsystems=new Hashtable<String,MDSubsystem>();
    	this.commands=new Hashtable<String,Command>();
    	this.sensors = new Hashtable<String, Telemetry>();
    	oi = new OI(this);
    	post(new RobotStateNotification(RobotState.RobotInit,false));
		
        mdRobotInit();
        
    }
	
	protected abstract void mdRobotInit();

	public void log(String logOrigin, String message) {
		logger.log(logOrigin, message);
		
	}
	public void log(Level level, String logOrigin, String message) {
		logger.log(level,logOrigin, message);
		
	}
	boolean periodicFirst = false;
	@Override
	public void disabledPeriodic() {
		if(periodicFirst){
	    	post(new RobotStateNotification(RobotState.DisabledPeriodic,false));
	    	periodicFirst = false;
		}
		if(i==0){ //subsamples the readings 
			RobotMap.IMU.read(sensors);
			RobotMap.builtInAccelerometer.read(sensors);
			post(new GameClockNotification(false));
			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

		}
		i++;
		i = i%factor;
		Scheduler.getInstance().run();
	}

    @Override
	public void autonomousInit() {
		periodicFirst = true;
    	post(new RobotStateNotification(RobotState.AutonomousInit,false));
        if (autonomousCommand != null) autonomousCommand.start();
        priorAutoPeriod = Timer.getFPGATimestamp();
    }

    /**
     * This function is called periodically during autonomous
     */
    double priorAutoPeriod = 0;
    @Override
	public void autonomousPeriodic() {
		if(periodicFirst){
	    	post(new RobotStateNotification(RobotState.AutonomousPeriodic,false));
	    	periodicFirst = false;
		}
		if(i==0){ //subsamples the readings 
			RobotMap.IMU.read(sensors);
			RobotMap.builtInAccelerometer.read(sensors);
			post(new GameClockNotification(false));
			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

		}
		i++;
		i = i%factor;
//		double now = Timer.getFPGATimestamp();
//		System.out.println("delta="+(now - priorAutoPeriod));
//		priorAutoPeriod = now;
        Scheduler.getInstance().run();
    }

    @Override
	public void teleopInit() {
    	post(new RobotStateNotification(RobotState.TeleopInit,false));
		periodicFirst = true;
    	
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        priorTeleopPeriod = Timer.getFPGATimestamp();
    }

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
     * This function is called periodically during operator control
     */
    double priorTeleopPeriod = 0;
    private int i=0;
    private int rate = 5;  // e.g. 5 means 5 times per second
    private int factor = 50 / rate;    // assume sample 50x / secs 
    @Override
	public void teleopPeriodic() {
		if(periodicFirst){
	    	post(new RobotStateNotification(RobotState.TeleopPeriodic,false));
	    	periodicFirst = false;
		}
		if(i==0){ //subsamples the readings 
			RobotMap.IMU.read(sensors);
			RobotMap.builtInAccelerometer.read(sensors);
			
			post(new GameClockNotification(false));
			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

		}
		i++;
		i = i%factor;
//		double now = Timer.getFPGATimestamp();
//		System.out.println("delta="+(now - priorTeleopPeriod));
//		priorTeleopPeriod = now;
        Scheduler.getInstance().run();
    }
    
	@Override
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
			RobotMap.IMU.read(sensors);
			RobotMap.builtInAccelerometer.read(sensors);
			post(new TelemetryNotification(sensors.values().toArray(new Telemetry[sensors.size()]),false));

		}
		i++;
		i = i%factor;
		
        LiveWindow.run();
    }
	
	@Override
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	@Override
	public EventManager getEventManager() {
		return eventManager;
	}
	@Override
	public void onConnect(Session session) {
		System.out.println("connected!");
		if(ConfigManager.hasItems()){
			post(new RobotConfigurationNotification(this,true));
		}
	}
	private Gson gson;
	public Map  decode(String json){
		return (Map) gson.fromJson(json, Object.class);
	}

	public  ComputedAnalog getVariable(String elementName) {
		if(!getSensors().containsKey(elementName))
		{
			getSensors().put(elementName, new ComputedAnalog(elementName));
		}
		return (ComputedAnalog) getSensors().get(elementName);
	}
	public ComputedAnalog getVariable(DistanceComputedAnalog distanceComputedAnalog) {
		String elementName = distanceComputedAnalog.getName();
		if(!getSensors().containsKey(elementName))
		{
			getSensors().put(elementName, distanceComputedAnalog);
		}
		return (ComputedAnalog) getSensors().get(elementName);
	}
}
