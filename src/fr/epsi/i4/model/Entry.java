package fr.epsi.i4.model;

import java.lang.reflect.Field;

public class Entry {

    private static int nextId = 1;

    public int couleur;
    public int noyaux;
    public int flagelles;
    public int membrane;

    private int id;

    public Entry() {
        id = nextId;
        nextId++;
    }

    public Entry(int couleur, int noyaux, int flagelles, int membrane) {
        this();
        this.couleur = couleur;
        this.noyaux = noyaux;
        this.flagelles = flagelles;
        this.membrane = membrane;
    }

    public Entry(int id, int couleur, int noyaux, int flagelles, int membrane) {
        this.id = id;
        this.couleur = couleur;
        this.noyaux = noyaux;
        this.flagelles = flagelles;
        this.membrane = membrane;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", couleur=" + couleur +
                ", noyaux=" + noyaux +
                ", flagelles=" + flagelles +
                ", membrane=" + membrane +
                '}';
    }

    /**
     * Calcule la distance de Hamming avec une autre entrée
     *
     * @param entry
     * @return
     */
    public int distanceHamming(Entry entry) {
        int distance = 0;

        try {
            for (Field field : getClass().getFields()) {
                if (!field.get(this).equals(field.get(entry))) {
                    distance++;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return distance;
    }

    /**
     * Calcule la distance maximale avec un autre cluster
     *
     * @param cluster
     * @return
     */
    public int getMaximumDistanceWithCluster(Cluster cluster) {
        int distance = 0;
        // Pour chaque entrée du cluster distant
        for (Entry entry : cluster.getEntries()) {
            // On récupère la distance entre les deux entrées
            int d = distanceHamming(entry);
            // Si elle est supérieure à la distance déjà enregistrée
            if (d > distance) {
                // On enregistre la nouvelle distance
                distance = d;
            }
        }
        return distance;
    }
}
