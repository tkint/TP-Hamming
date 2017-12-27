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
        initDispatch();
        cleanClusters();
        finalizeDispatch(n);
        finalizeCleanClusters();
    }

    /**
     * Initialise l'algorithme
     * On défini que chaque entry est son propre cluster
     * On y adjoint toutes les entries partageant la même distance avec ce cluster
     */
    private void initDispatch() {
        // Création des clusters
        for (Entry entry : entries) {
            Cluster cluster = new Cluster();
            cluster.addEntry(entry);
            // Ajout des entries les plus proches
            cluster.addClosestEntries(entries);
            clusters.add(cluster);
        }
    }

    /**
     * Nettoie les clusters en supprimant les doublons
     */
    private void cleanClusters() {
        List<Cluster> clustersToRemove = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = 0; j < clusters.size(); j++) {
                if (i != j) {
                    if (clusters.get(i).contains(clusters.get(j))) {
                        clustersToRemove.add(clusters.get(j));
                    }
                    if (clusters.get(j).contains(clusters.get(i))) {
                        clustersToRemove.add(clusters.get(i));
                    }
                }
            }
        }
        for (Cluster cluster : clustersToRemove) {
            clusters.remove(cluster);
        }
    }

    /**
     * Fusionne les clusters jusqu'à en obtenir que le nombre nécessaire
     *
     * @param n
     */
    private void finalizeDispatch(int n) {
        // Tant qu'on a pas le nombre de clusters désiré
        while (clusters.size() > n) {
            // On prend les deux clusters les plus proches l'un de l'autre
            Pair<Cluster, Cluster> clusterPair = getClosestClusters();
            // On les fusionne
            Cluster cluster = clusterPair.getKey();
            Cluster clusterToMerge = clusterPair.getValue();
            cluster.merge(clusterToMerge);
            // On supprime le cluster fusionné
            clusters.remove(clusterToMerge);
        }
    }

    /**
     * Nettoie les doublons
     */
    private void finalizeCleanClusters() {
        // Pour chaque cluster
        for (int i = 0; i < clusters.size(); i++) {
            // Pour chaque autre cluster
            for (int j = 0; j < clusters.size(); j++) {
                if (i != j) {
                    // Pour chaque entry du premier cluster
                    for (int k = 0; k < clusters.get(i).getEntries().size(); k++) {
                        Entry entry = clusters.get(i).getEntries().get(k);
                        // Si l'entry est présente dans le deuxième cluster
                        if (clusters.get(j).contains(entry)) {
                            // Si l'entry est plus proche du premier que du second
                            if (entry.getMaximumDistanceWithCluster(clusters.get(i)) < entry.getMaximumDistanceWithCluster(clusters.get(j))) {
                                // On la supprime du second
                                clusters.get(j).removeEntry(entry);
                                // Si l'entry est plus proche du second que du premier
                            } else if (entry.getMaximumDistanceWithCluster(clusters.get(i)) > entry.getMaximumDistanceWithCluster(clusters.get(j))) {
                                // On la supprime du premier
                                clusters.get(i).removeEntry(entry);
                            } else {
                                // Sinon, on en supprime au hasard
                                if (Math.random() > 0.5) {
                                    clusters.get(j).removeEntry(entry);
                                } else {
                                    clusters.get(i).removeEntry(entry);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Récupère les deux clusters les plus proches l'un de l'autre
     *
     * @return
     */
    private Pair<Cluster, Cluster> getClosestClusters() {
        int distance = 5;
        int d;
        int clusterIndex = 0;
        int clusterToMergeIndex = 0;
        // Pour chaque cluster
        for (int i = 0; i < clusters.size(); i++) {
            // Pour tous les clusters suivants
            for (int j = i + 1; j < clusters.size(); j++) {
                // On récupère la distance maximale entre les clusters
                d = clusters.get(i).getMaximumDistanceWithCluster(clusters.get(j));
                // Si la distance maximale est plus petite que la précédente enregistrée
                if (d <= distance) {
                    // On mets à jour la distance
                    distance = d;
                    // On récupère les index
                    clusterIndex = i;
                    clusterToMergeIndex = j;
                }
            }
        }

        return new Pair<>(clusters.get(clusterIndex), clusters.get(clusterToMergeIndex));
    }
}
