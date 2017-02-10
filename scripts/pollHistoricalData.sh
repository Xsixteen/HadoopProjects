#!/bin/bash
symbols=(AAPL F)

cd ./landingzone
for i in ${symbols[@]}; do
        wget http://ichart.finance.yahoo.com/table.csv?s=${i}&c=1962
done

hadoop fs -copyFromLocal * /user/worker/warehouse/stockprocessor/historic/input
rm *
exit 0