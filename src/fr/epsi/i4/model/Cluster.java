package fr.epsi.i4.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cluster {

    private List<Entry> data;

    public Cluster() {
        data = new ArrayList<>();
    }

    public List<Entry> getData() {
        return data;
    }

    public void setData(List<Entry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "data=" + data +
                '}';
    }

    public Entry addEntry(Entry entry) {
        if (data.add(entry)) {
            return entry;
        }
        return null;
    }

    public int getMaximumDistance() {
        int distance = 0;

        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                distance = Math.max(distance, data.get(i).calculateDistance(data.get(j)));
            }
        }

        return distance;
    }

    public Entry getMiddleEntry(Entry base) {
        Entry entry = new Entry();

        if (data.isEmpty()) {
            entry = base;
        } else {
            try {
                for (Field field : Entry.class.getFields()) {
                    field.set(entry, getValueWithBetterRatio(field));
                }
            } catch (Exception e) {
                System.out.println("getMiddleEntry : " + e);
            }
        }

        return entry;
    }

    public int getValueWithBetterRatio(Field field) {
        int value = -1;

        double ratio = 0d;

        try {
            for (Entry entry : data) {
                double r;
                if ((r = getRatio(field, (int) field.get(entry))) > ratio) {
                    value = (int) field.get(entry);
                }
            }
        } catch (Exception e) {
            System.out.println("getRatio : " + e);
        }

        return value;
    }

    public double getRatio(Field field, int value) {
        double count = 0d;
        try {
            for (Entry entry : data) {
                if (field.get(entry).equals(value)) {
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("getRatio : " + e);
        }
        return count / data.size();
    }

    public Set<Integer> getUniqueValues(Field field) {
        Set<Integer> uniqueValues = new HashSet<>();

        try {
            for (Entry entry : data) {
                uniqueValues.add((Integer) field.get(entry));
            }
        } catch (Exception e) {
            System.out.println("getUniqueValues : " + e);
        }

        return uniqueValues;
    }
}
