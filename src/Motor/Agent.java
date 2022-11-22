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
	private final double vitesseLin = 200.0;
	private final double accLin = 50.0;
	private final double vitesseAng = 20.0;
	private final double accAng = 10.0;
	private final Pince p = new Pince("A");
	private final Actionneur a = new Actionneur(vitesseLin, accLin, vitesseAng, accAng);
	private final Touch t =  new Touch();
	private final String[] position;
	private final Distance d = new Distance();
	private final CameraInfrarouge cir = new CameraInfrarouge();
	private final float xpourcent;
	private final float ypourcent;
	//private final boolean[] sample;

	public Agent() {
		float[] constant = setConstant();
		position = getPositionDepart();
		xpourcent = constant[0];
		ypourcent = constant[1];
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
		a.avancer(Double.POSITIVE_INFINITY); // non bloquante -->ferme le palet et avance en même temps
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
		a.avancer(-100);
		p.fermer(-360*4);
		while (p.isMoving()) {

		}
		a.stop();
		a.tourne(180);

	}

	public void prendPalet2() {
		int angle = 45;
		if(position.equals("Gauche")) {
			angle = -angle;
			// a modifier en fonction des mesures
			a.tourne(angle);
			a.avancer(250);
			p.fermer(360);
			a.tourne(-180+angle);
			a.avancer(200);
			p.ouvrir(360);
			a.reculer(100);
			prendPalet3();

		}
		else {
			a.tourne(angle);
			a.avancer(250);
			p.fermer(360);
			a.tourne(180-angle);
			a.avancer(200);
			p.ouvrir(360);
			a.reculer(100);
			prendPalet3();
		}




	}

	public void prendPalet3(){
		a.avancer(1000);
		p.fermer(360);
		a.tourne(180);
		a.avancer(1100);
		p.ouvrir(360);
		a.reculer(100);
		a.tourne(180);
	}


	public void orienteVersEnbut(double angleDepart) {

		a.tourne(180 - angleDepart);

	}

	public void avanceVersEnbut(){
		Color c = new Color();

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
		if(position[1]=="Up")bis=false;
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
		if(position[1]=="Down")i++;
		if(position[1]=="Up")i--;
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

	public void orienteVersPalet(int angle) {
		double dist  = cherchePalet(angle);
		a.avancer(dist*1000);
	}
	public void recuperePalet() {
		orienteVersPalet(360);
		if (t.getValue() == 1) {
			a.stop();
			p.fermerSurPalet();
		}
		while (d.getValue() < 0.30){
			a.tourne(90);
		}
		orienteVersPalet(360);


	}


	public String[] getPositionDepart() {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();

		g.drawString("Ligne depart? gauche | milieu | droite", 0, 0, 0);

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
