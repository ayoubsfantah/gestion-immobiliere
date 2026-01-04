package com.gestionimmobiliere.ui;

import com.gestionimmobiliere.dao.GestionBiens;
import com.gestionimmobiliere.model.Bien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panneau pour la gestion des biens immobiliers
 */
public class PanelBiens extends JPanel {
    private GestionBiens gestionBiens;
    private JTable tableBiens;
    private DefaultTableModel modelTable;
    private JTextField txtAdresse, txtType, txtSuperficie, txtPieces, txtLoyer, txtDescription;
    private JComboBox<String> comboEtat;
    private JTextField txtRecherche;

    public PanelBiens(GestionBiens gestionBiens) {
        this.gestionBiens = gestionBiens;
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
        btnRechercher.addActionListener(e -> rechercherBiens());
        panelRecherche.add(btnRechercher);
        JButton btnActualiser = new JButton("Actualiser");
        btnActualiser.addActionListener(e -> actualiserTable());
        panelRecherche.add(btnActualiser);

        // Table des biens
        String[] colonnes = {"ID", "Adresse", "Type", "Superficie (m²)", "Pièces", "Loyer (€)", "État"};
        modelTable = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableBiens = new JTable(modelTable);
        tableBiens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBiens.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerBien();
            }
        });
        JScrollPane scrollTable = new JScrollPane(tableBiens);

        // Panel de formulaire
        JPanel panelFormulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulaire.add(new JLabel("Adresse:"), gbc);
        gbc.gridx = 1;
        txtAdresse = new JTextField(25);
        panelFormulaire.add(txtAdresse, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulaire.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        txtType = new JTextField(25);
        panelFormulaire.add(txtType, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulaire.add(new JLabel("Superficie (m²):"), gbc);
        gbc.gridx = 1;
        txtSuperficie = new JTextField(25);
        panelFormulaire.add(txtSuperficie, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulaire.add(new JLabel("Nombre de pièces:"), gbc);
        gbc.gridx = 1;
        txtPieces = new JTextField(25);
        panelFormulaire.add(txtPieces, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulaire.add(new JLabel("Loyer mensuel (€):"), gbc);
        gbc.gridx = 1;
        txtLoyer = new JTextField(25);
        panelFormulaire.add(txtLoyer, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulaire.add(new JLabel("État:"), gbc);
        gbc.gridx = 1;
        comboEtat = new JComboBox<>(new String[]{"Disponible", "Loué", "En rénovation"});
        panelFormulaire.add(comboEtat, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulaire.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        txtDescription = new JTextField(25);
        panelFormulaire.add(txtDescription, gbc);

        // Panel de boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e -> ajouterBien());
        panelBoutons.add(btnAjouter);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(e -> modifierBien());
        panelBoutons.add(btnModifier);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerBien());
        panelBoutons.add(btnSupprimer);

        JButton btnNouveau = new JButton("Nouveau");
        btnNouveau.addActionListener(e -> nouveauBien());
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
        List<Bien> biens = gestionBiens.obtenirTousLesBiens();
        for (Bien bien : biens) {
            modelTable.addRow(new Object[]{
                bien.getId(),
                bien.getAdresse(),
                bien.getType(),
                bien.getSuperficie(),
                bien.getNombrePieces(),
                bien.getLoyerMensuel(),
                bien.getEtat()
            });
        }
    }

    private void rechercherBiens() {
        String recherche = txtRecherche.getText().trim();
        if (recherche.isEmpty()) {
            actualiserTable();
            return;
        }
        modelTable.setRowCount(0);
        List<Bien> biens = gestionBiens.rechercherBiens(recherche);
        for (Bien bien : biens) {
            modelTable.addRow(new Object[]{
                bien.getId(),
                bien.getAdresse(),
                bien.getType(),
                bien.getSuperficie(),
                bien.getNombrePieces(),
                bien.getLoyerMensuel(),
                bien.getEtat()
            });
        }
    }

    private void selectionnerBien() {
        int row = tableBiens.getSelectedRow();
        if (row >= 0) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Bien bien = gestionBiens.obtenirBien(id);
            if (bien != null) {
                txtAdresse.setText(bien.getAdresse());
                txtType.setText(bien.getType());
                txtSuperficie.setText(String.valueOf(bien.getSuperficie()));
                txtPieces.setText(String.valueOf(bien.getNombrePieces()));
                txtLoyer.setText(String.valueOf(bien.getLoyerMensuel()));
                comboEtat.setSelectedItem(bien.getEtat());
                txtDescription.setText(bien.getDescription());
            }
        }
    }

    private void nouveauBien() {
        txtAdresse.setText("");
        txtType.setText("");
        txtSuperficie.setText("");
        txtPieces.setText("");
        txtLoyer.setText("");
        comboEtat.setSelectedIndex(0);
        txtDescription.setText("");
        tableBiens.clearSelection();
    }

    private void ajouterBien() {
        try {
            Bien bien = new Bien();
            bien.setAdresse(txtAdresse.getText().trim());
            bien.setType(txtType.getText().trim());
            bien.setSuperficie(Double.parseDouble(txtSuperficie.getText().trim()));
            bien.setNombrePieces(Integer.parseInt(txtPieces.getText().trim()));
            bien.setLoyerMensuel(Double.parseDouble(txtLoyer.getText().trim()));
            bien.setEtat((String) comboEtat.getSelectedItem());
            bien.setDescription(txtDescription.getText().trim());

            gestionBiens.ajouterBien(bien);
            JOptionPane.showMessageDialog(this, "Bien ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            actualiserTable();
            nouveauBien();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierBien() {
        int row = tableBiens.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bien à modifier", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Bien bien = gestionBiens.obtenirBien(id);
            if (bien != null) {
                bien.setAdresse(txtAdresse.getText().trim());
                bien.setType(txtType.getText().trim());
                bien.setSuperficie(Double.parseDouble(txtSuperficie.getText().trim()));
                bien.setNombrePieces(Integer.parseInt(txtPieces.getText().trim()));
                bien.setLoyerMensuel(Double.parseDouble(txtLoyer.getText().trim()));
                bien.setEtat((String) comboEtat.getSelectedItem());
                bien.setDescription(txtDescription.getText().trim());

                gestionBiens.modifierBien(bien);
                JOptionPane.showMessageDialog(this, "Bien modifié avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerBien() {
        int row = tableBiens.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bien à supprimer", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce bien?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            if (gestionBiens.supprimerBien(id)) {
                JOptionPane.showMessageDialog(this, "Bien supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
                nouveauBien();
            }
        }
    }
}

