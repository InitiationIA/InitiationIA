package Motor;

public interface OuvrableContinue {

    public final static int ANGLE_OUVERTURE_TT = 90;
    public final static int ANGLE_FERMETURE_TT = 0;
    public final static int ANGLE_OUVERT_AVEC_PALET = 10; // lorsque les pinces ont saisie le palet
    public final static int ANGLE_OUVERT_SANS_PALET = 45; // un angle intéressant pour relacher le palet

    /**
     * 
     * @param angle
     */
    public void ouvrir(int angle);

    /**
     * 
     * @param angle
     */
    public void fermer(int angle);

}
