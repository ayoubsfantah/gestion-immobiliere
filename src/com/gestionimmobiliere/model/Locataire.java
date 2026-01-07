package com.gestionimmobiliere.model;

import java.io.Serializable;

/* Classe représentant un locataire*/
public class Locataire implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String dateNaissance;
    private String profession;
    private String garantNom;
    private String garantTelephone;

    public Locataire() {
    }

    public Locataire(int id, String nom, String prenom, String email, 
                     String telephone, String adresse, String dateNaissance, 
                     String profession, String garantNom, String garantTelephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.profession = profession;
        this.garantNom = garantNom;
        this.garantTelephone = garantTelephone;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getGarantNom() {
        return garantNom;
    }

    public void setGarantNom(String garantNom) {
        this.garantNom = garantNom;
    }

    public String getGarantTelephone() {
        return garantTelephone;
    }

    public void setGarantTelephone(String garantTelephone) {
        this.garantTelephone = garantTelephone;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    @Override
    public String toString() {
        return "Locataire [ID: " + id + ", Nom: " + getNomComplet() + 
               ", Email: " + email + ", Téléphone: " + telephone + "]";
    }
}

