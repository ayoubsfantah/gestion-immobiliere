package com.gestionimmobiliere.dao;

import com.gestionimmobiliere.model.Bien;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/* Classe de gestion des biens immobiliers avec persistance*/
public class GestionBiens {
    private static final String FICHIER_DONNEES = "data/biens.dat";
    private List<Bien> biens;
    private int dernierId;

    public GestionBiens() {
        this.biens = new ArrayList<>();
        chargerDonnees();
    }
    /* Ajouter un nouveau bien*/
    public void ajouterBien(Bien bien) {
        bien.setId(++dernierId);
        biens.add(bien);
        sauvegarderDonnees();
    }
    /* Modifier un bien existant*/
    public boolean modifierBien(Bien bienModifie) {
        for (int i = 0; i < biens.size(); i++) {
            if (biens.get(i).getId() == bienModifie.getId()) {
                biens.set(i, bienModifie);
                sauvegarderDonnees();
                return true;
            }
        }
        return false;
    }
    /* Supprimer un bien*/
    public boolean supprimerBien(int id) {
        Bien bien = biens.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
        if (bien != null) {
            biens.remove(bien);
            sauvegarderDonnees();
            return true;
        }
        return false;
    }

    /* Obtenir un bien par son ID*/
    public Bien obtenirBien(int id) {
        return biens.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /* Obtenir tous les biens*/
    public List<Bien> obtenirTousLesBiens() {
        return new ArrayList<>(biens);
    }

    /* Rechercher des biens par critères */
    public List<Bien> rechercherBiens(String recherche) {
        List<Bien> resultats = new ArrayList<>();
        String rechercheLower = recherche.toLowerCase();
        for (Bien bien : biens) {
            if (bien.getAdresse().toLowerCase().contains(rechercheLower) ||
                bien.getType().toLowerCase().contains(rechercheLower) ||
                bien.getEtat().toLowerCase().contains(rechercheLower)) {
                resultats.add(bien);
            }
        }
        return resultats;
    }

    /* Obtenir les biens disponibles */
    public List<Bien> obtenirBiensDisponibles() {
        List<Bien> disponibles = new ArrayList<>();
        for (Bien bien : biens) {
            if ("Disponible".equals(bien.getEtat())) {
                disponibles.add(bien);
            }
        }
        return disponibles;
    }

    /* Charger les données depuis le fichier */
    @SuppressWarnings("unchecked")
    private void chargerDonnees() {
        File fichier = new File(FICHIER_DONNEES);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fichier))) {
                biens = (List<Bien>) ois.readObject();
                // Trouver le dernier ID utilisé
                dernierId = biens.stream()
                        .mapToInt(Bien::getId)
                        .max()
                        .orElse(0);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur lors du chargement des biens: " + e.getMessage());
                biens = new ArrayList<>();
            }
        } else {
            // Créer le répertoire si nécessaire
            fichier.getParentFile().mkdirs();
        }
    }

    /* Sauvegarder les données dans le fichier */
    private void sauvegarderDonnees() {
        try {
            File fichier = new File(FICHIER_DONNEES);
            fichier.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(fichier))) {
                oos.writeObject(biens);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des biens: " + e.getMessage());
        }
    }
}

