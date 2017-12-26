package fr.epsi.i4;

import fr.epsi.i4.model.Entry;
import fr.epsi.i4.model.Master;

public class Main {

    public static void main(String[] args) {
        Master master = new Master();

        generateData(master);

        master.dispatch(2, false);

        System.out.println(master.toString());

//        System.out.println(master.displayDistances());
    }

    public static void generateData(Master master) {
        master.addEntry(new Entry(1, 1, 2, 2, 1)); // 1
        master.addEntry(new Entry(2, 1, 1, 2, 1)); // 2
        master.addEntry(new Entry(3, 2, 2, 2, 1)); // 3
        master.addEntry(new Entry(4, 1, 2, 1, 1)); // 4
        master.addEntry(new Entry(5, 1, 2, 2, 2)); // 5
        master.addEntry(new Entry(6, 1, 1, 1, 2)); // 6
        master.addEntry(new Entry(7, 2, 2, 2, 2)); // 7
        master.addEntry(new Entry(8, 2, 1, 1, 1)); // 8
        master.addEntry(new Entry(9, 2, 1, 1, 2)); // 9
        master.addEntry(new Entry(10, 2, 2, 1, 2)); // 10
    }
}
