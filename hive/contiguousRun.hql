use stockanalytics;

drop table if exists ContiguousRun;

CREATE EXTERNAL TABLE IF NOT EXISTS ContiguousRun(
        symbol String, 
        year BIGINT,
        positivecontiguous BIGINT,
        negativecontiguous BIGINT,
        positivity DECIMAL(11,7),
        negativity DECIMAL(11,7))
    COMMENT 'Contiguous Stock Data'
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    location '/user/worker/warehouse/master/ContiguousRun';
    
    CREATE EXTERNAL TABLE IF NOT EXISTS ActualDataContiguousRun(
        symbol String, 
        year BIGINT,
        positivecontiguous BIGINT,
        negativecontiguous BIGINT,
        positivity DECIMAL(11,7),
        negativity DECIMAL(11,7))
    COMMENT 'Contiguous Stock Data'
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    location '/user/worker/warehouse/master/ActualDataContiguousRun';
    
