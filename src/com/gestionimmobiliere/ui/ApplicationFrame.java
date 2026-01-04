package com.gestionimmobiliere.ui;

import com.gestionimmobiliere.dao.*;
import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application
 */
public class ApplicationFrame extends JFrame {
    private GestionBiens gestionBiens;
    private GestionLocataires gestionLocataires;
    private GestionLoyers gestionLoyers;
    private GestionPaiements gestionPaiements;
    
    private JTabbedPane tabbedPane;

    public ApplicationFrame() {
        // Initialiser les gestionnaires
        gestionBiens = new GestionBiens();
        gestionLocataires = new GestionLocataires();
        gestionLoyers = new GestionLoyers();
        gestionPaiements = new GestionPaiements();

        initComponents();
    }

    private void initComponents() {
        setTitle("Application de Gestion Immobilière");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Créer les panneaux
        PanelBiens panelBiens = new PanelBiens(gestionBiens);
        PanelLocataires panelLocataires = new PanelLocataires(gestionLocataires);
        PanelLoyers panelLoyers = new PanelLoyers(gestionLoyers, gestionBiens, gestionLocataires);
        PanelPaiements panelPaiements = new PanelPaiements(gestionPaiements, gestionLoyers);

        // Créer l'onglet
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Biens Immobiliers", panelBiens);
        tabbedPane.addTab("Locataires", panelLocataires);
        tabbedPane.addTab("Loyers", panelLoyers);
        tabbedPane.addTab("Paiements", panelPaiements);

        add(tabbedPane, BorderLayout.CENTER);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(e -> System.exit(0));
        menuFichier.add(menuItemQuitter);
        menuBar.add(menuFichier);

        JMenu menuAide = new JMenu("Aide");
        JMenuItem menuItemAPropos = new JMenuItem("À propos");
        menuItemAPropos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Application de Gestion Immobilière\n\n" +
                "Gestion des biens, locataires, loyers et paiements\n\n" +
                "Version 1.0",
                "À propos",
                JOptionPane.INFORMATION_MESSAGE);
        });
        menuAide.add(menuItemAPropos);
        menuBar.add(menuAide);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        // Utiliser le look and feel système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new ApplicationFrame().setVisible(true);
        });
    }
}

