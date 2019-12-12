create database 4eyg55o51S;
use 4eyg55o51S;

-- -----------------------------------------------------
-- Table `4eyg55o51s`.`account_rank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `account_rank` (
  `account_rank_id` INT(11) NOT NULL,
  `rank_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`account_rank_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51s`.`organisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `organisation` (
  `organisation_name` VARCHAR(45) NOT NULL,
  `organisation_address` VARCHAR(45) NOT NULL,
  `organisation_email` VARCHAR(45) NOT NULL,
  `organisation_mobile` VARCHAR(45) NOT NULL,
  `organisation_building_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`organisation_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51s`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `created` DATETIME NOT NULL,
  `organisation_name` VARCHAR(45) NOT NULL,
  `account_rank_account_rank_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_users_Account_Rank1`
    FOREIGN KEY (`account_rank_account_rank_id`)
    REFERENCES `account_rank` (`account_rank_id`),
  CONSTRAINT `fk_users_organisation1`
    FOREIGN KEY (`organisation_name`)
    REFERENCES `organisation` (`organisation_name`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51s`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comment` (
  `comment_Id` INT(11) NOT NULL AUTO_INCREMENT,
  `comment_title` VARCHAR(45) NOT NULL,
  `comment_text` VARCHAR(45) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`comment_Id`),
  CONSTRAINT `fk_comment_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `4eyg55o51s`.`maps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maps` (
  `map_id` INT(20) NOT NULL AUTO_INCREMENT,
  `org_name` VARCHAR(45) NOT NULL,
  `org_building` VARCHAR(45) NOT NULL,
  `map_name` VARCHAR(45) NOT NULL,
  `map_comments` VARCHAR(500) NOT NULL,
  `map_image` LONGBLOB NOT NULL,
  PRIMARY KEY (`map_id`, `org_building`),
  CONSTRAINT `fk_maps_organisation1`
    FOREIGN KEY (`org_name`)
    REFERENCES `organisation` (`organisation_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51s`.`map points`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `map points` (
  `point_id` INT NOT NULL,
  `point_name` VARCHAR(45) NOT NULL,
  `point_org` VARCHAR(45) NOT NULL,
  `point_building` VARCHAR(45) NOT NULL,
  `maps_map_id` INT(20) NOT NULL,
  `maps_org_building` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`point_id`),
  CONSTRAINT `fk_map points_maps1`
    FOREIGN KEY (`maps_map_id` , `maps_org_building`)
    REFERENCES `maps` (`map_id` , `org_building`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `4eyg55o51s`.`point_to`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `point_to` (
  `point_to_id` INT NOT NULL,
  `map points_point_id` INT NOT NULL,
  `point_weight` INT NOT NULL,
  `point_direction` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`point_to_id`),
  CONSTRAINT `fk_point_to_map points1`
    FOREIGN KEY (`map points_point_id`)
    REFERENCES `map points` (`point_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

