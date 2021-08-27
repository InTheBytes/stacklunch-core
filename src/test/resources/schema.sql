CREATE TABLE IF NOT EXISTS `authorization` (
  `token` VARCHAR(256) PRIMARY KEY NOT NULL,
  `expiration_date` DATETIME NOT NULL
);


CREATE TABLE IF NOT EXISTS `role` (
  `role_id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `name` VARCHAR(45) NOT NULL
  );


CREATE TABLE IF NOT EXISTS `user` (
  `user_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `user_role` INT UNSIGNED NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(81) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `active` TINYINT UNSIGNED NOT NULL DEFAULT '0',
  CONSTRAINT `fk_user_role_id`
    FOREIGN KEY (`user_role`)
    REFERENCES `role` (`role_id`)
    );


CREATE TABLE IF NOT EXISTS `confirmation` (
  `token_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  `confirmation_token` VARCHAR(256) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `is_confirmed` TINYINT UNSIGNED NOT NULL DEFAULT '0',
  CONSTRAINT `fk_confirmation_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    );


CREATE TABLE IF NOT EXISTS `driver` (
  `driver_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  `vehicle_id` VARCHAR(36) NOT NULL,
  `financial_id` VARCHAR(36) NOT NULL,
  `status` INT UNSIGNED NOT NULL,
  CONSTRAINT `fk_driver_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    );


CREATE TABLE IF NOT EXISTS `location` (
  `location_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `unit` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zip_code` INT UNSIGNED NOT NULL
  );


CREATE TABLE IF NOT EXISTS `restaurant` (
  `restaurant_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `location_id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `cuisine` VARCHAR(45) NOT NULL,
  CONSTRAINT `fk_restaurant_location_id`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`location_id`)
    );


CREATE TABLE IF NOT EXISTS `order` (
  `order_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  `restaurant_id` VARCHAR(36) NOT NULL,
  `destination_id` VARCHAR(36) NOT NULL,
  `status` INT UNSIGNED NOT NULL,
  `window_start` DATETIME NOT NULL,
  `window_end` DATETIME NOT NULL,
  `special_instructions` VARCHAR(200) NOT NULL,
  CONSTRAINT `fk_order_location_id`
    FOREIGN KEY (`destination_id`)
    REFERENCES `location` (`location_id`),
  CONSTRAINT `fk_order_restaurant_id`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_order_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    );


CREATE TABLE IF NOT EXISTS `delivery` (
  `delivery_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `order_id` VARCHAR(36) NOT NULL,
  `driver_id` VARCHAR(36) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `pickup_time` DATETIME NULL DEFAULT NULL,
  `deliver_time` DATETIME NULL DEFAULT NULL,
  CONSTRAINT `fk_delivery_id`
    FOREIGN KEY (`driver_id`)
    REFERENCES `driver` (`driver_id`),
  CONSTRAINT `fk_delivery_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `order` (`order_id`)
    );


CREATE TABLE IF NOT EXISTS `food` (
  `food_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `restaurant_id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(10,2) UNSIGNED NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  CONSTRAINT `fk_food_restaurant_id`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `restaurant` (`restaurant_id`)
    );


CREATE TABLE IF NOT EXISTS `manager` (
  `user_id` VARCHAR(36) NOT NULL,
  `restaurant_id` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`user_id`, `restaurant_id`),
  CONSTRAINT `fk_manager_restaurant_id`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_manager_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    );


CREATE TABLE IF NOT EXISTS `order_food` (
  `order_id` VARCHAR(36) NOT NULL,
  `food_id` VARCHAR(36) NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`order_id`, `food_id`),
  CONSTRAINT `fk_food_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `order` (`order_id`),
  CONSTRAINT `fk_order_food_id`
    FOREIGN KEY (`food_id`)
    REFERENCES `food` (`food_id`)
    );


CREATE TABLE IF NOT EXISTS `password_change` (
  `confirmation_token` VARCHAR(256) PRIMARY KEY NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  `created_time` DATETIME NOT NULL,
  CONSTRAINT `fk_passchange_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    );


CREATE TABLE IF NOT EXISTS `transaction` (
  `transaction_id` VARCHAR(36) PRIMARY KEY NOT NULL,
  `stripe_id` VARCHAR(36) NOT NULL,
  `order_id` VARCHAR(36) NOT NULL,
  `subtotal` DECIMAL(10,2) UNSIGNED NOT NULL,
  `tax` DECIMAL(10,2) UNSIGNED NOT NULL,
  `fee` DECIMAL(10,2) UNSIGNED NOT NULL,
  `discount` DECIMAL(10,2) UNSIGNED NOT NULL,
  `tip` DECIMAL(10,2) UNSIGNED NOT NULL,
  `total` DECIMAL(10,2) UNSIGNED NOT NULL,
  `payment_time` DATETIME NOT NULL,
  CONSTRAINT `fk_transaction_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `order` (`order_id`)
    );