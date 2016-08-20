package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import java.util.Map;

import com.google.gson.Gson;

public class JSON {
	
	private static JSON instance;
	private static JSON getInstance(){
		if(instance == null){
			instance = new JSON();
		}
		return instance;
	}
	
	private Gson gson = new Gson();
	
	@SuppressWarnings("rawtypes")
	public static Map  parse(String json){
		return getInstance().fromJson(json);
		
	}

	@SuppressWarnings("rawtypes")
	private Map fromJson(String json) {
		return (Map) gson.fromJson(json, Object.class);
	}

}
