package Action;

import lejos.hardware.*;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;



public class Pince extends EV3MediumRegulatedMotor {
    
    private boolean estOuvert; // true si pince ouvert
    private final double angleFermertureTT = 0.0;
    private final double angleOuvertureTT = 90.0;
    private final double angleFermerAvecPalet = 10.0; // lorsque les pinces ont saisie le palet
    private final double angleOuvertureRelachePalet = 45.0; // un angle intéressant pour relacher le palet
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
    }

    public void ouvrir(double angle) {
        estOuvert = true;
    }


    public void fermer(double angle) {
        estOuvert = false;
    }

    /**
     * 
     * @return estOuvert bool
     */
    public boolean getEstOuvert() {
        return estOuvert;
    }

}


