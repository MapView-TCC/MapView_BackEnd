create database DB_MAPVIEW;
use DB_MAPVIEW;

CREATE TABLE predio (
    id_predio INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    cod_predio VARCHAR(255),
    ativo BOOLEAN
);
create table area (
	id_area int primary key AUTO_INCREMENT NOT NULL,
    cod_area varchar(255),
    nome_area varchar(255),
    ativo tinyint

);
create table raspberry(
	id_raspberry int primary key AUTO_INCREMENT NOT NULL,
    nome_rasp varchar(255),
    id_predio int,
    id_area int,
    ativo tinyint

);
create table ambiente( 
	id_ambiente int primary key auto_increment null,
    nome_ambiente varchar(255),
    id_rasp int,
    ativo tinyint

);
create table rastreio(
	id_rastreio int primary key auto_increment not null,
    id_equipamento int,
    id_ambiente int,
    acao enum('SAIU','ENTROU'),
    ativo tinyint

);

create table posto (
	id_posto int primary key auto_increment not null,
    posto varchar(100),
    descrição varchar(255)
);
create table equipamentoResponsavel (

);

create table usuario(
id_user int primary key auto_increment not null,
role varchar(255),
ativo tinyint
);

create table responsaveis(

);



create table log_usuario(
	id_log int primary key auto_increment not null,
    id_user

);

create table raspberry(

);