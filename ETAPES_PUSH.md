# Étapes pour pousser votre code vers Git

## Étape 1 : Vérifier que Git est installé

Ouvrez PowerShell ou CMD et tapez :
```bash
git --version
```

Si Git n'est pas installé, téléchargez-le depuis : https://git-scm.com/download/win

---

## Étape 2 : Créer un dépôt sur GitHub (si vous n'en avez pas)

1. Allez sur https://github.com
2. Connectez-vous ou créez un compte
3. Cliquez sur le bouton **"+"** en haut à droite → **"New repository"**
4. Donnez un nom à votre dépôt (ex: `gestion-immobiliere`)
5. **NE COCHEZ PAS** "Initialize this repository with a README"
6. Cliquez sur **"Create repository"**
7. **Copiez l'URL** du dépôt (ex: `https://github.com/VOTRE_USERNAME/gestion-immobiliere.git`)

---

## Étape 3 : Initialiser Git dans votre projet

Ouvrez PowerShell ou CMD dans le dossier de votre projet :
```bash
cd "C:\xampp\htdocs\projet de java"
```

Puis exécutez :
```bash
git init
```

---

## Étape 4 : Configurer Git (si pas déjà fait)

```bash
git config --global user.name "Votre Nom"
git config --global user.email "votre.email@example.com"
```

---

## Étape 5 : Ajouter tous les fichiers

```bash
git add .
```

---

## Étape 6 : Faire le premier commit

```bash
git commit -m "Initial commit: Application de gestion immobilière complète"
```

---

## Étape 7 : Ajouter le dépôt distant

Remplacez `VOTRE_USERNAME` et `VOTRE_REPO` par vos informations :
```bash
git remote add origin https://github.com/VOTRE_USERNAME/VOTRE_REPO.git
```

---

## Étape 8 : Pousser le code

```bash
git branch -M main
git push -u origin main
```

Si vous êtes invité à vous connecter :
- GitHub vous demandera votre nom d'utilisateur et un **Personal Access Token** (pas votre mot de passe)
- Pour créer un token : GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic) → Generate new token

---

## Alternative : Utiliser le script automatique

1. Double-cliquez sur `git_push.bat`
2. Suivez les instructions à l'écran
3. Entrez votre message de commit
4. Le script vous guidera pour le reste

---

## Vérification

Après le push, allez sur votre dépôt GitHub. Vous devriez voir tous vos fichiers !

---

## Pour les prochains push (après modifications)

```bash
git add .
git commit -m "Description de vos modifications"
git push
```

