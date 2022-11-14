package Motor;

import java.util.Scanner;

import org.jfree.ui.tabbedui.TabbedApplet;
import org.jfree.util.Rotation;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;

public class Agent {
	
	private final double vitesseLin = 200.0;
	private final double accLin = 50.0;
	private final double vitesseAng = 20.0;
	private final double accAng = 10.0;
	private final Pince p = new Pince("A");
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
		
		a.setVitesseAngle(a.getMaxVitesseAngle());
		a.setVitesseLigne(a.getMaxVitesseLigne());
		a.setAccelLigne(a.getMaxVitesseLigne());
		a.setAccelAngle(a.getMaxVitesseAngle());
		
		String position = getPositionDepart();
		double pivot = 20;
		
		if (position.equals("Gauche"))
			pivot = -20;
		else if (position.equals("Milieu")) 
			pivot = 200;
		
		p.ouvrir(360*4);
		a.avancer(600); // mm
		p.fermer(-360*5);
		a.traverseArc(600, pivot);
		a.traverseArc(-400, pivot);
		a.avancer(Double.POSITIVE_INFINITY, true); // non bloquante -->ferme le palet et avance en même temps
		while (true) {
			//c.cs.fetchSample(c.sampleColor, 0);
			//float[] tab = c.getValue();
			
			//System.out.println("LALA");
			if (/*(tab[0]*100 < 15 && tab[1]*100 < 13 && tab[2]*100 < 7) || */ (d.getValue()*100 < 35)) {
				
				//System.out.println(tab[0]*100 +" "+ tab[1]*100 + " " + tab[2]*100);
				a.stop();
				//this.arret();
				break;
			}
		}
		//a.stop();
		p.ouvrir(360*4);
		a.avancer(-100, true); 
		p.fermer(-360*4);
		while (p.isMoving()) {
			
		}
		a.stop();
		a.tourne(180);
		
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
		a.reculer(100);
		a.tourne(90);
		recuperePalet();
	}
	
	// Sonar: trouver l'angle optimal (=le palet le plus proche)
		public double cherchePalet(int angle) {
		
		double distanceOptimal = 0;
		//double angle =30;
		double[] dist = new double[60];
		float distance = 240;
		int c = 0;
				
		/*int i = 0;
		while (angle!=330) {
			
			a.tourne(angle);
			tab[i] = d.getValue();
			angle+=60;
			i++;
		}
		a.tourne(30);
		tab[5] = d.getValue(); 
		
		double[] tabAngles = {30, 90, 150, 210, 270, 330};*/
		
		a.tourne(angle);
		while(a.estEnDeplacement()) { // récuperer les mesures
			dist[c] = d.getValue();
			c++;
		}
		for (int j = 0; j < c; j++) { // récuperer le plus proche
			if (dist[j] > 30 && dist[j] < distance) {
				distanceOptimal = dist[j];
				}
		}
		return distanceOptimal;		
	}
	
	
	public void orienteVersPalet(int angle) {
		double dist  = cherchePalet(angle);
		a.tourne(-angle);
		if(d.getValue() == dist) {
			a.stop();
		}
		//a.tourne(cherchePalet());
		a.avancer(Double.POSITIVE_INFINITY);
	}
	public void recuperePalet() {
		orienteVersPalet(180);
		if (d.getValue() < 30) {
			a.stop();
			a.tourne(90);
			a.avancer(100);
			orienteVersPalet(360);
			}
		if (t.getValue() == 1) {
			a.stop();
			p.fermerSurPalet();
		}
	}
	
	
	public String getPositionDepart() {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();

		g.drawString("Quelle est la position de depart?\n Bouton Gauche pour ligne Gauche\n Bouton Milieu pour ligne Milieu\nBouton Droit pour ligne Droit ",0,0,0); // valeur provisoire

		int but = Button.waitForAnyPress(1000000);
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
	
	public void testPince() {
		p.ouvrir(360*4); // ok
		p.fermer(-360*5); // ok
	}
	
	public static void main(String[] args) {
		//a.setVitesseLigne(a.getMaxVitesseLigne());
		Agent ag = new Agent();
		//ag.testPince();
		ag.prendPremierPalet();
	}

}
