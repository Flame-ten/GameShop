/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


-- -----------------------------------------------------
-- Table `init`.`language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`language`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `language` VARCHAR(20) NOT NULL,
    `deleted`  INT         NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`role`
(
    `id`          INT        NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(5) NOT NULL,
    `deleted`     INT        NOT NULL,
    `language_id` INT        NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_role_language1_idx` (`language_id` ASC),
    CONSTRAINT `fk_role_language1`
        FOREIGN KEY (`language_id`)
            REFERENCES `init`.`language` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`image`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(20) NOT NULL,
    `path`    VARCHAR(45) NOT NULL,
    `deleted` INT         NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`user`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(20) NOT NULL,
    `surname`  VARCHAR(20) NOT NULL,
    `login`    VARCHAR(20) NOT NULL,
    `password` VARCHAR(20) NOT NULL,
    `email`    VARCHAR(20) NOT NULL,
    `phone`    VARCHAR(20) NOT NULL,
    `gender`   VARCHAR(8)  NOT NULL,
    `country`  VARCHAR(30) NOT NULL,
    `cash`     DOUBLE      NOT NULL,
    `deleted`  INT         NOT NULL,
    `role_id`  INT         NOT NULL,
    `image_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `surname_UNIQUE` (`surname` ASC),
    INDEX `fk_user_role_idx` (`role_id` ASC),
    INDEX `fk_user_image1_idx` (`image_id` ASC),
    CONSTRAINT `fk_user_role`
        FOREIGN KEY (`role_id`)
            REFERENCES `init`.`role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_image1`
        FOREIGN KEY (`image_id`)
            REFERENCES `init`.`image` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`genre`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `info`        VARCHAR(20) NOT NULL,
    `deleted`     INT         NOT NULL,
    `language_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_genre_language1_idx` (`language_id` ASC),
    CONSTRAINT `fk_genre_language1`
        FOREIGN KEY (`language_id`)
            REFERENCES `init`.`language` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`publisher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`publisher`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(20) NOT NULL,
    `email`    VARCHAR(20) NOT NULL,
    `address`  VARCHAR(45) NOT NULL,
    `phone`    VARCHAR(20) NOT NULL,
    `country`  VARCHAR(20) NOT NULL,
    `deleted`  INT         NOT NULL,
    `image_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_publisher_image1_idx` (`image_id` ASC),
    CONSTRAINT `fk_publisher_image1`
        FOREIGN KEY (`image_id`)
            REFERENCES `init`.`image` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`game`
(
    `id`           INT         NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(20) NOT NULL,
    `amount`       INT         NOT NULL,
    `description`  TEXT        NOT NULL,
    `price`        DOUBLE      NOT NULL,
    `deleted`      INT         NOT NULL,
    `genre_id`     INT         NOT NULL,
    `image_id`     INT         NOT NULL,
    `publisher_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_game_genre1_idx` (`genre_id` ASC),
    INDEX `fk_game_image1_idx` (`image_id` ASC),
    INDEX `fk_game_publisher1_idx` (`publisher_id` ASC),
    CONSTRAINT `fk_game_genre1`
        FOREIGN KEY (`genre_id`)
            REFERENCES `init`.`genre` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_game_image1`
        FOREIGN KEY (`image_id`)
            REFERENCES `init`.`image` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_game_publisher1`
        FOREIGN KEY (`publisher_id`)
            REFERENCES `init`.`publisher` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`status`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `info`        VARCHAR(15) NOT NULL,
    `deleted`     INT         NOT NULL,
    `language_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_status_language1_idx` (`language_id` ASC),
    CONSTRAINT `fk_status_language1`
        FOREIGN KEY (`language_id`)
            REFERENCES `init`.`language` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`transaction`
(
    `id`        INT ZEROFILL NOT NULL AUTO_INCREMENT,
    `price`     DOUBLE       NOT NULL,
    `date`      DATE         NOT NULL,
    `time`      TIME         NOT NULL,
    `deleted`   INT          NOT NULL,
    `user_id`   INT          NOT NULL,
    `status_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_transaction_user1_idx` (`user_id` ASC),
    INDEX `fk_transaction_status1_idx` (`status_id` ASC),
    CONSTRAINT `fk_transaction_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `init`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_transaction_status1`
        FOREIGN KEY (`status_id`)
            REFERENCES `init`.`status` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`GameInTransaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GameInTransaction`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`GameInTransaction`
(
    `id`             INT          NOT NULL AUTO_INCREMENT,
    `user_name`      VARCHAR(45)  NOT NULL,
    `payment`        DOUBLE       NOT NULL,
    `amount`         INT          NOT NULL,
    `deleted`        INT          NOT NULL,
    `game_id`        INT          NOT NULL,
    `transaction_id` INT ZEROFILL NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_GameInTransaction_game1_idx` (`game_id` ASC),
    INDEX `fk_GameInTransaction_transaction1_idx` (`transaction_id` ASC),
    CONSTRAINT `fk_GameInTransaction_game1`
        FOREIGN KEY (`game_id`)
            REFERENCES `init`.`game` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_GameInTransaction_transaction1`
        FOREIGN KEY (`transaction_id`)
            REFERENCES `init`.`transaction` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `init`.`library`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `init`.`library`
(
    `user_id` INT NOT NULL,
    `game_id` INT NOT NULL,
    INDEX `fk_library_user1_idx` (`user_id` ASC),
    INDEX `fk_library_game1_idx` (`game_id` ASC),
    CONSTRAINT `fk_library_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `init`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_library_game1`
        FOREIGN KEY (`game_id`)
            REFERENCES `init`.`game` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 100
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

INSERT INTO role (id, deleted, name, language_id)
VALUES (1, 0, 'Admin', 1),
       (1, 0, 'Админ', 2);
INSERT INTO user (id, name, surname, login, password, email, phone, gender, country, cash, deleted, role_id, image_id)
VALUES (1, 'lol', 'lil', 'lul', 515678, 'great@mail.ru', '444444', 'male', 'kazak', 5555555, 0, 1, 1);