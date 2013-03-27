package org.usfirst.frc4817;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class EjectorSubsystem {

	Relay motor = new Relay(3);
	DigitalInput limitForward = new DigitalInput(3);
	DigitalInput limitReverse = new DigitalInput(4);
	
	//TODO add Ejector limit switch

	public EjectorSubsystem() {
			
	}
	
	public void eject() {
		motor.setDirection(Relay.Direction.kForward);
		motor.set(Relay.Value.kOn);
	}
	public void retract() {
		motor.setDirection(Relay.Direction.kReverse);
		motor.set(Relay.Value.kOn);
	}
	public void stop() {
		motor.set(Relay.Value.kOff);
	}
	
	//return true if ejector bar is too far forward
	public boolean tooFar() {
		return limitForward.get();
	}
	
	//returns true if ejector bar is too far backwardz
	public boolean tooBack() {
		return limitReverse.get();
	}
	
}
