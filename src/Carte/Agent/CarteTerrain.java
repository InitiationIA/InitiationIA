package Carte.Agent;

import java.util.HashMap;

public class CarteTerrain {

    /* indice tab delimitation
            0 : haut
            1 : bas
            2 : gauche
            3 : droite
         */

    protected HashMap<Coordonne,Delimitation[]> terrainPresent;
    protected HashMap<Coordonne,Delimitation[]> terrainFutur;

    public static HashMap<Coordonne,Delimitation[]> terrainComplet = new HashMap<>();

    static {
        terrainComplet.put(new Coordonne(1, 1), new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE, Delimitation.MUR, Delimitation.ROUGE});
        terrainComplet.put(new Coordonne(1, 2), new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE, Delimitation.ROUGE, Delimitation.NOIR});
                        terrainComplet        .put(new Coordonne(1, 3), new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE, Delimitation.NOIR, Delimitation.JAUNE});
                        terrainComplet        .put(new Coordonne(1, 4), new Delimitation[]{Delimitation.VERTE, Delimitation.BLANCHE, Delimitation.JAUNE, Delimitation.MUR});
                        terrainComplet        .put(new Coordonne(2, 1), new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE, Delimitation.MUR, Delimitation.ROUGE});
                        terrainComplet        .put(new Coordonne(2, 2), new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE, Delimitation.ROUGE, Delimitation.NOIR});
                        terrainComplet        .put(new Coordonne(2, 3), new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE, Delimitation.NOIR, Delimitation.JAUNE});
                        terrainComplet       .put(new Coordonne(2, 4), new Delimitation[]{Delimitation.NOIR, Delimitation.VERTE, Delimitation.JAUNE, Delimitation.MUR});
                        terrainComplet       .put(new Coordonne(3, 1), new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR, Delimitation.MUR, Delimitation.ROUGE});
                        terrainComplet       .put(new Coordonne(3, 2), new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR, Delimitation.ROUGE, Delimitation.NOIR});
                        terrainComplet       .put(new Coordonne(3, 3), new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR, Delimitation.NOIR, Delimitation.JAUNE});
                        terrainComplet        .put(new Coordonne(3, 4), new Delimitation[]{Delimitation.BLEU, Delimitation.NOIR, Delimitation.JAUNE, Delimitation.MUR});
                        terrainComplet        .put(new Coordonne(4, 1), new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU, Delimitation.MUR, Delimitation.ROUGE});
                        terrainComplet        .put(new Coordonne(4, 2), new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU, Delimitation.ROUGE, Delimitation.NOIR});
                        terrainComplet        .put(new Coordonne(4, 3), new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU, Delimitation.NOIR, Delimitation.JAUNE});
                        terrainComplet        .put(new Coordonne(4, 4), new Delimitation[]{Delimitation.BLANCHE, Delimitation.BLEU, Delimitation.JAUNE, Delimitation.MUR});
    }


    public HashMap<Coordonne,Delimitation[]> majTerrainFutur(HashMap<Coordonne,Delimitation[]> terrainPresent){
        terrainFutur.putAll(terrainPresent);
        for (Coordonne c: terrainPresent.keySet()) {
            for (Coordonne v: Coordonne.voisins(c)) {
                terrainFutur.putIfAbsent(v,terrainComplet.get(v));
            }
        }
        return terrainFutur;
    }
    /*
     *   TODO: completer cette fonction
     */
    public HashMap<Coordonne,Delimitation[]> reductionTerrain(Delimitation d){

        for (Delimitation[] dels: terrainFutur.values()) {
            for (Delimitation del: dels) {
                if (d.equals(del)){
                    return null;
                }
            }
        }
        return new HashMap<>(terrainFutur);
    }




    public CarteTerrain(String[] valeurPosition){

        terrainPresent = new HashMap<>();
        terrainFutur = new HashMap<>();

        if (valeurPosition[1].equals("Down")) {
            switch (valeurPosition[0]){
                case "Milieu":
                    terrainPresent.put(new Coordonne(1,2),CarteTerrain.terrainComplet.get(new Coordonne(1,2)));
                    terrainPresent.put(new Coordonne(1,3),CarteTerrain.terrainComplet.get(new Coordonne(1,3)));
                    terrainFutur = majTerrainFutur(terrainPresent);

                case "Gauche":
                    terrainPresent.put(new Coordonne(1,1),CarteTerrain.terrainComplet.get(new Coordonne(1,1)));
                    terrainPresent.put(new Coordonne(1,2),CarteTerrain.terrainComplet.get(new Coordonne(1,2)));
                    terrainFutur = majTerrainFutur(terrainPresent);

                case "Droit" :
                    terrainPresent.put(new Coordonne(1,3),CarteTerrain.terrainComplet.get(new Coordonne(1,3)));
                    terrainPresent.put(new Coordonne(1,4),CarteTerrain.terrainComplet.get(new Coordonne(1,4)));
                    terrainFutur = majTerrainFutur(terrainPresent);

            }



            ;
        } else if (valeurPosition[1].equals("Up")) {
            switch (valeurPosition[0]){
                case "Milieu":
                    terrainPresent.put(new Coordonne(4,2),CarteTerrain.terrainComplet.get(new Coordonne(4,2)));
                    terrainPresent.put(new Coordonne(4,3),CarteTerrain.terrainComplet.get(new Coordonne(4,3)));
                    terrainFutur = majTerrainFutur(terrainPresent);

                case "Gauche":
                    terrainPresent.put(new Coordonne(4,1),CarteTerrain.terrainComplet.get(new Coordonne(4,1)));
                    terrainPresent.put(new Coordonne(1,2),CarteTerrain.terrainComplet.get(new Coordonne(4,2)));
                    terrainFutur = majTerrainFutur(terrainPresent);
                case "Droit" :
                    terrainPresent.put(new Coordonne(4,3),CarteTerrain.terrainComplet.get(new Coordonne(4,3)));
                    terrainPresent.put(new Coordonne(4,4),CarteTerrain.terrainComplet.get(new Coordonne(4,4)));
                    terrainFutur = majTerrainFutur(terrainPresent);



            }



            //Collections.reverse(Arrays.asList(this.cases));
        } else{
            throw new RuntimeException("position depart enbut inconnue");
        }

    }

    @Override
    public String toString() {
        return terrainPresent.toString() + "\n" + terrainPresent.toString();
    }
    /*
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

     */
}
