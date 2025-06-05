#!/bin/bash

MAIN_CLASS="com.soccernow.ui.soccernowui.SoccerNowApp"

echo "Building project..."
mvn clean install || exit 1

echo "Running JavaFX application..."
mvn javafx:run -Dexec.mainClass=$MAIN_CLASS