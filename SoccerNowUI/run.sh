#!/bin/bash

echo "Building project..."
mvn clean install || exit 1

echo "Running JavaFX application..."
mvn javafx:run