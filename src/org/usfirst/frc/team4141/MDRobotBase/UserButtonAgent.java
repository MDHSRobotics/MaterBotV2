package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.hal.HALUtil;

public class UserButtonAgent implements Runnable {

	private InternalButton userButton;

	public UserButtonAgent(InternalButton userButton) {
		this.userButton = userButton;
	}

	@Override
	public void run() {
		userButton.setPressed(HALUtil.getFPGAButton());
	}
	
}
