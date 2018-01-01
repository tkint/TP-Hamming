package fr.epsi.i4;

import fr.epsi.i4.model.Entry;
import fr.epsi.i4.model.Master;

public class Main {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        try {
            Master master = new Master();

            generateData(master);

            master.dispatch(2);

            System.out.println(master.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("\nLe programme s'est exécuté en " + (System.currentTimeMillis() - time) + " ms");
    }

    public static void generateData(Master master) throws Exception {
        master.addEntry(new Entry(1, 2, 2, 1)); // 1
        master.addEntry(new Entry(1, 1, 2, 1)); // 2
        master.addEntry(new Entry(2, 2, 2, 1)); // 3
        master.addEntry(new Entry(1, 2, 1, 1)); // 4
        master.addEntry(new Entry(1, 2, 2, 2)); // 5
        master.addEntry(new Entry(1, 1, 1, 2)); // 6
        master.addEntry(new Entry(2, 2, 2, 2)); // 7
        master.addEntry(new Entry(2, 1, 1, 1)); // 8
        master.addEntry(new Entry(2, 1, 1, 2)); // 9
        master.addEntry(new Entry(2, 2, 1, 2)); // 10
    }

    public static void generateDataBis(Master master) throws Exception {
        master.addEntry(new Entry(0, 2, 2, 0)); // 1
        master.addEntry(new Entry(0, 1, 2, 0)); // 2
        master.addEntry(new Entry(1, 2, 1, 1)); // 3
        master.addEntry(new Entry(0, 2, 1, 0)); // 4
        master.addEntry(new Entry(0, 2, 2, 1)); // 5
        master.addEntry(new Entry(0, 1, 1, 1)); // 6
        master.addEntry(new Entry(1, 2, 2, 1)); // 7
        master.addEntry(new Entry(1, 1, 1, 0)); // 8
        master.addEntry(new Entry(1, 1, 1, 1)); // 9
        master.addEntry(new Entry(1, 2, 1, 1)); // 10
    }
}
