package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.hal.HALUtil;

public class RobotDiagnostics implements Sensor {

	private MDRobotBase robot;
	private String name;
	SensorReading[] readings = new SensorReading[getReadingsCount()];

	public RobotDiagnostics(){
		this(true);
	}
	public RobotDiagnostics(boolean observe){
		this(null,null,observe);
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
		//from ControllerPower  (14)
		//from DriverStation (8)
		//from HALUtil (3)
		//from PowerDistributionPanel ( 11 + kPDPModules*(kPDPChannels+5) )   //skipping these for now
		//from RobotState (6)
		//from RobotBase  (2)
		//from Utility class (2)
//		return 14+8+3+6+2+2+11+(PowerDistributionPanel.kPDPModules*(PowerDistributionPanel.kPDPChannels+5));
		return 14+8+3+6+11;
	}
	RobotDiagnostics(MDRobotBase robot, String name,boolean observe){
		this.robot = robot;
		this.name = name;
		this.observe = observe;
		int i=0;
		
		//from ControllerPower  (14)
		readings[i++]=new AnalogSensorReading("ControllerPower.Current3V3", ControllerPower.getCurrent3V3(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.Current5V", ControllerPower.getCurrent5V(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.Current6V", ControllerPower.getCurrent6V(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.InputCurrent", ControllerPower.getInputCurrent(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.InputVoltage", ControllerPower.getInputVoltage(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.Voltage3V3", ControllerPower.getVoltage3V3(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.Voltage5V", ControllerPower.getVoltage5V(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.Voltage6V", ControllerPower.getVoltage6V(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.FaultCount3V3", ControllerPower.getFaultCount3V3(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.FaultCount5V", ControllerPower.getFaultCount5V(),true);
		readings[i++]=new AnalogSensorReading("ControllerPower.FaultCount6V", ControllerPower.getFaultCount6V(),true);
		readings[i++]=new DigitalSensorReading("ControllerPower.Enabled3V3", ControllerPower.getEnabled3V3(),true);
		readings[i++]=new DigitalSensorReading("ControllerPower.Enabled5V", ControllerPower.getEnabled5V(),true);
		readings[i++]=new DigitalSensorReading("ControllerPower.Enabled6V", ControllerPower.getEnabled6V(),true);
		//from DriverStation (8)
		readings[i++]=new AnalogSensorReading("DriverStation.BatteryVoltage", DriverStation.getInstance().getBatteryVoltage());
		readings[i++]=new AnalogSensorReading("DriverStation.MatchTime", DriverStation.getInstance().getMatchTime());
		readings[i++]=new AnalogSensorReading("DriverStation.Location", DriverStation.getInstance().getLocation(),false);
		readings[i++]=new AnalogSensorReading("DriverStation.Alliance", DriverStation.getInstance().getAlliance().ordinal(),false);
		readings[i++]=new DigitalSensorReading("DriverStation.isBrownedOut", DriverStation.getInstance().isBrownedOut());
		readings[i++]=new DigitalSensorReading("DriverStation.isDSAttached", DriverStation.getInstance().isDSAttached());
		readings[i++]=new DigitalSensorReading("DriverStation.isFMSAttached", DriverStation.getInstance().isFMSAttached());
		readings[i++]=new DigitalSensorReading("DriverStation.isSysActive", DriverStation.getInstance().isSysActive());
		//from HALUtil (3)
		readings[i++]=new AnalogSensorReading("HALUtil.FPGARevision", HALUtil.getFPGARevision(),false);
		readings[i++]=new DigitalSensorReading("HALUtil.FPGAButton", HALUtil.getFPGAButton());
//		readings[i++]=new AnalogSensorReading("HALUtil.FPGATime", HALUtil.getFPGATime());
		readings[i++]=new AnalogSensorReading("HALUtil.FPGAVersion", HALUtil.getFPGAVersion(),false);
		//from RobotState (6)
		readings[i++]=new DigitalSensorReading("RobotState.isEnabled", RobotState.isEnabled(),true);		
		readings[i++]=new DigitalSensorReading("RobotState.isAutonomous", RobotState.isAutonomous(),true);
		readings[i++]=new DigitalSensorReading("RobotState.isDisabled", RobotState.isDisabled(),true);		
		readings[i++]=new DigitalSensorReading("RobotState.isOperatorControl", RobotState.isOperatorControl(),true);		
		readings[i++]=new DigitalSensorReading("RobotState.isTest", RobotState.isTest(),true);		
		readings[i++]=new RobotStateReading("RobotState");		
		//from RobotBase  (2)
//		readings[i++]=new DigitalSensorReading("RobotBase.isReal", RobotBase.isReal(),false);
//		readings[i++]=new DigitalSensorReading("RobotBase.isSimulation", RobotBase.isSimulation(),false);
		//from Timer (2)
//		readings[i++]=new AnalogSensorReading("Timer.FPGATimestamp", Timer.getFPGATimestamp());
//		readings[i++]=new AnalogSensorReading("Timer.MatchTime", Timer.getMatchTime());
		//from Utility class (1)
//		readings[i++]=new AnalogSensorReading("Utility.FPGATime", Utility.getFPGATime());
//		readings[i++]=new DigitalSensorReading("Utility.userButton", Utility.getUserButton());
		//from PowerDistributionPanel ( 11 + kPDPModules*(kPDPChannels+5) )
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kAnalogInputChannels", PowerDistributionPanel.kAnalogInputChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kAnalogOutputChannels", PowerDistributionPanel.kAnalogOutputChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kDigitalChannels", PowerDistributionPanel.kDigitalChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kPDPModules", PowerDistributionPanel.kPDPModules,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kPDPChannels", PowerDistributionPanel.kPDPChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kPwmChannels", PowerDistributionPanel.kPwmChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kRelayChannels", PowerDistributionPanel.kRelayChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kSolenoidModules", PowerDistributionPanel.kSolenoidModules,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kSolenoidChannels", PowerDistributionPanel.kSolenoidChannels,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.kSystemClockTicksPerMicrosecond", PowerDistributionPanel.kSystemClockTicksPerMicrosecond,false);
		readings[i++]=new AnalogSensorReading("PowerDistributionPanel.DefaultSolenoidModule", PowerDistributionPanel.getDefaultSolenoidModule(),false);
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
		//from ControllerPower  (14)
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getCurrent3V3());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getCurrent5V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getCurrent6V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getInputCurrent());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getInputVoltage());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getVoltage3V3());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getVoltage5V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getVoltage6V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getFaultCount3V3());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getFaultCount5V());
		((AnalogSensorReading)readings[i++]).setValue(ControllerPower.getFaultCount6V());
		((DigitalSensorReading)readings[i++]).setValue(ControllerPower.getEnabled3V3());
		((DigitalSensorReading)readings[i++]).setValue(ControllerPower.getEnabled5V());
		((DigitalSensorReading)readings[i++]).setValue(ControllerPower.getEnabled6V());
		//from DriverStation (8)
		((AnalogSensorReading)readings[i++]).setValue(DriverStation.getInstance().getBatteryVoltage());
		((AnalogSensorReading)readings[i++]).setValue(DriverStation.getInstance().getMatchTime());
		((AnalogSensorReading)readings[i++]).setValue(DriverStation.getInstance().getLocation());
		((AnalogSensorReading)readings[i++]).setValue(DriverStation.getInstance().getAlliance().ordinal());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isBrownedOut());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isDSAttached());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isFMSAttached());
		((DigitalSensorReading)readings[i++]).setValue(DriverStation.getInstance().isSysActive());
		//from HALUtil (3)
		((AnalogSensorReading)readings[i++]).setValue(HALUtil.getFPGARevision());
		((DigitalSensorReading)readings[i++]).setValue(HALUtil.getFPGAButton());
//		((AnalogSensorReading)readings[i++]).setValue(HALUtil.getFPGATime());
		((AnalogSensorReading)readings[i++]).setValue(HALUtil.getFPGAVersion());
		
				
		//from RobotState (6)
		((DigitalSensorReading)readings[i++]).setValue(RobotState.isEnabled());		
		((DigitalSensorReading)readings[i++]).setValue(RobotState.isAutonomous());
		((DigitalSensorReading)readings[i++]).setValue(RobotState.isDisabled());		
		((DigitalSensorReading)readings[i++]).setValue(RobotState.isOperatorControl());		
		((DigitalSensorReading)readings[i++]).setValue(RobotState.isTest());		
		((RobotStateReading)readings[i++]).refresh();				
		//from RobotBase  (2)
//		((DigitalSensorReading)readings[i++]).setValue(RobotBase.isReal());
//		((DigitalSensorReading)readings[i++]).setValue(RobotBase.isSimulation());
		//from Timer (2)
//		((AnalogSensorReading)readings[i++]).setValue(Timer.getFPGATimestamp());
//		((AnalogSensorReading)readings[i++]).setValue(Timer.getMatchTime());
		//from Utility class (2)
//		((AnalogSensorReading)readings[i++]).setValue(Utility.getFPGATime());
//		((DigitalSensorReading)readings[i++]).setValue(Utility.getUserButton());
		
		//from PowerDistributionPanel ( 11 + kPDPModules*(kPDPChannels+5) )
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kAnalogInputChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kAnalogOutputChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kDigitalChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kPDPModules);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kPDPChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kPwmChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kRelayChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kSolenoidModules);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kSolenoidChannels);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.kSystemClockTicksPerMicrosecond);
		((AnalogSensorReading)readings[i++]).setValue(PowerDistributionPanel.getDefaultSolenoidModule());
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
	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}
	
}
