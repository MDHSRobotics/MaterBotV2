package org.usfirst.frc.team4141.MDRobotBase.notifications;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase.RobotState;

public class RobotStateNotification extends RobotNotification {
	

	public RobotStateNotification(RobotState state,boolean display,boolean record, boolean console) {
		super("RobotStateNotification",display,record,console);
		this.state = state;
	}
	public RobotStateNotification(RobotState state,boolean display) {
		this(state,display,true,false);
	}
	private RobotState state;
	@Override
	protected void addJSONPayload() {
		if(sb.length()>0){
			sb.append(", ");
		}
		sb.append("\"fpgaTime\":");
		sb.append(getFpgaTime());
		if(state!=null){
			sb.append(", \"state\":\"");
			sb.append(state.toString());
			sb.append("\"");
		}
	}
}
