CREATE SCHEMA IF NOT EXISTS `4eyg55o51S` DEFAULT CHARACTER SET utf8 ;
USE `4eyg55o51S` ;

-- -----------------------------------------------------
-- Table `4eyg55o51S`.`account_rank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4eyg55o51S`.`account_rank` (
  `account_rank_id` INT(11) NOT NULL,
  `rank_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`account_rank_id`),
  UNIQUE INDEX `account_rank_id_UNIQUE` (`account_rank_id` ASC) VISIBLE,
  UNIQUE INDEX `rank_name_UNIQUE` (`rank_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51S`.`organisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4eyg55o51S`.`organisation` (
  `organisation_name` VARCHAR(45) NOT NULL,
  `organisation_address` VARCHAR(45) NOT NULL,
  `organisation_email` VARCHAR(45) NOT NULL,
  `organisation_mobile` VARCHAR(45) NOT NULL,
  `organisation_building_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`organisation_name`),
  UNIQUE INDEX `organisation_name_UNIQUE` (`organisation_name` ASC) VISIBLE,
  UNIQUE INDEX `organisation_building_name_UNIQUE` (`organisation_building_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51S`.`maps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4eyg55o51S`.`maps` (
  `map_id` INT(20) NOT NULL AUTO_INCREMENT,
  `map_location_url` VARCHAR(45) NOT NULL,
  `important_points` VARCHAR(45) NULL DEFAULT NULL,
  `map_comments` VARCHAR(45) NULL DEFAULT NULL,
  `map_image_floor` BLOB NULL,
  `org_building_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`map_id`),
  UNIQUE INDEX `idMaps_UNIQUE` (`map_id` ASC) VISIBLE,
  INDEX `fk_maps_organisation1_idx` (`org_building_name` ASC) VISIBLE,
  CONSTRAINT `fk_maps_organisation1`
    FOREIGN KEY (`org_building_name`)
    REFERENCES `4eyg55o51S`.`organisation` (`organisation_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `4eyg55o51S`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4eyg55o51S`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `organisation_name` VARCHAR(45) NULL,
  `account_rank_account_rank_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE,
  UNIQUE INDEX `idusers_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_Account_Rank1_idx` (`account_rank_account_rank_id` ASC) VISIBLE,
  INDEX `fk_users_organisation1_idx` (`organisation_name` ASC) VISIBLE,
  CONSTRAINT `fk_users_Account_Rank1`
    FOREIGN KEY (`account_rank_account_rank_id`)
    REFERENCES `4eyg55o51S`.`account_rank` (`account_rank_id`),
  CONSTRAINT `fk_users_organisation1`
    FOREIGN KEY (`organisation_name`)
    REFERENCES `4eyg55o51S`.`organisation` (`organisation_name`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


insert into account_rank values(1,"Adminstrator");
insert into account_rank values(2,"Standard_users");

insert into organisation values("Limerick Institue of Technology","Thurles","LIT@gmail.com",08701213212,"Lit Thurles");

insert into users values(0,"kevind","Kevin","Dunne","password","kevin@kevin",now(),"Limerick Institue of Technology",1);
insert into users values(0,"katep","Jeketerina","Pavlenko","password","kate@kate",now(),"Limerick Institue of Technology",1);
insert into users values(0,"chrisc","Christopher","Costello","password","Chris@kate",now(),"Limerick Institue of Technology",1);
