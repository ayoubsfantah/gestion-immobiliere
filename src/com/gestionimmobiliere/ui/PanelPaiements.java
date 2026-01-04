package com.gestionimmobiliere.ui;

import com.gestionimmobiliere.dao.GestionLoyers;
import com.gestionimmobiliere.dao.GestionPaiements;
import com.gestionimmobiliere.model.Paiement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panneau pour la gestion des paiements
 */
public class PanelPaiements extends JPanel {
    private GestionPaiements gestionPaiements;
    private GestionLoyers gestionLoyers;
    private JTable tablePaiements;
    private DefaultTableModel modelTable;
    private JComboBox<Integer> comboLoyerId;
    private JTextField txtDatePaiement, txtMontant, txtReference, txtPeriode, txtObservations;
    private JComboBox<String> comboModePaiement, comboStatut;

    public PanelPaiements(GestionPaiements gestionPaiements, GestionLoyers gestionLoyers) {
        this.gestionPaiements = gestionPaiements;
        this.gestionLoyers = gestionLoyers;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table des paiements
        String[] colonnes = {"ID", "Loyer ID", "Date", "Montant (€)", "Mode", "Période", "Statut"};
        modelTable = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePaiements = new JTable(modelTable);
        tablePaiements.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePaiements.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerPaiement();
            }
        });
        JScrollPane scrollTable = new JScrollPane(tablePaiements);

        // Panel de formulaire
        JPanel panelFormulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Loyer ID:"), gbc);
        gbc.gridx = 1;
        comboLoyerId = new JComboBox<>();
        actualiserComboLoyers();
        panelFormulaire.add(comboLoyerId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Date paiement (JJ/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        txtDatePaiement = new JTextField(25);
        panelFormulaire.add(txtDatePaiement, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Montant (€):"), gbc);
        gbc.gridx = 1;
        txtMontant = new JTextField(25);
        panelFormulaire.add(txtMontant, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Mode de paiement:"), gbc);
        gbc.gridx = 1;
        comboModePaiement = new JComboBox<>(new String[]{"Espèces", "Chèque", "Virement", "Carte"});
        panelFormulaire.add(comboModePaiement, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Référence:"), gbc);
        gbc.gridx = 1;
        txtReference = new JTextField(25);
        panelFormulaire.add(txtReference, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Période (MM/AAAA):"), gbc);
        gbc.gridx = 1;
        txtPeriode = new JTextField(25);
        panelFormulaire.add(txtPeriode, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Statut:"), gbc);
        gbc.gridx = 1;
        comboStatut = new JComboBox<>(new String[]{"Payé", "En attente", "Retard"});
        panelFormulaire.add(comboStatut, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Observations:"), gbc);
        gbc.gridx = 1;
        txtObservations = new JTextField(25);
        panelFormulaire.add(txtObservations, gbc);

        // Panel de boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e -> ajouterPaiement());
        panelBoutons.add(btnAjouter);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(e -> modifierPaiement());
        panelBoutons.add(btnModifier);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerPaiement());
        panelBoutons.add(btnSupprimer);

        JButton btnNouveau = new JButton("Nouveau");
        btnNouveau.addActionListener(e -> nouveauPaiement());
        panelBoutons.add(btnNouveau);

        JButton btnActualiser = new JButton("Actualiser");
        btnActualiser.addActionListener(e -> actualiserTable());
        panelBoutons.add(btnActualiser);

        // Assemblage
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.add(scrollTable, BorderLayout.CENTER);

        JPanel panelDroit = new JPanel(new BorderLayout());
        panelDroit.add(panelFormulaire, BorderLayout.CENTER);
        panelDroit.add(panelBoutons, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, panelDroit);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);

        actualiserTable();
    }

    private void actualiserComboLoyers() {
        comboLoyerId.removeAllItems();
        gestionLoyers.obtenirTousLesLoyers().forEach(loyer -> comboLoyerId.addItem(loyer.getId()));
    }

    private void actualiserTable() {
        modelTable.setRowCount(0);
        List<Paiement> paiements = gestionPaiements.obtenirTousLesPaiements();
        for (Paiement paiement : paiements) {
            modelTable.addRow(new Object[]{
                paiement.getId(),
                paiement.getLoyerId(),
                paiement.getDatePaiement(),
                paiement.getMontant(),
                paiement.getModePaiement(),
                paiement.getPeriode(),
                paiement.getStatut()
            });
        }
        actualiserComboLoyers();
    }

    private void selectionnerPaiement() {
        int row = tablePaiements.getSelectedRow();
        if (row >= 0) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Paiement paiement = gestionPaiements.obtenirPaiement(id);
            if (paiement != null) {
                comboLoyerId.setSelectedItem(paiement.getLoyerId());
                txtDatePaiement.setText(paiement.getDatePaiement());
                txtMontant.setText(String.valueOf(paiement.getMontant()));
                comboModePaiement.setSelectedItem(paiement.getModePaiement());
                txtReference.setText(paiement.getReference());
                txtPeriode.setText(paiement.getPeriode());
                comboStatut.setSelectedItem(paiement.getStatut());
                txtObservations.setText(paiement.getObservations());
            }
        }
    }

    private void nouveauPaiement() {
        comboLoyerId.setSelectedIndex(0);
        txtDatePaiement.setText("");
        txtMontant.setText("");
        comboModePaiement.setSelectedIndex(0);
        txtReference.setText("");
        txtPeriode.setText("");
        comboStatut.setSelectedIndex(0);
        txtObservations.setText("");
        tablePaiements.clearSelection();
    }

    private void ajouterPaiement() {
        try {
            Paiement paiement = new Paiement();
            paiement.setLoyerId((Integer) comboLoyerId.getSelectedItem());
            paiement.setDatePaiement(txtDatePaiement.getText().trim());
            paiement.setMontant(Double.parseDouble(txtMontant.getText().trim()));
            paiement.setModePaiement((String) comboModePaiement.getSelectedItem());
            paiement.setReference(txtReference.getText().trim());
            paiement.setPeriode(txtPeriode.getText().trim());
            paiement.setStatut((String) comboStatut.getSelectedItem());
            paiement.setObservations(txtObservations.getText().trim());

            gestionPaiements.ajouterPaiement(paiement);
            JOptionPane.showMessageDialog(this, "Paiement ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            actualiserTable();
            nouveauPaiement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierPaiement() {
        int row = tablePaiements.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un paiement à modifier", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Paiement paiement = gestionPaiements.obtenirPaiement(id);
            if (paiement != null) {
                paiement.setLoyerId((Integer) comboLoyerId.getSelectedItem());
                paiement.setDatePaiement(txtDatePaiement.getText().trim());
                paiement.setMontant(Double.parseDouble(txtMontant.getText().trim()));
                paiement.setModePaiement((String) comboModePaiement.getSelectedItem());
                paiement.setReference(txtReference.getText().trim());
                paiement.setPeriode(txtPeriode.getText().trim());
                paiement.setStatut((String) comboStatut.getSelectedItem());
                paiement.setObservations(txtObservations.getText().trim());

                gestionPaiements.modifierPaiement(paiement);
                JOptionPane.showMessageDialog(this, "Paiement modifié avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerPaiement() {
        int row = tablePaiements.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un paiement à supprimer", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce paiement?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            if (gestionPaiements.supprimerPaiement(id)) {
                JOptionPane.showMessageDialog(this, "Paiement supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
                nouveauPaiement();
            }
        }
    }
}

