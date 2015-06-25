drop table if exists user;
create table user(
	userID integer not null auto_increment,
	username varchar(20),
	password varchar(20),
    email text,
    avatarPath text,
    motto text,
    followerCount integer DEFAULT 0,
    followingCount integer DEFAULT 0,
	primary key(userID)
)default charset utf8;

drop table if exists question;
create table question(
	questionID integer not null auto_increment,
    userID integer,
    title text,
    content text,
    answerCount integer DEFAULT 0,
    questionTime datetime DEFAULT CURRENT_TIMESTAMP,
	primary key(questionID)
)default charset utf8;

drop table if exists messages;
create table messages(
	messageID integer not null auto_increment,
    fromUserID integer,
    toUserID integer,
    content text,
    sendTime datetime DEFAULT CURRENT_TIMESTAMP,
    isRead integer DEFAULT 0,
	primary key(messageID)
)default charset utf8;

drop table if exists follows;
create table follows(
	followID integer not null auto_increment,
    fromUserID integer,
    toUserID integer,
    followTime datetime DEFAULT CURRENT_TIMESTAMP,
	primary key(followID)
)default charset utf8;

drop table if exists answers;
create table answers(
	answerID integer not null auto_increment,
    questionID integer,
    userID integer,
    content text,
    replyCount integer DEFAULT 0,
    answerTime datetime DEFAULT CURRENT_TIMESTAMP,
	primary key(answerID)
)default charset utf8;

drop table if exists replies;
create table replies(
	replyID integer not null auto_increment,
    answerID integer,
    userID integer,
    content text,
    replyTime datetime DEFAULT CURRENT_TIMESTAMP,
	primary key(replyID)
)default charset utf8;