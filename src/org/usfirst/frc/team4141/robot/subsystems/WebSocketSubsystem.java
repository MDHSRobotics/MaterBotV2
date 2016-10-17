	package org.usfirst.frc.team4141.robot.subsystems;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.usfirst.frc.team4141.MDRobotBase.MDConsoleButton;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigPreferenceManager;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting.Type;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Dispatcher;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManager;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManagerWebSocket;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.JSON;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.MessageHandler;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotConfigurationNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotLogNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotNotification;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;

public class WebSocketSubsystem extends MDSubsystem implements MessageHandler{

	private EventManager eventManager;
	private Notifier dispatcher;
	private double updatePeriod = 0.1;  //0.1 seconds

	public WebSocketSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	}
	
	

	@Override
	protected void initDefaultCommand() {
//		System.out.println("WebSocketSubsystem.initDefaultCommand()");
		dispatcher = new Notifier(new Dispatcher(eventManager));
		dispatcher.startPeriodic(updatePeriod);
	}

	@Override
	protected void setUp() {
		announce();
		if(getConfigSettings()!=null && getConfigSettings().containsKey("enableWebSockets")){
//			System.out.println("enableWebSockets config  = "+((Boolean)(getConfigSettings().get("enableWebSockets").getValue())).toString());
			this.eventManager = new EventManager(this,(Boolean)(getConfigSettings().get("enableWebSockets").getValue()));
		}
		else
		{
			this.eventManager = new EventManager(this);
		}
		
//		if(eventManager.isWebSocketsEnabled()){
//			System.out.println("websockets enabled");
//		}
//		else{
//			System.out.println("websockets disabled");
//		}
		System.out.println("starting event manager");
		try {
			eventManager.start();
		} catch (Exception e) {
			System.out.println("unable to start web socket manager");
			e.printStackTrace();
		}
	}
	private JmDNS jmdns;

    private void announce() {
    	 try {
             // Create a JmDNS instance
             jmdns = JmDNS.create(InetAddress.getLocalHost());

             // Register a service
             ServiceInfo serviceInfo = ServiceInfo.create("_ws._tcp.local.", "Team4141Robot", eventManager.getPort(), "");
             jmdns.registerService(serviceInfo);

         } catch (IOException e) {
             System.out.println(e.getMessage());
         }		
		
	}
    
    

	//
    //EventManager helper methods
	public void post(RobotNotification notification){
		if(eventManager.isWebSocketsEnabled()){
			eventManager.post(notification);
		}
	}

	@SuppressWarnings({"rawtypes" })
	@Override
	public void process(String messageText) {
//		System.out.println("Robot received message: "+messageText);
		Map message = JSON.parse(messageText);
		if(message.containsKey("type")){
			String type = message.get("type").toString();
			if(type.equals("consoleButtonUpdate")){
				updateConsoleOIButton(message);
			}
			if(type.equals("settingUpdate")){
				updateSetting(message);
			}
			if(type.equals("remoteIdentification")){
				addRemote(message);
			}
		}
		message.keySet();
	}

	private void addRemote(Map message) {
		if(message.containsKey("id")){
			System.out.printf("%s connected!\n",message.get("id"));
		}
	}



	@SuppressWarnings("rawtypes")
	private void updateSetting(Map message) {
		//format: {"type":"settingUpdate", "subsystem":"core", "settingName":"autoCommand", "value":"AutonomousCommand2"}
		//we already know that it's a settingUpdate
		//do 2 things:
		//1. get setting from core and update it.
		//2. update preferences
		if(message.containsKey("subsystem") && message.containsKey("settingName")){
			String subsystemName = (String)(message.get("subsystem"));
			String settingName = (String)(message.get("settingName"));
			if(geRobot().getSubsystems().containsKey(subsystemName)){
				MDSubsystem subsystem = getRobot().getSubsystems().get(subsystemName);
				System.out.println("changing setting for subsystem "+subsystem.getName());
				if(subsystem.hasSetting(settingName)){
					ConfigSetting setting = subsystem.getSetting(settingName);
					System.out.println("updating setting "+setting.getName());
					if(message.containsKey("value")){
						setting.setValue(message.get("value"));
						System.out.println("value now set to "+setting.getValue().toString());
					}
					if(message.containsKey("min")){
						setting.setMin(message.get("min"));
						System.out.println("min now set to "+setting.getMin().toString());
					}
					if(message.containsKey("max")){
						setting.setMax(message.get("max"));
						System.out.println("max now set to "+setting.getMax().toString());
					}
					subsystem.settingChangeListener(setting);
					ConfigPreferenceManager.save(setting);
				}
			}
		}
		
	}

	@SuppressWarnings("rawtypes")
	private void updateConsoleOIButton(Map message) {
		//format: {"type":"consoleButtonUpdate", "buttonName":"ExampleCommand1", "buttonIndex":0, "pressed":true}
		//we already know that it's a consoleButtonUpdate
		//get buttonName, index and pressed
		if(message.containsKey("buttonName") && message.containsKey("buttonIndex") && message.containsKey("pressed")){
			String name = message.get("buttonName").toString();
			Integer index = (int)(Double.parseDouble(message.get("buttonIndex").toString()));
			Boolean pressed = Boolean.valueOf(message.get("pressed").toString());
			if(getRobot().getOi().getConsole().getButtons().containsKey(index)){
				MDConsoleButton button = getRobot().getOi().getConsole().getButtons().get(index);
				if(button.getName().equals(name)){
					button.setPressed(pressed);
					if(name.equals("ExampleCommand1") && pressed){
						System.out.println("need to rumble");
						getRobot().getOi().getConsole().setRumble(Joystick.RumbleType.kLeftRumble,0.5);
					}
				}
			}
		}
	}

	public MDRobotBase geRobot() {
		
		return getRobot();
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		System.out.println(changedSetting.getPath()+ " was changed to "+ changedSetting.getValue().toString());
		if(changedSetting.getName().equals("enableWebSockets") && changedSetting.getType().equals(Type.binary)){
			//we know that enableWe
			if(changedSetting.getBoolean()){
				System.out.println("enabling websockets");
				eventManager.enableWebSockets();
			}
			else{
				System.out.println("disabling websockets");
				eventManager.disableWebSockets();
			}
		}
	}

	@Override
	public void connect(EventManagerWebSocket socket) {
		eventManager.post(new RobotLogNotification(this.getClass().getName()+".connect()","Connection"));
		eventManager.post(new RobotConfigurationNotification(getRobot()));

		
	}

}