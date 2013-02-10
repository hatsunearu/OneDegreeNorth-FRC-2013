package org.usfirst.frc4817;

import edu.wpi.first.wpilibj.Relay;

public class EjectorSubsystem {

	Relay motor = new Relay(3);
	
	//TODO add Ejector limit switch
	
	public EjectorSubsystem(int port) {
		motor = new Relay(port);
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
	
}
