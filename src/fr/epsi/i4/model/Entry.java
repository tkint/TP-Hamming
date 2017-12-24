package fr.epsi.i4.model;

import java.lang.reflect.Field;
import java.util.List;

public class Entry {

    private int id;
    private static int nextId = 1;

    private Cluster cluster;

    public int couleur;
    public int noyaux;
    public int flagelles;
    public int membrane;

    public Entry() {
        id = nextId;
        nextId++;
    }

    public Entry(int id, int couleur, int noyaux, int flagelles, int membrane) {
        this.id = id;
        this.couleur = couleur;
        this.noyaux = noyaux;
        this.flagelles = flagelles;
        this.membrane = membrane;
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

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
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

    public Entry clone() {
        return new Entry(id, couleur, noyaux, flagelles, membrane);
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

        for (Entry entry : cluster.getEntries()) {
            distance = Math.max(distance, calculateDistance(entry));
        }

        return distance;
    }

    public int getMinimumDistanceWithCluster(Cluster cluster) {
        int distance = 0;

        for (Entry entry : cluster.getEntries()) {
            distance = Math.min(distance, calculateDistance(entry));
        }

        return distance;
    }

    public Cluster getCloserCluster(List<Cluster> clusters) {
        Cluster cluster = clusters.get(0);

        int maxDistance = 4;
        for (Cluster c : clusters) {
            if (getMaximumDistanceWithCluster(c) < maxDistance) {
                maxDistance = getMaximumDistanceWithCluster(c);
                cluster = c;
            }
        }

        return cluster;
    }
}
