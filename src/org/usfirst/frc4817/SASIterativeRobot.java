/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc4817;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class SASIterativeRobot extends IterativeRobot  {
	
	final static boolean DEBUG = false;
	
	//Initialize joysticks
	Joystick leftJoystick = new Joystick(1);
	Joystick rightJoystick = new Joystick(2);
	Joystick auxJoystick = new Joystick(3);
	
	RobotDrive drive = new RobotDrive(3,4);
		
	ElevatorSubsystem elevator = new ElevatorSubsystem();
	ServoSubsystem servo = new ServoSubsystem();
	EjectorSubsystem ejector = new EjectorSubsystem();
	RetractorSubsystem retractor = new RetractorSubsystem();
	
	DriverStation ds = DriverStation.getInstance();
	
		
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code. 
     */
    public void robotInit() {
    	log("Robot Initialized!");
    }

    public void autonomousInit() {
    	autonomousDeploySubsystem();
    	if(AllianceDetails.feedCapable) 
    		autonomousFeed();
    	else
    		autonomousDump();
    	
    }

    public void autonomousDeploySubsystem() {

    	/* frees feeder chutes
    	 * Precondition: Servo is closed or open.
    	 * Postcondition: Chutes are released, Servo is closed.
    	 * */
    	
    	//Total Minimum Elapsed Time: 2 seconds
    	
    	//stop robot, just in case
    	drive.tankDrive(0, 0);
    	
    	//set angles to closed, just in case
    	servo.setInitialPosition();
    	
    	//open servos
    	servo.setFinalPosition();
    	
    	Timer.delay(0.5);
    	
    	//jerk forward
    	drive.tankDrive(0.6, 0.6);
    	Timer.delay(0.1);
    	drive.tankDrive(0, 0);
    	
    	Timer.delay(0.5);
    	
    	drive.tankDrive(-0.6, -0.6);
    	Timer.delay(0.1);
    	drive.tankDrive(0, 0);
    	
    	Timer.delay(0.5);
    
    	elevator.init();
    
    	Timer.delay(0.3);
    	
    }
        
    public void autonomousDump() {
    	drive.tankDrive(0.4,0.4);
    	Timer.delay(AllianceDetails.preDumpDelay);
    	drive.tankDrive(0, 0);
    	
    	dumpAllDisks(0);
    }
    
    public void autonomousFeed() {
    	
    	Timer.delay(AllianceDetails.preFeedDelay);
    	
    	dumpAllDisks(AllianceDetails.feedInterval);
    }
    
    public void dumpAllDisks(float d) {
    	for(int i = 0; (i < 4 && ds.isAutonomous()); i++) {
    		
    		while(!ejector.tooFar() && ds.isAutonomous()) 
    			ejector.eject();	
    		ejector.stop();
    		
    		if(!isAutonomous())
    			break;
    		
    		Timer.delay(0.5);

    		if(!isAutonomous())
    			break;
    		
    		while(!ejector.tooBack() && ds.isAutonomous())
    			ejector.retract();
    		ejector.stop();
    		
    		if(!isAutonomous())
    			break;
  
    		Timer.delay(d+0.5);
    		
    	}
    }
    
    public void teleopInit() {
    	
    }
    public void teleopPeriodic() {
    	
        drive.tankDrive( leftJoystick.getY(),   rightJoystick.getY());
        
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
    		ejector.eject();
    		
    		//Drive eject motor forward
    		if(ejector.tooFar()) {
    			ejector.stop(); 	
    	   
    		}
    	}
    		
    	
    	//Missile button (silver button to the left)
    	else if(s.getRawButton(2)) {
    		log("Button 2");  
    		ejector.retract();
    		//Drive eject motor backward
    		if(ejector.tooBack())
    			ejector.stop();
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
    		if(elevator.tooHigh()){
    		elevator.down();
    		Timer.delay(0.1);
    		elevator.up();
    		if(elevator.tooHigh())
    			elevator.stop();
    	}
    		else if(elevator.tooLow()){
    		elevator.down();
    		Timer.delay(0.1);
    		elevator.up();
    		if(elevator.tooLow())
    			elevator.stop();
    		}
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
    		log("Button 6)");
    		
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
    		retractor.center();
    		
    	}
    	
    	else if(s.getRawButton(11)){
    		retractor.moveServo();
    		Timer.delay(5);
    		retractor.stop();
    		
    	}
    	
    	else if(s.getRawButton(12)){
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
