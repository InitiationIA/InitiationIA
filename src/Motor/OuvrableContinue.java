public interface OuvrableContinue {
    
    public final static double angleFermertureTT = 0.0;
    public final static double angleOuvertureTT = 90.0;
    public final static double angleFermerAvecPalet = 10.0; // lorsque les pinces ont saisie le palet
    public final static double angleOuvertureRelachePalet = 45.0; // un angle intéressant pour relacher le palet

    default public void ouvrir() {
        ouvrir(angleFermertureTT);
    }

    default public void fermer() {
        fermer(angleOuvertureTT);
    }
    public void ouvrir(double angle);
    public void fermer(double angle);
    
}   
