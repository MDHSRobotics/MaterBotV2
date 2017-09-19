package org.usfirst.frc.team4141.MDRobotBase.eventmanager;


public interface MessageHandler {
	void process(Request request);
//	MDRobotBase geRobot();
	void connect(EventManagerWebSocket socket);
	void close(EventManagerWebSocket socket);
}