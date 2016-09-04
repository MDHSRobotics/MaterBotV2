package org.usfirst.frc.team4141.MDRobotBase.eventmanager;


public class Dispatcher implements Runnable {
	
	
	private EventManager eventManager;

	public Dispatcher(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	@Override
	public void run() {
//		System.out.println("dispatching");
		eventManager.post();
//		robot.post(new HeartbeatNotification(robot, true));
	}

}
