drop table if exists user;
create table user(
	userID integer not null auto_increment,
	username varchar(20),
	password varchar(20),
    email text,
    avatarPath text,
    motto text,
	primary key(userID)
)default charset utf8;

drop table if exists question;
create table question(
	questionID integer not null auto_increment,
    userID integer,
    title text,
    content text,
    questionTime datetime DEFAULT CURRENT_TIMESTAMP,
	primary key(questionID)
)default charset utf8;