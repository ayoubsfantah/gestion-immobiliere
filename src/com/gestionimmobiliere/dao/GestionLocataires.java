package com.gestionimmobiliere.dao;

import com.gestionimmobiliere.model.Locataire;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* Classe de gestion des locataires avec persistance*/
public class GestionLocataires {
    private static final String FICHIER_DONNEES = "data/locataires.dat";
    private List<Locataire> locataires;
    private int dernierId;

    public GestionLocataires() {
        this.locataires = new ArrayList<>();
        chargerDonnees();
    }

    /* Ajouter un nouveau locataire*/
    public void ajouterLocataire(Locataire locataire) {
        locataire.setId(++dernierId);
        locataires.add(locataire);
        sauvegarderDonnees();
    }

    /* Modifier un locataire existant*/
    public boolean modifierLocataire(Locataire locataireModifie) {
        for (int i = 0; i < locataires.size(); i++) {
            if (locataires.get(i).getId() == locataireModifie.getId()) {
                locataires.set(i, locataireModifie);
                sauvegarderDonnees();
                return true;
            }
        }
        return false;
    }

    /* Supprimer un locataire*/
    public boolean supprimerLocataire(int id) {
        Locataire locataire = locataires.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
        if (locataire != null) {
            locataires.remove(locataire);
            sauvegarderDonnees();
            return true;
        }
        return false;
    }

    /* Obtenir un locataire par son ID*/
    public Locataire obtenirLocataire(int id) {
        return locataires.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /* Obtenir tous les locataires*/
    public List<Locataire> obtenirTousLesLocataires() {
        return new ArrayList<>(locataires);
    }

    /* Rechercher des locataires par critères */
    public List<Locataire> rechercherLocataires(String recherche) {
        List<Locataire> resultats = new ArrayList<>();
        String rechercheLower = recherche.toLowerCase();
        for (Locataire locataire : locataires) {
            if (locataire.getNom().toLowerCase().contains(rechercheLower) ||
                locataire.getPrenom().toLowerCase().contains(rechercheLower) ||
                locataire.getEmail().toLowerCase().contains(rechercheLower) ||
                locataire.getTelephone().contains(recherche)) {
                resultats.add(locataire);
            }
        }
        return resultats;
    }

    /* Charger les données depuis le fichier */
    @SuppressWarnings("unchecked")
    private void chargerDonnees() {
        File fichier = new File(FICHIER_DONNEES);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fichier))) {
                locataires = (List<Locataire>) ois.readObject();
                // Trouver le dernier ID utilisé
                dernierId = locataires.stream()
                        .mapToInt(Locataire::getId)
                        .max()
                        .orElse(0);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur lors du chargement des locataires: " + e.getMessage());
                locataires = new ArrayList<>();
            }
        } else {
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
                oos.writeObject(locataires);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des locataires: " + e.getMessage());
        }
    }
}

