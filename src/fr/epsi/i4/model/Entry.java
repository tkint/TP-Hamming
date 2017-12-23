package fr.epsi.i4.model;

import java.lang.reflect.Field;
import java.util.List;

public class Entry {

    private int id;
    private static int nextId = 1;

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

    private int getMinimumDistanceWithCluster(Cluster cluster) {
        int distance = 0;

        for (Entry entry : cluster.getEntries()) {
            distance = Math.min(distance, distanceHamming(entry));
        }

        return distance;
    }

    private int getMaximumDistanceWithCluster(Cluster cluster) {
        int distance = 0;

        for (Entry entry : cluster.getEntries()) {
            distance = Math.max(distance, distanceHamming(entry));
        }

        return distance;
    }

    public Cluster getCloserCluster() {
        int distance = 4;
        Cluster cluster = null;
        List<Cluster> clusters = Master.getInstance().getClusters();
        for (Cluster c : clusters) {
            int d = getMinimumDistanceWithCluster(c);
            if (d < distance) {
                distance = d;
                cluster = c;
            }
        }
        return cluster;
    }

    public Cluster getCloserClusterExcept(Cluster excluded) {
        int distance = 4;
        Cluster cluster = null;
        List<Cluster> clusters = Master.getInstance().getClusters();
        for (Cluster c : clusters) {
            if (!c.equals(excluded)) {
                int d = getMinimumDistanceWithCluster(c);
                if (d < distance) {
                    distance = d;
                    cluster = c;
                }
            }
        }
        return cluster;
    }
}
