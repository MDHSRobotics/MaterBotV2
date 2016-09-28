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
		readings[i++]=new AnalogSensorReading(this,"AccelX", getAccelX(), true, false);
		readings[i++]=new AnalogSensorReading(this,"AccelY", getAccelY(), true, false);
		readings[i++]=new AnalogSensorReading(this,"AccelZ", getAccelZ(), true, false);
		readings[i++]=new AnalogSensorReading(this,"Angle", getAngle(), false, false);
		readings[i++]=new AnalogSensorReading(this,"AngleX", getAngleX(), true, false);
		readings[i++]=new AnalogSensorReading(this,"AngleY", getAngleY(), true, false);
		readings[i++]=new AnalogSensorReading(this,"AngleZ", getAngleZ());
		readings[i++]=new AnalogSensorReading(this,"MagX", getMagX(), false, false);
		readings[i++]=new AnalogSensorReading(this,"MagY", getMagY(), false, false);
		readings[i++]=new AnalogSensorReading(this,"MagZ", getMagZ(), false, false);
		readings[i++]=new AnalogSensorReading(this,"Pitch", getPitch(), false, false);
		readings[i++]=new AnalogSensorReading(this,"Yaw", getYaw(), false, false);
		readings[i++]=new AnalogSensorReading(this,"Roll", getRoll(), false, false);
		readings[i++]=new AnalogSensorReading(this,"QuaternionW", getQuaternionW(), false, false);
		readings[i++]=new AnalogSensorReading(this,"QuaternionX", getQuaternionX(), false, false);
		readings[i++]=new AnalogSensorReading(this,"QuaternionY", getQuaternionY(), false, false);
		readings[i++]=new AnalogSensorReading(this,"QuaternionZ", getQuaternionZ(), false, false);
		readings[i++]=new AnalogSensorReading(this,"Rate", getRate(), false, false);
		readings[i++]=new AnalogSensorReading(this,"RateX", getRateX(), false, false);
		readings[i++]=new AnalogSensorReading(this,"RateY", getRateY(), false, false);
		readings[i++]=new AnalogSensorReading(this,"RateZ", getRateZ(), false, false);
		readings[i++]=new AnalogSensorReading(this,"Temperature", getTemperature(), false, false);
		readings[i++]=new AnalogSensorReading(this,"BarometricPressure", getBarometricPressure(), false, false);
		readings[i++]=new AnalogSensorReading(this,"LastSampleTime", getLastSampleTime(), false, false);
	}
	public MD_IMU() {
		this(null);
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
