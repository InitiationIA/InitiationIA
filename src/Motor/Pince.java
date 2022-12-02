package Motor;

import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

/**
 * classe  décrivant le pilotage des pinces.
 * À partir de la classe motrice
 * @see EV3MediumRegulatedMotor
 */
public class Pince extends EV3MediumRegulatedMotor implements Ouvrable {

    private boolean estOuvert; // true si pinces sont ouvertes
    private boolean paletEstAttraper; // true si les pince attrape un palet

    private int angleOuverture; // 0 à l'initialisation du robot

    /**
     * Initialise les attributs de la classe pince
     * @param nomPort nom du port moteur ("A","B","C","D")
     */
    public Pince(String nomPort) {
        super(BrickFinder.getDefault().getPort(nomPort));
        paletEstAttraper = false;
        angleOuverture = 0;
    }

    /**
     * décrit l'ouverture des pinces selon <code>ANGLE_OUVERTURE_SANS_PALET</code>
     * @deprecated
     */
    public void ouvrir() {
        ouvrir(ANGLE_OUVERTURE_SANS_PALET);
    }

    @Override
    public void ouvrir(int angle) {
        estOuvert = true;
        super.rotate(angle);
        angleOuverture += angle;
    }

    /**
     * décrit la fermeture des pinces
     * @param angle entier en degrees
     */
    public void fermer(int angle){
        ouvrir(-Math.abs(angle));
    }

    /**
     * ouverture sur le palet
     * @deprecated
     */
    public void ouvrirSurPalet() {
        ouvrir(ANGLE_OUVERTURE_SANS_PALET);
        paletEstAttraper = false;
    }

    /**
     * ferme les pinces à l'angle d'origine durant l'initialisation du robot
     */
    public void fermerSurPalet() {
        ouvrir(-angleOuverture);
        paletEstAttraper = true;
        angleOuverture = 0;
    }

    /**
     * vérifie si les pinces sont ouvertes
     * @return true si les pinces sont ouvertes
     */
    public boolean getEstOuvert() {
        return estOuvert;
    }

    /**
     * vérifie si le robot possède un palet entre ses pinces
     * @return true si palet attraper
     * @deprecated
     */
    public boolean getPaletEstAttraper() {
        return paletEstAttraper;
    }
}




