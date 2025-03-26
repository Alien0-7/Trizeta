#!/bin/bash

# Spostati nella cartella del progetto
cd "$(dirname "$0")"

# Compila il progetto
mvn clean install

# Avvia l'applicazione Javalin
mvn exec:java -Dexec.mainClass="org.example.Application"