package fr.epsi.i4.model;

import java.security.InvalidParameterException;

public class Entry {

    private static int nextId = 1;
    private static int size = -1;

    private int[] data;

    private int id;

    public Entry(int... data) {
        if (size > -1 && data.length != size) {
            throw new InvalidParameterException("Impossible d'ajouter ces data, vous allez casser l'intégrité des données");
        }
        id = nextId;
        nextId++;
        size = data.length;
        this.data = data;
    }

    /**
     * Retourne la taille maximale d'un cluster +1
     * pour la comparaison des distances max
     *
     * @return int
     */
    public static int getMaxSize() {
        return size + 1;
    }

    public static void resetNextId() {
        nextId = 1;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return id - 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Entry{");
        stringBuilder.append("id = ").append(id).append(", data = [");
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(data[i]);
            if (i < data.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    /**
     * Calcule la distance de Hamming avec une autre entrée
     *
     * @param entry Entry
     * @return int
     */
    public int distanceHamming(Entry entry) {
        int distance = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] != entry.data[i]) {
                distance++;
            }
        }

        return distance;
    }

    /**
     * Calcule la distance maximale avec un autre cluster
     *
     * @param cluster Cluster
     * @return int
     */
    public int getMaximumDistanceWithCluster(Cluster cluster) {
        int distance = 0;
        // Pour chaque entrée du cluster distant
        for (Entry entry : cluster.getEntries()) {
            // On récupère la distance entre les deux entrées
            int d = Master.getDistance(getPosition(), entry.getPosition());
            // Si elle est supérieure à la distance déjà enregistrée
            if (d > distance) {
                // On enregistre la nouvelle distance
                distance = d;
            }
        }
        return distance;
    }
}
