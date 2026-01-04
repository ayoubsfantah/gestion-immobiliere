@echo off
echo Lancement de l'application de gestion immobiliere...
if not exist bin (
    echo Le repertoire bin n'existe pas. Compilation en cours...
    call compiler.bat
)
java -cp bin com.gestionimmobiliere.Application
pause

