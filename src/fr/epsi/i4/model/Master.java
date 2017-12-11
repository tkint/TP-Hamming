package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void displayDistances() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                System.out.println("Distance between " + (i + 1) + " and " + (j + 1) + " : " + data.get(i).calculateDistance(data.get(j)));
            }
        }
    }

    public List<Entry> getBaseEntries(int number) {
        List<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0, 0,0 ,0 ));
        entries.add(new Entry(1, 2,2 ,1 ));

        return entries;
    }

    public Cluster getCloserCluster(Entry entry) {
        Cluster cluster = null;

        int distance = 0;
        for (Cluster c : clusters) {
            if (c.getMaximumDistance(entry) == distance) {
                return c;
            } else if (c.getMaximumDistance(entry) > distance) {
                cluster = c;
                distance = c.getMaximumDistance(entry);
            }
        }

        return cluster;
    }

    public void dispatch() {
        Cluster cluster1 = new Cluster();
        Cluster cluster2 = new Cluster();
        Cluster cluster3 = new Cluster();
        Cluster cluster4 = new Cluster();
        clusters = Arrays.asList(cluster1, cluster2, cluster3, cluster4);

        cluster1.addEntry(data.get(0));
        cluster2.addEntry(data.get(6));
        cluster3.addEntry(data.get(7));
        cluster4.addEntry(data.get(9));

        for (Entry entry : data) {
            getCloserCluster(entry).addEntry(entry);
        }
    }
}
