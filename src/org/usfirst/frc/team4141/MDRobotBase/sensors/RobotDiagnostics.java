package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.hal.HALUtil;


//TODO  Revisit what is captured in heartbeat and robot configuration
public class RobotDiagnostics implements Sensor {

	private MDRobotBase robot;
	private String name;
	SensorReading[] readings = new SensorReading[getReadingsCount()];

	public RobotDiagnostics(MDSubsystem subsystem){
		this(subsystem,true);
	}
	public RobotDiagnostics(MDSubsystem subsystem,boolean observe){
		this(subsystem,null,null,observe);
	}
	
	public void setRobot(MDRobotBase robot){
		this.robot = robot;
		this.name = robot.getName();
	}
	
	public void setRobot(MDRobotBase robot, String name){
		this.robot = robot;
		this.name = name;
	}
	
	private int getReadingsCount() {
		//from ControllerPower  (5)
		//from DriverStation (5)
		//from HALUtil (3)
		//from RobotState (1)
		//from Timer class (1)
		//from PowerDistributionPanel ( 11 + kPDPModules*(kPDPChannels+5) )   //skipping these for now
//		return 5+5+3+1+2+11+(PowerDistributionPanel.kPDPModules*(PowerDistributionPanel.kPDPChannels+5));
		return 5+5+3+1+1;
	}
	RobotDiagnostics(MDSubsystem subsystem,MDRobotBase robot, String name,boolean observe){
		this.robot = robot;
		this.name = name;
		this.observe = observe;
		int i=0;
		
		//from ControllerPower  (5)
//		readings[i++]=new AnalogSensorReading(this,"ControllerPower.Current3V3", ControllerPower.getCurrent3V3(),false,false);
//		readings[i++]=new AnalogSensorReading(this,"ControllerPower.Current5V", ControllerPower.getCurrent5V(),false,false);
//		readings[i++]=new AnalogSensorReading(this,"ControllerPower.Current6V", ControllerPower.getCurrent6V(),false,false);
		readings[i++]=new AnalogSensorReading(this,"ControllerPower.InputCurrent", ControllerPower.getInputCurrent());
		readings[i++]=new AnalogSensorReading(this,"ControllerPower.InputVoltage", ControllerPower.getInputVoltage());
//		readings[i++]=new AnalogSensorReading(this,"ControllerPower.Voltage3V3", ControllerPower.getVoltage3V3(),false,false);
//		readings[i++]=new AnalogSensorReading(this,"ControllerPower.Voltage5V", ControllerPower.getVoltage5V(),false,false);
//		readings[i++]=new AnalogSensorReading(this,"ControllerPower.Voltage6V", ControllerPower.getVoltage6V(),false,false);
		readings[i++]=new AnalogSensorReading(this,"ControllerPower.FaultCount3V3", ControllerPower.getFaultCount3V3());
		readings[i++]=new AnalogSensorReading(this,"ControllerPower.FaultCount5V", ControllerPower.getFaultCount5V());
		readings[i++]=new AnalogSensorReading(this,"ControllerPower.FaultCount6V", ControllerPower.getFaultCount6V());
//		readings[i++]=new DigitalSensorReading(this,"ControllerPower.Enabled3V3", ControllerPower.getEnabled3V3(),false,false);
//		readings[i++]=new DigitalSensorReading(this,"ControllerPower.Enabled5V", ControllerPower.getEnabled5V(),false,false);
//		readings[i++]=new DigitalSensorReading(this,"ControllerPower.Enabled6V", ControllerPower.getEnabled6V(),false,false);
		//from DriverStation (5)
		readings[i++]=new AnalogSensorReading(this,"DriverStation.Location", DriverStation.getInstance().getLocation(),false,false);
		readings[i++]=new AnalogSensorReading(this,"DriverStation.Alliance", DriverStation.getInstance().getAlliance().ordinal(),false,false);
		readings[i++]=new DigitalSensorReading(this,"DriverStation.isBrownedOut", DriverStation.getInstance().isBrownedOut());
		readings[i++]=new DigitalSensorReading(this,"DriverStation.isDSAttached", DriverStation.getInstance().isDSAttached(),true,false);
		readings[i++]=new DigitalSensorReading(this,"DriverStation.isFMSAttached", DriverStation.getInstance().isFMSAttached(),true,false);
//		readings[i++]=new DigitalSensorReading(this,"DriverStation.isSysActive", DriverStation.getInstance().isSysActive());
		//from HALUtil (3)
		readings[i++]=new AnalogSensorReading(this,"HALUtil.FPGARevision", HALUtil.getFPGARevision(),false,false);
		readings[i++]=new AnalogSensorReading(this,"HALUtil.FPGAVersion", HALUtil.getFPGAVersion(),false,false);
		readings[i++]=new DigitalSensorReading(this,"HALUtil.FPGAButton", HALUtil.getFPGAButton());
		//from RobotState (1)
//		readings[i++]=new DigitalSensorReading(this,"RobotState.isEnabled", RobotState.isEnabled());		
//		readings[i++]=new DigitalSensorReading(this,"RobotState.isAutonomous", RobotState.isAutonomous());
//		readings[i++]=new DigitalSensorReading(this,"RobotState.isDisabled", RobotState.isDisabled());		
//		readings[i++]=new DigitalSensorReading(this,"RobotState.isOperatorControl", RobotState.isOperatorControl());		
//		readings[i++]=new DigitalSensorReading(this,"RobotState.isTest", RobotState.isTest());		
		readings[i++]=new RobotStateReading(this,"RobotState");		

		//from Timer (1)
//		readings[i++]=new AnalogSensorReading(this,"Timer.FPGATimestamp", Timer.getFPGATimestamp());
		readings[i++]=new AnalogSensorReading(this,"Timer.MatchTime", Timer.getMatchTime());
		
		//from PowerDistributionPanel ( 11 + kPDPModules*(kPDPChannels+5) )
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kAnalogInputChannels", PowerDistributionPanel.kAnalogInputChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kAnalogOutputChannels", PowerDistributionPanel.kAnalogOutputChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kDigitalChannels", PowerDistributionPanel.kDigitalChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kPDPModules", PowerDistributionPanel.kPDPModules);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kPDPChannels", PowerDistributionPanel.kPDPChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kPwmChannels", PowerDistributionPanel.kPwmChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kRelayChannels", PowerDistributionPanel.kRelayChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kSolenoidModules", PowerDistributionPanel.kSolenoidModules);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kSolenoidChannels", PowerDistributionPanel.kSolenoidChannels);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.kSystemClockTicksPerMicrosecond", PowerDistributionPanel.kSystemClockTicksPerMicrosecond);
//		readings[i++]=new AnalogSensorReading(this,"PowerDistributionPanel.DefaultSolenoidModule", PowerDistributionPanel.getDefaultSolenoidModule());
		//( kPDPModules*(kPDPChannels+5) )
/*		try{
			for(int k=0;k<PowerDistributionPanel.kPDPModules;k++){
				PowerDistributionPanel pdp = new PowerDistributionPanel(k);
				readings[i++]=new AnalogSensorReading("PowerDistributionPanel.module["+k+"].Temperature", pdp.getTemperature());
				readings[i++]=new AnalogSensorReading("PowerDistributionPanel.module["+k+"].TotalCurrent", pdp.getTotalCurrent());
				readings[i++]=new AnalogSensorReading("PowerDistributionPanel.module["+k+"].TotalEnergy", pdp.getTotalEnergy());
				readings[i++]=new AnalogSensorReading("PowerDistributionPanel.module["+k+"].TotalPower", pdp.getTotalPower());
				readings[i++]=new AnalogSensorReading("PowerDistributionPanel.module["+k+"].Voltage", pdp.getVoltage());
				//(kPDPChannels)
				for(int j=0;j<PowerDistributionPanel.kPDPChannels;j++){
					readings[i++]=new AnalogSensorReading("PowerDistributionPanel.module["+k+"].channel["+j+"].Current", pdp.getCurrent(j));
				}
			}
		}catch(Exception e){
			System.out.println("error getting PDP details");
		}*/
	}
	
	public RobotDiagnostics() {
		this(null);
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public SensorReading[] getReadings() {
		return readings;
	}

	@Override
	public void refresh() {
		int i=0;
		//from ControllerPower  (5)
//		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getCurrent3V3());
//		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getCurrent5V());
//		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getCurrent6V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getInputCurrent());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getInputVoltage());
//		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getVoltage3V3());
//		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getVoltage5V());
//		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getVoltage6V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getFaultCount3V3());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getFaultCount5V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getFaultCount6V());
//		((DigitalSensorReading)readings[i++]).setValue(ControllerPower.getEnabled3V3());
//		((DigitalSensorReading)readings[i++]).setValue(ControllerPower.getEnabled5V());
//		((DigitalSensorReading)readings[i++]).setValue(ControllerPower.getEnabled6V());

		//from DriverStation (6)
		((AnalogSensorReading)readings[i++]).setValue(DriverStation.getInstance().getLocation());
		((AnalogSensorReading)readings[i++]).setValue(DriverStation.getInstance().getAlliance().ordinal());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isBrownedOut());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isDSAttached());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isFMSAttached());
//		((DigitalSensorReading)readings[i++]).setValue(HALUtil.getFPGAButton());
//		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isSysActive());

		//from HALUtil (3)
		((AnalogSensorReading)readings[i++]).setValue(HALUtil.getFPGARevision());
		((AnalogSensorReading)readings[i++]).setValue(HALUtil.getFPGAVersion());
		((DigitalSensorReading)readings[i++]).setValue(HALUtil.getFPGAButton());
		
				
		//from RobotState (1)
//		((DigitalSensorReading)readings[i++]).setValue(RobotState.isEnabled());		
//		((DigitalSensorReading)readings[i++]).setValue(RobotState.isAutonomous());
//		((DigitalSensorReading)readings[i++]).setValue(RobotState.isDisabled());		
//		((DigitalSensorReading)readings[i++]).setValue(RobotState.isOperatorControl());		
//		((DigitalSensorReading)readings[i++]).setValue(RobotState.isTest());		
		((RobotStateReading)readings[i++]).refresh();	
		

		//from Timer (1)
		((AnalogSensorReading)readings[i++]).setValue(Timer.getMatchTime());
		
		
		//from PowerDistributionPanel ( 11 + kPDPModules*(kPDPChannels+5) )
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kAnalogInputChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kAnalogOutputChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kDigitalChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kPDPModules);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kPDPChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kPwmChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kRelayChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kSolenoidModules);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kSolenoidChannels);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kSystemClockTicksPerMicrosecond);
//		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.getDefaultSolenoidModule());
		//( kPDPModules*(kPDPChannels+5) )
		/*try{
		for(int k=0;k<PowerDistributionPanel.kPDPModules;k++){
			PowerDistributionPanel pdp = new PowerDistributionPanel(k);
			((AnalogSensorReading)readings[i++]).setValue(pdp.getTemperature());
			((AnalogSensorReading)readings[i++]).setValue(pdp.getTotalCurrent());
			((AnalogSensorReading)readings[i++]).setValue(pdp.getTotalEnergy());
			((AnalogSensorReading)readings[i++]).setValue(pdp.getTotalPower());
			((AnalogSensorReading)readings[i++]).setValue(pdp.getVoltage());
			//(kPDPChannels)
			for(int j=0;j<PowerDistributionPanel.kPDPChannels;j++){
				((AnalogSensorReading)readings[i++]).setValue(pdp.getCurrent(j));
			}
		}
		}catch(Exception e){
			System.out.println("error getting PDP details");
		}*/
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public RobotBase getRobot() {
		return robot;
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
