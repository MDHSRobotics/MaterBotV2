package org.usfirst.frc.team4141.MDRobotBase;


import org.usfirst.frc.team4141.MDRobotBase.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;

public abstract class MDCommand extends Command {
	private MDRobotBase robot;
	private String name;
//	private Hashtable<String,String> attributes;

	public MDCommand(MDRobotBase robot,String name) {
		super();
		this.name = name;
		this.robot=robot;
//		this.attributes = new Hashtable<String,String>();
	}
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void log(String methodName, String message) {
		getRobot().log(this.getClass().getName()+"."+methodName+"()", message);
		
	}
	public void log(Level level, String methodName, String message) {
		getRobot().log(level,this.getClass().getName()+"."+methodName+"()", message);
		
	}
		
//	public void setAttribute(String name, String value){
//		attributes.put(name,value);
//	}
//	public String getAttribute(String name){
//		if(attributes.containsKey(name))
//			return attributes.get(name);
//		else return null;
//	}
	public MDRobotBase getRobot() {
		return robot;
	}

//	public Hashtable<String, String> getAttributes() {
//		return attributes;
//	}

	private boolean isInitialized = false;
	@Override
	protected void initialize() {
		if(!isInitialized){
			log("initialize",getName());
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		isInitialized =false;
	}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }

}
