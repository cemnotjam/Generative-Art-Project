#!/bin/bash
export JAVA_HOME=/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home
javac --module-path ~/Desktop/javafx-sdk-26/lib --add-modules javafx.controls,javafx.fxml,javafx.swing -d out src/GlitchApp.java src/GlitchEngine.java src/ImageProcessor.java src/MainView.java
java --module-path ~/Desktop/javafx-sdk-26/lib --add-modules javafx.controls,javafx.fxml,javafx.swing -cp out src.GlitchApp
