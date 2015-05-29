drop table if exists message;
create table message(
	messageID integer not null auto_increment,
    username varchar(20),
	messageText text,
	note_time varchar(50),
	primary key(messageID)
)default charset utf8;