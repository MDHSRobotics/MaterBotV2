package org.usfirst.frc.team4141.MDRobotBase.config;

import java.util.Map;

import org.usfirst.frc.team4141.MDRobotBase.eventmanager.JSON;

import edu.wpi.first.wpilibj.Preferences;

public class ConfigPreferenceManager {
	//Helper class to manage the persistence and retrieval of config settings to disk 
	// using the Preferences feature of the RoboRio

	public static void clearPreferences(){
		for(Object key: Preferences.getInstance().getKeys()){
			Preferences.getInstance().remove((String)key);
		}
	}
	
	public static void register(ConfigSetting setting){
		//when the robot is initialized, the default config setting are read from the code
		//however, config settings may have been changed during robot execution
		//saved settings should be read from file and override the default in the code
		
		//To do this:
		// When a setting is registered, see if it exists in the Preferences framework
		if(Preferences.getInstance().containsKey(setting.getPath())){
			//Preferences file has the setting, update the setting that was passed into the method
			//with the values from the Preferences file
			updateSetting(setting,Preferences.getInstance().getString(setting.getPath(), null));
		}
		else
		{
			//not in Preferences - add it
			//TODO: refactor so that settings are persisted only when they are changed by the console
			//move this to a command received by the event manager
			System.out.println("saving setting "+setting.toJSON()+" to Preferences");

			Preferences.getInstance().putString(setting.getPath(), setting.toJSON());
			
		}
	}

	private static void updateSetting(ConfigSetting robotSetting, String JSONsetting) {
		JSONsetting = JSONsetting.replace('\\', '"');
		System.out.println("updating setting "+robotSetting.getPath()+" from Preferences with "+JSONsetting);
	   if(JSONsetting == null) return;
	   @SuppressWarnings("rawtypes")
	   Map parsedSetting = JSON.parse(JSONsetting);
	   String key = "value";
	   if(parsedSetting.containsKey(key)){
		   robotSetting.setValue(parsedSetting.get(key));
	   }
	   key = "min";
	   if(parsedSetting.containsKey(key)){
		   robotSetting.setMin(parsedSetting.get(key));
	   }	   
	   key = "max";
	   if(parsedSetting.containsKey(key)){
		   robotSetting.setMax(parsedSetting.get(key));
	   }	   
	}

	public static void save(ConfigSetting setting) {
		Preferences.getInstance().putString(setting.getPath(), setting.toJSON());
	}
}
