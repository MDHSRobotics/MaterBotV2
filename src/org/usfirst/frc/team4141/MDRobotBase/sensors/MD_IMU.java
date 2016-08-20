package org.usfirst.frc.team4141.MDRobotBase.sensors;


import com.analog.adis16448.frc.ADIS16448_IMU;

public class MD_IMU extends ADIS16448_IMU implements Sensor{
	SensorReading[] readings = new SensorReading[24];
	String name;
	public MD_IMU(){
		this(true);
	}
	public MD_IMU(boolean observe){
		super();
		this.observe = observe;
		int i=0;
		readings[i++]=new AnalogSensorReading("AccelX", getAccelX());
		readings[i++]=new AnalogSensorReading("AccelY", getAccelY());
		readings[i++]=new AnalogSensorReading("AccelZ", getAccelZ());
		readings[i++]=new AnalogSensorReading("Angle", getAngle());
		readings[i++]=new AnalogSensorReading("AngleX", getAngleX());
		readings[i++]=new AnalogSensorReading("AngleY", getAngleY());
		readings[i++]=new AnalogSensorReading("AngleZ", getAngleZ());
		readings[i++]=new AnalogSensorReading("MagX", getMagX());
		readings[i++]=new AnalogSensorReading("MagY", getMagY());
		readings[i++]=new AnalogSensorReading("MagZ", getMagZ());
		readings[i++]=new AnalogSensorReading("Pitch", getPitch());
		readings[i++]=new AnalogSensorReading("Yaw", getYaw());
		readings[i++]=new AnalogSensorReading("Roll", getRoll());
		readings[i++]=new AnalogSensorReading("QuaternionW", getQuaternionW());
		readings[i++]=new AnalogSensorReading("QuaternionX", getQuaternionX());
		readings[i++]=new AnalogSensorReading("QuaternionY", getQuaternionY());
		readings[i++]=new AnalogSensorReading("QuaternionZ", getQuaternionZ());
		readings[i++]=new AnalogSensorReading("Rate", getRate());
		readings[i++]=new AnalogSensorReading("RateX", getRateX());
		readings[i++]=new AnalogSensorReading("RateY", getRateY());
		readings[i++]=new AnalogSensorReading("RateZ", getRateZ());
		readings[i++]=new AnalogSensorReading("Temperature", getTemperature());
		readings[i++]=new AnalogSensorReading("BarometricPressure", getBarometricPressure());
		readings[i++]=new AnalogSensorReading("LastSampleTime", getLastSampleTime());
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
	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}
}
