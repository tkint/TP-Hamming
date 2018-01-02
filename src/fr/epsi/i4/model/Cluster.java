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

    /**
     * Ajoute l'entry au cluster
     *
     * @param entry Entry
     * @return Entry
     */
    public Entry addEntry(Entry entry) {
        if (entries.add(entry)) {
            return entry;
        }
        return null;
    }

    /**
     * Retire l'entry du cluster
     *
     * @param entry Entry
     * @return boolean
     */
    public boolean removeEntry(Entry entry) {
        return entries.remove(entry);
    }

    /**
     * Calcule la distance maximale avec un autre cluster
     *
     * @param cluster Cluster
     * @return int
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
     * Ajoute l'ensemble des entrées d'un cluster à ce cluster
     *
     * @param cluster Cluster
     * @return Cluster
     */
    public Cluster merge(Cluster cluster) {
        for (int i = 0; i < cluster.entries.size(); i++) {
            if (!contains(cluster.entries.get(i))) {
                addEntry(cluster.entries.get(i));
            }
        }
        return this;
    }

    /**
     * Vérifie si le cluster contient l'entry
     *
     * @param entry Entry
     * @return boolean
     */
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

    /**
     * Vérifie si le cluster contient le cluster distant
     *
     * @param cluster Cluster
     * @return boolean
     */
    public boolean contains(Cluster cluster) {
        boolean contains = true;
        int i = 0;
        while (i < cluster.entries.size() && contains) {
            if (!contains(cluster.entries.get(i))) {
                contains = false;
            }
            i++;
        }
        return contains;
    }

    /**
     * Ajoute les entry les plus proches
     *
     * @param entriesToAdd List<Entry>
     */
    public void addClosestEntries(List<Entry> entriesToAdd) {
        int distance = Entry.getMaxSize();
        int d;
        // Pour chaque entry
        for (Entry entry : entriesToAdd) {
            // Si on est pas sur la première entry du cluster
            if (!entry.equals(entries.get(0))) {
                // On récupère la distance entre les entry
                d = entry.distanceHamming(entries.get(0));
                // Si la distance est plus petite
                if (d < distance) {
                    // On mets à jour la distance
                    distance = d;
                    // On vide le cluster sauf la première donnée car c'est celle de référence
                    entries = entries.subList(0, 1);
                    // On ajoute l'élément actuel
                    addEntry(entry);
                    // Sinon, si la distance est égale
                } else if (d == distance) {
                    // On ajoute l'élément actuel
                    addEntry(entry);
                }
            }
        }
    }
}
