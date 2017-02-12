#!/bin/bash

echo "HadoopProjects Project Installer===="

echo "Building Gradle Project===="
gradle build

echo "Please Specify Install Location.  Make sure to use the full path, reference path below"
pwd

echo "Install Location? " 

read install_loc

echo "echo '$install_loc'" > "./scripts/variables.sh"
echo "Installing in: $install_loc"

mkdir $install_loc/lib
mkdir $install_loc/scripts
mkdir $install_loc/hive

cp ./build/libs/* $install_loc/lib/
cp ./scripts/* $install_loc/scripts/
cp ./hive/* $install_loc/hive/


echo "Installation Completed===="