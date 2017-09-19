package org.usfirst.frc.team4141.MDRobotBase;

import edu.wpi.first.wpilibj.SpeedController;

public class MultiSpeedController implements SpeedController {
    private SpeedController[] speedControllers;
    private double speed;

    public MultiSpeedController(SpeedController... speedControllers) {
        this.speedControllers = speedControllers;
        this.set(0.0);
    }

    @Override
    public double get() {
        return this.speed;
    }

    @Override
    public void set(double speed) {
        this.speed = speed;

        for (SpeedController speedController : this.speedControllers) {
            speedController.set(speed);
        }
    }
//TODO Fixed a error by commenting out Override
//    @Override
    public void set(double speed, byte syncGroup) {
        this.set(speed);
    }

    @Override
    public void pidWrite(double output) {
        this.set(output);
    }

    @Override
    public void disable() {
        for (SpeedController speedController : this.speedControllers) {
            speedController.disable();
        }
    }
    
    private boolean isInverted = false;

	@Override
	public void setInverted(boolean isInverted) {
		this.isInverted = isInverted;
	}

	@Override
	public boolean getInverted() {
		return isInverted;
	}

	@Override
	public void stopMotor() {
        this.set(0);
	}
}
