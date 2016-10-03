package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public interface MessageHandler {
	void process(String message);
	//TODO:  refactor, remove getRobot() from this interface.  It breaks clean encapsulation.
	MDRobotBase geRobot();
	//TODO:  add connect() to handle connection events, see MSee
	//void connect();
}
