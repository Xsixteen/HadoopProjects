use stockanalytics;
CREATE EXTERNAL TABLE IF NOT EXISTS StockMasterTbl(
        stockdate string,
        open decimal(11,7),
        high decimal(11,7),
        low decimal(11,7),
        close decimal(11,7),
        volume decimal(15,1),
        exdiv decimal(5,2),
        splitrat decimal(5,2),
        adjopen decimal(10,4),
        adjhigh decimal(11,7),
        adjlow decimal(11,7),
        adjclose decimal(11,7),
        adjvolume decimal(15,1),
        symbol string,
	createddate timestamp)
ROW FORMAT SERDE 
'org.apache.hive.hcatalog.data.JsonSerDe' 
location '/project/stock/master/StockMasterTbl'
