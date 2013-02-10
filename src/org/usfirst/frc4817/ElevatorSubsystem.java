package org.usfirst.frc4817;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class ElevatorSubsystem {
	Relay motor = null;
	boolean moving = false;

	/*how high the bucket is
	* 0 = bottom, low release and intake
	* 1 = top, high release
	* switches, low, mid, high in that order
	*/
	DigitalInput[] switches = {new DigitalInput(1), new DigitalInput(2)};
	
	
	public ElevatorSubsystem(int pin) {
		motor = new Relay(pin);
	}
	
	public void init() {
		while(!switches[0].get()) {
			down();
		}
		stop();
	}


	
	public void stop() {
		motor.set(Relay.Value.kOff);
	}
	
	public void up() {
		motor.setDirection(Relay.Direction.kReverse);
		motor.set(Relay.Value.kOn);
	}
	
	public void down() {
		motor.setDirection(Relay.Direction.kForward);
		motor.set(Relay.Value.kOn);
	}
	
	public boolean tooHigh() {
		return switches[1].get();
	}
	public boolean tooLow() {
		return switches[0].get();
	}
}

/* public void moveToTop(){
 * while (
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */ 
