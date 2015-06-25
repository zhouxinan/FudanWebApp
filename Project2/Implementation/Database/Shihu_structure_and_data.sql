/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : Shihu

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 06/25/2015 21:38:36 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `answers`
-- ----------------------------
DROP TABLE IF EXISTS `answers`;
CREATE TABLE `answers` (
  `answerID` int(11) NOT NULL AUTO_INCREMENT,
  `questionID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `content` text,
  `answerTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `replyCount` int(11) DEFAULT '0',
  PRIMARY KEY (`answerID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `answers`
-- ----------------------------
BEGIN;
INSERT INTO `answers` VALUES ('1', '1', '5', '考完试了，先来扯谈一篇轻松愉快简单粗暴的。跟那篇「到底哪个西方国家没去过」一样，本文继续来讨论下那段讲话中的另一句金句「美国的那个华莱士比你们不知道高到哪里去了」。\n\n华莱士到底有多高呢，我们试图以长者的身高来推出他的身高来。先来看一张长者和华莱士的合影。\n\n这张是我在“CBS60分钟”里的截图', '2015-06-18 15:41:33', '0'), ('2', '1', '3', '185cm，我就明确告诉你。', '2015-06-18 15:48:16', '0'), ('3', '1', '7', 'too simple', '2015-06-18 15:48:25', '0'), ('4', '7', '1', '苟利国家生死以，岂因祸福避趋之。', '2015-06-18 21:25:11', '0'), ('5', '9', '1', '哪个西方国家我没去过？', '2015-06-18 21:25:28', '0'), ('6', '8', '1', '天天有人续。', '2015-06-18 21:25:44', '0'), ('29', '9', '7', '我去过奥地利。', '2015-06-21 21:26:09', '0'), ('58', '2', '7', '现在声乐界唱法应该分为4类，即美声唱法、民族唱法、通俗唱法，以及近年来才加上的原生态唱法。', '2015-06-25 16:40:23', '0');
COMMIT;

-- ----------------------------
--  Table structure for `follows`
-- ----------------------------
DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
  `followID` int(11) NOT NULL AUTO_INCREMENT,
  `fromUserID` int(11) DEFAULT NULL,
  `toUserID` int(11) DEFAULT NULL,
  `followTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`followID`)
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `messages`
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `messageID` int(11) NOT NULL AUTO_INCREMENT,
  `fromUserID` int(11) DEFAULT NULL,
  `toUserID` int(11) DEFAULT NULL,
  `content` text,
  `sendTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `isRead` int(11) DEFAULT '0',
  PRIMARY KEY (`messageID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `questionID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `title` text,
  `content` text,
  `questionTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `answerCount` int(11) DEFAULT '0',
  PRIMARY KEY (`questionID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `question`
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES ('1', '2', '美国的华莱士到底有多高？', '很好奇，向大家请教一下，谢谢！', '2015-06-18 14:47:46', '3'), ('2', '1', '声乐有几种唱法？', null, '2015-06-18 15:47:24', '1'), ('3', '1', '三个代表重要思想的内容是什么？', null, '2015-06-18 15:47:48', '0'), ('4', '1', '为什么有那么多人膜蛤？', null, '2015-06-18 15:47:51', '0'), ('5', '1', '我和华莱士到底谁更高？', null, '2015-06-18 20:38:06', '0'), ('6', '1', '为什么现在的新闻工作者动不动就喜欢搞个大新闻？', null, '2015-06-18 20:38:46', '0'), ('7', '7', '你读过最美的一句诗是什么', null, '2015-06-18 21:24:42', '1'), ('8', '3', '保持长寿的秘诀是什么？', null, '2015-06-18 21:25:53', '1'), ('9', '2', '你去过哪些西方国家？', null, '2015-06-18 21:26:18', '2');
COMMIT;

-- ----------------------------
--  Table structure for `replies`
-- ----------------------------
DROP TABLE IF EXISTS `replies`;
CREATE TABLE `replies` (
  `replyID` int(11) NOT NULL AUTO_INCREMENT,
  `answerID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `content` text,
  `replyTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyID`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` text,
  `avatarPath` text,
  `motto` text,
  `followerCount` int(11) DEFAULT '0',
  `followingCount` int(11) DEFAULT '0',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', '江泽民', '123456a', 'jzm@z.cn', '1.jpg', '传说中的长者', '0', '0'), ('2', '张宝华', '123456a', 'zbh@z.cn', '2.jpg', '香港著名女记者', '0', '0'), ('3', '华莱士', '123456a', 'hls@z.cn', '3.jpg', '比你们不知道高到哪里去', '0', '0'), ('4', '董建华', '123456a', 'djh@z.cn', '4.jpg', '香港第一任特首', '0', '0'), ('5', '江选研讨会', '123456a', 'jx@z.cn', '5.jpg', '一起学习三个代表重要思想', '0', '0'), ('6', '江绵恒', '123456a', 'jmh@z.cn', '6.jpg', '上海科技大学校长', '0', '0'), ('7', '宋祖英', '123456a', 'szy@z.cn', '7.jpg', '中国著名歌唱家', '0', '0'), ('8', '薄谷开来', '123456a', 'bgkl@z.cn', '8.jpg', '薄熙来的妻子', '0', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
