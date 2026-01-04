# Application de Gestion Immobilière

Application complète de gestion immobilière développée en Java pur (Java SE uniquement, sans dépendances externes).

## Fonctionnalités

L'application permet de gérer :

1. **Biens Immobiliers**
   - Ajout, modification, suppression de biens
   - Informations : adresse, type, superficie, nombre de pièces, loyer mensuel, état
   - Recherche de biens
   - Filtrage par état (Disponible, Loué, En rénovation)

2. **Locataires**
   - Gestion complète des locataires
   - Informations : nom, prénom, email, téléphone, adresse, date de naissance, profession
   - Informations du garant
   - Recherche de locataires

3. **Loyers**
   - Gestion des contrats de location
   - Association bien/locataire
   - Dates de début et fin de contrat
   - Montant mensuel, date d'échéance
   - État du contrat (Actif, Résilié, Terminé)
   - Garantie et observations

4. **Paiements**
   - Enregistrement des paiements de loyer
   - Modes de paiement : Espèces, Chèque, Virement, Carte
   - Suivi des périodes (mois/année)
   - Statut : Payé, En attente, Retard
   - Références et observations

## Structure du Projet

```
projet de java/
├── src/
│   └── com/
│       └── gestionimmobiliere/
│           ├── Application.java          # Point d'entrée principal
│           ├── model/                     # Classes d'entités
│           │   ├── Bien.java
│           │   ├── Locataire.java
│           │   ├── Loyer.java
│           │   └── Paiement.java
│           ├── dao/                       # Classes de gestion/persistance
│           │   ├── GestionBiens.java
│           │   ├── GestionLocataires.java
│           │   ├── GestionLoyers.java
│           │   └── GestionPaiements.java
│           └── ui/                        # Interface utilisateur Swing
│               ├── ApplicationFrame.java
│               ├── PanelBiens.java
│               ├── PanelLocataires.java
│               ├── PanelLoyers.java
│               └── PanelPaiements.java
├── data/                                   # Données persistées (créé automatiquement)
│   ├── biens.dat
│   ├── locataires.dat
│   ├── loyers.dat
│   └── paiements.dat
└── README.md
```

## Compilation et Exécution

### Prérequis
- Java JDK 11 ou supérieur
- Aucune dépendance externe requise

### Compilation

```bash
# Compiler tous les fichiers Java
javac -d bin -encoding UTF-8 src/com/gestionimmobiliere/**/*.java
```

### Exécution

```bash
# Exécuter l'application
java -cp bin com.gestionimmobiliere.Application
```

Ou directement depuis le répertoire src :

```bash
java -cp . com.gestionimmobiliere.Application
```

## Utilisation

1. **Lancer l'application** : Exécutez la classe `Application`
2. **Interface à onglets** : Naviguez entre les différents modules (Biens, Locataires, Loyers, Paiements)
3. **Ajouter des données** : Utilisez les formulaires à droite pour ajouter de nouvelles entrées
4. **Modifier/Supprimer** : Sélectionnez une ligne dans le tableau et utilisez les boutons correspondants
5. **Rechercher** : Utilisez la barre de recherche pour filtrer les données

## Persistance des Données

Les données sont sauvegardées automatiquement dans des fichiers binaires (sérialisation Java) dans le répertoire `data/` :
- `data/biens.dat` - Liste des biens
- `data/locataires.dat` - Liste des locataires
- `data/loyers.dat` - Liste des contrats de location
- `data/paiements.dat` - Liste des paiements

Les données sont chargées automatiquement au démarrage et sauvegardées à chaque modification.

## Technologies Utilisées

- **Java SE 11+** : Langage de programmation
- **Java Swing** : Interface graphique utilisateur
- **Sérialisation Java** : Persistance des données

## Notes

- L'application utilise uniquement les bibliothèques standard de Java
- Aucune base de données externe n'est requise
- Les données sont stockées localement dans des fichiers binaires
- L'interface est en français

## Auteur

Application développée pour la gestion immobilière complète.

