package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public interface MessageHandler {
	void process(String message);
	MDRobotBase geRobot();
}
