package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.List;

public class Master {

    private List<Cluster> clusters;
    private List<Entry> entries;

    public Master() {
        clusters = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Master: ");
        for (int i = 0; i < clusters.size(); i++) {
            stringBuilder
                    .append("\n---------------------------------")
                    .append("\nCluster ")
                    .append(i + 1);
            for (Entry entry : clusters.get(i).getEntries()) {
                stringBuilder
                        .append("\n")
                        .append(entry.toString());
            }
        }
        return stringBuilder.toString();
    }

    public Entry addEntry(Entry entry) {
        if (entries.add(entry)) {
            return entry;
        }
        return null;
    }

    public int getMaximumDistance() {
        int distance = 0;

        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                distance = Math.max(distance, entries.get(i).calculateDistance(entries.get(j)));
            }
        }

        return distance;
    }

    public Cluster getFirstEmptyCluster() {
        for (Cluster c : clusters) {
            if (c.isEmpty()) {
                return c;
            }
        }

        return null;
    }

    public Cluster getLastNotEmptyCluster() {
        Cluster cluster = clusters.get(0);

        for (Cluster c : clusters) {
            if (c.isEmpty()) {
                return cluster;
            }
            cluster = c;
        }

        return cluster;
    }

    public void displayDistances() {
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                System.out.println("Distance between " + entries.get(i).getId() + " and " + entries.get(j).getId() + " : " + entries.get(i).calculateDistance(entries.get(j)));
            }
        }
    }

    private List<Cluster> getClustersExcept(Cluster cluster) {
        List<Cluster> clusters = new ArrayList<>();
        for (Cluster c : this.clusters) {
            if (!c.equals(cluster)) {
                clusters.add(c);
            }
        }
        return clusters;
    }

    // TODO: Il faut redispatcher au fur et à mesure du remplissage des clusters
    // Si la distance max entre une entry d'un cluster et un autre cluster est plus petite que la distance max
    // Du cluster dans lequel elle est, alors bouger l'entry dans le cluster distant
    public void dispatch(int n) {
        // Création des clusters
        for (int i = 0; i < n; i++) {
            clusters.add(new Cluster());
        }

        // Pour chaque donnée
        clusters.get(0).addEntry(entries.get(0));
        for (int i = 1; i < entries.size(); i++) {
            // On récupère le cluster le plus proche
            Cluster cluster = entries.get(i).getCloserCluster(clusters);
            // Si l'entry est la plus proche des entries
            if (entries.get(i).equals(cluster.getClosestEntry(entries))) {
                // On l'ajoute à ce cluster et on la retire des entries
                cluster.addEntry(entries.get(i).clone());
//                entries.remove(i);
            }
            // Sinon
            else {
                // On récupère le premier cluster vide
//                cluster = getFirstEmptyCluster();
//                if (cluster == null) {
//                    cluster = entries.get(i).getCloserCluster(clusters);
//                }
                // On l'ajoute à ce cluster et on la retire des entries
                cluster.addEntry(entries.get(i).clone());
//                entries.remove(i);
            }
        }

    }
}
