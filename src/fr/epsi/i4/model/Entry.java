package fr.epsi.i4.model;

import java.lang.reflect.Field;
import java.util.List;

public class Entry {

    private int id;
    private static int nextId;

    public int couleur;
    public int noyaux;
    public int flagelles;
    public int membrane;

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

    public int getId() {
        return id;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public int getNoyaux() {
        return noyaux;
    }

    public void setNoyaux(int noyaux) {
        this.noyaux = noyaux;
    }

    public int getFlagelles() {
        return flagelles;
    }

    public void setFlagelles(int flagelles) {
        this.flagelles = flagelles;
    }

    public int getMembrane() {
        return membrane;
    }

    public void setMembrane(int membrane) {
        this.membrane = membrane;
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

    public int calculateDistance(Entry entry) {
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

    public int getMaximumDistanceWithCluster(Cluster cluster) {
        int distance = 0;

        for (Entry entry : cluster.getData()) {
            distance = Math.max(distance, calculateDistance(entry));
        }

        return distance;
    }

    public int getMinimumDistanceWithCluster(Cluster cluster) {
        int distance = 0;

        for (Entry entry : cluster.getData()) {
            distance = Math.min(distance, calculateDistance(entry));
        }

        return distance;
    }

    public Cluster getCloserCluster(List<Cluster> clusters) {
        Cluster cluster = clusters.get(0);

        int maxDistance = 0;
        for (Cluster c : clusters) {
            if (getMaximumDistanceWithCluster(c) <= c.getMaximumDistance()) {
                return c;
            }
            if (maxDistance == 0 || getMaximumDistanceWithCluster(c) < maxDistance) {
                maxDistance = getMaximumDistanceWithCluster(c);
                cluster = c;
            }
        }

        return cluster;
    }

    public boolean isFarther(List<Entry> entries) {
        boolean farther = false;

        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                farther = calculateDistance(entries.get(i)) > entries.get(i).calculateDistance(entries.get(j));
            }
        }


        return farther;
    }
}
