package Motor;

/*
 *  importation des packages nécessaires pour la définition de l'actionneur :
 * 
 *  lejos.hardware.BrickFinder : pour implémenter un serveur de noms qui peut 
 *  répondre aux requêtes générées par les méthodes de découverte et de recherche. 
 *  Le code utilisateur n'aura normalement pas besoin d'exécuter
 *  une instance de ceci car le menu leJOS le fera généralement.
 * 
 *  lejos.hardware.motor.* : pour accéder aux moteurs pris en charge par l'EV3.
 *  
 *  lejos.robotics.chassis.* : pour la modélisation de véhicules à roues
 *  
 *  lejos.robotics.navigation.MovePilot : contient des méthodes pour contrôler les mouvements du robot : 
 *  se déplacer vers l'avant ou vers l'arrière en ligne droite ou
 *  sur une trajectoire circulaire ou pivoter dans une nouvelle direction. 
 *  Cette classe fonctionnera avec n'importe quel châssis. 
 *  Certains types de châssis peuvent ne pas prendre en charge 
 *  tous les mouvements pris en charge par ce pilote. Un objet de cette classe suppose 
 *  qu'il a le contrôle exclusif de ses moteurs. Si un autre objet fait des appels à ses moteurs, 
 *  les résultats sont imprévisibles.
 */
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.*;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;


/*
 * Classe définissant l'actionneur:
 * comprend deux roues, un chassis et un mécanisme de pilotage
 */
public class Actionneur {
    
    
    protected final Wheel wheel1 ;
    protected final Wheel wheel2 ;
    private final Chassis chassis ;
    private final MovePilot pilot ;
    

    /*
     * Constructeur de l'actionneur
     */
    public Actionneur() {
    	
        wheel1 = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("B")), 56).offset(-80);
        wheel2 = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("C")), 56).offset(80);
        chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        
    }

    public Actionneur(double vitesseLin, double accelerationLin) {
        this();
        pilot.setLinearSpeed(vitesseLin);
        pilot.setLinearAcceleration(accelerationLin);

    }

    public Actionneur(double vitesseLin, double accLin, double vitesseAng, double accAng) {
        this();
        pilot.setLinearSpeed(vitesseLin);
        pilot.setLinearAcceleration(accLin);
        pilot.setAngularSpeed(vitesseAng);
        pilot.setAngularAcceleration(accAng);
    }

    
    /*
     * retourne la valeur du tachomètre (en degré)
     * de la roue gauche
     */
    public double getTachometer() {
    	return wheel2.getMotor().getTachoCount();
    }
    
	/*
	 * retourne la valeur de l'accélération linéaire
	 */
	public double getAccelLigne() {
	    return this.pilot.getLinearAcceleration();
	}
	
	/*
	 * modifie l'accélération linéaire 
	 */
	public void setAccelLigne(double acceleration) {
	    this.pilot.setLinearAcceleration(acceleration);
	}

	/*
	 * retourne l'accélération angulaire
	 */
	public double getAccelAngle() {
	    return this.pilot.getAngularAcceleration();
	}
	
	/*
	 * modifie l'accélaration linéaire
	 */
	public void setAccelAngle(double acceleration) {
	    this.pilot.setAngularAcceleration(acceleration);
	}

	/*
	 * retourne la vitesse linéaire
	 */
	public double getVitesseLigne() {
	    return this.pilot.getLinearSpeed();
	}  

	/*
	 * modifie la vitesse linéaire
	 */
	public void setVitesseLigne(double speed) {
	    this.pilot.setLinearSpeed(speed);
	}

	/*
	 * retourne la vitesse linéaire maximale
	 */
	public double getMaxVitesseLigne() {
	    return chassis.getMaxLinearSpeed();
	}  

	/*
	 * retourne la vitesse angulaire
	 */
	public double getVitesseAngle() {
	    return this.pilot.getAngularSpeed();
	}
	  
	/*
	 * modifie la vitesse angulaire
	 */
	public void setVitesseAngle(double speed) {
	    this.pilot.setAngularSpeed(speed);
	}
	
	/*
	 * retourne la vitesse angulaire maximale
	 */
	public double getMaxVitesseAngle() {
	    return chassis.getMaxAngularSpeed();
	}  
	  
	
	/*
	 * modifie l'angle pendant que le robot avance en arc
	 */
	public void arcAvant(double radius) {
	    this.pilot.arcForward(radius);
	}

	/*
	 * modifie l'angle pendant que le robot recule en arc
	 */
	public void arcArriere(double radius) {
		this.pilot.arcBackward(radius);
	}

	/*
	 * avance en arc selon un gradient et un angle donné 
	 */
	public void arcSimple(double radius, double angle) {
		this.pilot.arc(radius, angle);
	}
	
	/*
	 * traverse en arc selon un gradient et une distance
	 */
	public void traverseArc(double radius, double distance) {
		this.pilot.travelArc(radius, distance, false);
	}

	/*
	 * traverse en arc selon un gradient, une distance, en synchronisant
	 */
	public void traverseArc(double radius, double distance, boolean immediateReturn) {
		this.pilot.arc(radius,  distance / (2 * Math.PI), immediateReturn);
	}



	/*
	 * arrête le mouvement du robot
	 */
    public void stop(){
        pilot.stop();
    }

    /*
     * retourne true si le robot est 
     * en cours de déplacement
     */
    public boolean estEnDeplacement(){
        return pilot.isMoving();
    }
    
    /*
     * avance le robot d'une distance donnée
     */
  	public void avancer(double dis) {
  		this.pilot.travel(dis);
  	}
  	
  	/*
  	 * avance le robot d'une distance donnée, en synchronisant
  	 */
  	public void avancer(double dis, boolean b) {
  		this.pilot.travel(dis, b);
  	}
  	
  	/*
  	 * recule le robot d'une distance donnée
  	 */
  	public void reculer(double dis) {
  		this.pilot.travel(dis, true);
  	}
  	
  	/*
  	 * tourne le robot d'un angle donné
  	 */
  	public void tourne(double ang) {
  		this.pilot.rotate(ang, true);
  	}

}
