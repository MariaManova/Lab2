
create schema if not exists db_Sputniks default charset utf8; 


use db_Sputniks; 


CREATE TABLE if not exists Sputnik( 
ID_Sputnik9 int not null auto_increment primary key, 
ID_Scientist integer not null, 
ID_Planet integer not null, 
Name char(20), 
Diametr int(30), 
Period int(10), 
Data char(10), 


constraint unique IDSputnikUnique(ID_Sputnik), 

constraint foreign key IdPlanetFK (ID_Planet) references Planet(ID_Planet) on delete restrict 
); 

CREATE TABLE Planet( 
ID_Planet integer  primary key, 
ID_Scientist integer not null, 
ID_Star integer not null, 
Name char(20), 
Mass int(30), 
Radius int(30), 
Km_from_SUN int(50), 
Data char(10) 
constraint unique IDPlanetUnique(ID_Planet), 
);