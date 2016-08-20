package org.usfirst.frc.team4141.MDRobotBase;


public class MDPrintCommand extends MDCommand {
	
	
	private String message;

	public MDPrintCommand(MDRobotBase robot, String name, String message) {
		super(robot,name);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
	
	@Override
	protected void execute() {
		super.execute();
		System.out.println(message);
	}

}
