package com.gestionimmobiliere.dao;

import com.gestionimmobiliere.model.Loyer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de gestion des loyers avec persistance
 */
public class GestionLoyers {
    private static final String FICHIER_DONNEES = "data/loyers.dat";
    private List<Loyer> loyers;
    private int dernierId;

    public GestionLoyers() {
        this.loyers = new ArrayList<>();
        chargerDonnees();
    }

    /**
     * Ajouter un nouveau loyer
     */
    public void ajouterLoyer(Loyer loyer) {
        loyer.setId(++dernierId);
        loyers.add(loyer);
        sauvegarderDonnees();
    }

    /**
     * Modifier un loyer existant
     */
    public boolean modifierLoyer(Loyer loyerModifie) {
        for (int i = 0; i < loyers.size(); i++) {
            if (loyers.get(i).getId() == loyerModifie.getId()) {
                loyers.set(i, loyerModifie);
                sauvegarderDonnees();
                return true;
            }
        }
        return false;
    }

    /**
     * Supprimer un loyer
     */
    public boolean supprimerLoyer(int id) {
        Loyer loyer = loyers.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
        if (loyer != null) {
            loyers.remove(loyer);
            sauvegarderDonnees();
            return true;
        }
        return false;
    }

    /**
     * Obtenir un loyer par son ID
     */
    public Loyer obtenirLoyer(int id) {
        return loyers.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtenir tous les loyers
     */
    public List<Loyer> obtenirTousLesLoyers() {
        return new ArrayList<>(loyers);
    }

    /**
     * Obtenir les loyers actifs
     */
    public List<Loyer> obtenirLoyersActifs() {
        List<Loyer> actifs = new ArrayList<>();
        for (Loyer loyer : loyers) {
            if ("Actif".equals(loyer.getEtat())) {
                actifs.add(loyer);
            }
        }
        return actifs;
    }

    /**
     * Obtenir les loyers d'un bien
     */
    public List<Loyer> obtenirLoyersParBien(int bienId) {
        List<Loyer> resultats = new ArrayList<>();
        for (Loyer loyer : loyers) {
            if (loyer.getBienId() == bienId) {
                resultats.add(loyer);
            }
        }
        return resultats;
    }

    /**
     * Obtenir les loyers d'un locataire
     */
    public List<Loyer> obtenirLoyersParLocataire(int locataireId) {
        List<Loyer> resultats = new ArrayList<>();
        for (Loyer loyer : loyers) {
            if (loyer.getLocataireId() == locataireId) {
                resultats.add(loyer);
            }
        }
        return resultats;
    }

    /**
     * Charger les données depuis le fichier
     */
    @SuppressWarnings("unchecked")
    private void chargerDonnees() {
        File fichier = new File(FICHIER_DONNEES);
        if (fichier.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fichier))) {
                loyers = (List<Loyer>) ois.readObject();
                dernierId = loyers.stream()
                        .mapToInt(Loyer::getId)
                        .max()
                        .orElse(0);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur lors du chargement des loyers: " + e.getMessage());
                loyers = new ArrayList<>();
            }
        } else {
            fichier.getParentFile().mkdirs();
        }
    }

    /**
     * Sauvegarder les données dans le fichier
     */
    private void sauvegarderDonnees() {
        try {
            File fichier = new File(FICHIER_DONNEES);
            fichier.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(fichier))) {
                oos.writeObject(loyers);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des loyers: " + e.getMessage());
        }
    }
}

