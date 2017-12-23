package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.List;

public class Master {

    private static Master instance;
    private List<Cluster> clusters;
    private List<Entry> entries;

    private Master() {
        clusters = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public static Master getInstance() {
        if (instance == null) {
            instance = new Master();
        }
        return instance;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Entry> getEntries() {
        return entries;
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
                            .append(entry);
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
            this.clusters.add(new Cluster());
            generateClusters(number - 1);
        }
    }

    public void dispatchInClusters() {
        int i = 0;
        for (Entry entry : entries) {
            Cluster cluster = clusters.get(i);
            cluster.addEntry(entry);

            Entry e = cluster.getFartherEntry();
            if (e != null) {
                cluster.extractEntry(e);
            }
            i = (i + 1) % clusters.size();
        }
    }
}
