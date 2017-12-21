package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.List;

public class Master {

    private List<Cluster> clusters;
    private List<Entry> data;

    public Master() {
        clusters = new ArrayList<>();
        data = new ArrayList<>();
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
}
