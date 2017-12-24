package fr.epsi.i4.model;

import java.util.*;

public class Cluster {

    private List<Entry> entries;

    public Cluster() {
        entries = new ArrayList<>();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "entries=" + entries +
                '}';
    }

    public Entry addEntry(Entry entry) {
        if (entries.add(entry)) {
            entry.setCluster(this);
            return entry;
        }
        return null;
    }

    public Entry removeEntry(int id) {
        return entries.remove(id);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public int getInternMaximumDistance() {
        int distance = 0;

        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                distance = Math.max(distance, entries.get(i).calculateDistance(entries.get(j)));
            }
        }

        return distance;
    }

    public void dispatch(List<Cluster> clusters) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            Cluster cluster = entries.get(i).getCloserCluster(clusters);
            if (!cluster.equals(this)) {
                cluster.addEntry(entries.get(i));
                indexes.add(i);
            }
        }
        for (int i : indexes) {
            entries.remove(i);
        }
    }

    public Entry getClosestEntry(List<Entry> entries) {
        if (isEmpty()) {
            return entries.get(0);
        }
        int distance = 4;
        Entry entry = null;
        for (Entry e : entries) {
            int d = e.getMinimumDistanceWithCluster(this);
            if (d < distance) {
                distance = d;
                entry = e;
            }
        }
        return entry;
    }
}
