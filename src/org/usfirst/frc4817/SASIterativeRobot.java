/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//this is pretty good code Dongi -- Winston
package org.usfirst.frc4817;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SASIterativeRobot extends IterativeRobot  {
	
	final static boolean DEBUG = true;
	
	//Initialize joysticks
	Joystick leftJoystick = new Joystick(1);
	Joystick rightJoystick = new Joystick(2);
	Joystick auxJoystick = new Joystick(3);
	
	RobotDrive drive = new RobotDrive(3,4);
		
	ElevatorSubsystem elevator;
	ServoSubsystem servo;
	EjectorSubsystem ejector;
	
		
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code. 
     */
    public void robotInit() {
    	log("Robot Initialized!");
    	elevator = new ElevatorSubsystem(4);
    	servo = new ServoSubsystem();
    }

    public void autonomousInit() {
   
    	/* frees feeder chutes
    	 * Precondition: Servo is closed or open.
    	 * Postcondition: Chutes are released, Servo is closed.
    	 * */
    	
    	//stop robot, just in case
    	drive.tankDrive(0, 0);
    	
    	Timer.delay(1);
    	
    	//set angles to closed, just in case
    	servo.setInitialPosition();
    	
    	Timer.delay(1);
    	
    	//open servos
    	servo.setFinalPosition();
    	
    	Timer.delay(1);
    	
    	//jerk forward
    	drive.tankDrive(0.6, 0.6);
    	Timer.delay(0.1);
    	drive.tankDrive(0, 0);
    	
    	Timer.delay(2);
    	
    	//back to initial
    	servo.setInitialPosition();
    
    	elevator.init();
    }
    public void autonomousPeriodic() {
    	
    }
        
    public void teleopInit() {
    	
    }
    public void teleopPeriodic() {
    	
        drive.tankDrive( leftJoystick.getY(), rightJoystick.getY());
        
		buttonActions(auxJoystick);
		
		//Elevator Limit Switch debug
		log("H: "+elevator.tooHigh()+" L: "+elevator.tooLow());
		
    }
    
    //Button mappings
    public void buttonActions(Joystick s) {
    	
    	//One of these if/elseifs get triggered repeatedly while it is pressed
    	
    	//Trigger
    	if(s.getRawButton(1)) {
    		log("Button 1");
    		
    		//Drive eject motor forward
    		ejector.eject();
    	}
    	
    	//Missile button (silver button to the left)
    	else if(s.getRawButton(2)) {
    		log("Button 2");  
    		
    		//Drive eject motor backward
    		ejector.retract();
    	}
    	
    	//Top left lower button
    	else if(s.getRawButton(3)) {
    		log("Button 3");
    		elevator.down();
    		if(elevator.tooLow())
    			elevator.stop();
    	}
    	
    	//Top right lower button
    	else if(s.getRawButton(4)) {
    		log("Button 4");
    		elevator.down();
    	}
    	
    	//Top left upper button
    	else if(s.getRawButton(5)) {
    		log("Button 5");
    		elevator.up();
       		if(elevator.tooHigh())
    			elevator.stop();
    	}
    	
    	//Top right upper button
    	else if(s.getRawButton(6)) {
    		log("Button 6");
    		elevator.up();
    	}
    	
    	else if(s.getRawButton(7)) {
    		log("Button 7");
    	}
    	
    	else if(s.getRawButton(8)) {
    		log("Button 8");
    	}
    	
    	else if(s.getRawButton(9)) {
    		log("Button 9");
    	}
    	
    	else if(s.getRawButton(10)) {
    		log("Button 10");
    	}
    	
    	//no buttons pressed
    	else {
    		
    		//stop all motors
    		ejector.stop();
    		elevator.stop();
    	}
    	
    }
    
    private static void log(String s) {
    	if(DEBUG) 
    		System.out.println(s);
    }
    
}
