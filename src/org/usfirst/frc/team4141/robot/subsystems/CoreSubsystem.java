package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

public class CoreSubsystem extends MDSubsystem {

	public CoreSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	}

	@Override
	protected void initDefaultCommand() {
	}

	@Override
	protected void setUp() {
//		System.out.println("command chooser keys:");
//		for(String key : getRobot().getCommandChooser().keySet()){
//			System.out.println(key);
//		}
//		System.out.println("config keys:");
//		for(String key : getConfigSettings().keySet()){
//			System.out.println(key);
//		}
		if(getConfigSettings().containsKey("name")){
			getRobot().setName(getConfigSettings().get("name").getString());
		}
		if(getConfigSettings().containsKey("autoCommand")){
			getRobot().setAutoCommand(getConfigSettings().get("autoCommand").getString());
		}
	}

	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(getConfigSettings()!=null && getConfigSettings().containsKey("autoCommand")){
			getRobot().setAutoCommand(getConfigSettings().get("autoCommand").getString());
		}	
		if(getConfigSettings()!=null && getConfigSettings().containsKey("name")){
			getRobot().setName(getConfigSettings().get("name").getString());
		}	
	}
}
