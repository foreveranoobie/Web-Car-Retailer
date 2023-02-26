CREATE TABLE `webautoshop`.`products` (
  `products_id` INT NOT NULL,
  `products_mark` VARCHAR(45) NOT NULL,
  `products_model` VARCHAR(45) NOT NULL,
  `products_price` INT UNSIGNED NOT NULL,
  `products_year` INT UNSIGNED NOT NULL,
  `products_mileage` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`products_id`),
  UNIQUE INDEX `products_id_UNIQUE` (`products_id` ASC) VISIBLE);