
CREATE TABLE IF NOT EXISTS  `account_rank` (
  `account_rank_id` INT(11) NOT NULL,
  `rank_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`account_rank_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table  `organisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  `organisation` (
  `organisation_name` VARCHAR(45) NOT NULL,
  `organisation_address` VARCHAR(45) NOT NULL,
  `organisation_email` VARCHAR(45) NOT NULL,
  `organisation_mobile` VARCHAR(45) NOT NULL,
  `organisation_building_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`organisation_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table  `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  `users` (
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
  INDEX `fk_users_Account_Rank1` (`account_rank_account_rank_id` ASC),
  INDEX `fk_users_organisation1` (`organisation_name` ASC),
  CONSTRAINT `fk_users_Account_Rank1`
    FOREIGN KEY (`account_rank_account_rank_id`)
    REFERENCES  `account_rank` (`account_rank_id`),
  CONSTRAINT `fk_users_organisation1`
    FOREIGN KEY (`organisation_name`)
    REFERENCES  `organisation` (`organisation_name`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table  `comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  `comment` (
  `comment_Id` INT(11) NOT NULL AUTO_INCREMENT,
  `comment_title` VARCHAR(45) NOT NULL,
  `comment_text` VARCHAR(45) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`comment_Id`),
  INDEX `fk_comment_users1` (`user_id` ASC),
  CONSTRAINT `fk_comment_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES  `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table  `maps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  `maps` (
  `map_id` INT(20) NOT NULL AUTO_INCREMENT,
  `org_name` VARCHAR(45) NOT NULL,
  `org_building` VARCHAR(45) NOT NULL,
  `map_name` VARCHAR(45) NOT NULL,
  `map_comments` VARCHAR(500) NOT NULL,
  `map_image` LONGBLOB NOT NULL,
  PRIMARY KEY (`map_id`),
  INDEX `fk_maps_organisation1` (`org_name` ASC),
  UNIQUE INDEX `map_name_UNIQUE` (`map_name` ASC),
  UNIQUE INDEX `map_id_UNIQUE` (`map_id` ASC),
  CONSTRAINT `fk_maps_organisation1`
    FOREIGN KEY (`org_name`)
    REFERENCES  `organisation` (`organisation_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table  `map points`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  `map points` (
  `point_id` INT UNSIGNED NOT NULL,
  `point_name` VARCHAR(45) NOT NULL,
  `point_org` VARCHAR(45) NOT NULL,
  `maps_map_id` INT(20) NOT NULL,
  PRIMARY KEY (`point_id`),
  INDEX `fk_map points_maps1_idx` (`maps_map_id` ASC),
  CONSTRAINT `fk_map points_maps1`
    FOREIGN KEY (`maps_map_id`)
    REFERENCES  `maps` (`map_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  `point_to`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  `point_to` (
  `point_to_id` INT NOT NULL,
  `map points_point_id` INT UNSIGNED NOT NULL,
  `point_weight` INT NOT NULL,
  `point_direction` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`point_to_id`),
  INDEX `fk_point_to_map points1_idx` (`map points_point_id` ASC),
  CONSTRAINT `fk_point_to_map points1`
    FOREIGN KEY (`map points_point_id`)
    REFERENCES  `map points` (`point_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

insert into `account_rank` (`account_rank_id`, `rank_name`) values
(1, 'Adminstrator'),
(2, 'Standard_users');


insert into `organisation` (`organisation_name`, `organisation_address`, `organisation_email`, `organisation_mobile`, `organisation_building_name`) values
('Limerick Institute of Technology', 'Thurles', 'LIT@gmail.com', '0873010201', 'Lit Thurles'),
('Codec', 'Dublin', 'Codec@gmail.com', '0873010201', 'Codec Dublin');

insert into `users` (`user_id`, `user_name`, `first_name`, `last_name`, `password`, `email`, `created`, `organisation_name`, `account_rank_account_rank_id`) values
(1, 'kevind', 'Kevin', 'Dunne', 'password', 'kevin@kevin', '2019-12-02 18:22:26', 'Limerick Institute of Technology', 1),
(2, 'katep', 'Jeketerina', 'Pavlenko', 'password', 'kate@kate', '2019-12-02 18:22:26', 'Limerick Institute of Technology', 1),
(3, 'chrisc', 'Christopher', 'Costello', 'password', 'Chris@kate', '2019-12-02 18:22:26', 'Limerick Institute of Technology', 1);

