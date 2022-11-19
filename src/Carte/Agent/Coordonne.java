package Carte.Agent;


import java.util.ArrayList;

/**
 * problème x,y inversion
 */
public class Coordonne {

    public final int x;
    public final int y;

    public Coordonne(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public static ArrayList<Coordonne> voisins(Coordonne c){
        ArrayList<Coordonne> res = new ArrayList<>();
        for (int i = c.x-1; i < c.x+1; i++) {
            for (int j = c.y-1; j < c.y+1; j++) {
                if (i==c.x && j==c.y)
                    continue;
                else if (i>0 && i<=4 && j>0 && j<=4) {
                    res.add(new Coordonne(i,j));
                }
            }
        }
        return res;
    }
}

