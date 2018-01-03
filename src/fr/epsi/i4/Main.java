package fr.epsi.i4;

import fr.epsi.i4.model.Master;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Initialisation de l'input
        Scanner scanner = new Scanner(System.in);

        // Première question
        System.out.println("Allez-vous utiliser le random? [o/n]");

        // Récupération de la réponse
        String answer = scanner.next();

        // Initialisation du random par défaut
        boolean useRandom = false;

        // Si la réponse est à o, on active le random
        if (answer.equals("o")) {
            useRandom = true;
            System.out.println("Avec random");
        } else {
            System.out.println("Sans random");
        }

        System.out.println("En combien de clusters voulez-vous diviser vos données? (0 pour quitter)");
        while (scanner.hasNext()) {
            // On demande un chiffre
            String value = scanner.next();

            try {
                // On récupère le nombre donné
                int nb = Integer.parseInt(value);

                // Si le nombre est à 0, on arrête le programme
                if (nb == 0) {
                    break;
                }

                // Instanciation de la classe contenant l'algo
                Master master = new Master("./entries.txt");

                // Récupération de time
                long time = System.currentTimeMillis();

                // Exécution de l'algorithme
                master.dispatch(nb, useRandom);

                // Affichage du temps d'exécution
                System.out.println("\nLe programme s'est exécuté en " + (System.currentTimeMillis() - time) + " ms");

                // Affichage du résultat
                System.out.println(master.toString());

                // Affichage de la nouvelle exécution
                System.out.println("En combien de clusters voulez-vous diviser vos données? (0 pour quitter)");
            } catch (NumberFormatException e) {
                System.out.println("Vous devez entrer un nombre!");
                System.out.println("En combien de clusters voulez-vous diviser vos données? (0 pour quitter)");
            }
        }
    }
}
