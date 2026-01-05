# Application de Gestion Immobilière

Application Java complète pour la gestion immobilière : biens, locataires, loyers et paiements.

## Démarrage Rapide

### Compiler
```bash
compiler.bat
```

### Exécuter
```bash
run.bat
```

##  Fonctionnalités

- **Biens Immobiliers** : Gestion complète des propriétés
- **Locataires** : Gestion des locataires et garants
- **Loyers** : Contrats de location
- **Paiements** : Suivi des paiements de loyer

## Technologies

- Java SE (sans dépendances externes)
- Java Swing (interface graphique)
- Sérialisation Java (persistance des données)

## Structure

```
src/
├── model/     # Classes d'entités
├── dao/       # Gestion des données
└── ui/        # Interface utilisateur
```

## Données

Les données sont sauvegardées automatiquement dans le dossier `data/`.

## Compilation Manuelle

```bash
javac -d bin -encoding UTF-8 src/com/gestionimmobiliere/**/*.java
java -cp bin com.gestionimmobiliere.Application
```

## Licence

Projet éducatif - Gestion Immobilière

