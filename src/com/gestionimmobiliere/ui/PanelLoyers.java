package com.gestionimmobiliere.ui;

import com.gestionimmobiliere.dao.*;
import com.gestionimmobiliere.model.Loyer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panneau pour la gestion des loyers
 */
public class PanelLoyers extends JPanel {
    private GestionLoyers gestionLoyers;
    private GestionBiens gestionBiens;
    private GestionLocataires gestionLocataires;
    private JTable tableLoyers;
    private DefaultTableModel modelTable;
    private JComboBox<Integer> comboBienId, comboLocataireId;
    private JTextField txtDateDebut, txtDateFin, txtMontant, txtDateEcheance, txtGarantie, txtObservations;
    private JComboBox<String> comboEtat;

    public PanelLoyers(GestionLoyers gestionLoyers, GestionBiens gestionBiens, GestionLocataires gestionLocataires) {
        this.gestionLoyers = gestionLoyers;
        this.gestionBiens = gestionBiens;
        this.gestionLocataires = gestionLocataires;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table des loyers
        String[] colonnes = {"ID", "Bien ID", "Locataire ID", "Date Début", "Date Fin", "Montant (€)", "État"};
        modelTable = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLoyers = new JTable(modelTable);
        tableLoyers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLoyers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerLoyer();
            }
        });
        JScrollPane scrollTable = new JScrollPane(tableLoyers);

        // Panel de formulaire
        JPanel panelFormulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Bien ID:"), gbc);
        gbc.gridx = 1;
        comboBienId = new JComboBox<>();
        actualiserComboBiens();
        panelFormulaire.add(comboBienId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Locataire ID:"), gbc);
        gbc.gridx = 1;
        comboLocataireId = new JComboBox<>();
        actualiserComboLocataires();
        panelFormulaire.add(comboLocataireId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Date début (JJ/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        txtDateDebut = new JTextField(25);
        panelFormulaire.add(txtDateDebut, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Date fin (JJ/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        txtDateFin = new JTextField(25);
        panelFormulaire.add(txtDateFin, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Montant mensuel (€):"), gbc);
        gbc.gridx = 1;
        txtMontant = new JTextField(25);
        panelFormulaire.add(txtMontant, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Date échéance (jour):"), gbc);
        gbc.gridx = 1;
        txtDateEcheance = new JTextField(25);
        panelFormulaire.add(txtDateEcheance, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("État:"), gbc);
        gbc.gridx = 1;
        comboEtat = new JComboBox<>(new String[]{"Actif", "Résilié", "Terminé"});
        panelFormulaire.add(comboEtat, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Garantie:"), gbc);
        gbc.gridx = 1;
        txtGarantie = new JTextField(25);
        panelFormulaire.add(txtGarantie, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulaire.add(new JLabel("Observations:"), gbc);
        gbc.gridx = 1;
        txtObservations = new JTextField(25);
        panelFormulaire.add(txtObservations, gbc);

        // Panel de boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e -> ajouterLoyer());
        panelBoutons.add(btnAjouter);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(e -> modifierLoyer());
        panelBoutons.add(btnModifier);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerLoyer());
        panelBoutons.add(btnSupprimer);

        JButton btnNouveau = new JButton("Nouveau");
        btnNouveau.addActionListener(e -> nouveauLoyer());
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

    private void actualiserComboBiens() {
        comboBienId.removeAllItems();
        gestionBiens.obtenirTousLesBiens().forEach(bien -> comboBienId.addItem(bien.getId()));
    }

    private void actualiserComboLocataires() {
        comboLocataireId.removeAllItems();
        gestionLocataires.obtenirTousLesLocataires().forEach(loc -> comboLocataireId.addItem(loc.getId()));
    }

    private void actualiserTable() {
        modelTable.setRowCount(0);
        List<Loyer> loyers = gestionLoyers.obtenirTousLesLoyers();
        for (Loyer loyer : loyers) {
            modelTable.addRow(new Object[]{
                loyer.getId(),
                loyer.getBienId(),
                loyer.getLocataireId(),
                loyer.getDateDebut(),
                loyer.getDateFin(),
                loyer.getMontantMensuel(),
                loyer.getEtat()
            });
        }
        actualiserComboBiens();
        actualiserComboLocataires();
    }

    private void selectionnerLoyer() {
        int row = tableLoyers.getSelectedRow();
        if (row >= 0) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Loyer loyer = gestionLoyers.obtenirLoyer(id);
            if (loyer != null) {
                comboBienId.setSelectedItem(loyer.getBienId());
                comboLocataireId.setSelectedItem(loyer.getLocataireId());
                txtDateDebut.setText(loyer.getDateDebut());
                txtDateFin.setText(loyer.getDateFin());
                txtMontant.setText(String.valueOf(loyer.getMontantMensuel()));
                txtDateEcheance.setText(loyer.getDateEcheance());
                comboEtat.setSelectedItem(loyer.getEtat());
                txtGarantie.setText(loyer.getGarantie());
                txtObservations.setText(loyer.getObservations());
            }
        }
    }

    private void nouveauLoyer() {
        comboBienId.setSelectedIndex(0);
        comboLocataireId.setSelectedIndex(0);
        txtDateDebut.setText("");
        txtDateFin.setText("");
        txtMontant.setText("");
        txtDateEcheance.setText("");
        comboEtat.setSelectedIndex(0);
        txtGarantie.setText("");
        txtObservations.setText("");
        tableLoyers.clearSelection();
    }

    private void ajouterLoyer() {
        try {
            Loyer loyer = new Loyer();
            loyer.setBienId((Integer) comboBienId.getSelectedItem());
            loyer.setLocataireId((Integer) comboLocataireId.getSelectedItem());
            loyer.setDateDebut(txtDateDebut.getText().trim());
            loyer.setDateFin(txtDateFin.getText().trim());
            loyer.setMontantMensuel(Double.parseDouble(txtMontant.getText().trim()));
            loyer.setDateEcheance(txtDateEcheance.getText().trim());
            loyer.setEtat((String) comboEtat.getSelectedItem());
            loyer.setGarantie(txtGarantie.getText().trim());
            loyer.setObservations(txtObservations.getText().trim());

            gestionLoyers.ajouterLoyer(loyer);
            JOptionPane.showMessageDialog(this, "Loyer ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            actualiserTable();
            nouveauLoyer();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierLoyer() {
        int row = tableLoyers.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un loyer à modifier", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = (Integer) modelTable.getValueAt(row, 0);
            Loyer loyer = gestionLoyers.obtenirLoyer(id);
            if (loyer != null) {
                loyer.setBienId((Integer) comboBienId.getSelectedItem());
                loyer.setLocataireId((Integer) comboLocataireId.getSelectedItem());
                loyer.setDateDebut(txtDateDebut.getText().trim());
                loyer.setDateFin(txtDateFin.getText().trim());
                loyer.setMontantMensuel(Double.parseDouble(txtMontant.getText().trim()));
                loyer.setDateEcheance(txtDateEcheance.getText().trim());
                loyer.setEtat((String) comboEtat.getSelectedItem());
                loyer.setGarantie(txtGarantie.getText().trim());
                loyer.setObservations(txtObservations.getText().trim());

                gestionLoyers.modifierLoyer(loyer);
                JOptionPane.showMessageDialog(this, "Loyer modifié avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLoyer() {
        int row = tableLoyers.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un loyer à supprimer", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce loyer?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            int id = (Integer) modelTable.getValueAt(row, 0);
            if (gestionLoyers.supprimerLoyer(id)) {
                JOptionPane.showMessageDialog(this, "Loyer supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                actualiserTable();
                nouveauLoyer();
            }
        }
    }
}

