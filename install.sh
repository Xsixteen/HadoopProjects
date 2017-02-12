#!/bin/bash

echo "HadoopScripts Project Installer===="

echo "Please Specify Install Location: " 

read install_loc

cp ./build/libs/* $install_loc/lib/
cp ./scripts/* $install_loc/scripts/