package fr.epsi.i4.model;

public class Cluster {

    private int[] lines;

    public Cluster(int size) {
        lines = new int[size];
        for (int i = 0; i < size; i++) {
            lines[i] = -1;
        }
    }

    public int[] getLines() {
        return lines;
    }

    public boolean isEmpty() {
        boolean empty = true;
        int i = 0;
        while (i < lines.length && empty) {
            if (lines[i] != -1) {
                empty = false;
            }
        }
        return empty;
    }
}
