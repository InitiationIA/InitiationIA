package Motor;
/**
 * La classe qui agrège les actioneur : 
 *  -les pinces
 *  -la motorisation (moteur + roue)
 *
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.motor.*;
import lejos.robotics.chassis.*;
//import lejos.hardware.port.*;

import lejos.robotics.navigation.MovePilot;
public class Actionneur {
    
    //Pince p = new Pince(MotorPort.B);
    /**
     * TODO: 
     */
    private final Wheel wheel1 ;
    private final Wheel wheel2 ;
    private final Chassis chassis ;
   // private final Pince p;  
    private final MovePilot pilot ;

    public Actionneur() {
    	
        wheel1 = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("B")), 56).offset(-80);
        wheel2 = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("C")), 56).offset(80);
        chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
       // p = new Pince("A");
        pilot = new MovePilot(chassis);
    }

    public Actionneur(double vitesseLin,double accelerationLin) {
        this();
        pilot.setLinearSpeed(vitesseLin);
        pilot.setLinearAcceleration(accelerationLin);

    }

    public Actionneur(double vitesseLin,double accLin,double vitesseAng,double accAng) {
        this();
        pilot.setLinearSpeed(vitesseLin);
        pilot.setLinearAcceleration(accLin);
        pilot.setAngularSpeed(vitesseAng);
        pilot.setAngularAcceleration(accAng);
    }

	  
	public double getAccelLigne() {
	    return this.pilot.getLinearAcceleration();
	}
	
	
	public void setAccelLigne(double acceleration) {
	    this.pilot.setLinearAcceleration(acceleration);
	}

	
	public double getAccelAngle() {
	    return this.pilot.getAngularAcceleration();
	}
	
	
	public void setAccelAngle(double acceleration) {
	    this.pilot.setAngularAcceleration(acceleration);
	}

	  
	public double getVitesseLigne() {
	    return this.pilot.getLinearSpeed();
	}  

	
	public void setVitesseLigne(double speed) {
	    this.pilot.setLinearSpeed(speed);
	}

	  
	public double getMaxVitesseLigne() {
	    return chassis.getMaxLinearSpeed();
	}  

	
	public double getVitesseAngle() {
	    return this.pilot.getAngularSpeed();
	}
	  
	
	public void setVitesseAngle(double speed) {
	    this.pilot.setAngularSpeed(speed);
	}
	
	 
	public double getMaxVitesseAngle() {
	    return chassis.getMaxAngularSpeed();
	}  
	  
	
	
	public void arcAvant(double radius) {
	    this.pilot.arcForward(radius);
	  }

	public void arcArriere(double radius) {
		this.pilot.arcBackward(radius);
	}

	public void arcSimple(double radius, double angle) {
		this.pilot.arc(radius, angle);
	}

	public void traverseArc(double radius, double distance) {
		this.pilot.travelArc(radius, distance, false);
	}

	public void traverseArc(double radius, double distance, boolean immediateReturn) {
		this.pilot.arc(radius,  distance / (2 * Math.PI), immediateReturn);
	}



	  
    public void stop(){
        pilot.stop();
    }

    public boolean estEnDeplacement(){
        return pilot.isMoving();
    }
    
  	public void avancer(double dis) {
  		this.pilot.travel(dis);
  	}
  		
  	public void reculer(double dis) {
  		this.pilot.travel(dis);
  	}
  	
  	public void tourne(double ang) {
  		this.pilot.rotate(ang);
  	}
  	
  
  	
  	
		
	
	
	// differnetial pilot = movePilot
	
	/*
	 * TODO comment appeler des méthodes sur la variable pilot (voir MovePilotClass)
	 */
	
	/*
	 * utiliser Navigator: créer une instances des objets
	 * 			Pose: position + direction
	 * 			MoveController	
	 */
	
	
	public static void main(String[] args) {
		

		
	}

}
