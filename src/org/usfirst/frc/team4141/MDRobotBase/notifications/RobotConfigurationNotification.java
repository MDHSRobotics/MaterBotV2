package org.usfirst.frc.team4141.MDRobotBase.notifications;

import java.util.Hashtable;

import org.usfirst.frc.team4141.MDRobotBase.MDConsoleButton;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SolenoidBase;

public class RobotConfigurationNotification extends RobotNotification {
	
	
	private MDRobotBase robot;
	public RobotConfigurationNotification(MDRobotBase robot) {
		this(robot, false, false, true, true);
	}
	public RobotConfigurationNotification(MDRobotBase robot,boolean showMDConsole) {
		this(robot, false, showMDConsole,true, true);
	}
	public RobotConfigurationNotification(MDRobotBase robot,boolean showMDConsole, boolean record) {
		this(robot, false, showMDConsole,true, record);
	}
	public RobotConfigurationNotification(MDRobotBase robot, boolean showJavaConsole, boolean showMDConsole, boolean broadcast, boolean record) {
		super("RobotConfigurationNotification", showJavaConsole, showMDConsole, broadcast, record);
		this.robot = robot;
	}

	
	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		Object[] subsystemNames = robot.getSubsystems().keySet().toArray();
		
		if(subsystemNames.length>0){
			sb.append(", \"subsystems\":{");
			boolean firstWritten = false;
			for(int k=0;k<subsystemNames.length;k++){
				String subsystemName = (String)subsystemNames[k];
				MDSubsystem subsystem = robot.getSubsystems().get(subsystemName);
				if(firstWritten) sb.append(", ");
				else firstWritten = true;
				Hashtable<String, ConfigSetting> settings = subsystem.getConfigSettings();
				Hashtable<String, PWM> motors = subsystem.getMotors();
				Hashtable<String, SolenoidBase> solenoids = subsystem.getSolenoids();
				Hashtable<String, Sensor> sensors = subsystem.getSensors();
					sb.append("\"");
					sb.append(subsystemName);
					sb.append("\":");
					sb.append("{\"subsystem\":\"");
					sb.append(subsystemName);
					sb.append("\"");
					if(subsystem.isCore()){
						sb.append(", \"isCore\":true");
					}
					else{
						sb.append(", \"isCore\":false");
					}
					if(settings!=null && settings.size()>0){
						sb.append(", \"settings\":[");
						appendSettings(settings);
						sb.append("]");
					}
					if(sensors!=null && sensors.size()>0){
						sb.append(", \"sensors\":{");
						appendSensors(sensors);
						sb.append("}");
					}
					if(motors!=null && motors.size()>0){
						sb.append(", \"motors\":[");
						appendMotors(motors);
						sb.append("]");
					}
					if(solenoids!=null && solenoids.size()>0){
						sb.append(", \"solenoids\":[");
						appendSolenoids(solenoids);
						sb.append("]");
					}
					sb.append("}");
			}

			sb.append("}");
		}
		if(robot.getOi().getConsole()!=null){
			System.out.println("MDConsoleOI to configure");

			sb.append(", \"consoleOI\":{");
			sb.append("\"rumbles\":{");
			sb.append("\"left\":0,");
			sb.append("\"right\":0");
			sb.append("},");
			sb.append("\"buttons\":[");
			boolean first = true;
			for(Integer buttonIndex : robot.getOi().getConsole().getButtons().keySet()){
				System.out.println("buttonIndex: "+buttonIndex);
				if(first){first = false;}
				else{
					sb.append(", ");
				}
				append(robot.getOi().getConsole().getButtons().get(buttonIndex));
			}
			sb.append("]");
			sb.append("}");
		}		
	}
	
	private void append(MDConsoleButton mdConsoleButton) {
		sb.append("{\"name\":\"");
		sb.append(mdConsoleButton.getName());
		sb.append("\", \"index\":");
		sb.append(mdConsoleButton.getButtonNumber());
		if(mdConsoleButton.getCommand()!=null){
			sb.append(", \"command\":\"");
			sb.append(mdConsoleButton.getCommand().getName());
			sb.append("\"");
		}
		sb.append("}");
	}
	
	private void appendSolenoids(Hashtable<String, SolenoidBase> solenoids) {
		boolean first = true;
		for(String solenoidName : solenoids.keySet()){
			if(first) first = false;
			else sb.append(',');
			append(solenoidName,solenoids.get(solenoidName));
		}
	}
	
	private void append(String solenoidName, SolenoidBase solenoidBase) {
		sb.append("{\"name\":\"");
		sb.append(solenoidName);
		sb.append("\"");	
		if(solenoidBase instanceof Solenoid){
			Solenoid solenoid = (Solenoid) solenoidBase;
			sb.append(", \"type\":\"single\"");
			sb.append(", \"value\":");
			sb.append(solenoid.get());
		}
		else if(solenoidBase instanceof DoubleSolenoid){
			DoubleSolenoid solenoid = (DoubleSolenoid) solenoidBase;
			sb.append(", \"type\":\"double\"");
			sb.append(", \"value\":");
			sb.append(solenoid.get());
		}
		sb.append("}");
	}
	
	private void appendSensors(Hashtable<String, Sensor> sensors) {
		boolean first = true;
		for(String sensorName : sensors.keySet()){
			if(first) first = false;
			else sb.append(", ");
			sb.append("\"");
			sb.append(sensorName);
			sb.append("\":");
			append(sensors.get(sensorName));
		}
	}
	
	private void append(Sensor sensor) {
		sb.append("{\"name\":\"");
		sb.append(sensor.getName());
		sb.append("\"");
		if(sensor.getReadings()!=null && sensor.getReadings().length>0){
			sb.append(", \"readings\":{");
			boolean first = true;
			for(SensorReading reading : sensor.getReadings()){
				if(first) first = false;
				else{ sb.append(", ");}
				sb.append("\"");
				sb.append(reading.getName());
				sb.append("\":");
				sb.append(reading.toJSON());
			}
			sb.append("}");
		}
		sb.append("}");
	}

	private void appendMotors(Hashtable<String, PWM> motors) {
		boolean first = true;
		for(String motorName : motors.keySet()){
			if(first) first = false;
			else sb.append(',');
			append(motorName,motors.get(motorName));
		}
	}
	
	private void append(String motorName,PWM motor) {
		sb.append("{\"name\":\"");
		sb.append(motorName);
		sb.append("\", \"channel\":\"");
		sb.append(motor.getChannel());
		sb.append("\", \"isServo\":");
		if(motor instanceof Servo){
			sb.append(true);
		}
		else{
			sb.append(false);
		}
		sb.append(", \"position\":");
		sb.append(motor.getPosition());
		sb.append(", \"speed\":");
		sb.append(motor.getSpeed());
		sb.append(", \"class\":\"");
		sb.append(motor.getClass().getName());
		sb.append("\"");
		sb.append("}");
	}
	
	private void appendSettings(Hashtable<String, ConfigSetting> settings){
		boolean first = true;
		for(String settingName : settings.keySet()){
			if(first) first = false;
			else sb.append(',');
			sb.append(settings.get(settingName).toJSON());
		}
	}

}
