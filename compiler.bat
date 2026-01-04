@echo off
echo Compilation de l'application de gestion immobiliere...
if not exist bin mkdir bin
javac -d bin -encoding UTF-8 src/com/gestionimmobiliere/model/*.java src/com/gestionimmobiliere/dao/*.java src/com/gestionimmobiliere/ui/*.java src/com/gestionimmobiliere/*.java
if %errorlevel% == 0 (
    echo Compilation reussie!
    echo.
    echo Pour executer l'application, utilisez: run.bat
) else (
    echo Erreur lors de la compilation!
    pause
)

