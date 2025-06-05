#!/bin/bash

MODULE_NAME="com.soccernow.ui.soccernowui"
MAIN_CLASS="com.soccernow.ui.soccernowui.SoccerNowApp"
JAVAFX_VERSION="21"
JAVAFX_LIB="$HOME/.m2/repository/org/openjfx"
MODULE_PATH="target/classes"

for lib in javafx-controls javafx-fxml javafx-web javafx-swing; do
  MODULE_PATH="$MODULE_PATH:$JAVAFX_LIB/$lib/$JAVAFX_VERSION/$lib-$JAVAFX_VERSION.jar"
done

echo "Building project..."
mvn clean install || exit 1

echo "Running JavaFX application..."
java \
  --module-path "$MODULE_PATH" \
  --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing \
  --module "$MODULE_NAME/$MAIN_CLASS"