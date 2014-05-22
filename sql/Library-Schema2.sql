drop database if exists library;
create database library;

use library;

create table bookfile (
	ID		int not null auto_increment primary key,
	ISBN		varchar(13) not null,
	Title		varchar(100) not null,
	Autor		varchar(50) not null,
	Lengua		varchar(20) not null,
	Edicion		varchar(20) not null,
	FechaEdicion	date not null,
	FechaImpresion	date not null,
	Editorial	varchar(20) not null
);

create table users (
	Username	varchar(20) not null primary key,
	Realname	varchar(50) not null,
	Password	varchar(10) not null
);

create table review (
	ID		int not null auto_increment primary key,
	ISBN		varchar(13) not null,
	Username	varchar(20) not null,
	Realname	varchar(50) not null,
	UltEdicion	timestamp,
	Content		varchar(500) not null,

	foreign key(Username) references users(Username)
);

