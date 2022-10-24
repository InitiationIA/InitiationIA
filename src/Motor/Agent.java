package Action;

import java.util.Scanner;

import org.jfree.ui.tabbedui.TabbedApplet;
import org.jfree.util.Rotation;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;

public class Agent {
	
	private final double vitesseLin = 60.0;
	private final double accLin = 20.0;
	private final double vitesseAng = 20.0;
	private final double accAng = 10.0;
	private final Pince p = new Pince("Nom_Port");
	private final Actionneur a = new Actionneur(vitesseLin, accLin, vitesseAng, accAng);
	private final Touch t =  new Touch();
	private final String[] position = {"gauche", "milieu", "droite"};
	private final Distance d = new Distance();
	//private final boolean[] sample;
	
	public Agent() {
		
	}
	/*
	
	public displayQuestion() {
		Scanner s = new Scanner(System.in);
		System.out.println("De quel coté du terrain êtes-vous ?: ");
	}
	*/
	
	// spécifie la position de départ: gauche, milieu, droite.
	public void specifiePosition(String pos) {
		
		
	}
	
	
	public void prendPremierPalet() {
		String position = getPositionDepart();
		double d = 20;
		
		if (position.equals("Gauche"))
			d = -20;
		else if (position.equals("Milieu")) 
			d = -20;
		
			
		p.ouvrir(60);
		a.avancer(vitesseLin);
		// si touche
		if (t.getValue() == 1) {
			p.fermerSurPalet();				
			a.traverseArc(d, 180);
	
			//si enbut
			avanceVersEnbut();
			
		}
		
	}
	
	public void orienteVersEnbut(double angleDepart) {
		
	    a.tourne(180 - angleDepart);
		
	}

	public void avanceVersEnbut(){
		Color c = new Color();
		
		// tant qu'on n'a pas la couleur blanche
		
		
		while (true) {
			c.cs.fetchSample(c.sampleColor, 0);
			float[] tab = c.getValue();
			if (tab[0]>=240 && tab[1]>=240 && tab[2]>=240) {
				a.stop();
				this.arret();
			}
		}	
	}
	
	
	public void arret() {
		p.ouvrir(50);
		a.reculer(10);
		a.tourne(180);
	}
	
	// Sonar: trouver l'angle optimal (=le palet le plus proche)
	public double cherchePalet() {
		
		double angleOptimal = 0;
		double angle =30;
		double[] tab = new double[6];
		
		while (a.estEnDeplacement()) {
			//je prends les mesures
		}
		
		
		int i = 0;
		while (angle!=330) {
			
			a.tourne(angle);
			tab[i] = d.getDistance();
			angle+=60;
			i++;
		}
		a.tourne(30);
		tab[5] = d.getDistance(); 
		
		double[] tabAngles = {30, 90, 150, 210, 270, 330};
		double distance = 240;
		for (int j = 0; j < tab.length; j++) {
			if (tab[j] > 30 && tab[j] < distance) {
				distance = tab[j];
				angleOptimal = tabAngles[j];
				
			}
				
		}
		return angleOptimal;
		
	}
	
	
	public void orienteVersPalet() {
		a.tourne(cherchePalet());
		a.avancer(Double.POSITIVE_INFINITY);
	}
	
	public void recuperePalet() {
		if (d.getDistance() < 30)
			return;
		if (t.getValue() == 1) {
			a.stop();
			p.fermerSurPalet();
		}
	}
	
	
	public String getPositionDepart() {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();

		g.drawString("Quelle est la position de depart?\n Bouton Gauche pour ligne Gauche\n Bouton Milieu pour ligne Milieu\nBouton Droit pour ligne Droit ",0,0,0); // valeur provisoire

		int but = Button.waitForAnyPress(10000);
		String pressed = "";
		if (but == 0)
			pressed = "None";
		else if ((but & Button.ID_ENTER) != 0)
			pressed += "Milieu";
		else if ((but & Button.ID_LEFT) != 0)
			pressed += "Gauche";
		else if ((but & Button.ID_RIGHT) != 0)
			pressed += "Droit";
		g.clear();
		return pressed;
	}
	
	public static void main(String[] args) {
		Agent ag = new Agent();
		ag.prendPremierPalet();
	}

}
