package org.usfirst.frc.team4141.robot.subsystems;

import org.eclipse.jetty.websocket.api.Session;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManager;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.EventManagerCallBack;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.Notification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotConfigurationNotification;

public class WebSocketSubsystem extends MDSubsystem implements EventManagerCallBack{

	private EventManager eventManager;

	public WebSocketSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		this.eventManager = new EventManager(this);
	}

	@Override
	protected void initDefaultCommand() {

	}

	@Override
	protected void setUp() {
		if(getConfigSettings()!=null && getConfigSettings().containsKey("enableWebSockets")){
			if(eventManager!=null){
				eventManager.setEnableWebSockets((Boolean)(getConfigSettings().get("enableWebSockets").getValue()));
			}
		}
		if(eventManager.isWebSocketsEnabled()){
			System.out.println("websockets enabled");
		}
		else{
			System.out.println("websockets enabled");
		}
		System.out.println("starting event manager");
		if(eventManager!=null)
			try {
				eventManager.start();
			} catch (Exception e) {
				System.out.println("error starting event manager");
			}
	}
	
	
    //
    //EventManager helper methods
	public void post(Notification notification){
		if(eventManager.isWebSocketsEnabled()){
			eventManager.post(notification);
		}
	}
	//WebSocket handlers
	@Override
	public void onConnect(Session session) {
		System.out.println("connected!");
		//TODO refactor configuration communications to console
		post(new RobotConfigurationNotification(getRobot(),true));
	}
	
	//methods to configure WebSockets
	public void enableWebSockets(){
		if(this.eventManager!=null) eventManager.setEnableWebSockets(true);
	}
	public void disableWebSockets(){
		if(this.eventManager!=null) eventManager.setEnableWebSockets(false);
	}
	
	@Override
	public void onClose(Session session, int closeCode, String closeReason) {
		System.out.printf("disconnected code[%d] reason:%s\n",closeCode,closeReason);
	}
	@Override
	public void onError(Session session, Throwable err) {
		System.err.println("error: "+err.getMessage());
	}
	@Override
	public void onBinary(Session session, byte[] bytes, int arg2, int arg3) {
		throw new NotImplementedException("Event manager does not yet support binary messages");
	}

	@Override
	public void onText(Session session, String event) {
		//TODO Refactor UI sending to robot
		//this should be built into the base
		//initiate a command on demand
		//set a configuration setting
		//Code to process message
		System.out.printf("message received from UI\n\t%s\n",event);
		
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

	}

}