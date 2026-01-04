@echo off
echo ========================================
echo  Push du code vers Git
echo ========================================
echo.

REM Vérifier si Git est installé
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERREUR: Git n'est pas installe ou n'est pas dans le PATH
    echo Veuillez installer Git depuis https://git-scm.com/
    pause
    exit /b 1
)

echo Git detecte avec succes!
echo.

REM Vérifier si c'est un dépôt Git
if not exist .git (
    echo Initialisation du depot Git...
    git init
    echo.
)

REM Ajouter tous les fichiers
echo Ajout des fichiers au staging...
git add .
echo.

REM Demander le message de commit
set /p commit_message="Entrez le message de commit (ou appuyez sur Entree pour utiliser le message par defaut): "
if "%commit_message%"=="" set commit_message="Update: Application de gestion immobiliere"

REM Faire le commit
echo Creation du commit...
git commit -m "%commit_message%"
echo.

REM Vérifier si le remote existe
git remote get-url origin >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ATTENTION: Aucun remote 'origin' configure!
    echo.
    echo Pour ajouter un remote, utilisez:
    echo   git remote add origin https://github.com/VOTRE_USERNAME/VOTRE_REPO.git
    echo.
    echo Ou modifiez ce script pour ajouter automatiquement votre URL.
    echo.
    pause
    exit /b 0
)

REM Demander confirmation avant de pousser
echo.
set /p push_confirm="Voulez-vous pousser vers le remote 'origin'? (O/N): "
if /i "%push_confirm%"=="O" (
    echo.
    echo Poussage vers le remote...
    git push -u origin main 2>nul
    if %errorlevel% neq 0 (
        git push -u origin master 2>nul
        if %errorlevel% neq 0 (
            echo.
            echo ERREUR lors du push. Verifiez votre configuration Git.
            echo Branche actuelle:
            git branch
        )
    )
    echo.
    echo Push termine avec succes!
) else (
    echo Push annule.
)

echo.
pause

