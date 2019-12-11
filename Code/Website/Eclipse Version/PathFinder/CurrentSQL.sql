create table `account_rank` (
  `account_rank_id` int(11) not null,
  `rank_name` varchar(45) default null,
  primary key (`account_rank_id`))
engine = innodb
default character set = utf8;

insert into `account_rank` (`account_rank_id`, `rank_name`) values
(1, 'Adminstrator'),
(2, 'Standard_users');


create table if not exists `organisation` (
  `organisation_name` varchar(45) not null,
  `organisation_address` varchar(45) not null,
  `organisation_email` varchar(45) not null,
  `organisation_mobile` varchar(45) not null,
  `organisation_building_name` varchar(45) not null,
  primary key (`organisation_name`))
engine = innodb
default character set = utf8;


insert into `organisation` (`organisation_name`, `organisation_address`, `organisation_email`, `organisation_mobile`, `organisation_building_name`) values
('Limerick Institute of Technology', 'Thurles', 'LIT@gmail.com', '0873010201', 'Lit Thurles'),
('Codec', 'Dublin', 'Codec@gmail.com', '0873010201', 'Codec Dublin');


create table if not exists `maps` (
  `map_id` int(20) not null auto_increment,
  `org_name` varchar(45) not null,
  `org_building` varchar(45) not null,
  `map_name` varchar(45) null default null,
  `map_comments` varchar(45) null default null,
  `map_image` longblob null default null,
  primary key (`map_id`),
  constraint `fk_maps_organisation1`
    foreign key (`org_name`)
    references `organisation` (`organisation_name`))
engine = innodb
default character set = utf8;

create table if not exists `users` (
  `user_id` int(11) not null auto_increment,
  `user_name` varchar(45) not null,
  `first_name` varchar(45) not null,
  `last_name` varchar(45) not null,
  `password` varchar(45) not null,
  `email` varchar(45) null default null,
  `created` datetime null default null,
  `organisation_name` varchar(45) null default null,
  `account_rank_account_rank_id` int(11) not null,
  primary key (`user_id`),
  constraint `fk_users_Account_Rank1`
    foreign key (`account_rank_account_rank_id`)
    references `account_rank` (`account_rank_id`),
  constraint `fk_users_organisation1`
    foreign key (`organisation_name`)
    references `organisation` (`organisation_name`))
engine = innodb
auto_increment = 1
default character set = utf8;


insert into `users` (`user_id`, `user_name`, `first_name`, `last_name`, `password`, `email`, `created`, `organisation_name`, `account_rank_account_rank_id`) values
(1, 'kevind', 'Kevin', 'Dunne', 'password', 'kevin@kevin', '2019-12-02 18:22:26', 'Limerick Institute of Technology', 1),
(2, 'katep', 'Jeketerina', 'Pavlenko', 'password', 'kate@kate', '2019-12-02 18:22:26', 'Limerick Institute of Technology', 1),
(3, 'chrisc', 'Christopher', 'Costello', 'password', 'Chris@kate', '2019-12-02 18:22:26', 'Limerick Institute of Technology', 1);

create table if not exists `comment` (
  `comment_Id` int not null,
  `comment_title` varchar(45) null,
  `comment_text` varchar(45) null,
  `created` timestamp null,
  `user_id` int(11) not null,
  primary key (`comment_Id`),
  constraint `fk_comment_users1`
    foreign key (`user_id`)
    references `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
engine = innodb;

