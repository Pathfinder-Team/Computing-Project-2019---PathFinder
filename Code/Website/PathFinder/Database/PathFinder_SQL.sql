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
-- Table `mydb`.`Organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Organisation` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Organisation` (
  `idOrganisation` INT NOT NULL,
  `organisation_name` VARCHAR(45) NOT NULL,
  `Maps_idMaps` INT NOT NULL,
  PRIMARY KEY (`idOrganisation`, `organisation_name`, `Maps_idMaps`),
  CONSTRAINT `fk_Organisation_Maps1`
    FOREIGN KEY (`Maps_idMaps`)
    REFERENCES `mydb`.`Maps` (`idMaps`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Organisation_Maps1_idx` ON `mydb`.`Organisation` (`Maps_idMaps` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Rank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Rank` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Rank` (
  `rank_id` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`rank_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `status_UNIQUE` ON `mydb`.`Rank` (`status` ASC) VISIBLE;

CREATE UNIQUE INDEX `rank_id_UNIQUE` ON `mydb`.`Rank` (`rank_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User` ;

CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL,
  `user_rank` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `organisation` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `created` DATETIME NULL,
  `Organisation_idOrganisation` INT NOT NULL,
  `Rank_rank_id` INT NOT NULL,
  PRIMARY KEY (`idUser`, `Organisation_idOrganisation`, `Rank_rank_id`),
  CONSTRAINT `fk_User_Organisation1`
    FOREIGN KEY (`Organisation_idOrganisation`)
    REFERENCES `mydb`.`Organisation` (`idOrganisation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Rank1`
    FOREIGN KEY (`Rank_rank_id`)
    REFERENCES `mydb`.`Rank` (`rank_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `user_name_UNIQUE` ON `mydb`.`User` (`user_name` ASC) VISIBLE;

CREATE INDEX `fk_User_Organisation1_idx` ON `mydb`.`User` (`Organisation_idOrganisation` ASC) VISIBLE;

CREATE INDEX `fk_User_Rank1_idx` ON `mydb`.`User` (`Rank_rank_id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
insert into Account_Status values(1,"Adminstrator");
insert into Account_Status values(2,"Standard_User");

insert into users values(0,"kevind","Kevin","Dunne","password","kevin@kevin",now(),1);
insert into users values(0,"katep","Jeketerina","Pavlenko","password","kate@kate",now(),1);
insert into users values(0,"chrisc","Christopher","Costello","password","Chris@kate",now(),1);