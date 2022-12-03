package Motor;


import lejos.hardware.BrickFinder;
import lejos.hardware.motor.*;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;




/**
 *  Classe définissant l'actionneur:
 * comprend deux roues, un chassis et un mécanisme de pilotage
 *
 */
public class Actionneur {
    
    
    protected final Wheel wheel1 ;
    protected final Wheel wheel2 ;
    private final Chassis chassis ;
    private final MovePilot pilot ;
    

    
    /**
     * Constructeur de l'actionneur
     */
    public Actionneur() {
    	
        wheel1 = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("B")), 56).offset(-80);
        wheel2 = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("C")), 56).offset(80);
        chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        
    }

    /**
     * constructeur vitesse en cm/s
     * @param vitesseLin
     * @param accelerationLin
     */
    public Actionneur(double vitesseLin, double accelerationLin) {
        this();
        pilot.setLinearSpeed(vitesseLin);
        pilot.setLinearAcceleration(accelerationLin);

    }

    /**
     * vitesse en degré/s
     * @param vitesseLin
     * @param accLin
     * @param vitesseAng
     * @param accAng
     */
    public Actionneur(double vitesseLin, double accLin, double vitesseAng, double accAng) {
        this();
        pilot.setAngularSpeed(vitesseAng);
        pilot.setAngularAcceleration(accAng);
    }

    
    
    /**
     * @return la valeur du tachomètre (en degré) de la roue droite
     */
    public double getTachometer() {
    	return wheel2.getMotor().getTachoCount();
    }
    
	
	/**
	 * @return  la valeur de l'accélération linéaire
	 */
	public double getAccelLigne() {
	    return this.pilot.getLinearAcceleration();
	}
	
	
	/**
	 * modifie l'accélération linéaire 
	 * @param acceleration nouvelle accelération linéaire
	 */
	public void setAccelLigne(double acceleration) {
	    this.pilot.setLinearAcceleration(acceleration);
	}

	
	/**
	 * @return  l'accélération angulaire
	 */
	public double getAccelAngle() {
	    return this.pilot.getAngularAcceleration();
	}
	
	
	/**
	 * modifie l'accelération angulaire
	 * @param acceleration nouvelle accelération angulaire
	 */
	public void setAccelAngle(double acceleration) {
	    this.pilot.setAngularAcceleration(acceleration);
	}

	
	/**
	 * @return  la vitesse linéaire
	 */
	public double getVitesseLigne() {
	    return this.pilot.getLinearSpeed();
	}  

	
	/**
	 *  modifie la vitesse linéaire
	 * @param speed nouvelle vitesse linéaire
	 */
	public void setVitesseLigne(double speed) {
	    this.pilot.setLinearSpeed(speed);
	}

	
	/**
	 * @return  la vitesse linéaire maximale
	 */
	public double getMaxVitesseLigne() {
	    return chassis.getMaxLinearSpeed();
	}  

	
	/**
	 * @return  la vitesse angulaire
	 */
	public double getVitesseAngle() {
	    return this.pilot.getAngularSpeed();
	}
	  
	
	/**
	 * modifie la vitesse angulaire
	 * @param speed nouvelle vitesse angulaire
	 */
	public void setVitesseAngle(double speed) {
	    this.pilot.setAngularSpeed(speed);
	}
	
	
	/**
	 * @return  la vitesse angulaire maximale
	 */
	public double getMaxVitesseAngle() {
	    return chassis.getMaxAngularSpeed();
	}  
	  
	
	
	/**
	 * modifie l'angle pendant que le robot avance en arc
	 * @param radius 
	 */
	public void arcAvant(double radius) {
	    this.pilot.arcForward(radius);
	}

	
	/**
	 * modifie l'angle pendant que le robot recule en arc
	 * @param radius
	 */
	public void arcArriere(double radius) {
		this.pilot.arcBackward(radius);
	}

	
	/**
	 * avance en arc selon un gradient et un angle donné
	 * @param radius
	 * @param angle
	 */
	public void arcSimple(double radius, double angle) {
		this.pilot.arc(radius, angle);
	}
	
	
	/**
	 * traverse en arc selon un gradient et une distance
	 * @param radius
	 * @param distance
	 */
	public void traverseArc(double radius, double distance) {
		this.pilot.travelArc(radius, distance, false);
	}

	
	/**
	 * traverse en arc selon un gradient, une distance, en synchronisant
	 * @param radius
	 * @param distance
	 * @param immediateReturn
	 */
	public void traverseArc(double radius, double distance, boolean immediateReturn) {
		this.pilot.arc(radius,  distance / (2 * Math.PI), immediateReturn);
	}



    /**
     * arrête le mouvement du robot
     */
    public void stop(){
        pilot.stop();
    }

    
    /**
     * permet de savoir si le robot est en deplacement
     * @return true si le robot est 
     * en cours de déplacement
     */
    public boolean estEnDeplacement(){
        return pilot.isMoving();
    }
    
    
  	/**
  	 * avance le robot d'une distance donnée
  	 * @param dis la distance à parcourir
  	 */
  	public void avancer(double dis) {
  		this.pilot.travel(dis);
  	}
  	
  
  	/**
  	 * avance le robot d'une distance donnée, en synchronisant
  	 * @param dis la distance donnée
  	 * @param b true si la méthode travel doit return immédiatement
  	 */
  	public void avancer(double dis, boolean b) {
  		this.pilot.travel(dis, b);
  	}
  	
  	
  	/**
  	 * recule le robot d'une distance donnée
  	 * @param dis distance donnée
  	 */
  	public void reculer(double dis) {
  		this.pilot.travel(dis, true);
  	}
  	
  	
  	/**
  	 * tourne le robot d'un angle donné
  	 * @param ang l'angle donnée
  	 */
  	public void tourne(double ang) {
  		this.pilot.rotate(ang, true);
  	}

}
