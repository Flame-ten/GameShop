-- MySQL Script generated by MySQL Workbench
-- Fri Mar 13 18:19:33 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(45) NOT NULL,
    `surname` VARCHAR(45) NOT NULL,
    `phone`   VARCHAR(45) NOT NULL,
    `gender`  VARCHAR(45) NOT NULL,
    `country` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Company`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Game`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `id company` INT         NOT NULL,
    `name`       VARCHAR(45) NOT NULL,
    `price`      VARCHAR(45) NOT NULL,
    `discount`   VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Game_Company1_idx` (`id company` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_Game_Company1`
        FOREIGN KEY (`id company`)
            REFERENCES `mydb`.`Company` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Transaction`
(
    `id`      INT NOT NULL AUTO_INCREMENT,
    `user id` INT NOT NULL,
    `game id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_transaction_user_idx` (`user id` ASC) VISIBLE,
    INDEX `fk_transaction_Game1_idx` (`game id` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_transaction_user`
        FOREIGN KEY (`user id`)
            REFERENCES `mydb`.`User` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_transaction_Game1`
        FOREIGN KEY (`game id`)
            REFERENCES `mydb`.`Game` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TransactionDetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TransactionInfo`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `payment` VARCHAR(45) NOT NULL,
    `amount`  VARCHAR(45) NOT NULL,
    `date`    DATE        NOT NULL,
    `time`    TIME        NOT NULL,
    INDEX `fk_transactionInfo_transaction1_idx` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_transactionInfo_transaction1`
        FOREIGN KEY (`id`)
            REFERENCES `mydb`.`Transaction` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`AccountLibriary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`AccountLibriary`
(
    `id`        INT         NOT NULL,
    `game id`   INT         NOT NULL,
    `user name` VARCHAR(45) NOT NULL,
    `game name` VARCHAR(45) NOT NULL,
    INDEX `fk_accountLibriary_Game1_idx` (`game id` ASC) VISIBLE,
    CONSTRAINT `fk_accountLibriary_user1`
        FOREIGN KEY (`id`)
            REFERENCES `mydb`.`User` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_accountLibriary_Game1`
        FOREIGN KEY (`game id`)
            REFERENCES `mydb`.`Game` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Employee`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(45) NOT NULL,
    `surname` VARCHAR(45) NOT NULL,
    `phone`   VARCHAR(45) NOT NULL,
    `country` VARCHAR(45) NOT NULL,
    `adress`  VARCHAR(45) NOT NULL,
    INDEX `fk_Employee_user1_idx` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_Employee_user1`
        FOREIGN KEY (`id`)
            REFERENCES `mydb`.`User` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
