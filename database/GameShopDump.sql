-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: init
-- ------------------------------------------------------
-- Server version	8.0.18

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

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game`
(
    `id`             int(11)     NOT NULL AUTO_INCREMENT,
    `name`           varchar(30) NOT NULL,
    `amount`         int(11)     NOT NULL,
    `description_ru` text        NOT NULL,
    `description_en` text        NOT NULL,
    `price`          double      NOT NULL,
    `deleted`        int(11)     NOT NULL,
    `genre_id`       int(11)     NOT NULL,
    `image_id`       int(11)     NOT NULL,
    `publisher_id`   int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_game_genre1_idx` (`genre_id`),
    KEY `fk_game_image1_idx` (`image_id`),
    KEY `fk_game_publisher1_idx` (`publisher_id`),
    CONSTRAINT `fk_game_genre1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
    CONSTRAINT `fk_game_image1` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
    CONSTRAINT `fk_game_publisher1` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game`
    DISABLE KEYS */;
INSERT INTO `game`
VALUES (1, 'Assasins creed', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 5000, 0, 2, 1, 3),
       (2, 'Cyberpunk 2077', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 10000, 0, 2, 2,
        1),
       (3, 'For honor', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 7000, 0, 1, 3, 3),
       (4, 'Overwatch', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 4000, 0, 3, 4, 2),
       (5, 'Raibow 6 siege', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 3500, 0, 3, 5, 3),
       (6, 'StarCraft-2', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 8000, 0, 5, 6, 2),
       (7, 'Witcher 3 Wild hunt', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 9000, 0, 2,
        7, 1),
       (8, 'World of WarCraft', 100, 'Here will be placed your decription', 'Здесь будет ваше описание', 6000, 0, 4, 8,
        2);
/*!40000 ALTER TABLE `game`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gameintransaction`
--

DROP TABLE IF EXISTS `gameintransaction`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gameintransaction`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `amount`         int(11) NOT NULL,
    `deleted`        int(11) NOT NULL,
    `game_id`        int(11) NOT NULL,
    `transaction_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_GameInTransaction_game1_idx` (`game_id`),
    KEY `fk_GameInTransaction_transaction1_idx` (`transaction_id`),
    CONSTRAINT `fk_GameInTransaction_game1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `fk_GameInTransaction_transaction1` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gameintransaction`
--

LOCK TABLES `gameintransaction` WRITE;
/*!40000 ALTER TABLE `gameintransaction`
    DISABLE KEYS */;
INSERT INTO `gameintransaction`
VALUES (1, 1, 0, 1, 1);
/*!40000 ALTER TABLE `gameintransaction`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `name_en` varchar(30) NOT NULL,
    `name_ru` varchar(30) NOT NULL,
    `deleted` int(11)     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre`
    DISABLE KEYS */;
INSERT INTO `genre`
VALUES (1, 'Action', 'Экшн', 0),
       (2, 'RPG', 'РПГ', 0),
       (3, 'Shooter', 'Шутер', 0),
       (4, 'MMORPG', 'ММОРПГ', 0),
       (5, 'Strategy', 'Стратегия', 0);
/*!40000 ALTER TABLE `genre`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `name`    varchar(20) NOT NULL,
    `path`    varchar(45) NOT NULL,
    `deleted` int(11)     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image`
    DISABLE KEYS */;
INSERT INTO `image`
VALUES (1, 'Assassin', 'img/ass.jpg', 0),
       (2, 'CyberPunk', 'img/cyber.jpg', 0),
       (3, 'ForHonor', 'img/honor.jpg', 0),
       (4, 'Overwatch', 'img/over.png', 0),
       (5, 'Rainbow6', 'img/rainbow.jpg', 0),
       (6, 'StarCraft2', 'img/star.jpg', 0),
       (7, 'Witcher3', 'img/witcher.jpg', 0),
       (8, 'WorlOfWarCraft', 'img/wow.jpg', 0),
       (9, 'SDPR', 'img/cdpr.jpg', 0),
       (10, 'Blizzard', 'img/blizzard.png', 0),
       (11, 'Ubisoft', 'img/ubisoft.png', 0);
/*!40000 ALTER TABLE `image`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `library`
(
    `user_id` int(11) NOT NULL,
    `game_id` int(11) NOT NULL,
    KEY `fk_library_user1_idx` (`user_id`),
    KEY `fk_library_game1_idx` (`game_id`),
    CONSTRAINT `fk_library_game1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `fk_library_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library`
    DISABLE KEYS */;
INSERT INTO `library`
VALUES (2, 1);
/*!40000 ALTER TABLE `library`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `name`    varchar(30) NOT NULL,
    `email`   varchar(50) NOT NULL,
    `address` varchar(45) NOT NULL,
    `phone`   varchar(20) NOT NULL,
    `country` varchar(20) NOT NULL,
    `deleted` int(11)     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher`
    DISABLE KEYS */;
INSERT INTO `publisher`
VALUES (1, 'CD PROJEKT RED', 'creators@cdprojektred.com', 'ul. Jagiellonska 74 Warzawa', '777', 'Poland', 0),
       (2, 'Blizzard Entertaiment', 'tours@blizzard.com.', 'Box 18979 Irvine, CA 92623', '777', 'USA', 0),
       (3, 'Ubisoft', 'support.ubisoft.com', '625 Third Street San Francisco, CA 92623', '777', 'USA', 0);
/*!40000 ALTER TABLE `publisher`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `name_en` varchar(15) NOT NULL,
    `name_ru` varchar(15) NOT NULL,
    `deleted` int(11)     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role`
    DISABLE KEYS */;
INSERT INTO `role`
VALUES (1, 'Admin', 'Админ', 0),
       (2, 'User', 'Пользователь', 0);
/*!40000 ALTER TABLE `role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `name_en` varchar(20) NOT NULL,
    `name_ru` varchar(20) NOT NULL,
    `deleted` int(11)     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status`
    DISABLE KEYS */;
INSERT INTO `status`
VALUES (1, 'Successfull', 'Успешно', 0),
       (2, 'Fail', 'Провал', 0);
/*!40000 ALTER TABLE `status`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `date`      date    NOT NULL,
    `time`      time    NOT NULL,
    `deleted`   int(11) NOT NULL,
    `user_id`   int(11) NOT NULL,
    `status_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_transaction_user1_idx` (`user_id`),
    KEY `fk_transaction_status1_idx` (`status_id`),
    CONSTRAINT `fk_transaction_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
    CONSTRAINT `fk_transaction_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction`
    DISABLE KEYS */;
INSERT INTO `transaction`
VALUES (1, '2020-06-15', '19:30:10', 0, 2, 1);
/*!40000 ALTER TABLE `transaction`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`       int(11)     NOT NULL AUTO_INCREMENT,
    `name`     varchar(20) NOT NULL,
    `surname`  varchar(20) NOT NULL,
    `login`    varchar(20) NOT NULL,
    `password` varchar(20) NOT NULL,
    `email`    varchar(20) NOT NULL,
    `phone`    varchar(20) NOT NULL,
    `gender`   varchar(8)  NOT NULL,
    `country`  varchar(30) NOT NULL,
    `cash`     double      NOT NULL,
    `deleted`  int(11)     NOT NULL,
    `role_id`  int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `login_UNIQUE` (`login`),
    KEY `fk_user_role_idx` (`role_id`),
    CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, 'Андрей', 'Тен', 'Naipe', 'Andrey515678', 'naipe@gmail.com', '87073026800', 'male', 'Казахстан', 1000000, 0,
        1),
       (2, 'НеАндрей', 'Тен', 'Snaipe', 'Andrey515678', 'naipe@mail.ru', '87073026800', 'female', 'Казахстан', 0, 0, 2);
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-08-16 19:48:55
