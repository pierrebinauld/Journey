create table country(
	idcountry       int             unsigned not null,
	namecountry     varchar(40)     not null,
	description     varchar(400)    not null,

	primary key pk_country (idcountry),
	unique u_country_name (namecountry)
);

create table city(
	idcity          int             unsigned not null,
	idcountry       int             unsigned not null,
	x               decimal(10, 4)  not null,
	y               decimal(10, 4)  not null,

	primary key pk_city (idcity, idcountry),
	foreign key fk_city_country (idcountry) references country (idcountry)
);

create table lookup(
	idlookup        int             unsigned not null,
	namelookup      varchar(40)     not null,

	primary key pk_lookup (idlookup)
);

create table stats(
	idstats         int             unsigned not null,
	best            int             unsigned not null,
	countexe        int             unsigned not null,
	average         int             unsigned not null,
	idcountry       int             unsigned not null,
	idlookup        int             unsigned not null,

	primary key pk_stats (idstats),
	foreign key fk_stats_country (idcountry) references country (idcountry),
	foreign key fk_stats_lookup (idlookup) references lookup (idlookup)
);

create table distance (
<<<<<<< HEAD
	idcity1         int         unsigned not null,
	idcity2         int         unsigned not null,
	distance        int         unsigned not null,

	primary key pk_distance (idcity1, idcity2),
	foreign key fk_distance_city1 (idcity1) references city (idcity),
	foreign key fk_distance_city2 (idcity2) references city (idcity)
=======
	idcountry       int         unsigned not null,
	idcity1         int         unsigned not null,
	idcity2         int         unsigned not null,
	distance        int         unsigned not null,

	primary key pk_distance (idcountry, idcity1, idcity2),
	foreign key fk_distance_city1 (idcountry, idcity1) references city (idcountry, idcity),
	foreign key fk_distance_city2 (idcountry, idcity2) references city (idcountry, idcity)
>>>>>>> branch 'master' of git@github.com:pierrebinauld/Journey.git
);
