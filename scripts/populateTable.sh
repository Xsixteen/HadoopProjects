#!/bin/bash
echo "Kill and Fill Process Starting ===="
hadoop fs -rm /user/worker/warehouse/master/HistoricalStockData/*
echo "Rebuilding Table ===="
hive -f ../hive/historicalstocks.hive
hadoop fs -mv /user/worker/warehouse/stockprocessor/historic/output/* /user/worker/warehouse/master/HistoricalStockData