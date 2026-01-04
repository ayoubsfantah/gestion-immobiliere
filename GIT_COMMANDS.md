# Commandes Git pour pousser le code

## Initialisation du dépôt Git (si pas déjà fait)

```bash
git init
```

## Configuration Git (si pas déjà configuré)

```bash
git config --global user.name "Votre Nom"
git config --global user.email "votre.email@example.com"
```

## Ajouter tous les fichiers au staging

```bash
git add .
```

## Faire un commit

```bash
git commit -m "Initial commit: Application de gestion immobilière complète"
```

## Ajouter le remote (remplacez par votre URL GitHub/GitLab)

```bash
git remote add origin https://github.com/VOTRE_USERNAME/VOTRE_REPO.git
```

Ou pour SSH:
```bash
git remote add origin git@github.com:VOTRE_USERNAME/VOTRE_REPO.git
```

## Pousser le code vers le dépôt distant

```bash
git branch -M main
git push -u origin main
```

## Commandes complètes en une seule fois

```bash
# Initialiser le dépôt
git init

# Ajouter tous les fichiers
git add .

# Faire le premier commit
git commit -m "Initial commit: Application de gestion immobilière complète"

# Ajouter le remote (remplacez par votre URL)
git remote add origin https://github.com/VOTRE_USERNAME/VOTRE_REPO.git

# Pousser vers main
git branch -M main
git push -u origin main
```

## Pour les commits suivants

```bash
git add .
git commit -m "Description de vos modifications"
git push
```

