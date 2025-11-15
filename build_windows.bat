@echo off
title Build Windows Application - Kineska Gramatika
echo 🔨 Building Windows Application...

:: Provjeri da li je Maven instaliran
mvn --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven nije instaliran ili nije u PATH-u!
    pause
    exit /b 1
)

:: Očisti prethodni build
echo 🧹 Cleaning previous build...
call mvn clean

:: Build projekta
echo 🔧 Building application...
call mvn package

:: Provjeri da li je build uspješan
if errorlevel 1 (
    echo ❌ Build failed!
    pause
    exit /b 1
)

echo ✅ Build uspješan!
echo 📁 JAR file: target\kineska-gramatika-1.0.0.jar

:: Kreiraj batch file za pokretanje
echo @echo off > RunKineskaGramatika.bat
echo java -jar target\kineska-gramatika-1.0.0.jar %%* >> RunKineskaGramatika.bat

echo 🚀 Kreiran RunKineskaGramatika.bat za pokretanje aplikacije
echo.
echo 📋 Naredbe:
echo    mvn javafx:run   - Pokreće aplikaciju direktno
echo    RunKineskaGramatika.bat - Pokreće iz JAR fajla
echo.

pause
