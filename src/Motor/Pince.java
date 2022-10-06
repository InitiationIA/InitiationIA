package Action;

import lejos.hardware.*;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;



public class Pince extends EV3MediumRegulatedMotor implements OuvrableContinue {
    
    private boolean estOuvert; // true si pince ouvert
    private boolean paletEstAttraper; // true si les pince attrape un palet


    //private final static Port PORT_PINCE = ;
    /**
     * TODO 1: trouver le bon type de du port pour les pinces
     * TODO 2 : trouver les méthode permetant de faire bouger les pinces
     * rotation ? avance ? 
     * @param p
     */

     /**
      * 
      * @param nomPort nom du port moteur ("A","B","C","D")
      */
    public Pince(String nomPort) {
        super(BrickFinder.getDefault().getPort(nomPort));
        paletEstAttraper = false;
        fermer();
    }

    /**
     * 
     * @param angle
     */
    public void ouvrir(double angle) {
        estOuvert = true;
    }

    /**
     * 
     * @param angle
     */
    public void fermer(double angle) {
        estOuvert = false;
    }
    
    public void ouvrirSurPalet() {
        fermer(angleOuvertureRelachePalet);
        paletEstAttraper = false;
    }

    public void fermerSurPalet() {
        fermer(angleFermerAvecPalet);
        paletEstAttraper = true;
    }

    /**
     * 
     * @return estOuvert bool
     */
    public boolean getEstOuvert() {
        return estOuvert;
    }

    public boolean getPaletEstAttraper() {
        return paletEstAttraper;
    }
}



