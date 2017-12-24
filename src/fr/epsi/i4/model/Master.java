package fr.epsi.i4.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Master {

    private List<Cluster> clusters;
    private List<Entry> entries;

    public Master() {
        clusters = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
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
                        .append(entry.toString());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Ajoute une entrée
     *
     * @param entry
     * @return
     */
    public Entry addEntry(Entry entry) {
        if (entries.add(entry)) {
            return entry;
        }
        return null;
    }

    /**
     * Affiche l'ensemble des distances entre les entrées
     */
    public String displayDistances() {
        StringBuilder stringBuilder = new StringBuilder("Distances:");
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                stringBuilder.append("\nDistance entre ")
                        .append(entries.get(i).getId())
                        .append(" et ")
                        .append(entries.get(j).getId())
                        .append(" : ")
                        .append(entries.get(i).distanceHamming(entries.get(j)));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Divise les entrées en n clusters
     *
     * @param n
     */
    public void dispatch(int n) {
        // Création des clusters
        for (Entry entry : entries) {
            Cluster cluster = new Cluster();
            cluster.addEntry(entry);
            clusters.add(cluster);
        }

        // Tant qu'on a pas le nombre de clusters désiré
        while (clusters.size() > n) {
            // On prend les deux clusters les plus proches l'un de l'autre
            Pair<Cluster, Cluster> clusterPair = getClosestClusters();
            if (clusterPair != null) {
                // On les fusionne
                Cluster cluster = clusterPair.getKey();
                Cluster clusterToMerge = clusterPair.getValue();
                cluster.merge(clusterToMerge);
                // On supprime le cluster fusionné
                clusters.remove(clusterToMerge);
            }
        }
    }

    /**
     * Récupère les deux clusters les plus proches l'un de l'autre
     * TODO: Définir un élément de pertinence pour éviter de toujours prendre la première paire en cas
     * TODO: de < ou la dernière paire en cas de <= (nombre d'éléments différents?)
     * @return
     */
    private Pair<Cluster, Cluster> getClosestClusters() {
        Pair<Cluster, Cluster> clusterPair = null;

        int distance = 5;
        // Pour chaque cluster
        for (int i = 0; i < clusters.size(); i++) {
            // Pour tous les clusters suivants
            for (int j = i + 1; j < clusters.size(); j++) {
                // On récupère la distance maximale entre les clusters
                int d = clusters.get(i).getMaximumDistanceWithCluster(clusters.get(j));
                // Si elle est inférieure ou égale à la précédente distance max
                if (d < distance) {
                    distance = d;
                    // Les deux clusters actuels sont les plus proches
                    clusterPair = new Pair<>(clusters.get(i), clusters.get(j));
                }
            }
        }

        return clusterPair;
    }
}
