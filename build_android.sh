#!/bin/bash

# Build script za Android aplikaciju
echo "🔨 Building Android APK..."

# Provjeri da li je Android SDK postavljen
if [ -z "$ANDROID_HOME" ]; then
    echo "❌ ANDROID_HOME nije postavljen!"
    exit 1
fi

# Prikaži verziju
echo "Android SDK: $ANDROID_HOME"

# Očisti prethodni build
echo "🧹 Cleaning previous build..."
./gradlew clean

# Build debug APK
echo "🔧 Building debug APK..."
./gradlew assembleDebug

# Provjeri da li je build uspješan
if [ $? -eq 0 ]; then
    echo "✅ Debug APK uspješno izgrađen!"
    echo "📱 APK location: app/build/outputs/apk/debug/app-debug.apk"
    
    # Kopiraj APK u glavni folder
    cp app/build/outputs/apk/debug/app-debug.apk KineskaGramatika-debug.apk
    echo "📂 Kopiran u: KineskaGramatika-debug.apk"
else
    echo "❌ Build failed!"
    exit 1
fi

# Build release APK (opcionalno)
read -p "Želite li buildati release APK? (y/n): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🔧 Building release APK..."
    ./gradlew assembleRelease
    
    if [ $? -eq 0 ]; then
        echo "✅ Release APK uspješno izgrađen!"
        cp app/build/outputs/apk/release/app-release.apk KineskaGramatika-release.apk
        echo "📂 Kopiran u: KineskaGramatika-release.apk"
    else
        echo "❌ Release build failed!"
    fi
fi

echo "🎉 Build process završen!"
