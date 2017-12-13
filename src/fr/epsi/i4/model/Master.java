package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Master {

    private List<Cluster> clusters;
    private List<Entry> data;

    public Master() {
        clusters = new ArrayList<>();
        data = new ArrayList<>();
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Entry> getData() {
        return data;
    }

    public void setData(List<Entry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Master: ");
        for (int i = 0; i < clusters.size(); i++) {
            stringBuilder
                    .append("\n---------------------------------")
                    .append("\nCluster ")
                    .append(i + 1);
            for (Entry entry : clusters.get(i).getData()) {
                stringBuilder
                        .append("\n")
                        .append(entry.toString());
            }
        }
        return stringBuilder.toString();
    }

    public Entry addEntry(Entry entry) {
        if (data.add(entry)) {
            return entry;
        }
        return null;
    }

    public int getMaximumDistance() {
        int distance = 0;

        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                distance = Math.max(distance, data.get(i).calculateDistance(data.get(j)));
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

        return clusters.get(0);
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
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                System.out.println("Distance between " + data.get(i).getId() + " and " + data.get(j).getId() + " : " + data.get(i).calculateDistance(data.get(j)));
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
        for (int i = 0; i < n; i++) {
            clusters.add(new Cluster());
        }

        List<Entry> placedEntries = new ArrayList<>();

        for (Entry entry : data) {
            if (entry.isFarther(placedEntries)) {
                Cluster lastCluster = getFirstEmptyCluster();
                lastCluster.dispatch(getClustersExcept(lastCluster));
                lastCluster.addEntry(entry);
            } else {
                Cluster cluster = entry.getCloserCluster(clusters);
                cluster.addEntry(entry);
            }
            placedEntries.add(entry);
        }
    }
}
