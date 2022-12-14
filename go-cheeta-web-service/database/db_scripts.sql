-- Branch
-- Customer
-- Vehicle - fk to vehicle_Type
-- vehicle_Type - add cost per km
-- location - fk to branch
-- driver - fk to vehcile
-- bookings add feedback column

CREATE SCHEMA `gocheeta` ;

CREATE TABLE `gocheeta`.`branch` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `gocheeta`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `telephone` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `gocheeta`.`vehicle_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `rate` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `gocheeta`.`driver` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `telephone` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL,
  `branch_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_branch_id_idx` (`branch_id` ASC) VISIBLE,
  CONSTRAINT `fk_driver_branch_id`
    FOREIGN KEY (`branch_id`)
    REFERENCES `gocheeta`.`branch` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `gocheeta`.`vehicle` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `make` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `year` VARCHAR(4) NULL,
  `driver_id` INT NOT NULL,
  `branch_id` INT NOT NULL,
  `vehicle_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_branch_id_idx` (`branch_id` ASC) VISIBLE,
  INDEX `fk_driver_id_idx` (`driver_id` ASC) VISIBLE,
  INDEX `fk_vehicle_vehicle_type_id_idx` (`vehicle_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_vehicle_branch_id`
    FOREIGN KEY (`branch_id`)
    REFERENCES `gocheeta`.`branch` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_driver_id`
    FOREIGN KEY (`driver_id`)
    REFERENCES `gocheeta`.`driver` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION),
 CONSTRAINT `fk_vehicle_vehicle_type_id_idx`
    FOREIGN KEY (`vehicle_type_id`)
    REFERENCES `gocheeta`.`vehicle_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `gocheeta`.`location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(200) NOT NULL,
  `branch_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_location_branch_id_idx` (`branch_id` ASC) VISIBLE,
  CONSTRAINT `fk_location_branch_id`
    FOREIGN KEY (`branch_id`)
    REFERENCES `gocheeta`.`branch` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `booking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fare` decimal(10,0) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'PENDING',
  `customer_feedback` varchar(45) DEFAULT NULL,
  `driver_feedback` varchar(45) DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `duration_minute` int DEFAULT NULL,
  `vehicle_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `branch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_booking_customer_id_idx` (`customer_id`),
  KEY `fk_booking_vehicle_id_idx` (`vehicle_id`),
  KEY `fk_booking_branch_id_idx` (`branch_id`),
  CONSTRAINT `fk_booking_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  CONSTRAINT `fk_booking_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_booking_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`));

CREATE TABLE `gocheeta`.`login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(10) NOT NULL,
  `password` VARCHAR(40) NOT NULL,
  `role` VARCHAR(10) NOT NULL,
  `reference_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`username` ASC) VISIBLE);

CREATE TABLE `gocheeta`.`admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `telephone` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `designation` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `gocheeta`.`branch` 
ADD UNIQUE INDEX `unique_branch_city_idx` (`city` ASC) INVISIBLE,
ADD UNIQUE INDEX `unique_branch_name_idx` (`name` ASC) VISIBLE;

ALTER TABLE `gocheeta`.`customer` 
ADD UNIQUE INDEX `uk_customer_telephone_idx` (`telephone` ASC) INVISIBLE,
ADD UNIQUE INDEX `uk_customer_email_idx` (`email` ASC) VISIBLE;

ALTER TABLE `gocheeta`.`location` 
ADD UNIQUE INDEX `uniq_address_location_idx` (`address` ASC) VISIBLE;

ALTER TABLE `gocheeta`.`admin` 
ADD UNIQUE INDEX `uk_admin_telephone_idx` (`telephone` ASC) INVISIBLE,
ADD UNIQUE INDEX `uk_admin_email_idx` (`email` ASC) VISIBLE;

CREATE TABLE `gocheeta`.`distance_matrix` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `locId1` INT NOT NULL,
  `locId2` INT NOT NULL,
  `distance` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_distance_loc1_idx` (`locId1` ASC) VISIBLE,
  INDEX `fk_distance_loc2_idx` (`locId2` ASC) VISIBLE,
  CONSTRAINT `fk_distance_loc1`
    FOREIGN KEY (`locId1`)
    REFERENCES `gocheeta`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_distance_loc2`
    FOREIGN KEY (`locId2`)
    REFERENCES `gocheeta`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `gocheeta`.`booking` 
DROP COLUMN `duration_minute`,
ADD COLUMN `booktime` DATETIME NOT NULL DEFAULT NOW() AFTER `branch_id`,
ADD COLUMN `starttime` DATETIME NULL AFTER `booktime`,
ADD COLUMN `endtime` DATETIME NULL AFTER `starttime`;

INSERT INTO `gocheeta`.`admin` (`name`, `telephone`, `email`, `designation`) VALUES ('Peter', '072565487', 'peter@gocheeta.lk', 'manager');
INSERT INTO `gocheeta`.`login` (`username`, `password`, `role`, `reference_id`) VALUES ('peter', 'peter', 'ADMIN', (select id from admin where name = 'Peter'));

INSERT INTO `gocheeta`.`branch`(`name`, `city`) VALUES ('Colombo City Center', 'Colombo');
INSERT INTO `gocheeta`.`branch`(`name`, `city`) VALUES ('Kandy Central', 'Kandy');
INSERT INTO `gocheeta`.`branch`(`name`, `city`) VALUES ('Malabe', 'Battaramulla');

INSERT INTO `gocheeta`.`location`(`address`, `branch_id`) VALUES ('Borella Junction', (select id from `gocheeta`.`branch` where city = 'Colombo'));
INSERT INTO `gocheeta`.`location`(`address`, `branch_id`) VALUES ('Colombo Fort', (select id from `gocheeta`.`branch` where city = 'Colombo'));
INSERT INTO `gocheeta`.`location`(`address`, `branch_id`) VALUES ('Kirulapana Junction', (select id from `gocheeta`.`branch` where city = 'Colombo'));

INSERT INTO `gocheeta`.`location`(`address`, `branch_id`) VALUES ('Malabe Junction', (select id from `gocheeta`.`branch` where city = 'Battaramulla'));
INSERT INTO `gocheeta`.`location`(`address`, `branch_id`) VALUES ('Piliyandala Junction', (select id from `gocheeta`.`branch` where city = 'Battaramulla'));

INSERT INTO `gocheeta`.`distance_matrix` (`locId1`, `locId2`, `distance`) VALUES ((select id from `gocheeta`.`location` where address = 'Borella Junction'), (select id from `gocheeta`.`location` where address = 'Colombo Fort'), 12);
INSERT INTO `gocheeta`.`distance_matrix` (`locId1`, `locId2`, `distance`) VALUES ((select id from `gocheeta`.`location` where address = 'Borella Junction'), (select id from `gocheeta`.`location` where address = 'Kirulapana Junction'), 8);
INSERT INTO `gocheeta`.`distance_matrix` (`locId1`, `locId2`, `distance`) VALUES ((select id from `gocheeta`.`location` where address = 'Colombo Fort'), 	 (select id from `gocheeta`.`location` where address = 'Kirulapana Junction'), 15);

INSERT INTO `gocheeta`.`distance_matrix` (`locId1`, `locId2`, `distance`) VALUES ((select id from `gocheeta`.`location` where address = 'Malabe Junction'), (select id from `gocheeta`.`location` where address = 'Piliyandala Junction'), 20);