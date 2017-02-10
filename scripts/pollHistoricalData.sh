#!/bin/bash
symbols=(AAPL F)

cd /home/worker/landingzone
for i in ${symbols[@]}; do
        wget http://ichart.finance.yahoo.com/table.csv?s=${i}&c=1962
done

hadoop fs -copyFromLocal /home/worker/landingzone/* /user/worker/warehouse/stockprocessor/historic/input/landingzone
rm /home/worker/landingzone/*
exit 0