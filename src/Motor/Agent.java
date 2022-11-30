package Motor;

import Sensor.Color;
import Sensor.Distance;
import Sensor.Touch;
import Sensor.CameraInfrarouge;
import java.util.ArrayList;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
public class Agent {
	
	private static final int TITLE_DELAY = 60000;
	private final double vitesseLin = 200.0;	
	private final double accLin = 50.0;
	private final double vitesseAng = 20.0;
	private final double accAng = 10.0;
	public final String[] position;
	public int anglett;
	private final float xpourcent;
	private final float ypourcent;
	
	private final Pince p = new Pince("A");
	private final Actionneur a = new Actionneur(vitesseLin, accLin,vitesseAng, accAng);
	private final Touch t =  new Touch();
	private final Distance d = new Distance();
	private final CameraInfrarouge cir = new CameraInfrarouge();
	private static Color c = new Color();
	float tab = c.getValue();
	
	
	
	/*
	 * constructeur de agent
	 */
	public Agent() {
		float[] constant = setConstant();
		xpourcent = constant[0];
		ypourcent = constant[1];
		p.ouvrir(700);
		anglett = 0;
		position = getPositionDepart();
	}
/*
 * re-initialise les tachometre à 0
 */
	public void resetTachometre() {
		a.wheel1.getMotor().resetTachoCount();
		a.wheel2.getMotor().resetTachoCount();
	}
	
	/*
	 * si true alors retourne l'angle de tachomètre de la roue droite
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
     * corrige la différence de vitesse entre les deux roues
     */
	public void setVitesseRoue() { 
		a.wheel2.getMotor().setSpeed((int) (a.wheel2.getMotor().getMaxSpeed()*0.99));
	}
	
	/*
	 *  Récupere le premier palet en dur et appelle prendPalet2
	 */
	public void prendPremierPalet() {
		a.tourne(-3);
		a.setVitesseAngle(a.getMaxVitesseAngle());
		a.setVitesseLigne(a.getMaxVitesseLigne());
		a.setAccelLigne(a.getMaxVitesseLigne());
		a.setAccelAngle(a.getMaxVitesseAngle());
		p.setSpeed(p.getMaxSpeed());
		
		System.out.println(position[0]);
		boolean cote = true;
		double angle = -16;
		double distance = 2000;
		double angle2 = 145;
		
		a.avancer(630);
		p.fermer(-650);
		if (position[0].equals("Gauche")) {
			System.out.println("gauche");
		    cote = false;
		    a.tourne(15);
		    a.avancer(1100);
		    angle = -25;
		    distance = 1050;
		    angle2 = 145;
		}
		a.tourne(angle);
		a.avancer(distance);
		a.stop();
		p.ouvrir(1620);
		a.avancer(-100); 
		a.stop();
		a.tourne(angle2);
		prendPalet2(cote);
	}
	/*
	 *  Récupere le deuxième palet en dur et appelle prendPalet3
	 */
	public void prendPalet2(boolean cote) {
		a.avancer(500);
		p.fermer(-1620);
		a.tourne(135);
		a.avancer(500);
		p.ouvrir(1620);
		a.reculer(-100);
		a.tourne(130);
		prendPalet3(cote);
	}
	/*
	 *  Récupere le troisième palet en dur et appelle chercherPalet avec un angle de rotation maximum de 180
	 */
	public void prendPalet3(boolean cote){
		a.avancer(1100);
		p.fermer(-1620);
		a.tourne(140);
		a.avancer(1200);
		p.ouvrir(1620);
		a.reculer(-100);
		if (cote == false) {
			a.tourne(170);
			a.avancer(500);
			a.tourne(-90);
			a.avancer(200);
			cherchePalet(180);
			return;
		}
		else a.tourne(-80);
		a.tourne(-90);
		anglett += -90;
		a.avancer(200);
		cherchePalet(180);
	}
	
	/*
	 * effectue un sonar avec une rotation inférieure ou égale à angleMax (180 si dans l'enbut pour ne pas chercher dans l'enbut, 360 sinon)
	 * Si une mesure est entre 30 cm et 1m20 est récupérée, alors un appel à recuperePalet avec cette mesure est effectué
	 * 
	 */
	
	public void cherchePalet(int angleMax) {// Sonar: trouver l'angle optimal (=le palet le plus proche)
		setVitesseRoue();
        double dist = 0;
        System.out.println("dans chercher palet");
        anglett = 0;
        while(Math.abs((anglett%360))<Math.abs(angleMax-1)) { // récuperer les mesures : -1 car (anglett%360) ne va pas à 360 donc 359
            dist = d.getValue();
            System.out.println(dist);
            if(dist>= 0.30 && dist<=1.2) {
                        a.stop();
                        recuperePalet(dist);
                        System.out.println("Un palet");
                        }
            a.tourne(-10);
            anglett += -10;
            System.out.println("pas de palets");
            }
        recuperePalet(dist);
}
    /*
     * avance de 15 cm par 15 cm jusqu'à ce qu'il est avancé d'une distance plus grande que dist1, puis appelle orienteVersEnBut
     * Si il mesure une distance inférieur à 0.20 pendant qu'il avance, alors il évite l'obstacle et appelle chercherPalet(360) pour trouver un nouveau palet
     */
    public void recuperePalet(double dist1) {
        double di = 0;
        while(true) {
        	if(di>dist1) {
        		a.stop();
                p.fermer(-1620);
                orienteVersEnbut();
                return;
        	}
        	a.avancer(150);
        	di = di + 0.150;
        	double dv = d.getValue();
        	if(dv < 0.20){ //permet d'éviter les collisions
                a.tourne(-90);
                anglett +=-90;
                a.avancer(300);
                cherchePalet(360);
                return;
        	}
        }
    }
/*
 * Calcul l'angle pour revenir à l'en but, et s'oriente en fonction de cet angle, puis appelle avance vers en but
 */
public void orienteVersEnbut() {
	System.out.println("oriente vers enbut");
		int angleRetour = 180 - (anglett % 360);
	    a.tourne(angleRetour);
	    avanceVersEnbut(angleRetour);
		
	}

   /*
    * avance de 10 cm par 10 cm jusqu'à trouver une ligne blanche puis appelle arret
    */
    public void avanceVersEnbut(int angleRetour){
		
		while (true) {// tant qu'on n'a pas la couleur blanche
			System.out.println(tab);
			tab =c.getValue();
			if (tab == 6.0) {
				a.stop();
				this.arret();
				return;
			}
			if(d.getValue()<0.14) {// gestion des collisions
					a.tourne(-90);
					a.avancer(150);
					a.tourne(90);
			}
			a.avancer(100);
		}	
	}
    /*
     * permet de déposer un palet dans l'enbut et de recommencer à chercher en appelant cherchePalet avec un angle de 180
     */
    public void arret() {
		p.ouvrir(1620);
		a.reculer(-100);
		anglett = 0;
		a.tourne(-80);
		a.tourne(-90);
		anglett += -90;
		a.avancer(200);
		cherchePalet(180);
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
		if(position[1].equals("Up"))i--;
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
	/*
	 * retourne un tableau de string correspondant à la position de départ du robot
	 */
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

		return pressed;
	}	
	
	public static void main(String[] args) {	// main utilisé pour les matches
		Agent ag = new Agent();
		ag.prendPremierPalet();	
	}

}
