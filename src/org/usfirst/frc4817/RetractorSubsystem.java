package org.usfirst.frc4817;

/**
 * @author SAS Robotics
 *
 */

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class RetractorSubsystem {
	
	DigitalInput chuteswitch = new DigitalInput(5);
	Servo retract = new Servo(7);
	
	double ChuteAngle = retract.getAngle();
	
	public void moveServo() {
		retract.setRaw(255);
	}
	public void stop() {
		retract.setRaw(0);
		
	}
	public void center() {
		retract.setRaw(128);
		
	}
	
	public boolean isClosed() {
		return chuteswitch.get();

	}
}