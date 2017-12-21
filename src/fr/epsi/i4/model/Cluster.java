package fr.epsi.i4.model;

import java.lang.reflect.Field;
import java.util.*;

public class Cluster {

    private List<Entry> data;
    private List<Integer> distances;

    public Cluster() {
        data = new ArrayList<>();
        distances = new ArrayList<>();
    }

    public List<Entry> getData() {
        return data;
    }

    public void setData(List<Entry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "data=" + data +
                '}';
    }

    public Entry addEntry(Entry entry) {
        if (data.add(entry)) {
            for (Entry e : data) {
                distances.add(e.calculateDistance(entry));
            }
            return entry;
        }
        return null;
    }

    public int countDistanceOccurences(int distance) {
        int count = 0;

        for (int d : distances) {
            if (d == distance) {
                count++;
            }
        }

        return count;
    }

    public Entry removeEntry(int id) {
        return data.remove(id);
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int getInternMaximumDistance() {
        int distance = 0;

        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                distance = Math.max(distance, data.get(i).calculateDistance(data.get(j)));
            }
        }

        return distance;
    }

    public void dispatch(List<Cluster> clusters) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Cluster cluster = data.get(i).getCloserCluster(clusters);
            if (!cluster.equals(this)) {
                cluster.addEntry(data.get(i));
                indexes.add(i);
            }
        }
        for (int i : indexes) {
            data.remove(i);
        }
    }
}
