use stockanalytics;

insert into stockmastertbl 
select
	stockdate,
	open,
	high,
	low,
	close,
	volume,
	exdiv,
	splitrat,
	adjopen,
	adjhigh,
	adjlow,
	adjclose,
	adjvolume,
	symbol,
	unix_timestamp()
from stockstagingtbl;
