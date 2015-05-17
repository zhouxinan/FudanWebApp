drop table if exists User;
create table User(
	userID integer not null auto_increment,
	username varchar(20),
	password varchar(20),
	primary key(userID)
)default charset utf8;

drop table if exists message;
create table message(
	messageID integer not null auto_increment,
	userID integer,
	message text,
	note_time varchar(50),
	primary key(messageID)
)default charset utf8;

insert User(username, password) values('test user', 'testPassword');
insert message(userID, message, note_time) values(1, 'test message message message', '2015-11-11 12:23:23');

