package fr.epsi.i4.model;

import java.lang.reflect.Field;
import java.util.*;

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
}
