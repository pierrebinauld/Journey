create table country(
	idcountry int not null primary key,
	nomcountry varchar(40) not null,
	description varchar(400) not null,
	dimension int not null
);

create table city(
	idcity int NOT NULL PRIMARY KEY,
	latitude long not null,
	longitude long not null,
	idcountry int not null
);

create table lookup(
	idlookup int  not null primary key,
	namelookup varchar(40) not null
);

create table stats(
	id int not null primary key,
	best long not null,
	countexe int not null,
	total long not null,
	average long not null,
	idcountry int not null,
	idlookup int  not null
);

alter table city add foreign key fk_city(idcountry) references country (idcountry);
alter table stats add foreign key fk_stats_country(idcountry) references country (idcountry);
alter table stats add foreign key fk_stats_lookup(idlookup) references lookup (idlookup);