package com.gestionimmobiliere.model;

import java.io.Serializable;

/**
 * Classe représentant un paiement de loyer
 */
public class Paiement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int loyerId;
    private String datePaiement;
    private double montant;
    private String modePaiement; // Espèces, Chèque, Virement, Carte
    private String reference; // Numéro de chèque, référence virement, etc.
    private String periode; // Mois/Année concerné (ex: "01/2024")
    private String statut; // Payé, En attente, Retard
    private String observations;

    public Paiement() {
    }

    public Paiement(int id, int loyerId, String datePaiement, double montant, 
                    String modePaiement, String reference, String periode, 
                    String statut, String observations) {
        this.id = id;
        this.loyerId = loyerId;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.reference = reference;
        this.periode = periode;
        this.statut = statut;
        this.observations = observations;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoyerId() {
        return loyerId;
    }

    public void setLoyerId(int loyerId) {
        this.loyerId = loyerId;
    }

    public String getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Paiement [ID: " + id + ", Loyer ID: " + loyerId + ", Date: " + datePaiement + 
               ", Montant: " + montant + " €, Mode: " + modePaiement + ", Période: " + periode + 
               ", Statut: " + statut + "]";
    }
}

