#!/bin/bash
echo "Populating data files ====="
./pollHistoricalData.sh
echo "Populating Tables   ====="
./populateTable.sh