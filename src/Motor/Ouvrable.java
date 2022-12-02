package Motor;

/**
 *  définit la méthode abstraite d'un ouvrable
 */
public interface Ouvrable {
    /**
     * un angle intéressant pour attraper le palet
     */ 
    final static int ANGLE_OUVERTURE_SANS_PALET = 360*4;
    /**
     * décrit l'ouverture des pinces du robot
     * ouvre les pinces si <code> angle</code> est positif
     * ferme les pinces si <code> angle</code> est négatif
     * @param angle un entier en degrees
     */
    public void ouvrir(int angle);

}
