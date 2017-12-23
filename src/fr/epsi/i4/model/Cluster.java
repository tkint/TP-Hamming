package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private List<Entry> entries;

    public Cluster() {
        this.entries = new ArrayList<>();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public Entry addEntry(Entry entry) {
        if (entries.add(entry)) {
            return entry;
        }
        return null;
    }

    public Entry getFartherEntry() {
        Entry entry = null;
        int distance = 0;
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i; j < entries.size(); j++) {
                int d = entries.get(i).distanceHamming(entries.get(j));
                if (d > distance) {
                    distance = d;
                    entry = entries.get(i);
                }
            }
        }
        return entry;
    }

    public void extractEntry(Entry entry) {
        if (entries.remove(entry)) {
            Cluster cluster = entry.getCloserClusterExcept(this);
            if (cluster != null) {
                cluster.addEntry(entry);
            }
        }
    }
}
