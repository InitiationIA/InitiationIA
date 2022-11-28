package Motor;

import java.util.ArrayList;

import Sensor.CameraInfrarouge;
import Sensor.Color;
import Sensor.Distance;
import Sensor.Touch;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
public class Agent {

	private static final int TITLE_DELAY = 10000;
	private static final double vitesseLin = 200.0;
	private static final double accLin = 50.0;
	private static final double vitesseAng = 20.0;
	private static final double accAng = 10.0;
	private static final Pince p = new Pince("A");
	private static final Actionneur a = new Actionneur(vitesseLin, accLin, vitesseAng, accAng);
	private static final Touch t =  new Touch();
	
	private static final Distance d = new Distance();
	private final CameraInfrarouge cir = new CameraInfrarouge();
	private static final Color c = new Color();
	
	private final float xpourcent;
	private final float ypourcent;
	private final String[] position;
	//private final boolean[] sample;

	public Agent() {
		float[] constant = setConstant();
		position = getPositionDepart();
		xpourcent = constant[0];
		ypourcent = constant[1];
		
		
		a.wheel1.getMotor().getTachoCount();
		a.wheel2.getMotor().getTachoCount();
		// ajuster la puissance du moteur 
		//this.setVitesseRoue(true, -50);
	}
	


	public void prendPremierPalet() {
		a.tourne(-5);
		a.setVitesseAngle(a.getMaxVitesseAngle());
		a.setVitesseLigne(a.getMaxVitesseLigne());
		a.setAccelLigne(a.getMaxVitesseLigne());
		a.setAccelAngle(a.getMaxVitesseAngle());
		p.setSpeed(p.getMaxSpeed());
		
		System.out.println(position[0]);
		boolean cote = true;
		double angle = -15;
		double distance = 2000;
		double angle2 = 153;
		
		
		
		p.ouvrir(450);
		a.avancer(630);
		p.fermer(-375);
		if (position[0].equals("Gauche")) {
			System.out.println("gauche");
		    cote = false;
		    a.tourne(12);
		    a.avancer(1100);
		    angle = -33;
		    distance = 1050;
		    angle2 = 160;
		}
		a.tourne(angle);
		
		a.avancer(distance);
		
		a.stop();
		p.ouvrir(540);
		a.avancer(-100); 
		a.stop();
		a.tourne(angle2);
		prendPalet2(cote);
	}
	public void prendPalet2(boolean cote) {
		a.avancer(450);
		p.fermer(-540);
		a.tourne(140);
		a.avancer(450);
		p.ouvrir(540);
		a.reculer(-100);
		a.tourne(130);
		prendPalet3(cote);

			
		
	}
	
	public void prendPalet3(boolean cote){
		a.avancer(1100);
		p.fermer(-540);
		a.tourne(140);
		a.avancer(1200);
		p.ouvrir(540);
		a.reculer(-100);
		if (cote == false) { a.tourne(270);}
		else a.tourne(50);
		cherchePalet(180);
	}


	/*
	 * reset les angles des tachomètres des deux roues
	 */
	public void resetTachometre() {
		a.wheel1.getMotor().resetTachoCount();
		a.wheel2.getMotor().resetTachoCount();
		
	}
	
	/*
	 * si true alors retourne l'angle de tachomèrre de la roue droite
	 * sinon celui de gauche
	 */
	public int getTachometre(boolean b) {
		if (b)
			return a.wheel1.getMotor().getTachoCount();
		
		return a.wheel2.getMotor().getTachoCount(); 
		
	}
	/*
	 * retourne la différence des angles des tachomètres 
	 * des roues droite et gauche 
	 */
	public int diffAngleTacho() {
		int a = getTachometre(true);
		int b = getTachometre(false);
		return a - b;
	}
	
	/* 
	 * si true, alors modifie la vitesse de la roue droite
	 * sinon celle de gauche
	 */
	public void setVitesseRoue(boolean b) { 
		if (b) a.wheel1.getMotor().setSpeed(a.wheel2.getMotor().getSpeed());
		
		a.wheel2.getMotor().setSpeed(a.wheel1.getMotor().getSpeed());
	}
	
	public void orienteVersEnbut(double angleDepart) {

		a.tourne(180 - angleDepart);

	}

	public void avanceVersEnbut(){
	
		// tant qu'on n'a pas la couleur blanche


		while (true) {
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
		double dist =0;
		a.tourne(angle);
		while(a.estEnDeplacement()) { // récuperer les mesures
			dist = d.getValue();
			if(dist  >= 2.55){
				while(true){
					dist = d.getValue();
					if(dist < 2.40 && dist> 0.3) {
						a.stop();
						return dist;
					}
				}
			}
		}
		return dist;
	}



	int findMySelf(String[] tabSampleRed,String[] tabSampleRed2,int[] pos) {
		if(pos[1]==0)return pos[0];
		boolean bis = true;
		if(position[1].equals("Up"))
			bis=false;
		int x1 = Integer.parseInt((tabSampleRed[pos[0]].split(";"))[1]);
		int y1 = Integer.parseInt((tabSampleRed[pos[0]].split(";"))[2]);
		int x2 = Integer.parseInt((tabSampleRed[pos[1]].split(";"))[1]);
		int y2 = Integer.parseInt((tabSampleRed[pos[1]].split(";"))[2]);
		int x11 = Integer.parseInt((tabSampleRed2[pos[0]].split(";"))[1]);
		int y11 = Integer.parseInt((tabSampleRed2[pos[0]].split(";"))[2]);
		int x21 = Integer.parseInt((tabSampleRed2[pos[1]].split(";"))[1]);
		int y21 = Integer.parseInt((tabSampleRed2[pos[1]].split(";"))[2]);
		double vect1 = Math.sqrt(Math.round(x1-x11)*Math.round(x1-x11)+Math.round(y1-y11)*Math.round(y1-y11));
		double vect2 = Math.sqrt(Math.round(x1-x21)*Math.round(x1-x21)+Math.round(y1-y21)*Math.round(y1-y21));
		double vect3 = Math.sqrt(Math.round(x2-x11)*Math.round(x2-x11)+Math.round(y2-y11)*Math.round(y2-y11));
		double vect4 = Math.sqrt(Math.round(x2-x21)*Math.round(x2-x21)+Math.round(y2-y21)*Math.round(y2-y21));
		if(bis) {
			if(vect1<vect2)return pos[0];
			position[1]="Up";
			return pos[1];
		}else {
			if(vect3 > vect4) return pos[1];
			position[1]="Down";
			return pos[0];

		}

	}

	int[] object(String[] first,String[] second) {
		boolean f;
		int a=0;
		int b=0;
		ArrayList<Boolean> count = new ArrayList<Boolean>();
		for(int i=0;i<first.length;i++) {
			f = true;
			String[] minus= first[i].split(";");
			int selfx= (int) (Integer.parseInt(minus[1])*xpourcent);
			int selfy = (int) (Integer.parseInt(minus[2])*ypourcent);
			for(int k=0;k<second.length;k++) {
				String[] max= second[k].split(";");
				int selx= (int) (Integer.parseInt(max[1])*xpourcent);
				int sely = (int) (Integer.parseInt(max[2])*ypourcent);
				if(selfx==selx && sely==selfy) {
					f = false;
				}
			}
			count.add(f);
		}
		for(int q=0;q<count.size();q++) {
			if(count.get(q)&& a!=0 && b==0)b=q;
			if(count.get(q)&& a==0)a=q;
		}
		return new int[] {a,b};
	}

	int findNearMe(String[] tabSampleRed,int i) {
		if(position[1].equals("Down"))i++;
		if(position[1.equals("Up"))i--;
		return i;
	}

	public void  chercherInfra() {
		String[] first;
		String[] second;
		int[] pos;
		p.fermerSurPalet();
		while(t.getValue()==0.0) {
			try {
				first = cir.getValue();
				a.avancer(100);
				second = cir.getValue();
				pos = object(first,second);
				int id = findMySelf(first,second,pos);
				int ne = findNearMe(first,id);
				String[] coord = first[id].split(";");
				String[] direct = first[ne].split(";");
				int selfx = Integer.parseInt(coord[1]);
				int selfy = Integer.parseInt(coord[2]);
				int directionx = Integer.parseInt(direct[1]);
				int directiony = Integer.parseInt(direct[2]);

				coord = second[id].split(";");
				directionx -= Integer.parseInt(coord[1]);
				directiony -= Integer.parseInt(coord[2]);
				selfx = Integer.parseInt(coord[1])-selfx;
				selfy = Integer.parseInt(coord[2])-selfy;
				directionx = Math.abs(directionx);
				directiony = Math.abs(directiony);
				selfy= Math.abs(selfy);
				selfx= Math.abs(selfx);
				double vectself = Math.sqrt(selfx * selfx + selfy * selfy);
				double vectdirect = Math.sqrt(directionx * directionx + directiony * directiony);//à utiliser pour arcforward(angle,distance);
				double scalaire = directionx * selfx + directiony * selfy;
				double angle = Math.acos(scalaire / (vectself * vectdirect));
				angle = Math.toDegrees(angle);
				//angle=180-angle;
				if (selfy > directiony && position[1].equals("Down")) {
					a.traverseArc(-angle, vectdirect*10);
					angle=-angle;
				} else if (directiony > selfy && position[1].equals("Down")) {
					a.traverseArc(angle,vectdirect*10);
				}else if(selfy > directiony && position[1].equals("Up")) {
					a.traverseArc(angle, vectdirect*10);
				}else if(selfy < directiony && position[1].equals("Up")) {
					a.traverseArc(-angle, vectdirect*10);
					angle=-angle;
				}
				a.avancer(10);
				System.out.println(angle +":"+vectdirect );
				t.getValue();
			} catch (Exception e) {
				continue;
			}
		}
		p.fermerSurPalet();
	}

	public float[] setConstant() {
		String[] tabSampleRed;
		try {
			tabSampleRed = cir.getValue();
			String[] firstxy = tabSampleRed[0].split(";");
			String[] lastxy = tabSampleRed[tabSampleRed.length - 1].split(";");
			int correctx = 0 - Integer.parseInt(firstxy[1]);
			int correcty = 0 - Integer.parseInt(firstxy[2]);
			int xpourcent = (1/(Integer.parseInt(lastxy[1]) + correctx) / 180)*100;
			int ypourcent = (1/(Integer.parseInt(lastxy[2]) + correcty) / 200)*100;
			return new float[] {((float)xpourcent)/100,((float)ypourcent)/100};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new float[] {0,0};
	}
//debut
	public void cherchePalet(int angleMax) {// Sonar: trouver l'angle optimal (=le palet le plus proche)
		p.ouvrir(1620);
        double dist = 0;
        System.out.println("dans chercher palet");
       
        anglett = 0;
        while(Math.abs((anglett%360))<Math.abs(angleMax-1)) { // récuperer les mesures : -1 car (anglett%360) ne va pas à 360 donc 359
            dist = d.getValue();
            System.out.println(dist);
            if(dist>= 0.30 && dist<=1.2) {//à modif le 1.2
                        a.stop();
                        orienteVersPalet(dist);
                        System.out.println("Un palet");
                        orienteVersPalet(dist);
                }
            a.tourne(-10);
            anglett += -10;
            System.out.println("pas de palets");
            }
        orienteVersPalet(dist);
}
	
	public void orienteVersPalet(double dist1) {
		
        double dist  = dist1;
        System.out.println("Dans chercher palet");
       /* if(dist1 == 0.0) {
        	System.out.println("===================");
        	if(d.getValue() < 0.30){//attention au mur
                a.tourne(-90);
                anglett +=-90;      
        	}*/
        a.avancer(300);
      
        recuperePalet(360);
        return;
        }
        //a.tourne(dist1);
       // recuperePalet(dist);
        //return dist;
    
    public void recuperePalet(double dist1) {
        double dist = dist1;
        double di = 0;
        double distPrecedente = 0;
        //di + d.getValue() = dist;
       /*while (t.getValue() == 0 ) {while (d.getValue()<=dist) {while(true){
        	if(di>dist) {// on est sur le palet = si touch marche vraiment pas
        		a.stop();
                p.fermerSurPalet();//surement à changer par p.fermer(450);
                orienteVersEnbut();
                return;
        	}*/
        while(di<dist) {
        	/*if(distPrecedente<40) {
        		a.avancer(30);
        		a.stop();
                p.fermer(-1620);
                orienteVersEnbut();
                return;
        	}*/
        		
              	/*if(distPrecedente > 43 && d.getValue()>distPrecedente) {//palet raté car distance augmente d'un coup sans raison
        		orienteVersPalet(360);
                return;
        	}
        	if(d.getValue() < 0.20){//mur
                a.tourne(-90);
                anglett +=-90;
                orienteVersPalet(360);
            }*/
        	distPrecedente = d.getValue();
        	a.avancer(100);
        	di+= 100;
        	
        }
            a.stop();
            p.fermer(-1620);//pareil
            orienteVersEnbut();
    }

public void orienteVersEnbut() {
		int angleRetour = 90 - (anglett % 360);
	    a.tourne(angleRetour);
	    avanceVersEnbut(angleRetour);
		
	}

   
    public void avanceVersEnbut(int angleRetour){
		Color c = new Color();
		float[] tab = c.getValue();
		while (true) {// tant qu'on n'a pas la couleur blanche
			if (tab[0]>=0.90 && tab[1]>=0.90 && tab[2]>=0.90) {
				a.stop();
				this.arret();
				return;
			}
			if(d.getValue()<0.30) {
				if(angleRetour<=180) {// si il est parti sur la gauche ou tout droit
					a.tourne(-90);
					a.avancer(15);
					a.tourne(90);
					
				}
				else {// on par du principe qu'il ne se prend pas de mur pour l'instant
					a.tourne(-90);
					a.avancer(15);
					a.tourne(90);
					
				}
			}
			a.avancer(100);
		}	
	}
    public void arret() {
		p.ouvrir(1620);
		a.reculer(100);
		anglett = 0;
		int m = (int)Math.random();//pour pas tourner tout le temps du même cote
		if(m==0) { 
			a.tourne(90);
			//linefollower pour se remettre droit
			a.tourne(180);
		}
		else{
			a.tourne(-90);
			//line follower pour se remettre droit
		}
		cherchePalet(180);
	}

	//fin
			    
	public void chercheMur(int angleMax) {
        double dist = 0;       
        double anglett = 0;
        while(Math.abs((anglett%360))<Math.abs(angleMax-1)) { 
            dist = d.getValue();
           // System.out.println(dist);
            if(dist < 0.3) {
                a.stop();
                orienteVersMur(dist);
                System.out.println("Un mur");
            }
            a.tourne(-10);
            anglett += -10;
            System.out.println("pas de palets");
        }
        orienteVersMur(dist);
	}

	public void orienteVersMur(double dist1) {
		double dist = dist1;
		a.avancer(dist);
		orienteVersEnbut();
		return;
	}
	
	/*
	 * oriente vers enbut
	 */
	/*public void orienteVersEnbut(int angle) {
		
		a.tourne(orienteVersMur(angle));
		
		float[] tab = c.getValue();
		while (tab[0]<=0.240 && tab[1]<=0.240 && tab[2]<=0.240) {
			System.out.println(tab[0]+"::"+ tab[2]);
			a.avancer(Double.POSITIVE_INFINITY, true);
			
		}
		a.stop();
		p.ouvrir(360);
		a.reculer(100);
		a.tourne(angle*2);
		
	}*/

	public String[] getPositionDepart() {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();

		g.drawString("Ligne depart? gauche | milieu | droite", 0, 0, 0);

		int but = Button.waitForAnyPress(TITLE_DELAY);
		String[] pressed = new String[2];
		
		if (but == 0)
			pressed[0] = "None";
		else if ((but & Button.ID_ENTER) != 0)
			pressed[0] = "Milieu";
		else if ((but & Button.ID_LEFT) != 0)
			pressed[0] = "Gauche";
		else if ((but & Button.ID_RIGHT) != 0)
			pressed[0] = "Droit";
		g.clear();

		g.drawString("Coté ? le bas -> x = 0 pour capteur IR", 0, 0, 0);
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
		ag.chercherInfra();
	}

}
