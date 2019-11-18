-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

set global time_zone = '+0:00';
SET SQL_SAFE_UPDATES = 0;
-- -----------------------------------------------------
-- Table `mydb`.`account_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account_status` (
  `idAccount_Status` INT(11) NOT NULL,
  `Status` VARCHAR(45) NULL,
  PRIMARY KEY (`idAccount_Status`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `idtable1` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL unique,
  `first_name` VARCHAR(45) not NULL,
  `last_name` VARCHAR(45) not NULL,
  `password` VARCHAR(250) NOT NULL,
  `email` VARCHAR(45) NULL ,
  `created` TIMESTAMP NULL,
  `Account_Status_Users` INT(11) NOT NULL,
  PRIMARY KEY (`idtable1`, `Account_Status_Users`),
  CONSTRAINT `fk_Users_Account_Status1`
    FOREIGN KEY (`Account_Status_Users`)
    REFERENCES `mydb`.`account_status` (`idAccount_Status`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`bulletinboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`bulletinboard` (
  `idbulletinboard` INT(11) NOT NULL auto_increment,
  `userName` VARCHAR(45) not NULL,
  `title` VARCHAR(45) not NULL,
  `message` VARCHAR(45) not NULL,
  `email` VARCHAR(45) not NULL,
  `users_idtable1` INT(11) NOT NULL,
  PRIMARY KEY (`idbulletinboard`, `users_idtable1`),
  CONSTRAINT `fk_bulletinboard_users1`
    FOREIGN KEY (`users_idtable1`)
    REFERENCES `mydb`.`users` (`idtable1`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`comment` (
  `idComment` INT(11) NOT NULL AUTO_INCREMENT,
  `comment_title` TEXT not NULl,
  `comment_text` TEXT not NULL,
  `created` TIMESTAMP NULL,
  `Users_idtable1` INT(11) NOT NULL,
  PRIMARY KEY (`idComment`, `Users_idtable1`),
  CONSTRAINT `fk_Comment_Users1`
    FOREIGN KEY (`Users_idtable1`)
    REFERENCES `mydb`.`users` (`idtable1`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`posts` (
  `idPosts` INT(11) NOT NULL AUTO_INCREMENT,
  `post_title` VARCHAR(45) not NULL,
  `post_text` TEXT not NULL,
  `created` TIMESTAMP NULL,
  `Users_idtable1` INT(11) NOT NULL,
  `threadID` VARCHAR(45) NULL,
  PRIMARY KEY (`idPosts`, `Users_idtable1`),
  CONSTRAINT `fk_Posts_Users`
    FOREIGN KEY (`Users_idtable1`)
    REFERENCES `mydb`.`users` (`idtable1`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


insert into account_status values(1,"Administrator");
insert into account_status values(2,"Standard User");
insert into account_status values(3,"Guest");

select * from users;
insert into users values(0,"kevind","Kevin","Dunne","password","Kevin@gmail.com",now(),1);

insert into users values(0,"katep","Jekaterina","Pavlenko","password","Jekaterina@gmail.com",now(),1);

insert into users values(0,"pam","Pamela","OBrien","password","Pamela@gmail.com",now(),2);

insert into users values(0,"nat","Natasha","Kiley","password","Natasha@gmail.com",now(),2);
select * from users;