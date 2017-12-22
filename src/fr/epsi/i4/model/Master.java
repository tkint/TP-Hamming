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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Master: ");
        for (int i = 0; i < clusters.size(); i++) {
            stringBuilder
                    .append("\n---------------------------------")
                    .append("\nCluster ")
                    .append(i + 1);
            for (int j = 0; j < clusters.get(i).getLines().length; j++) {
                if (clusters.get(i).getLines()[j] != -1) {
                    stringBuilder
                            .append("\n")
                            .append(entries.get(clusters.get(i).getLines()[j]).toString());
                }
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

    public void generateClusters(int number) {
        if (number > 0) {
            this.clusters.add(new Cluster(this.entries.size()));
            generateClusters(number - 1);
        }
    }

    public void dispatchInClusters() {
        for (Cluster cluster : clusters) {
            if (cluster.isEmpty()) {

            }
        }
    }
}
