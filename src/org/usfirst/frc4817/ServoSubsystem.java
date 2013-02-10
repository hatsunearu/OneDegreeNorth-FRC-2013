package org.usfirst.frc4817;

import edu.wpi.first.wpilibj.Servo;

public class ServoSubsystem {
	
	final static int lowSInit = 80;
	final static int lowSFinal = 0;
	
	final static int highSInit = 0;
	final static int highSFinal = 66;
	
	Servo lowS = new Servo(5);
	Servo highS = new Servo(6);

	
	public void setInitialPosition() {
    	lowS.setAngle(lowSInit);
    	highS.setAngle(highSInit);
	}
	
	public void setFinalPosition() {
		lowS.setAngle(lowSFinal);
		highS.setAngle(highSFinal);
	}
	

}
