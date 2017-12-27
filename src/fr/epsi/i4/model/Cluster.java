package fr.epsi.i4.model;

import java.util.ArrayList;
import java.util.List;

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

    public boolean removeEntry(Entry entry) {
        return entries.remove(entry);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Calcule la distance maximale avec un autre cluster
     *
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
     *
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
     * Calcule la distance cumulée avec un autre cluster
     *
     * @param cluster
     * @return
     */
    public int getCumulatedDistanceWithCluster(Cluster cluster) {
        int distance = 0;
        // Pour chaque entrée de ce cluster
        for (Entry entry1 : entries) {
            // Pour chaque entrée du cluster distant
            for (Entry entry2 : cluster.getEntries()) {
                // On ajoute la distance entre les deux entrées
                distance += entry1.distanceHamming(entry2);
            }
        }
        return distance;
    }

    /**
     * Ajoute l'ensemble des entrées d'un cluster à ce cluster
     *
     * @param cluster
     * @return
     */
    public Cluster merge(Cluster cluster) {
        for (int i = 0; i < cluster.entries.size(); i++) {
            if (!contains(cluster.entries.get(i))) {
                addEntry(cluster.entries.get(i));
            }
        }
        return this;
    }

    public boolean equals(Cluster cluster) {
        boolean equals = true;
        if (entries.size() == cluster.entries.size()) {
            for (Entry entry : cluster.entries) {
                equals &= contains(entry);
            }
        } else {
            equals = false;
        }
        return equals;
    }

    public boolean contains(Entry entry) {
        boolean contains = false;
        int i = 0;
        while (i < entries.size() && !contains) {
            if (entries.get(i).equals(entry)) {
                contains = true;
            }
            i++;
        }

        return contains;
    }

    public boolean contains(Cluster cluster) {
        boolean contains = true;
        for (Entry entry : cluster.entries) {
            contains &= contains(entry);
        }
        return contains;
    }

    public void clearExceptFirst() {
        entries = entries.subList(0, 1);
    }

    public void addClosestEntries(List<Entry> entriesToAdd) {
        int distance = 5;
        int d;
        // Pour chaque entry
        for (int i = 0; i < entriesToAdd.size(); i++) {
            // Si on est pas sur la première entry du cluster
            if (!entriesToAdd.get(i).equals(entries.get(0))) {
                // On récupère la distance entre les entry
                d = entriesToAdd.get(i).distanceHamming(entries.get(0));
                // Si la distance est plus petite
                if (d < distance) {
                    // On mets à jour la distance
                    distance = d;
                    // On vide le cluster
                    clearExceptFirst();
                    // On ajoute l'élément actuel
                    addEntry(entriesToAdd.get(i));
                    // Sinon, si la distance est égale
                } else if (d == distance) {
                    // On ajoute l'élément actuel
                    addEntry(entriesToAdd.get(i));
                }
            }
        }
    }
}
