create database simpleBook;

use simpleBook;

CREATE TABLE `book_man_sys_user` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NOT NULL,
	`token` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `username` (`username`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `book_man_sys_books` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`bookname` VARCHAR(50) NOT NULL,
	`author` VARCHAR(50) NOT NULL,
	`category` VARCHAR(50) NOT NULL,
	`count` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `bookname` (`bookname`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `book_man_sys_borrow` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`uname` VARCHAR(50) NOT NULL,
	`bname` VARCHAR(50) NOT NULL,
	`author` VARCHAR(50) NOT NULL,
	`category` VARCHAR(50) NOT NULL,
	`borrowtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
