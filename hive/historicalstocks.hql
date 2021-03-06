use stockanalytics;

drop table if exists HistoricalStockData;

CREATE EXTERNAL TABLE IF NOT EXISTS HistoricalStockData(
        symbol_date DATE, 
        open DECIMAL(11,7),
        high DECIMAL(11,7),
        low DECIMAL(11,7), 
        close DECIMAL(11,7),
        volume BIGINT,
        adjclose DECIMAL(11,7),
        symbol STRING)
    COMMENT 'Historical Stock Data'
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    location '/user/worker/warehouse/master/HistoricalStockData';
    
    CREATE EXTERNAL TABLE IF NOT EXISTS HistoricalStockDataStartEnd(
        year INT,
        month INT, 
        monthstart DECIMAL(11,7),
        monthend DECIMAL(11,7),
        difference DECIMAL(11,7),
        symbol STRING)
    COMMENT 'Historical Stock Data'
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    location '/user/worker/warehouse/master/HistoricalStockDataStartEnd';

   
