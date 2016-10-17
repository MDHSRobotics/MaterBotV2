package org.usfirst.frc.team4141.MDRobotBase.eventmanager;


public interface MessageHandler {
	void process(String message);
//	MDRobotBase geRobot();
	void connect(EventManagerWebSocket socket);
}