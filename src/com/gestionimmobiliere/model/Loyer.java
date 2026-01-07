package com.gestionimmobiliere.model;

import java.io.Serializable;

/* Classe représentant un contrat de location (loyer)*/
public class Loyer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int bienId;
    private int locataireId;
    private String dateDebut;
    private String dateFin;
    private double montantMensuel;
    private String dateEcheance; // Jour du mois pour le paiement
    private String etat; // Actif, Résilié, Terminé
    private String garantie; // Montant de la garantie
    private String observations;

    public Loyer() {
    }

    public Loyer(int id, int bienId, int locataireId, String dateDebut, 
                 String dateFin, double montantMensuel, String dateEcheance, 
                 String etat, String garantie, String observations) {
        this.id = id;
        this.bienId = bienId;
        this.locataireId = locataireId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montantMensuel = montantMensuel;
        this.dateEcheance = dateEcheance;
        this.etat = etat;
        this.garantie = garantie;
        this.observations = observations;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBienId() {
        return bienId;
    }

    public void setBienId(int bienId) {
        this.bienId = bienId;
    }

    public int getLocataireId() {
        return locataireId;
    }

    public void setLocataireId(int locataireId) {
        this.locataireId = locataireId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public double getMontantMensuel() {
        return montantMensuel;
    }

    public void setMontantMensuel(double montantMensuel) {
        this.montantMensuel = montantMensuel;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getGarantie() {
        return garantie;
    }

    public void setGarantie(String garantie) {
        this.garantie = garantie;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Loyer [ID: " + id + ", Bien ID: " + bienId + ", Locataire ID: " + locataireId + 
               ", Montant: " + montantMensuel + " €, État: " + etat + "]";
    }
}

