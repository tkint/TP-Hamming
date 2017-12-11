package fr.epsi.i4.model;

import java.lang.reflect.Field;

public class Entry {

    public int couleur;
    public int noyaux;
    public int flagelles;
    public int membrane;

    public Entry() {
    }

    public Entry(int couleur, int noyaux, int flagelles, int membrane) {
        this.couleur = couleur;
        this.noyaux = noyaux;
        this.flagelles = flagelles;
        this.membrane = membrane;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public int getNoyaux() {
        return noyaux;
    }

    public void setNoyaux(int noyaux) {
        this.noyaux = noyaux;
    }

    public int getFlagelles() {
        return flagelles;
    }

    public void setFlagelles(int flagelles) {
        this.flagelles = flagelles;
    }

    public int getMembrane() {
        return membrane;
    }

    public void setMembrane(int membrane) {
        this.membrane = membrane;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "couleur=" + couleur +
                ", noyaux=" + noyaux +
                ", flagelles=" + flagelles +
                ", membrane=" + membrane +
                '}';
    }

    public int calculateDistance(Entry entry) {
        int distance = 0;

        try {
            for (Field field : getClass().getDeclaredFields()) {
                if (!field.get(this).equals(field.get(entry))) {
                    distance++;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return distance;
    }
}
