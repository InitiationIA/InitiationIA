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
		String position = getPositionDepart();
		double d = 20;
		
		if (position.equals("Gauche"))
			d = -20;
		else if (position.equals("Milieu")) 
			d = 200;
		
		System.out.println(position);	
		p.ouvrir(360*4);
		a.avancer(600); // mm
		p.fermer(-360*4);
		a.traverseArc(600, d);
		a.traverseArc(-400, d);
		
		// + avancer vers l'enbut (30 cm par rapport au mur)
		
		
		// si touche
		if (t.getValue() == 1) { // ça marche pas 
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
		
		double distanceOptimal = 0;
		//double angle =30;
		float[] dist = new double[60];
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
		
		a.tourne(180);
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
	
	
	public void orienteVersPalet() {
		float dist  = a.cherchePalet();
		a.tourne(180);
		if(d.getValue() == dist) {
			a.stop();
		}
		//a.tourne(cherchePalet());
		a.avancer(Double.POSITIVE_INFINITY);
	}
	public void recuperePalet() {
		if (d.getValue() < 30)
			return;
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
		Agent ag = new Agent();
		//ag.testPince();
		ag.prendPremierPalet();
	}

}
