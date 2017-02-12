#!/bin/bash
symbols=(SPY F CSCO T QQQ XLE XLV GM BA O AAPL GOOGL CAT XOM MSFT GE HD SI IBM AXP MMM SNE DIS MET ACL ALL HON BBY TXN AMZN LU MGM LLL ET HSY KO NVDA PIXR CTXS WSM CRM TSN LLY FFIV MTD PNRA TTC DPZ NNI MCD WAG GSK PG FE TM COP VZ DCM GS ORCL MRK)

ROOT_LOCATION="$(./variables.sh)"

echo "Base Location = ${ROOT_LOCATION}"

cd /home/worker/landingzone
for i in ${symbols[@]}; do
        wget "http://ichart.finance.yahoo.com/table.csv?s=${i}&c=1962" -O ${ROOT_LOCATION}/historicalstocks/landingzone/1${i}.csv
done

hadoop fs -copyFromLocal  ${ROOT_LOCATION}/historicalstocks/landingzone /user/worker/warehouse/stockprocessor/historic/input/
rm  ${ROOT_LOCATION}/historicalstocks/landingzone/*
exit 0