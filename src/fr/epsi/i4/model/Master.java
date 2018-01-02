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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Master: ");
        for (int i = 0; i < clusters.size(); i++) {
            stringBuilder
                    .append("\n---------------------------------")
                    .append("\nCluster ")
                    .append(i + 1)
                    .append("\n");
            for (Entry entry : clusters.get(i).getEntries()) {
                stringBuilder
                        .append(entry.toString())
                        .append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Ajoute une entrée
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
     * Retourne l'ensemble des distances entre les entrées
     *
     * @return String
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
     * <p>
     * L'algorithme fonctionne en quatre étapes
     * - Initialisation:
     * On défini que chaque entry est un cluster
     * On y adjoint toutes les entries partageant la même distance avec ce cluster
     * On a donc:
     * 1 2 3 4 5
     * 2 1
     * 3 1 7
     * 4 1
     * 5 1 7
     * 6 9
     * 7 3 5 10
     * 8 9
     * 9 6 8 10
     * 10 7 9
     * - Premier nettoyage:
     * On supprime les clusters en trop, ceux qui sont contenus dans d'autres clusters
     * On a donc:
     * 1 2 3 4 5
     * 3 1 7
     * 5 1 7
     * 7 3 5 10
     * 9 6 8 10
     * 10 7 9
     * - Fusion:
     * On fusionne les clusters les plus proches les uns des autres jusqu'à n'avoir plus que n clusters
     * On a donc:
     * 1 2 3 4 5 7 10 9
     * 9 6 8 10
     * - Deuxième nettoyage:
     * On supprime les doublons des clusters desquels ils sont le plus éloigné
     * A noter que si une entry est équidistante avec plusieurs clusters, un aléaoire se joue pour décider dans
     * lequel la laisser. Les résultats pour n > 3 sont donc susceptibles de changer
     * On a donc:
     * 1 2 3 4 5 7
     * 9 6 8 10
     *
     * @param n int
     */
    public void dispatch(int n, boolean useRandom) {
        // Initialisation
        initDispatch();
        // Premier nettoyage (suppression des clusters en trop)
        cleanClusters(n);
        // Fusion jusqu'à n'avoir plus que n clusters
        finalizeDispatch(n);
        // Deuxième nettoyage (dédoublonnage)
        finalizeCleanClusters(useRandom);
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
     * On limite la suppression par le nombre de clusters désirés par l'algo principal
     */
    private void cleanClusters(int n) {
        List<Cluster> clustersToRemove = new ArrayList<>();
        // Pour chaque cluster
        for (int i = 0; i < clusters.size(); i++) {
            // Pour chaque cluster
            for (int j = 0; j < clusters.size(); j++) {
                // Si on est pas sur le même cluster
                if (i != j) {
                    // Si le second cluster est dans le premier, on l'ajoute à la liste
                    if (clusters.get(i).contains(clusters.get(j))) {
                        clustersToRemove.add(clusters.get(j));
                    }
                }
            }
        }
        // On retire les clusters
        int i = 0;
        while (i < clustersToRemove.size() && clusters.size() > n) {
            clusters.remove(clustersToRemove.get(i));
            i++;
        }
    }

    /**
     * Fusionne les clusters jusqu'à en obtenir que le nombre nécessaire
     *
     * @param n int
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
    private void finalizeCleanClusters(boolean useRandom) {
        int distanceCluster1;
        int distanceCluster2;
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
                            // On récupère les distances max avec les deux clusters
                            distanceCluster1 = entry.getMaximumDistanceWithCluster(clusters.get(i));
                            distanceCluster2 = entry.getMaximumDistanceWithCluster(clusters.get(j));
                            // Si l'entry est plus proche du premier que du second
                            if (distanceCluster1 < distanceCluster2) {
                                // On la supprime du second
                                clusters.get(j).removeEntry(entry);
                                // Si l'entry est plus proche du second que du premier
                            } else {
                                if (useRandom) {
                                    if (distanceCluster1 > distanceCluster2) {
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
     * Tentative infructueuse d'amélioration des performances...
     * Dommage
     */
    private void finalizeCleanClustersv2() {
        // Pour chaque entry
        for (Entry entry : entries) {
            int distance = Entry.getMaxSize();
            int d;
            Cluster previousCluster = null;
            // Pour chaque cluster
            for (Cluster cluster : clusters) {
                // Si le cluster a l'entry
                if (cluster.contains(entry)) {
                    // On récupère la distance
                    d = entry.getMaximumDistanceWithCluster(cluster);
                    if (previousCluster != null) {
                        // Si elle est plus petite que la stockée
                        if (d < distance) {
                            distance = d;
                            previousCluster.removeEntry(entry);
                        } else {
                            cluster.removeEntry(entry);
                        }
                    }
                    previousCluster = cluster;
                }
            }
        }
    }

    /**
     * Récupère les deux clusters les plus proches l'un de l'autre
     *
     * @return Pair
     */
    private Pair<Cluster, Cluster> getClosestClusters() {
        int distance = Entry.getMaxSize();
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
                if (d < distance) {
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
