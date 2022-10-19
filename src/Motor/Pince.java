import lejos.hardware.*;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;

public class Pince extends EV3MediumRegulatedMotor implements OuvrableContinue {

    private boolean estOuvert; // true si pince ouvert
    private boolean paletEstAttraper; // true si les pince attrape un palet

    /**
     * 
     * @param nomPort nom du port moteur ("A","B","C","D")
     */
    public Pince(String nomPort) {
        super(BrickFinder.getDefault().getPort(nomPort));
        paletEstAttraper = false;
        fermer();
        super.rotateTo(ANGLE_OUVERTURE_TT);
    }

    /**
     * 
     */
    public void ouvrir() {
        ouvrir(ANGLE_OUVERTURE_TT);
    }

    @Override
    public void ouvrir(int angle) {
        estOuvert = true;
        super.rotate(angle);

    }

    /**
     * 
     * 
     */
    public void fermer() {
        fermer(ANGLE_FERMETURE_TT);
    }

    @Override
    public void fermer(int angle) {
        super.rotate(angle);
        estOuvert = false;
    }

    /**
     * 
     * 
     */
    public void ouvrirSurPalet() {
        fermer(ANGLE_OUVERT_SANS_PALET);
        paletEstAttraper = false;
    }

    /**
     * 
     * 
     */
    public void fermerSurPalet() {
        fermer(ANGLE_OUVERT_AVEC_PALET);
        paletEstAttraper = true;
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




