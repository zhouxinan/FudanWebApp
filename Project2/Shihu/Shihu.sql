drop table if exists user;
create table user(
	userID integer not null auto_increment,
	username varchar(20),
	password varchar(20),
    email text,
	primary key(userID)
)default charset utf8;