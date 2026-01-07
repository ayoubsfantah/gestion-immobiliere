package com.gestionimmobiliere.model;

import java.io.Serializable;

/* Classe représentant un bien immobilier*/
public class Bien implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String adresse;
    private String type; // Appartement, Maison, Studio, etc.
    private double superficie;
    private int nombrePieces;
    private double loyerMensuel;
    private String etat; // Disponible, Loué, En rénovation
    private String description;

    public Bien() {
    }

    public Bien(int id, String adresse, String type, double superficie, 
                int nombrePieces, double loyerMensuel, String etat, String description) {
        this.id = id;
        this.adresse = adresse;
        this.type = type;
        this.superficie = superficie;
        this.nombrePieces = nombrePieces;
        this.loyerMensuel = loyerMensuel;
        this.etat = etat;
        this.description = description;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public int getNombrePieces() {
        return nombrePieces;
    }

    public void setNombrePieces(int nombrePieces) {
        this.nombrePieces = nombrePieces;
    }

    public double getLoyerMensuel() {
        return loyerMensuel;
    }

    public void setLoyerMensuel(double loyerMensuel) {
        this.loyerMensuel = loyerMensuel;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Bien [ID: " + id + ", Adresse: " + adresse + ", Type: " + type + 
               ", Superficie: " + superficie + " m², Pièces: " + nombrePieces + 
               ", Loyer: " + loyerMensuel + " €, État: " + etat + "]";
    }
}

