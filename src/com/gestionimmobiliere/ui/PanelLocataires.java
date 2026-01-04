package com.gestionimmobiliere.ui;

import com.gestionimmobiliere.dao.GestionLocataires;
import com.gestionimmobiliere.model.Locataire;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panneau pour la gestion des locataires
 */
public class PanelLocataires extends JPanel {
    private GestionLocataires gestionLocataires;
    private JTable tableLocataires;
    private DefaultTableModel modelTable;
    private JTextField txtNom, txtPrenom, txtEmail, txtTelephone, txtAdresse, 
                       txtDateNaissance, txtProfession, txtGarantNom, txtGarantTelephone;
    private JTextField txtRecherche;

    public PanelLocataires(GestionLocataires gestionLocataires) {
        this.gestionLocataires = gestionLocataires;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de recherche
        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRecherche.add(new JLabel("Rechercher:"));
        txtRecherche = new JTextField(20);
        panelRecherche.add(txtRecherche);
        JButton btnRechercher = new JButton("Rechercher");
        btnRechercher.addActionListener(e -> rechercherLocataires());
        panelRecherche.add(btnRechercher);
        JButton btnActualiser = new JButton("Actualiser");
        btnActualiser.addActionListener(e -> actualiserTable());
        panelRecherche.add(btnActualiser);

        // Table des locataires
        String[] colonnes = {"ID", "Nom", "Prénom", "Email", "Téléphone"};
        modelTable = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLocataires = new JTable(modelTable);
        tableLocataires.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLocataires.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerLocataire();
            }
        });
        JScrollPane scrollTable = new JScrollPane(tableLocataires);

        // Panel de formulaire
        JPanel panelFormulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        txtNom = new JTextField(25);
        panelFormulaire.add(txtNom, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        txtPrenom = new JTextField(25);
        panelFormulaire.add(txtPrenom, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(25);
        panelFormulaire.add(txtEmail, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Téléphone:"), gbc);
        gbc.gridx = 1;
        txtTelephone = new JTextField(25);
        panelFormulaire.add(txtTelephone, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Adresse:"), gbc);
        gbc.gridx = 1;
        txtAdresse = new JTextField(25);
        panelFormulaire.add(txtAdresse, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Date de naissance:"), gbc);
        gbc.gridx = 1;
        txtDateNaissance = new JTextField(25);
        panelFormulaire.add(txtDateNaissance, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Profession:"), gbc);
        gbc.gridx = 1;
        txtProfession = new JTextField(25);
        panelFormulaire.add(txtProfession, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Garant - Nom:"), gbc);
        gbc.gridx = 1;
        txtGarantNom = new JTextField(25);
        panelFormulaire.add(txtGarantNom, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Garant - Téléphone:"), gbc);
        gbc.gridx = 1;
        txtGarantTelephone = new JTextField(25);
        panelFormulaire.add(txtGarantTelephone, gbc);

        // Panel de boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e -> ajouterLocataire());
        panelBoutons.add(btnAjouter);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(e -> modifierLocataire());
        panelBoutons.add(btnModifier);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerLocataire());
        panelBoutons.add(btnSupprimer);

        JButton btnNouveau = new JButton("Nouveau");
        btnNouveau.addActionListener(e -> nouveauLocataire());
        panelBoutons.add(btnNouveau);

        // Assemblage
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.add(panelRecherche, BorderLayout.NORTH);
        panelGauche.add(scrollTable, BorderLayout.CENTER);

        JPanel panelDroit = new JPanel(new BorderLayout());
        panelDroit.add(panelFormulaire, BorderLayout.CENTER);
        panelDroit.add(panelBoutons, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, panelDroit);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);

        actualiserTable();
    }

    private void actualiserTable() {
        modelTable.setRowCount(0);
        List<Locataire> locataires = gestionLocataires.obtenirTousLesLocataires();
        for (Locataire locataire : locataires) {
            modelTable.addRow(new Object[]{
                locataire.getId(),
                locataire.getNom(),
                locataire.getPrenom(),
                locataire.getEmail(),
                locataire.getTelephone()
            });
        }
    }

    private void rechercherLocataires() {
        String recherche = txtRecherche.getText().trim();
        if (recherche.isEmpty()) {
            actualiserTable();
            return;
        }
        modelTable.setRowCount(0);
        List<Locataire> locataires = gestionLocataires.rechercherLocataires(recherche);
        for (Locataire locataire : locataires) {
            modelTable.addRow(new Object[]{
                locataire.getId(),
                locataire.getNom(),
                locataire.getPrenom(),
                locataire.getEmail(),
                locataire.getTelephone()
            });
        }
    }

    private void selectionnerLocataire() {
        int row = tableLocataires.getSelectedRow();
        if (row >= 0) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Locataire locataire = gestionLocataires.obtenirLocataire(id);
            if (locataire != null) {
                txtNom.setText(locataire.getNom());
                txtPrenom.setText(locataire.getPrenom());
                txtEmail.setText(locataire.getEmail());
                txtTelephone.setText(locataire.getTelephone());
                txtAdresse.setText(locataire.getAdresse());
                txtDateNaissance.setText(locataire.getDateNaissance());
                txtProfession.setText(locataire.getProfession());
                txtGarantNom.setText(locataire.getGarantNom());
                txtGarantTelephone.setText(locataire.getGarantTelephone());
            }
        }
    }

    private void nouveauLocataire() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
        txtTelephone.setText("");
        txtAdresse.setText("");
        txtDateNaissance.setText("");
        txtProfession.setText("");
        txtGarantNom.setText("");
        txtGarantTelephone.setText("");
        tableLocataires.clearSelection();
    }

    private void ajouterLocataire() {
        try {
            Locataire locataire = new Locataire();
            locataire.setNom(txtNom.getText().trim());
            locataire.setPrenom(txtPrenom.getText().trim());
            locataire.setEmail(txtEmail.getText().trim());
            locataire.setTelephone(txtTelephone.getText().trim());
            locataire.setAdresse(txtAdresse.getText().trim());
            locataire.setDateNaissance(txtDateNaissance.getText().trim());
            locataire.setProfession(txtProfession.getText().trim());
            locataire.setGarantNom(txtGarantNom.getText().trim());
            locataire.setGarantTelephone(txtGarantTelephone.getText().trim());

            gestionLocataires.ajouterLocataire(locataire);
            JOptionPane.showMessageDialog(this, "Locataire ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            actualiserTable();
            nouveauLocataire();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierLocataire() {
        int row = tableLocataires.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un locataire à modifier", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Locataire locataire = gestionLocataires.obtenirLocataire(id);
            if (locataire != null) {
                locataire.setNom(txtNom.getText().trim());
                locataire.setPrenom(txtPrenom.getText().trim());
                locataire.setEmail(txtEmail.getText().trim());
                locataire.setTelephone(txtTelephone.getText().trim());
                locataire.setAdresse(txtAdresse.getText().trim());
                locataire.setDateNaissance(txtDateNaissance.getText().trim());
                locataire.setProfession(txtProfession.getText().trim());
                locataire.setGarantNom(txtGarantNom.getText().trim());
                locataire.setGarantTelephone(txtGarantTelephone.getText().trim());

                gestionLocataires.modifierLocataire(locataire);
                JOptionPane.showMessageDialog(this, "Locataire modifié avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLocataire() {
        int row = tableLocataires.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un locataire à supprimer", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce locataire?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            if (gestionLocataires.supprimerLocataire(id)) {
                JOptionPane.showMessageDialog(this, "Locataire supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
                nouveauLocataire();
            }
        }
    }
}

