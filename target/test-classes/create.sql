Ч —оздание базы данных 
create schema if not exists db_Sputniks default charset utf8; 

Ч ќпределение базы данных по умолчанию 
use db_Sputnics; 

Ч —оздание таблиц 
CREATE TABLE if not exists Sputnik( 
ID_Sputnik integer not null primary key, 
ID_Scientist integer not null, 
ID_Planet integer not null, 
Name char(20), 
Diametr int(30), 
Period int(10), 
Data char(10), 

constraint foreign key IdPlanetFK (ID_Planet) references Planet(ID_Planet) on delete restrict 
); 

CREATE TABLE Planet( 
ID_Planet integer not null primary key, 
ID_Scientist integer not null, 
ID_Star integer not null, 
Name char(20), 
Mass int(30), 
Radius int(30), 
Km_from_SUN int(50), 
Data char(10) 
constraint unique IDPlanetUnique(ID_Planet), 
);