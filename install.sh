#!/bin/bash

echo "HadoopScripts Project Installer===="

echo "Please Specify Install Location.  Make sure to use the full path, reference path below"
pwd

echo "Installation Path: " 

read install_loc

cp ./build/libs/* $install_loc/lib/
cp ./scripts/* $install_loc/scripts/

echo "Installation Completed===="