package fr.epsi.i4;

import fr.epsi.i4.model.Entry;
import fr.epsi.i4.model.Master;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class Main {

    private static String message = "En combien de clusters voulez-vous diviser vos données? (0 pour quitter)";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Allez-vous utiliser le random? [o/n]");

        String answer = scanner.next();

        boolean useRandom = false;

        if (answer.equals("o")) {
            useRandom = true;
            System.out.println("Avec random");
        } else {
            System.out.println("Sans random");
        }

        System.out.println(message);
        while (scanner.hasNext()) {
            String value = scanner.next();

            try {
                int nb = Integer.parseInt(value);

                if (nb == 0) {
                    break;
                }

                Master master = new Master();

                generateData(master);

                long time = System.currentTimeMillis();

                master.dispatch(nb, useRandom);

                System.out.println("\nLe programme s'est exécuté en " + (System.currentTimeMillis() - time) + " ms");

                System.out.println(master.toString());
            } catch (NumberFormatException e) {
                System.out.println("Vous devez entrer un nombre!");
            } finally {
                System.out.println(message);
            }
        }

    }

    private static void generateData(Master master) {
        Entry.resetNextId();

        File file = new File("./entries.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] stringValues = line.split(" ");

                int[] values = new int[stringValues.length];

                for (int i = 0; i < stringValues.length; i++) {
                    values[i] = Integer.valueOf(stringValues[i]);
                }

                master.addEntry(new Entry(values));
            }
        } catch (NumberFormatException ex) {
            System.out.println("Mauvais format de données");
            System.exit(1);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier est introuvable");
            System.exit(1);
        }
    }
}
