package org.usfirst.frc.team4141.MDRobotBase;

public class DiagnosticScan implements Runnable {
	
	public DiagnosticScan() {
		
	}
	
	boolean state = true;
	@Override
	public void run() {
		
		state = !state;
	}

}
