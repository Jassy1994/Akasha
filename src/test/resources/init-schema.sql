DROP TABLE IF EXISTS `mydatabase`.`user_table`;
CREATE TABLE `user_table` (
  `id`            INT(11)     NOT NULL AUTO_INCREMENT,
  `username`      VARCHAR(45) NOT NULL,
  `password`      VARCHAR(45) NOT NULL,
  `head_url`      TEXT,
  `introduction`  VARCHAR(200)         DEFAULT NULL,
  `agreement_num` INT(11)     NOT NULL DEFAULT '0',
  `salt`          VARCHAR(45)          DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`));

DROP TABLE IF EXISTS `mydatabase`.`question_table`;
CREATE TABLE `question_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_anonymous` tinyint(4) NOT NULL,
  `description` text NOT NULL,
  `answer_num` int(11) NOT NULL DEFAULT '0',
  `ask_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`));

DROP TABLE IF EXISTS `mydatabase`.`answer_table`;
CREATE TABLE `answer_table` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  `is_anonymous` TINYINT NOT NULL,
  `content` TEXT NOT NULL,
  `agreement_num` INT NOT NULL DEFAULT 0,
  `comment_num` INT NOT NULL DEFAULT 0,
  `answer_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS `mydatabase`.`information_table`;
CREATE TABLE `information_table` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `link` TEXT NOT NULL,
  `image` TEXT NULL,
  `agreement_num` INT NOT NULL DEFAULT 0,
  `comment_num` INT NOT NULL DEFAULT 0,
  `release_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS `mydatabase`.`comment_table`;
CREATE TABLE `comment_table` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `entity_type` VARCHAR(45) NOT NULL,
  `entity_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `agreement_num` INT NOT NULL DEFAULT 0,
  `comment_date` DATETIME NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`));



