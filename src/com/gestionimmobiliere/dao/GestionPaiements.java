package com.gestionimmobiliere.dao;

import com.gestionimmobiliere.model.Paiement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* Classe de gestion des paiements avec persistance*/
public class GestionPaiements {
    private static final String FICHIER_DONNEES = "data/paiements.dat";
    private List<Paiement> paiements;
    private int dernierId;

    public GestionPaiements() {
        this.paiements = new ArrayList<>();
        chargerDonnees();
    }

    /* Ajouter un nouveau paiement*/
    public void ajouterPaiement(Paiement paiement) {
        paiement.setId(++dernierId);
        paiements.add(paiement);
        sauvegarderDonnees();
    }

    /* Modifier un paiement existant*/
    public boolean modifierPaiement(Paiement paiementModifie) {
        for (int i = 0; i < paiements.size(); i++) {
            if (paiements.get(i).getId() == paiementModifie.getId()) {
                paiements.set(i, paiementModifie);
                sauvegarderDonnees();
                return true;
            }
        }
        return false;
    }

    /* Supprimer un paiement*/
    public boolean supprimerPaiement(int id) {
        Paiement paiement = paiements.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (paiement != null) {
            paiements.remove(paiement);
            sauvegarderDonnees();
            return true;
        }
        return false;
    }

    /* Obtenir un paiement par son ID*/
    public Paiement obtenirPaiement(int id) {
        return paiements.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /* Obtenir tous les paiements*/
    public List<Paiement> obtenirTousLesPaiements() {
        return new ArrayList<>(paiements);
    }

    /* Obtenir les paiements d'un loyer*/
    public List<Paiement> obtenirPaiementsParLoyer(int loyerId) {
        List<Paiement> resultats = new ArrayList<>();
        for (Paiement paiement : paiements) {
            if (paiement.getLoyerId() == loyerId) {
                resultats.add(paiement);
            }
        }
        return resultats;
    }

    /* Obtenir les paiements payés*/
    public List<Paiement> obtenirPaiementsPayes() {
        List<Paiement> payes = new ArrayList<>();
        for (Paiement paiement : paiements) {
            if ("Payé".equals(paiement.getStatut())) {
                payes.add(paiement);
            }
        }
        return payes;
    }

    /* Obtenir les paiements en retard*/
    public List<Paiement> obtenirPaiementsEnRetard() {
        List<Paiement> enRetard = new ArrayList<>();
        for (Paiement paiement : paiements) {
            if ("Retard".equals(paiement.getStatut())) {
                enRetard.add(paiement);
            }
        }
        return enRetard;
    }

    /* Calculer le total des paiements pour un loyer*/
    public double calculerTotalPaiements(int loyerId) {
        double total = 0.0;
        for (Paiement paiement : paiements) {
            if (paiement.getLoyerId() == loyerId && "Payé".equals(paiement.getStatut())) {
                total += paiement.getMontant();
            }
        }
        return total;
    }

    /* Charger les données depuis le fichier*/
    @SuppressWarnings("unchecked")
    private void chargerDonnees() {
        File fichier = new File(FICHIER_DONNEES);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fichier))) {
                paiements = (List<Paiement>) ois.readObject();
                dernierId = paiements.stream()
                        .mapToInt(Paiement::getId)
                        .max()
                        .orElse(0);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur lors du chargement des paiements: " + e.getMessage());
                paiements = new ArrayList<>();
            }
        } else {
            fichier.getParentFile().mkdirs();
        }
    }

    /* Sauvegarder les données dans le fichier*/
    private void sauvegarderDonnees() {
        try {
            File fichier = new File(FICHIER_DONNEES);
            fichier.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(fichier))) {
                oos.writeObject(paiements);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des paiements: " + e.getMessage());
        }
    }
}

