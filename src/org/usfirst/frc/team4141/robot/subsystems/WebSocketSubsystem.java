	package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Dispatcher;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManager;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.MessageHandler;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotNotification;

import edu.wpi.first.wpilibj.Notifier;

public class WebSocketSubsystem extends MDSubsystem implements MessageHandler{

	private EventManager eventManager;
	private Notifier dispatcher;
	private double updatePeriod = 0.1;  //0.1 seconds

	public WebSocketSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
	}

	@Override
	protected void initDefaultCommand() {
		System.out.println("WebSocketSubsystem.initDefaultCommand()");
		dispatcher = new Notifier(new Dispatcher(eventManager));
		dispatcher.startPeriodic(updatePeriod);
	}

	@Override
	protected void setUp() {
		if(getConfigSettings()!=null && getConfigSettings().containsKey("enableWebSockets")){
			System.out.println("enableWebSockets config  = "+((Boolean)(getConfigSettings().get("enableWebSockets").getValue())).toString());
			this.eventManager = new EventManager(this,(Boolean)(getConfigSettings().get("enableWebSockets").getValue()));
		}
		else
		{
			this.eventManager = new EventManager(this);
		}
		
		if(eventManager.isWebSocketsEnabled()){
			System.out.println("websockets enabled");
		}
		else{
			System.out.println("websockets disabled");
		}
		System.out.println("starting event manager");
		try {
			eventManager.start();
		} catch (Exception e) {
			System.out.println("unable to start web socket manager");
			e.printStackTrace();
		}
	}
	

    //
    //EventManager helper methods
	public void post(RobotNotification notification){
		if(eventManager.isWebSocketsEnabled()){
			eventManager.post(notification);
		}
	}

//	@Override
//	public void onText(Session session, String event) {
		//TODO Refactor UI sending to robot
		//this should be built into the base
		//initiate a command on demand
		//set a configuration setting
		//Code to process message
//		System.out.printf("message received from UI\n\t%s\n",event);
		
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

//	}

	@Override
	public void process(String message) {
		System.out.println("Robot received message: "+message);
		
	}

	@Override
	public MDRobotBase geRobot() {
		
		return getRobot();
	}

}