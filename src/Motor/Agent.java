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
	private final String[] position;
	private final Distance d = new Distance();
	//private final boolean[] sample;
	
	public Agent() {
		 position = getPositionDepart();
		public Agent() {
		a.setVitesseAngle(800);
		a.setVitesseLigne(800);
		a.setAccelLigne(700);
		a.setAccelAngle(700);
	
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

		double distanceOptimal = 240;
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
			dist[0] = d.getValue();
			Delay.msDelay(50);
			c++;
		}
		for (int j = 0; j < c; j++) { // récuperer le plus proche
			if (dist[j] > 30 && dist[j] < distanceOptimal) {
				distanceOptimal = dist[j];
				}
		}
		return distanceOptimal;		
	}
	

	int findMySelf(String[] tabSampleRed) {
		int id = 0;
		boolean bis = true;
		if(position[1]=="Up")bis=false;
		String[] firstxy = tabSampleRed[tabSampleRed.length-1].split(";");
		String[] lastxy = tabSampleRed[tabSampleRed.length-1].split(";");
		int correctx = -Integer.parseInt(firstxy[0]);
		int correcty = -Integer.parseInt(firstxy[1]);
		int xpourcent = (1 -(Integer.parseInt(lastxy[0]) + correctx)/180) + 1;
		int ypourcent = (1 -(Integer.parseInt(lastxy[1]) + correcty)/200) + 1 ;
		for(int i=0;i<tabSampleRed.length;i++) {
			String[] xy = tabSampleRed[i].split(";");
			int x = (Integer.parseInt(xy[0])*xpourcent)/10;
			int y = (Integer.parseInt(xy[1])*ypourcent)/10;
			if(Math.round(x)%6==0 && Math.round(y)%5==0);
			else {
				if(bis)return i;
				bis = true;
			}
		}
		return id;
	}

	int findNearMe(String[] tabSampleRed,int i) {
		//if(position=="gauche")return tabSampleRed[i+1]
		//if(position=="droite")return tabSampleRed[i-1]
		return i;
	}

	public void  chercherInfra() {
		CameraInfrarouge cir = new CameraInfrarouge();
		Touch tc = new Touch();
		while(tc.getValue()==0.0) {
			try {
				String[] tabSampleRed = cir.getValue();
				int id = findMySelf(tabSampleRed);
				int ne = findNearMe(tabSampleRed,id);
				String[] coord = tabSampleRed[id].split(";");
				String[] direct = tabSampleRed[ne].split(";");
				int selfx = Integer.parseInt(coord[0]);
				int selfy = Integer.parseInt(coord[1]);
				int directionx = Integer.parseInt(direct[0]);
				int directiony = Integer.parseInt(direct[1]);
				a.avancer(10);
				tabSampleRed = cir.getValue();
				coord = tabSampleRed[id].split(";");
				directionx -= Integer.parseInt(coord[0]);
				directiony -= Integer.parseInt(coord[1]);
				selfx = Integer.parseInt(coord[0])-selfx;
				selfy = Integer.parseInt(coord[1])-selfy;
				directionx = Math.abs(directionx);
				directiony = Math.abs(directiony);
				selfy= Math.abs(selfy);
				selfx= Math.abs(selfx);
				double vectself = Math.sqrt(selfx * selfx + selfy * selfy);
				double vectdirect = Math.sqrt(directionx * directionx + directiony * directiony);//à utiliser pour arcforward(angle,distance);
				double scalaire = directionx * selfx + directiony * selfy;
				double angle = Math.acos(scalaire / (vectself * vectdirect));
				angle = Math.toDegrees(angle);
				if (selfy > directiony && position[1]=="Down") {
					a.traverseArc(-angle, vectdirect);
				} else if (directiony > selfy && position[1]=="Down") {
					a.traverseArc(angle,vectdirect);
				}else if(selfy > directiony && position[1]=="Up") {
					a.traverseArc(angle, vectdirect);
				}else if(selfy < directiony && position[1]=="Up") {
					a.traverseArc(-angle, vectdirect);
				}
				a.avancer(10);
				System.out.println(angle);
				tc.getValue();
			} catch (Exception e) {
				continue;
			}
		}

	}
	
	public void orienteVersPalet(int angle) {
		double dist  = cherchePalet(angle);
		a.tourne(-angle-90);
		if(d.getValue() == dist) {
			a.stop();
			//Delay.msDelay(0); // mettre des vitesse plus lentes
		}
		
		//a.tourne(cherchePalet());
		a.avancer(dist*10);
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
	
	
	public String[] getPositionDepart() {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
		
		g.drawString("Ligne depart? gauche | milieu | droite")
		
		int but = Button.waitForAnyPress(TITLE_DELAY);
		String[] pressed = new String[2];
		if (but == 0)
                pressed[0] = "None";
		else if ((but & Button.ID_ENTER) != 0)
				pressed[0] += "Milieu";
          	else if ((but & Button.ID_LEFT) != 0)
				pressed[0] += "Gauche";
    		else if ((but & Button.ID_RIGHT) != 0)
				pressed[0] += "Droit";
		g.clear();
		
		g.drawString("Coté ? le bas -> x = 0 pour capteur IR");
		but = Button.waitForAnyPress(TITLE_DELAY);
		if (but == 0)
			pressed[1] = "None";
		else if ((but & Button.ID_UP) != 0)
        	pressed[1] = "Down";
		else if ((but & Button.ID_DOWN) != 0)
        	pressed[1] = "Up";

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
		//ag.prendPremierPalet();
		ag.recuperePalet();
	}

}
