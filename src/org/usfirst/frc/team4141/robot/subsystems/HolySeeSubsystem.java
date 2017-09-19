package org.usfirst.frc.team4141.robot.subsystems;

import java.util.Date;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.notifications.SwitchChannelNotification;
import org.usfirst.frc.team4141.MDRobotBase.sensors.TegraConnectionSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.VisionConnectedSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.ConsoleConnectionSensor;

/**
 * HolySeeSubsystem is a subsystem class based off the MDSubsystem.
 * This subsystem is used to help the robot decipher a image for reflective
 * light by using a on-board camera. This information could be later used to
 * help the driver aim better, and even let the robot aim and take a shot by
 * itself.
 */
public class HolySeeSubsystem extends MDSubsystem{

	private VisionConnectedSensor visionConnected;
	private ConsoleConnectionSensor consoleConnection;
	private TegraConnectionSensor tegraConnection;
	
	/**
	 * The constructor is used to hold the parameters robot and name.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @param name the default name used after the string in the constructor
	 */
	public HolySeeSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
	}
	
	public MDSubsystem configure(){
		super.configure();

		if(getSensors()==null || !getSensors().containsKey("visionConnected"))
			throw new IllegalArgumentException("Invalid HolySeeSubsystem configuration, missing VisionConnectedSensor.");
		visionConnected = (VisionConnectedSensor)(getSensors().get("visionConnected"));
		if(getSensors()==null || !getSensors().containsKey("Steam Target Acquired"))
			throw new IllegalArgumentException("Invalid HolySeeSubsystem configuration, missing SteamTargetSensor.");
			throw new IllegalArgumentException("Invalid HolySeeSubsystem configuration, missing ConsoleConnectionSensor.");
//		consoleConnection = (ConsoleConnectionSensor)(getSensors().get("console"));
//		if(getSensors()==null || !getSensors().containsKey("tegra"))
//			throw new IllegalArgumentException("Invalid HolySeeSubsystem configuration, missing TegraConnectionSensor.");
//		tegraConnection = (TegraConnectionSensor)(getSensors().get("tegra"));
//		return this;
	}
	
	// ------------------------------------------------ //
	
	/**
	 * This method holds the default command used in a subsystem.
	 * Which is not being used in this subsystem. 
	 */
	@Override
	protected void initDefaultCommand() {
	}

	@Override
	protected void setUp() {
	}

	@Override
	public void settingChangeListener(ConfigSetting setting) {
	}

	public void switchChannel() {
		debug("switching channel\n");
		getRobot().post(new SwitchChannelNotification(getRobot()));
	}
	
	// ------------------------------------------------ //

	public boolean getVisionConnected(){
		return visionConnected.get();
	}
	
	public String getConsoleAddress(){
		return consoleConnection.get();
	}	
	
	public String getTegraAddress(){
		return tegraConnection.get();
	}

	// ------------------------------------------------ //
	
	private long start;
	
	public void setVisionConnected(boolean connected){
		visionConnected.set(connected);
		if (connected) {
			start = (new Date()).getTime();
		} else {
			debug("connection closed after " + ((new Date()).getTime()-start));
		}
	}

	public void setConsoleAddress(String consoleAddress){
		consoleConnection.set(consoleAddress);
	}
	
	public void setTegraAddress(String tegraAddress){
		tegraConnection.set(tegraAddress);
	}
}
