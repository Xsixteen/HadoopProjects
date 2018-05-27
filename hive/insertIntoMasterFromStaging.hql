use stockanalytics;

insert into stockmastertbl 
select
	stg.stockdate,
	stg.open,
	stg.high,
	stg.low,
	stg.close,
	stg.volume,
	stg.exdiv,
	stg.splitrat,
	stg.adjopen,
	stg.adjhigh,
	stg.adjlow,
	stg.adjclose,
	stg.adjvolume,
	stg.symbol,
	unix_timestamp()
from stockstagingtbl stg left join stockmastertbl master ON (stg.stockdate = master.stockdate AND stg.symbol = master.symbol)
WHERE master.stockdate IS NULL and master.symbol IS NULL ;
