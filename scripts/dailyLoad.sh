#!/bin/bash
echo "Populating data files ====="
./pollHistoricalData.sh
echo "Running the Hadoop Processor Job ====="
yarn jar ../lib/HadoopProcessor-0.1.0.jar com.eulicny.driver.HistoricalLoadDriver
echo "Populating Tables   ====="
./populateTable.sh