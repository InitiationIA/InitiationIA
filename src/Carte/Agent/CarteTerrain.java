package Carte.Agent;

import java.util.Arrays;
import java.util.Collections;

public class CarteTerrain {

    public static Case[] cases;

    protected static final class Case{
        protected final int X ;
        protected final int Y;
        protected final Delimitation[] delimitations;
        /* indice
            0 : haut
            1 : bas
            2 : gauche
            3 : droite
         */

        public Case(int x,int y,Delimitation[] c) {
            X = x;
            Y = y;
            this.delimitations = Arrays.copyOf(c, 4);
        }

        @Override
        public String toString() {
            return "("+X + "," + Y + ")" + "," + Arrays.toString(delimitations) + "\n";
        }
    }


    public CarteTerrain(String valeurPosition){

            this.cases = new Case[]{
                    new Case(1,1,
                            new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE,Delimitation.MUR,Delimitation.ROUGE}),
                    new Case(1,2,
                            new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE,Delimitation.ROUGE,Delimitation.NOIR}),
                    new Case(1,3,
                            new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE,Delimitation.NOIR,Delimitation.JAUNE}),
                    new Case(1,4,
                            new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE,Delimitation.JAUNE,Delimitation.MUR}),
                    new Case(2,1,
                            new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE,Delimitation.MUR,Delimitation.ROUGE}),
                    new Case(2,2,
                            new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE,Delimitation.ROUGE,Delimitation.NOIR}),
                    new Case(2,3,
                            new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE,Delimitation.NOIR,Delimitation.JAUNE}),
                    new Case(2,4,
                            new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE,Delimitation.JAUNE,Delimitation.MUR}),
                    new Case(3,1,
                            new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR,Delimitation.MUR,Delimitation.ROUGE}),
                    new Case(3,2,
                            new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR,Delimitation.ROUGE,Delimitation.NOIR}),
                    new Case(3,3,
                            new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR,Delimitation.NOIR,Delimitation.JAUNE}),
                    new Case(3,4,
                            new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR,Delimitation.JAUNE,Delimitation.MUR}),
                    new Case(4,1,
                            new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU,Delimitation.MUR,Delimitation.ROUGE}),
                    new Case(4,2,
                            new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU,Delimitation.ROUGE,Delimitation.NOIR}),
                    new Case(4,3,
                            new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU,Delimitation.NOIR,Delimitation.JAUNE}),
                    new Case(4,4,
                            new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU,Delimitation.JAUNE,Delimitation.MUR}),
            };

        if (valeurPosition.equals("Down")) {
            return;
        } else if (valeurPosition.equals("Up")) {
           Collections.reverse(Arrays.asList(this.cases));
        } else{
            throw new RuntimeException("position depart enbut inconnue");
        }

    }

    @Override
    public String toString() {
        return Arrays.toString(cases);
    }

    public void resetTerrain(){
        Collections.reverse(Arrays.asList(cases));
    }

    public static void main(String[] args) {
        CarteTerrain c = new CarteTerrain("Up");
        System.out.println(c);
        c = new CarteTerrain("Down");
        System.out.println(c);
        c.resetTerrain();
        System.out.println(c);
    }
}
