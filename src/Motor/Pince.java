package Motor;

import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class Pince extends EV3MediumRegulatedMotor implements OuvrableContinue {

    private boolean estOuvert; // true si pince ouvert
    private boolean paletEstAttraper; // true si les pince attrape un palet

    private int angleOuverture;

    /**
     * 
     * @param nomPort nom du port moteur ("A","B","C","D")
     */
    public Pince(String nomPort) {
        super(BrickFinder.getDefault().getPort(nomPort));
        paletEstAttraper = false;
        angleOuverture = 0;
    }

    /**
     * 
     */
    public void ouvrir() {
        ouvrir(ANGLE_OUVERTUVERTURE_SANS_PALET);
    }

    @Override
    public void ouvrir(int angle) {
        estOuvert = true;
        super.rotate(angle);
        angleOuverture += angle;
    }

    /**
     * 
     * 
     */

    public void fermer(int angle){
        ouvrir(-Math.abs(angle));
    }

    /**
     * 
     * 
     */
    public void ouvrirSurPalet() {
        ouvrir(ANGLE_OUVERTUVERTURE_SANS_PALET);
        paletEstAttraper = false;
    }

    /**
     * 
     * 
     */
    public void fermerSurPalet() {
        ouvrir(-angleOuverture);
        paletEstAttraper = true;
        angleOuverture = 0;
    }

    /**
     * 
     * @return estOuvert bool
     */
    public boolean getEstOuvert() {
        return estOuvert;
    }

    /**
     * 
     * @return true si palet attraper
     */
    public boolean getPaletEstAttraper() {
        return paletEstAttraper;
    }
}




