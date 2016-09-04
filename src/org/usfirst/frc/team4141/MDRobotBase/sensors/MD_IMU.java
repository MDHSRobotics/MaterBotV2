package org.usfirst.frc.team4141.MDRobotBase.sensors;


import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

import com.analog.adis16448.frc.ADIS16448_IMU;

public class MD_IMU extends ADIS16448_IMU implements Sensor{
	SensorReading[] readings = new SensorReading[24];
	String name;
	public MD_IMU(MDSubsystem subsystem){
		this(subsystem,true);
	}
	public MD_IMU(MDSubsystem subsystem,boolean observe){
		super();
		this.observe = observe;
		this.subsystem = subsystem;
		int i=0;
		readings[i++]=new AnalogSensorReading(this,"AccelX", getAccelX());
		readings[i++]=new AnalogSensorReading(this,"AccelY", getAccelY());
		readings[i++]=new AnalogSensorReading(this,"AccelZ", getAccelZ());
		readings[i++]=new AnalogSensorReading(this,"Angle", getAngle());
		readings[i++]=new AnalogSensorReading(this,"AngleX", getAngleX());
		readings[i++]=new AnalogSensorReading(this,"AngleY", getAngleY());
		readings[i++]=new AnalogSensorReading(this,"AngleZ", getAngleZ());
		readings[i++]=new AnalogSensorReading(this,"MagX", getMagX());
		readings[i++]=new AnalogSensorReading(this,"MagY", getMagY());
		readings[i++]=new AnalogSensorReading(this,"MagZ", getMagZ());
		readings[i++]=new AnalogSensorReading(this,"Pitch", getPitch());
		readings[i++]=new AnalogSensorReading(this,"Yaw", getYaw());
		readings[i++]=new AnalogSensorReading(this,"Roll", getRoll());
		readings[i++]=new AnalogSensorReading(this,"QuaternionW", getQuaternionW());
		readings[i++]=new AnalogSensorReading(this,"QuaternionX", getQuaternionX());
		readings[i++]=new AnalogSensorReading(this,"QuaternionY", getQuaternionY());
		readings[i++]=new AnalogSensorReading(this,"QuaternionZ", getQuaternionZ());
		readings[i++]=new AnalogSensorReading(this,"Rate", getRate());
		readings[i++]=new AnalogSensorReading(this,"RateX", getRateX());
		readings[i++]=new AnalogSensorReading(this,"RateY", getRateY());
		readings[i++]=new AnalogSensorReading(this,"RateZ", getRateZ());
		readings[i++]=new AnalogSensorReading(this,"Temperature", getTemperature());
		readings[i++]=new AnalogSensorReading(this,"BarometricPressure", getBarometricPressure());
		readings[i++]=new AnalogSensorReading(this,"LastSampleTime", getLastSampleTime());
	}

	@Override
	public void refresh() {
		int i=0;
		((AnalogSensorReading)readings[i++]).setValue(getAccelX());
		((AnalogSensorReading)readings[i++]).setValue(getAccelY());
		((AnalogSensorReading)readings[i++]).setValue(getAccelZ());
		((AnalogSensorReading)readings[i++]).setValue(getAngle());
		((AnalogSensorReading)readings[i++]).setValue(getAngleX());
		((AnalogSensorReading)readings[i++]).setValue(getAngleY());
		((AnalogSensorReading)readings[i++]).setValue(getAngleZ());
		((AnalogSensorReading)readings[i++]).setValue(getMagX());
		((AnalogSensorReading)readings[i++]).setValue(getMagY());
		((AnalogSensorReading)readings[i++]).setValue(getMagZ());
		((AnalogSensorReading)readings[i++]).setValue(getPitch());
		((AnalogSensorReading)readings[i++]).setValue(getYaw());
		((AnalogSensorReading)readings[i++]).setValue(getRoll());
		((AnalogSensorReading)readings[i++]).setValue(getQuaternionW());
		((AnalogSensorReading)readings[i++]).setValue(getQuaternionX());
		((AnalogSensorReading)readings[i++]).setValue(getQuaternionY());
		((AnalogSensorReading)readings[i++]).setValue(getQuaternionZ());
		((AnalogSensorReading)readings[i++]).setValue(getRate());
		((AnalogSensorReading)readings[i++]).setValue(getRateX());
		((AnalogSensorReading)readings[i++]).setValue(getRateY());
		((AnalogSensorReading)readings[i++]).setValue(getRateZ());
		((AnalogSensorReading)readings[i++]).setValue(getTemperature());
		((AnalogSensorReading)readings[i++]).setValue(getBarometricPressure());
		((AnalogSensorReading)readings[i++]).setValue(getLastSampleTime());
	}

	@Override
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public SensorReading[] getReadings() {
		return readings;
	}
	private boolean observe;
	private MDSubsystem subsystem;
	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}
	@Override
	public MDSubsystem getSubsystem() {
		return subsystem;
	}
	@Override
	public Sensor setSubsystem(MDSubsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}
}
