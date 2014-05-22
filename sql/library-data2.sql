insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788448044411", 'Lobo espacial', 'William King', 'Español', 'Primera','2012/01/01', '2012/01/01', 'Timun Mas');

insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788448033934", 'El señor del tiempo', 'Louise Cooper', 'Español', 'Tercera','2007-1-1', '2007-1-1', 'Timun Mas');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788448035891", 'El exodo de los Gnomos', 'Terry pratchett', 'Español', 'Tercera','2008-1-1', '2008-1-1', 'Timun Mas');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788499085586", 'El color de la magia', 'Terry pratchett', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Debolsillo');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788499085587", 'La dama del lago', 'Andrej Sapkosky', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Gigamesh');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788499085588", 'El retorno de los Dragones', 'Margaret Weiss', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Dragonlance');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788499085589", 'Juego de Tronos', 'George Martin', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Gigamesh');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788499085580", 'El nombre del Viento', 'Patrick Rottheus', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Gigamesh');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788499085596", 'La espada de Joram', 'Margaret Weiss', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Citadel');


insert into bookfile (ISBN, Title, Autor, Lengua, Edicion, FechaEdicion, FechaImpresion, Editorial) values ("9788099085586", 'El bosque de los Pigmeos', 'Isabel Allende', 'Español', 'Primera','2010-1-1', '2010-1-1', 'Citadel');



insert into users (Username,Realname,Password) values ('admin', 'administrador', admin);
insert into users (Username,Realname,Password) values ('miki', 'Miquel Delgado', 1234);

insert into realmroles (Username,Rolename) values ('admin','admin');
insert into realmroles (Username,Rolename) values ('miki','Registered');


insert into review (ISBN, Username, Realname, UltEdicion, Content) values ("9788499085586", 'test', 'Tester', now(), 'Buen libro, muy divertido');
insert into review (ISBN, Username, Realname, UltEdicion, Content) values ("9788448044411", 'admin', 'Administrador', now(), 'Gran historia del espacio');
insert into review (ISBN, Username, Realname, UltEdicion, Content) values ("9788448035891", 'test', 'Tester', now(), 'Adoro los Gnomos!');
insert into review (ISBN, Username, Realname, UltEdicion, Content) values ("9788448033934", 'admin', 'Administrador', now(), 'Disfrutas con cada pagina');