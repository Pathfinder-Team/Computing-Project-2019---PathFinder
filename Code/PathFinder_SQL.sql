-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Maps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Maps` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Maps` (
  `idMaps` INT NOT NULL,
  `map_location_url` VARCHAR(45) NULL,
  PRIMARY KEY (`idMaps`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `map_location_url_UNIQUE` ON `mydb`.`Maps` (`map_location_url` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`organisation` ;

CREATE TABLE IF NOT EXISTS `mydb`.`organisation` (
  `idorganisation` INT NOT NULL,
  `organisation_name` VARCHAR(45) NOT NULL,
  `Maps_idMaps` INT NOT NULL,
  PRIMARY KEY (`idorganisation`, `organisation_name`, `Maps_idMaps`),
  CONSTRAINT `fk_organisation_Maps1`
    FOREIGN KEY (`Maps_idMaps`)
    REFERENCES `mydb`.`Maps` (`idMaps`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_organisation_Maps1_idx` ON `mydb`.`organisation` (`Maps_idMaps` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Account_Rank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Account_Rank` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Account_Rank` (
  `Account_Rank_id` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`Account_Rank_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `status_UNIQUE` ON `mydb`.`Account_Rank` (`status` ASC) VISIBLE;

CREATE UNIQUE INDEX `Account_Rank_id_UNIQUE` ON `mydb`.`Account_Rank` (`Account_Rank_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `idusers` INT NOT NULL AUTO_INCREMENT,
  `users_name` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `created` DATETIME NULL,
  `organisation_idorganisation` INT NOT NULL,
  `Account_Rank_Account_Rank_id` INT NOT NULL,
  PRIMARY KEY (`idusers`, `organisation_idorganisation`, `Account_Rank_Account_Rank_id`),
  CONSTRAINT `fk_users_organisation1`
    FOREIGN KEY (`organisation_idorganisation`)
    REFERENCES `mydb`.`organisation` (`idorganisation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_Account_Rank1`
    FOREIGN KEY (`Account_Rank_Account_Rank_id`)
    REFERENCES `mydb`.`Account_Rank` (`Account_Rank_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `users_name_UNIQUE` ON `mydb`.`users` (`users_name` ASC) VISIBLE;

CREATE INDEX `fk_users_organisation1_idx` ON `mydb`.`users` (`organisation_idorganisation` ASC) VISIBLE;

CREATE INDEX `fk_users_Account_Rank1_idx` ON `mydb`.`users` (`Account_Rank_Account_Rank_id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into Account_Rank values(1,"Adminstrator");
insert into Account_Rank values(2,"Standard_users");

insert into users values(0,"kevind","Kevin","Dunne","password","kevin@kevin",now(),1);
insert into users values(0,"katep","Jeketerina","Pavlenko","password","kate@kate",now(),1);
insert into users values(0,"chrisc","Christopher","Costello","password","Chris@kate",now(),1);