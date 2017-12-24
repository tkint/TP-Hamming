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

    /**
     * Calcule la distance maximale avec un autre cluster
     * @param cluster
     * @return
     */
    public int getMaximumDistanceWithCluster(Cluster cluster) {
        int distance = 0;
        // Pour chaque entrée de ce cluster
        for (Entry entry1 : entries) {
            // Pour chaque entrée du cluster distant
            for (Entry entry2 : cluster.getEntries()) {
                // On récupère la distance entre les deux entrées
                int d = entry1.distanceHamming(entry2);
                // Si elle est supérieure à la distance déjà enregistrée
                if (d > distance) {
                    // On enregistre la nouvelle distance
                    distance = d;
                }
            }
        }
        return distance;
    }

    /**
     * Calcule la distance minimale avec un autre cluster
     * @param cluster
     * @return
     */
    public int getMinimumDistanceWithCluster(Cluster cluster) {
        int distance = 5;
        // Pour chaque entrée de ce cluster
        for (Entry entry1 : entries) {
            // Pour chaque entrée du cluster distant
            for (Entry entry2 : cluster.getEntries()) {
                // On récupère la distance entre les deux entrées
                int d = entry1.distanceHamming(entry2);
                // Si elle est inférieure à la distance déjà enregistrée
                if (d < distance) {
                    // On enregistre la nouvelle distance
                    distance = d;
                }
            }
        }
        return distance;
    }

    /**
     * Ajoute l'ensemble des entrées d'un cluster à ce cluster
     * @param cluster
     * @return
     */
    public Cluster merge(Cluster cluster) {
        for (Entry entry : cluster.entries) {
            addEntry(entry);
        }
        return this;
    }
}
