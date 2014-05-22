drop user 'library'@'localhost';
create user 'library'@'localhost' identified by 'library';
grant all privileges on library.* to 'library'@'localhost';
flush privileges;