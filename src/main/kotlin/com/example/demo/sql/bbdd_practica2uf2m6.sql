DROP DATABASE IF EXISTS Practica2uf2m6;
CREATE DATABASE Practica2uf2m6;
#CREATE USER administrador1 IDENTIFIED BY 'admin1';
GRANT ALL PRIVILEGES ON Practica2uf2m6.* TO administrador1 WITH GRANT OPTION;
USE Practica2uf2m6;

CREATE TABLE Alumne(
id_alumne INTEGER AUTO_INCREMENT,
Nom VARCHAR(15),
Cognoms VARCHAR(30),
edat INTEGER,
PRIMARY KEY(id_alumne)
); 

INSERT INTO Alumne VALUES(null,"Ricard","Pinzon Suller", 29);


show variables like "max_connections";
select * from Alumne;

set global max_connections = 2000;