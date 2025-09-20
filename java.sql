-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `city_id` bigint DEFAULT NULL,
  `district_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ward_id` bigint DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (2,NULL,1,NULL,'kk'),(2,24,2,691,'fff'),(2,24,3,691,'fff'),(2,NULL,4,NULL,'kk'),(2,NULL,5,NULL,'kk');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `deleted` tinyint(1) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (0,1,0,'Áo'),(0,2,0,'Quần'),(0,3,0,'Dép'),(0,4,1,'Áo thun'),(0,5,1,'Áo sơ mi'),(0,6,1,'Áo polo'),(0,7,1,'Áo hoodie'),(0,8,2,'Quần âu'),(0,9,3,'Tông lào'),(0,10,2,'Quần bò'),(0,11,3,'Bánh mỳ'),(0,12,0,'Mũ'),(0,13,12,'Mũ lưỡi trai'),(0,14,12,'Mũ phớt'),(0,15,0,'Giày'),(0,16,15,'Giày thể thao'),(0,17,15,'Giày đá bóng'),(0,18,1,'Áo len cổ lọ'),(0,19,1,'Áo blazer'),(0,20,1,'Áo ba lỗ'),(0,21,1,'Áo khoác'),(0,22,1,'Áo croptop'),(0,23,1,'Áo giữ nhiệt');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `hex_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'Đen','#000000'),(2,'Trắng','#FFFFFF'),(3,'Xám','#808080'),(4,'Kem','#FFFDD0'),(5,'Be','#F5F5DC'),(6,'Nâu','#8B4513'),(7,'Kaki','#C3B091'),(8,'Đỏ','#FF0000'),(9,'Hồng','#FFC0CB'),(10,'Cam','#FFA500'),(11,'Vàng','#FFFF00'),(12,'Xanh lá','#008000'),(13,'Xanh rêu','#4B5320'),(14,'Xanh dương','#0000FF'),(15,'Xanh navy','#000080'),(16,'Xanh da trời','#87CEEB'),(17,'Xanh ngọc','#00CED1'),(18,'Tím','#800080'),(19,'Bordeaux','#800000');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail`
--

DROP TABLE IF EXISTS `detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail` (
  `color_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  `sold_count` bigint DEFAULT NULL,
  `stock` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detail_color_id` (`color_id`),
  KEY `fk_detail_product_id` (`product_id`),
  KEY `fk_detail_size_id` (`size_id`),
  CONSTRAINT `fk_detail_color_id` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `fk_detail_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_detail_size_id` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail`
--

LOCK TABLES `detail` WRITE;
/*!40000 ALTER TABLE `detail` DISABLE KEYS */;
INSERT INTO `detail` VALUES (1,1,21,5,0,10),(8,2,21,2,0,5),(11,3,21,3,0,3),(14,4,21,7,0,2),(12,5,21,1,0,7),(1,6,22,5,0,5),(16,7,22,4,0,6),(12,8,22,7,0,7),(14,9,22,1,0,8),(1,10,23,1,0,7),(8,11,23,2,0,8),(3,12,23,3,0,9),(14,13,23,4,0,10),(2,14,24,7,0,7),(11,15,24,6,0,3),(14,16,24,5,0,1),(9,17,25,4,0,7),(11,18,25,3,0,7),(3,19,25,2,0,7),(14,20,25,1,0,7),(1,21,26,6,0,5),(8,22,26,7,0,3),(2,23,26,7,0,11),(14,24,26,7,0,7),(1,25,27,4,0,6),(2,26,27,4,0,13),(3,27,27,4,0,15),(13,28,27,3,0,9),(10,29,28,3,0,8),(1,30,28,3,0,8),(2,31,28,1,0,14),(3,32,28,2,0,20),(1,33,29,5,0,17),(18,34,29,6,0,7),(2,35,29,7,0,10),(1,36,30,1,0,12),(2,37,30,2,0,13),(11,38,30,3,0,5),(13,39,30,4,0,4),(1,40,2,5,0,10),(3,41,2,2,0,5),(15,42,2,3,0,3),(17,43,3,4,0,4),(17,44,3,3,0,4),(6,45,3,5,0,3),(1,46,3,4,0,3),(1,47,3,5,0,5),(1,48,4,3,0,5),(1,49,4,4,0,3),(2,50,4,4,0,3),(15,51,4,4,0,8),(15,52,4,5,0,3),(17,53,4,4,0,5),(17,54,4,5,0,3),(1,55,5,4,0,6),(6,56,5,4,0,1),(1,57,5,5,0,7),(11,58,5,3,0,1),(15,59,5,4,0,2),(15,60,5,5,0,1),(5,61,6,4,0,3),(1,62,6,4,0,2),(1,63,6,5,0,3),(8,64,6,4,0,1),(1,65,6,3,0,3),(15,66,6,4,0,6),(15,67,6,5,0,10),(2,68,7,3,0,1),(2,69,7,4,0,10),(2,70,7,5,0,5),(3,71,7,4,0,3),(15,72,7,4,0,5),(15,73,7,5,0,21),(17,74,7,4,0,3),(1,75,8,4,0,3),(1,76,8,5,0,10),(2,77,8,4,0,3),(2,78,8,5,0,5),(3,79,8,4,0,3),(5,80,8,4,0,2),(5,81,8,5,0,3),(7,82,8,4,0,2),(17,83,8,4,0,6),(17,84,8,5,0,1),(1,85,9,3,0,3),(1,86,9,4,0,1),(1,87,9,5,0,5),(2,88,9,4,0,3),(3,89,9,4,0,3),(15,90,2,4,0,3),(15,91,2,5,0,3),(2,92,10,4,0,3),(3,93,10,4,0,1),(3,94,10,5,0,1),(15,95,10,4,0,3),(15,96,10,5,0,3),(17,97,10,4,0,3),(17,98,10,5,0,3),(1,99,1,4,0,3),(1,100,1,5,0,5),(3,101,1,4,0,8),(15,102,1,4,0,10),(15,103,1,5,0,10),(17,104,1,4,0,10),(10,105,11,3,0,10),(1,106,11,3,0,10),(8,107,11,3,0,10),(2,108,11,3,0,10),(13,109,11,3,0,10),(2,110,12,3,0,25),(1,111,12,3,0,25),(14,112,13,3,0,10),(11,113,13,3,0,10),(2,114,13,3,0,10),(1,115,13,3,0,20),(13,116,14,3,0,10),(3,117,14,3,0,10),(2,118,14,3,0,10),(8,119,14,3,0,10),(1,120,14,3,0,10),(3,121,15,3,0,10),(11,122,15,3,0,10),(2,123,15,3,0,10),(8,124,15,3,0,10),(1,125,15,3,0,10),(14,126,16,3,0,10),(3,127,16,3,0,10),(2,128,16,3,0,10),(1,129,16,3,0,10),(5,130,16,3,0,10),(14,131,17,3,0,10),(3,132,17,3,0,10),(2,133,17,3,0,10),(9,134,17,3,0,10),(8,135,17,3,0,5),(1,136,17,3,0,5),(14,137,18,3,0,10),(2,138,18,3,0,10),(18,139,18,3,0,10),(9,140,18,3,0,10),(1,141,18,3,0,5),(5,142,18,3,0,5),(12,143,19,3,0,10),(11,144,19,3,0,10),(2,145,19,3,0,10),(18,146,19,3,0,5),(9,147,19,3,0,5),(8,148,19,3,0,5),(1,149,19,3,0,5),(11,150,20,3,0,10),(2,151,20,3,0,10),(6,152,20,3,0,10),(1,153,20,3,0,10),(5,154,20,3,0,10),(15,155,31,4,0,10),(3,156,31,4,0,10),(6,157,31,4,0,10),(2,158,32,4,0,7),(3,159,32,4,0,5),(6,160,32,4,0,10),(9,161,32,4,0,11),(5,162,32,4,0,3),(1,163,33,4,0,6),(3,164,33,4,0,5),(6,165,33,4,0,7),(5,166,33,4,0,5),(1,167,34,4,0,9),(6,168,34,4,0,15),(1,169,35,4,0,10),(2,170,35,4,0,7),(3,171,35,4,0,5),(15,172,35,4,0,15),(1,173,36,4,0,15),(5,174,36,4,0,10),(8,175,36,4,0,5),(2,176,37,4,0,5),(6,177,37,4,0,5),(8,178,37,4,0,7),(15,179,37,4,0,5),(1,180,38,4,0,10),(2,181,38,4,0,10),(6,182,38,4,0,5),(8,183,38,4,0,5),(9,184,38,4,0,4),(18,185,2,4,0,3),(1,186,39,4,0,10),(6,187,39,4,0,10);
/*!40000 ALTER TABLE `detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `deleted` tinyint(1) DEFAULT NULL,
  `percent` double DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (0,30,1),(0,66,2),(0,30,3),(0,39,4),(0,30,5),(0,20,6);
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8f4j36u3ealttx057oeppnphm` (`user_id`),
  CONSTRAINT `FKh3f2dg11ibnht4fvnmx60jcif` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (4,1),(1,3),(2,4),(3,6);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `deleted` tinyint(1) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `feedback_time` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_feedback_product_id` (`product_id`),
  KEY `fk_feedback_user_id` (`user_id`),
  CONSTRAINT `fk_feedback_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_feedback_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (0,5,'2025-06-07 00:00:00.000000',1,2,1,'Shop giao hàng nhanh, đóng gói cẩn thận, shipper thân thiện, cho shop 5s'),(0,3,'2025-06-08 05:37:05.780000',2,7,3,'Áo đẹp\n');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `src` varchar(255) DEFAULT NULL,
  `color_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_image_product_id` (`product_id`),
  CONSTRAINT `fk_image_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,21,'http://res.cloudinary.com/dxx1lgamz/image/upload/693b700b-f883-4828-b724-5aad8a5e6274_giay3soc-den',1),(2,21,'http://res.cloudinary.com/dxx1lgamz/image/upload/a5ef8ad5-ef98-4f67-b6db-ffc7a92cbfbc_giay3soc-do',8),(3,21,'http://res.cloudinary.com/dxx1lgamz/image/upload/18d5fe97-0ef3-4f48-8a89-c232d98a1826_giay3soc-vang',11),(4,21,'http://res.cloudinary.com/dxx1lgamz/image/upload/7fb52a68-5918-460b-9fe4-5e62fc81e72e_giay3soc-xanh-duong',14),(5,21,'http://res.cloudinary.com/dxx1lgamz/image/upload/aece7b05-89ee-4793-95bb-16fdcdab7772_giay3soc-xanh-la',12),(6,22,'http://res.cloudinary.com/dxx1lgamz/image/upload/66464014-4da9-4c4d-89dc-a7fb5b277023_copa-den',1),(7,22,'http://res.cloudinary.com/dxx1lgamz/image/upload/48ad03d4-12fe-48dd-afac-0eee8620bbc8_copa-nau',6),(8,22,'http://res.cloudinary.com/dxx1lgamz/image/upload/2b89634a-4529-42cd-8fca-cd7580304720_copa-trang',2),(9,22,'http://res.cloudinary.com/dxx1lgamz/image/upload/4d144e4c-1b05-4ac4-9c5c-5541ce421e23_copa-xanh-duong',14),(10,23,'http://res.cloudinary.com/dxx1lgamz/image/upload/69f4a684-f0ec-4d99-9f5e-efa8b7c3b25e_predator-league-den',1),(11,23,'http://res.cloudinary.com/dxx1lgamz/image/upload/79f2cc77-8031-4277-9e04-2971f9ad2d39_predator-league-do',8),(12,23,'http://res.cloudinary.com/dxx1lgamz/image/upload/33337d6f-3952-4548-bac8-b6575af724f3_predator-league-xam',3),(13,23,'http://res.cloudinary.com/dxx1lgamz/image/upload/35b0b6e5-dc6f-464a-8058-49242cc25349_predator-league-xanh-duong',14),(14,24,'http://res.cloudinary.com/dxx1lgamz/image/upload/9c5f45fa-43a4-461a-8ba6-0182341177b8_puma-future-trang',2),(15,24,'http://res.cloudinary.com/dxx1lgamz/image/upload/09e82c83-2815-4f4a-a4c4-1323c107e236_puma-future-vang',11),(16,24,'http://res.cloudinary.com/dxx1lgamz/image/upload/f31cad1f-6dcd-41c6-9f9a-a1a8281bf4f8_puma-future-xanh-duong',14),(17,25,'http://res.cloudinary.com/dxx1lgamz/image/upload/88503b32-8e21-4ef7-9e35-785d0b555d3b_toni-kroos-hong',9),(18,25,'http://res.cloudinary.com/dxx1lgamz/image/upload/f54ebf40-2ab1-4d08-8752-5cf9045a3c5f_toni-kroos-vang',11),(19,25,'http://res.cloudinary.com/dxx1lgamz/image/upload/2655d0bd-f624-4bd3-83e3-afdedeb1002e_toni-kroos-xam',3),(20,25,'http://res.cloudinary.com/dxx1lgamz/image/upload/bc3e99b9-9c6e-4792-962c-1739203920f0_toni-kroos-xanh-duong',14),(21,26,'http://res.cloudinary.com/dxx1lgamz/image/upload/389f2fd3-7d3b-4b44-86d6-f6a0c83186fa_nike-air-jordan-den',1),(22,26,'http://res.cloudinary.com/dxx1lgamz/image/upload/baf87afb-165f-4c88-a8e1-1490e32f46a1_nike-air-jordan-do',8),(23,26,'http://res.cloudinary.com/dxx1lgamz/image/upload/60ca6ad3-511f-4830-a9b1-ac669f6227a6_nike-air-jordan-trang',2),(24,26,'http://res.cloudinary.com/dxx1lgamz/image/upload/f8b75f21-e81a-4b53-aba2-8b31cd64e1d1_nike-air-jordan-xanh-duong',14),(25,27,'http://res.cloudinary.com/dxx1lgamz/image/upload/6f45d675-1730-4f33-99ec-0256711226cd_adidas-utraboost-den',1),(26,27,'http://res.cloudinary.com/dxx1lgamz/image/upload/e0b9dff5-1fb7-43a0-84c1-3fc11fd5e16c_adidas-utraboost-trang',2),(27,27,'http://res.cloudinary.com/dxx1lgamz/image/upload/7e816168-0dba-4114-8e74-c4def852295f_adidas-utraboost-xam',3),(28,27,'http://res.cloudinary.com/dxx1lgamz/image/upload/87582fcc-ec6e-42f3-a728-6a83b3a42116_adidas-utraboost-xanh-reu',13),(29,28,'http://res.cloudinary.com/dxx1lgamz/image/upload/6a0ce971-7007-4c0a-865a-bde7d826ee7a_biti-huner-cam',10),(30,28,'http://res.cloudinary.com/dxx1lgamz/image/upload/40e91909-c1f6-47d5-9ebd-9d179b9561ac_biti-hunter-den',1),(31,28,'http://res.cloudinary.com/dxx1lgamz/image/upload/22797e74-0dc8-4dc8-9ea1-0678a2061c12_biti-hunter-trang',2),(32,28,'http://res.cloudinary.com/dxx1lgamz/image/upload/25b71846-6961-4d54-92f3-5dfddd4a40f9_biti-hunter-xam',3),(33,29,'http://res.cloudinary.com/dxx1lgamz/image/upload/b29c940d-a36f-40de-a23b-766bf6f5fb4d_mlb-bigball-den',1),(34,29,'http://res.cloudinary.com/dxx1lgamz/image/upload/9baac69e-ddb0-4594-87a6-a1bd2fa01bb9_mlb-bigball-tim',18),(35,29,'http://res.cloudinary.com/dxx1lgamz/image/upload/2a0601dd-920d-4edd-af71-13c5e9a5c4f3_mlb-bigball-trang',2),(36,30,'http://res.cloudinary.com/dxx1lgamz/image/upload/27c7c33e-42cb-494d-a37a-ef2574196d48_nike-court-den',1),(37,30,'http://res.cloudinary.com/dxx1lgamz/image/upload/062755d6-47fc-4277-b9a9-9c75864fbd5f_nike-court-trang',2),(38,30,'http://res.cloudinary.com/dxx1lgamz/image/upload/53c89d59-d3fc-4eb0-a568-c1406ea9dc5e_nike-court-vang',11),(39,30,'http://res.cloudinary.com/dxx1lgamz/image/upload/9410baf0-e918-4590-8c32-38a33d0aa613_nike-court-xanh-reu',13),(40,2,'http://res.cloudinary.com/dxx1lgamz/image/upload/fa30c56c-c83e-460a-bd40-ed452f6c2333_au-cong-so-nam-den',1),(41,2,'http://res.cloudinary.com/dv6fjob4v/image/upload/b0302499-d528-4e2a-b36b-d0ec8babccba_au-cong-so-nam-xam',3),(42,2,'http://res.cloudinary.com/dv6fjob4v/image/upload/4b301df2-f036-496f-8bda-53fbecf5a832_au-cong-so-nam-xanh-navy',15),(43,3,'http://res.cloudinary.com/dv6fjob4v/image/upload/ecbb4bd4-ee50-4d69-8c9b-5817ac4dd770_au-cs-nu-xanh-ngoc',17),(44,3,'http://res.cloudinary.com/dv6fjob4v/image/upload/2f08c4a2-ef89-4ba6-bf3e-ac00a2cb3a6d_au-cs-nu-nau',6),(45,3,'http://res.cloudinary.com/dv6fjob4v/image/upload/798d08ff-3e5a-4c25-8d4d-b6aada12fed5_au-cs-nu-den',1),(46,4,'http://res.cloudinary.com/dv6fjob4v/image/upload/69e475e6-19bc-44a8-aa0e-2d2c0f5d3d5c_au-ni-nam-den',1),(47,4,'http://res.cloudinary.com/dv6fjob4v/image/upload/ab7f73c5-73e6-43ba-bc00-b1ed10f75ce2_au-ni-nam-soc-trang',2),(48,4,'http://res.cloudinary.com/dv6fjob4v/image/upload/842af061-58c0-45c6-ac0c-200a5ec832a8_au-ni-nam-xanh-navy',15),(49,4,'http://res.cloudinary.com/dv6fjob4v/image/upload/c86e6bea-a107-475f-8551-9c1e0fd52009_au-ni-nam-xanh-ngoc',17),(50,5,'http://res.cloudinary.com/dv6fjob4v/image/upload/7ab9b8f3-e7d4-4394-90fe-4ac11b7a7227_au-nu-ongrong-xanh-navy',15),(51,5,'http://res.cloudinary.com/dv6fjob4v/image/upload/a7a6407a-b811-4087-98d5-d29f2b2b1b49_au-nu-ongrong-vang',11),(52,5,'http://res.cloudinary.com/dv6fjob4v/image/upload/8249680e-319a-47ab-85de-29b554812b28_au-nu-ongrong-nau',6),(53,5,'http://res.cloudinary.com/dv6fjob4v/image/upload/f40a8e94-471b-485b-823a-8097d6f11f36_au-nu-ongrong-den',1),(54,6,'http://res.cloudinary.com/dv6fjob4v/image/upload/c69c8ce0-8f0a-4b8c-9cf6-b1ac061183c1_vai-au-nu-be',5),(55,6,'http://res.cloudinary.com/dv6fjob4v/image/upload/4a728e8f-6366-4281-b133-70d83c705643_vai-au-nu-den',1),(56,6,'http://res.cloudinary.com/dv6fjob4v/image/upload/f3ff0bbe-bd07-4440-9257-cb8b086eea8d_vai-au-nu-do',8),(57,6,'http://res.cloudinary.com/dv6fjob4v/image/upload/bbc23e35-85a8-47a6-a38e-bd235ffd120a_vai-au-nu-xanh-navy',15),(58,7,'http://res.cloudinary.com/dv6fjob4v/image/upload/0a21d408-283a-41ee-9755-aeee123a1691_bo-nam-jeans-trang',2),(59,7,'http://res.cloudinary.com/dv6fjob4v/image/upload/1c90e873-e944-40a2-b152-2959b0d336f9_bo-nam-jeans-xam',3),(60,7,'http://res.cloudinary.com/dv6fjob4v/image/upload/7ee50fd9-8d8e-4dde-8bb9-fba8a0f8c8ab_bo-nam-jeans-xanh-navy',15),(61,7,'http://res.cloudinary.com/dv6fjob4v/image/upload/78736dd6-d25f-4651-b85e-bd8d5ab82aec_bo-nam-jeans-xanh-ngoc',17),(62,8,'http://res.cloudinary.com/dv6fjob4v/image/upload/863d93db-8f39-4cae-9caa-7d1a98370f3a_bo-nam-oongrong-be',5),(63,8,'http://res.cloudinary.com/dv6fjob4v/image/upload/d942169f-81c3-4357-af00-8eaf3551be9b_bo-nam-ongrong-xanh-ngoc',17),(64,8,'http://res.cloudinary.com/dv6fjob4v/image/upload/83806698-6ca0-4a0a-89ff-ecbf1cc444b2_bo-nam-ongrong-xam',3),(65,8,'http://res.cloudinary.com/dv6fjob4v/image/upload/87c1a38d-bcca-45de-9007-c2baa2bf8a2b_bo-nam-ongrong-trang',2),(66,8,'http://res.cloudinary.com/dv6fjob4v/image/upload/51d8b804-8af4-4662-9a66-aa72532716c6_bo-nam-ongrong-kaki',7),(67,8,'http://res.cloudinary.com/dv6fjob4v/image/upload/6df35850-2988-432f-95b0-6f028938af93_bo-nam-ongrong-den',1),(68,9,'http://res.cloudinary.com/dv6fjob4v/image/upload/6d8e1a19-7f1d-4e0a-8370-85d69b7672cb_bo-nu-rong-den',1),(69,9,'http://res.cloudinary.com/dv6fjob4v/image/upload/460c7648-f261-431a-ae82-ce781632dd4a_bo-nu-rong-trang',2),(70,9,'http://res.cloudinary.com/dv6fjob4v/image/upload/553055ef-abc4-42e8-aafe-f3d0972152ec_bo-nu-rong-xam',3),(71,9,'http://res.cloudinary.com/dv6fjob4v/image/upload/66583591-3019-4751-9509-240e54fa2070_bo-nu-rong-xanh-navy',15),(72,10,'http://res.cloudinary.com/dv6fjob4v/image/upload/076b9c11-aa81-46ee-a0d7-418fcb03a29f_shortbo-nam-be',3),(73,10,'http://res.cloudinary.com/dv6fjob4v/image/upload/b7f650da-c2d4-4ebc-8c3a-cd50983b1729_shortbo-nam-trang',2),(74,10,'http://res.cloudinary.com/dv6fjob4v/image/upload/2511963f-ad1c-4ea4-978a-ee7190a02db7_shortbo-nam-xanh-ngoc',17),(75,10,'http://res.cloudinary.com/dv6fjob4v/image/upload/32a40edb-0283-45fa-8662-13d19381fe62_shortbo-nam-xanh-navy',15),(76,1,'http://res.cloudinary.com/dv6fjob4v/image/upload/4776433d-4760-4bb7-8ebc-7a38d051c2be_shortbo-nu-xanh-ngoc',17),(77,1,'http://res.cloudinary.com/dv6fjob4v/image/upload/a2738f71-e5ab-4cb5-b072-7eeb5d01006d_shortbo-nu-xanh-navy',15),(78,1,'http://res.cloudinary.com/dv6fjob4v/image/upload/18a31a8a-d024-4f9b-a87e-02417cc795bd_shortbo-nu-den',1),(79,1,'http://res.cloudinary.com/dv6fjob4v/image/upload/431f3831-121e-46d2-a6eb-b384088facd1_shortbo-nu-xam',3),(80,11,'http://res.cloudinary.com/dxx1lgamz/image/upload/91d6c08b-234d-4b55-9bc6-c931feff00e4_ao-thun-cam',10),(81,11,'http://res.cloudinary.com/dq4guha5o/image/upload/c490d445-725a-45a2-b646-f6897b9d9292_ao-thun-den',1),(82,11,'http://res.cloudinary.com/dq4guha5o/image/upload/173185f9-ef79-48c7-9fa7-6dc147008063_ao-thun-do',8),(83,11,'http://res.cloudinary.com/dq4guha5o/image/upload/96612c14-c8b7-447a-bb3c-e4877ab9d3bc_ao-thun-trang',2),(84,11,'http://res.cloudinary.com/dq4guha5o/image/upload/88ba657e-6587-4afd-9373-295fb7819c07_ao-thun-xanh-reu-removebg-preview',13),(85,12,'http://res.cloudinary.com/dq4guha5o/image/upload/4496f06a-5e82-45da-bf8f-5723a78dd889_ao-somi-trang',2),(86,12,'http://res.cloudinary.com/dq4guha5o/image/upload/0573b789-3c14-4deb-868a-1d1b1afc9f15_ao-somi-den',1),(87,13,'http://res.cloudinary.com/dq4guha5o/image/upload/c6feb4c3-5248-4f0c-a7d3-6ce851f7fdb9_ao-polo-xanh-duong',14),(88,13,'http://res.cloudinary.com/dq4guha5o/image/upload/f5bad87e-9b4d-46d2-8e20-115862e5ea55_ao-polo-vang',11),(89,13,'http://res.cloudinary.com/dq4guha5o/image/upload/62181ab7-b54f-4aef-863a-62260c18d010_ao-polo-trang',2),(90,13,'http://res.cloudinary.com/dq4guha5o/image/upload/b26baaa1-5d90-4526-8152-7875f5322327_ao-polo-den',1),(91,14,'http://res.cloudinary.com/dq4guha5o/image/upload/8dde53d7-4838-4234-aca7-2309871eb3c3_ao-hoodie-xanh-reu',13),(92,14,'http://res.cloudinary.com/dq4guha5o/image/upload/483de2a1-4818-45e6-964e-7da722e8243c_ao-hoodie-xam',3),(93,14,'http://res.cloudinary.com/dq4guha5o/image/upload/4c6abc68-6c02-4a78-adb0-4506b41faf55_ao-hoodie-trang',2),(94,14,'http://res.cloudinary.com/dq4guha5o/image/upload/f30dbea2-d3cd-42ca-9384-424274718cf9_ao-hoodie-do',8),(95,14,'http://res.cloudinary.com/dq4guha5o/image/upload/fed95f1e-8889-4483-bf50-ebad5207d801_ao-hoodie-den',1),(96,15,'http://res.cloudinary.com/dq4guha5o/image/upload/3738af00-2e84-442f-8629-7c971c36107a_ao-lencolo-xam',3),(97,15,'http://res.cloudinary.com/dq4guha5o/image/upload/5bfb87e7-a793-4791-90ac-b73f5cbcc75b_ao-lencolo-vang',11),(98,15,'http://res.cloudinary.com/dq4guha5o/image/upload/a81bfaa8-0158-4808-b1af-b70506373bf2_ao-lencolo-trang',2),(99,15,'http://res.cloudinary.com/dq4guha5o/image/upload/212b53c5-cee6-4949-b280-66bf04005643_ao-lencolo-do',8),(100,15,'http://res.cloudinary.com/dq4guha5o/image/upload/3862c6d4-9e11-4ebc-af66-c0bc3c17b688_ao-lencolo-den',1),(101,16,'http://res.cloudinary.com/dq4guha5o/image/upload/aca339a4-a994-45d9-ac63-b04f851b6979_ao-blazer-xanh-duong',14),(102,16,'http://res.cloudinary.com/dq4guha5o/image/upload/4339dc13-e7e1-4c88-b9e2-058ba5374bb9_ao-blazer-xam',3),(103,16,'http://res.cloudinary.com/dq4guha5o/image/upload/9f8b19e1-2a72-4f94-8130-d7387561ddeb_ao-blazer-trang',2),(104,16,'http://res.cloudinary.com/dq4guha5o/image/upload/9a06fb20-bad9-48e7-ada9-4454043116a6_ao-blazer-den',1),(105,16,'http://res.cloudinary.com/dq4guha5o/image/upload/e9217170-b8b6-46c3-b2a9-43aefd271201_ao-blazer-be',5),(106,17,'http://res.cloudinary.com/dq4guha5o/image/upload/7d42141c-b06a-48fd-bdb5-ab813e59fde2_ao-balo-xanh-duong',14),(107,17,'http://res.cloudinary.com/dq4guha5o/image/upload/41384d9d-27ac-48cd-aae1-b04421aae9a5_ao-balo-xam',3),(108,17,'http://res.cloudinary.com/dq4guha5o/image/upload/69c18200-e691-471c-ac36-8a0d6bc6d6d2_ao-balo-trang',2),(109,17,'http://res.cloudinary.com/dq4guha5o/image/upload/2ff1b0d0-349a-4372-b65f-5a1fea740bda_ao-balo-hong',9),(110,17,'http://res.cloudinary.com/dq4guha5o/image/upload/6d2ff540-ce64-4ed6-af2a-f488237e048a_ao-balo-do',8),(111,17,'http://res.cloudinary.com/dq4guha5o/image/upload/ca1e1868-9bbc-4cbc-a048-38dc5894ec9c_ao-balo-den',1),(112,18,'http://res.cloudinary.com/dq4guha5o/image/upload/96342cc6-d7ab-43f4-ab08-06419bcf7cbe_ao-khoac-xanh-duong',14),(113,18,'http://res.cloudinary.com/dq4guha5o/image/upload/20fc5884-2c57-411b-ae55-1b678763f2f6_ao-khoac-trang',2),(114,18,'http://res.cloudinary.com/dq4guha5o/image/upload/6b4098a5-1509-4ea8-8336-cdf8f4dfe70f_ao-khoac-tim',18),(115,18,'http://res.cloudinary.com/dq4guha5o/image/upload/6d739132-6003-4ae5-800e-9f3c154e1b9a_ao-khoac-hong',9),(116,18,'http://res.cloudinary.com/dq4guha5o/image/upload/87e92637-5e57-4415-94ad-b50d2d944761_ao-khoac-den',1),(117,18,'http://res.cloudinary.com/dq4guha5o/image/upload/b7d1dddf-9322-49d0-b067-c408ddcb527e_ao-khoac-be',5),(118,19,'http://res.cloudinary.com/dq4guha5o/image/upload/b1e83070-a72a-4eb5-8d37-afa4ea3e8cfd_ao-croptop-xanh-la',12),(119,19,'http://res.cloudinary.com/dq4guha5o/image/upload/3f886a13-1a50-4089-b282-3e0b6d9b731a_ao-croptop-vang',11),(120,19,'http://res.cloudinary.com/dq4guha5o/image/upload/d8d9e6b0-1b51-46b0-8018-057eddcb85a8_ao-croptop-trang',2),(121,19,'http://res.cloudinary.com/dq4guha5o/image/upload/39cf148c-24b7-478e-a1b5-acae3176da49_ao-croptop-tim',18),(122,19,'http://res.cloudinary.com/dq4guha5o/image/upload/5accdfcb-e7c9-4945-8dd8-fbf40a75c75d_ao-croptop-hong',9),(123,19,'http://res.cloudinary.com/dq4guha5o/image/upload/f61134e9-f7a0-484b-9cf7-ca804fbea587_ao-croptop-do',8),(124,19,'http://res.cloudinary.com/dq4guha5o/image/upload/ca85e163-c5b5-4f2e-ad01-b8099beb090e_ao-croptop-den',1),(125,20,'http://res.cloudinary.com/dq4guha5o/image/upload/67be3f7e-006c-4d3a-a2bc-c1812f89a50c_ao-giunhiet-vang',11),(126,20,'http://res.cloudinary.com/dq4guha5o/image/upload/dc56e5bd-f924-4157-ac3e-fa77fd7dcd6b_ao-giunhiet-trang',2),(127,20,'http://res.cloudinary.com/dq4guha5o/image/upload/2df178d2-1601-4f47-97a7-5acc36347a9e_ao-giunhiet-nau',6),(128,20,'http://res.cloudinary.com/dq4guha5o/image/upload/2aacca8f-3aca-49a6-81c1-a8d9e8193fec_ao-giunhiet-den',1),(129,20,'http://res.cloudinary.com/dq4guha5o/image/upload/39c585af-2753-4419-8dbf-77434d7b1f9f_ao-giunhiet-be',5),(130,31,'http://res.cloudinary.com/dv6fjob4v/image/upload/3304de05-ecd9-488d-a97e-b11ceafd1529_photnam-ni-xanh-navy',15),(131,31,'http://res.cloudinary.com/dv6fjob4v/image/upload/dc5b648d-2972-4145-889e-184a0177def3_photnam-ni-xam',3),(132,31,'http://res.cloudinary.com/dv6fjob4v/image/upload/4b27f085-02b1-40b4-a594-d68933dc3f91_photnam-ni-nau',6),(133,32,'http://res.cloudinary.com/dv6fjob4v/image/upload/02cd1eaa-f470-43ab-a8e2-ec5cfccc8e42_photnu-ni-trang',2),(134,32,'http://res.cloudinary.com/dv6fjob4v/image/upload/1a1ca876-3aea-463d-a2fa-aa69bd8c1c7d_photnu-ni-xam',3),(135,32,'http://res.cloudinary.com/dv6fjob4v/image/upload/f6a7f5d7-756b-4e3e-9e1a-7f01e0aa0611_photnu-ni-nau',6),(136,32,'http://res.cloudinary.com/dv6fjob4v/image/upload/02f219cf-e80f-466e-a43a-8bf5d3cfad2d_photnu-ni-hong',9),(137,32,'http://res.cloudinary.com/dv6fjob4v/image/upload/735209cb-b909-4ac4-a7b8-f4070f6536e4_photnu-ni-be',5),(138,33,'http://res.cloudinary.com/dv6fjob4v/image/upload/7f992190-0035-4ead-92a3-20dc9e8d4248_photnam-coi-xam',3),(139,33,'http://res.cloudinary.com/dv6fjob4v/image/upload/3c0812a4-6ce4-4109-a843-eb796a4815d4_photnam-coi-nau',6),(140,33,'http://res.cloudinary.com/dv6fjob4v/image/upload/60f0f5e9-fbb1-47a0-a87a-c5fad226a831_photnam-coi-den',1),(141,33,'http://res.cloudinary.com/dv6fjob4v/image/upload/ded95b9f-7e3e-4498-b7e1-7a8d75af7821_photnam-coi-be',5),(142,34,'http://res.cloudinary.com/dv6fjob4v/image/upload/2a924229-e27a-40b1-a713-0af5684e8b50_photdabo-den',1),(143,34,'http://res.cloudinary.com/dv6fjob4v/image/upload/0c71e832-3615-44ea-8282-e49d33dd4292_photdabo-nau',6),(144,35,'http://res.cloudinary.com/dv6fjob4v/image/upload/7a276819-89f8-438c-ae59-3710f91ffb73_luoitrai-nike-xanh-navy',15),(145,35,'http://res.cloudinary.com/dv6fjob4v/image/upload/f6294e4a-5395-44c5-acbc-d068a8c545d8_luoitrai-nike-xam',3),(146,35,'http://res.cloudinary.com/dv6fjob4v/image/upload/1cb36be2-bd37-40e5-bc57-63407355124d_luoitrai-nike-trang',2),(147,35,'http://res.cloudinary.com/dv6fjob4v/image/upload/95d23330-8779-458f-a08b-376267fe9311_luoitrai-nike-den',1),(148,36,'http://res.cloudinary.com/dv6fjob4v/image/upload/96273f14-d18c-4bf3-a49e-4bce312d5b30_luoitrai-teelab-be',5),(149,36,'http://res.cloudinary.com/dv6fjob4v/image/upload/5b04de3d-1343-445e-94f1-99a4f9d5eac1_luoitrai-teelab-den',1),(150,36,'http://res.cloudinary.com/dv6fjob4v/image/upload/36f017a1-171d-4946-a8b7-ca06e42160d1_luoitrai-teelab-do',8),(151,37,'http://res.cloudinary.com/dv6fjob4v/image/upload/32c9e2b7-2451-44cf-be7e-8853de3a5387_luoiitrai-jogarbola-vn-trang',2),(152,37,'http://res.cloudinary.com/dv6fjob4v/image/upload/043e59d5-9978-49ce-a9a0-12acc6edfae9_luoitrai-jogarbola-vn-nau',6),(153,37,'http://res.cloudinary.com/dv6fjob4v/image/upload/9e78326f-ecc8-4666-8ce3-d14692235084_luotrai-jogarbola-vn-do',8),(154,37,'http://res.cloudinary.com/dv6fjob4v/image/upload/5a49725f-b031-485d-ac98-d474604b7f84_luoitrai-jogarbola-vn-xanh-navy',15),(155,38,'http://res.cloudinary.com/dv6fjob4v/image/upload/a57fdcc3-0961-4c86-95f1-4f6f5ae5c2e0_luotra-nhung-hong',9),(156,38,'http://res.cloudinary.com/dv6fjob4v/image/upload/66ab6f8d-3a98-4c6b-ab77-9732d3fd646a_luoitrai-nhung-trang',2),(157,38,'http://res.cloudinary.com/dv6fjob4v/image/upload/87b6bc70-f896-45dd-8a0b-bbb68c4758f2_luoitrai-nhung-tim',18),(158,38,'http://res.cloudinary.com/dv6fjob4v/image/upload/11f800f2-1fd0-423b-b656-df0ff786ac6f_luoitrai-nhung-den',1),(159,38,'http://res.cloudinary.com/dv6fjob4v/image/upload/7f1f7015-db81-441b-8fdf-56d2f26bde30_luoitrai-nhung-do',8),(160,39,'http://res.cloudinary.com/dv6fjob4v/image/upload/f283bed1-c6df-49cc-9e0a-1bc0f2702d23_luoitrai-da-nau',6),(161,39,'http://res.cloudinary.com/dv6fjob4v/image/upload/f96c30b3-4d61-492a-a178-ee1370fd1109_luoitrai-da-den',1);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_product` (
  `price` float DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `image_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  `color_id` bigint DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_product_order_id` (`order_id`),
  KEY `fk_order_product_product_id` (`product_id`),
  KEY `fk_order_product_size_id` (`size_id`),
  KEY `fk_order_product_color_id` (`color_id`),
  KEY `fk_order_product_image_id` (`image_id`),
  CONSTRAINT `fk_order_product_color_id` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `fk_order_product_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `fk_order_product_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_product_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_order_product_size_id` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product`
--

LOCK TABLES `order_product` WRITE;
/*!40000 ALTER TABLE `order_product` DISABLE KEYS */;
INSERT INTO `order_product` VALUES (140000,1,1,2,40,5,1,1),(140000,2,2,2,40,5,1,1),(300000,3,3,7,58,3,2,1),(30600,4,4,11,80,3,10,1),(300000,5,5,7,58,3,2,1);
/*!40000 ALTER TABLE `order_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `deleted` tinyint(1) DEFAULT NULL,
  `address_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_id` bigint DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_address_id` (`address_id`),
  KEY `fk_orders_payment_id` (`payment_id`),
  KEY `fk_orders_user_id` (`user_id`),
  CONSTRAINT `fk_orders_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `fk_orders_payment_id` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  CONSTRAINT `fk_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,2,1,1,'2025-06-06 15:28:22.016000',6,'Chờ xác nhận',140000),(0,3,2,1,'2025-06-06 15:29:52.598000',6,'Chờ xác nhận',140000),(0,1,3,2,'2025-06-08 05:34:51.209000',3,'Chờ xác nhận',300000),(0,4,4,2,'2025-06-08 05:36:09.741000',3,'Chờ xác nhận',30600),(0,5,5,2,'2025-06-08 05:36:30.041000',3,'Chờ xác nhận',300000);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_payment_user_id` (`user_id`),
  CONSTRAINT `fk_payment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,6,'COD','Thanh toán khi nhận hàng'),(2,3,'COD','Thanh toán khi nhận hàng');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `deleted` tinyint(1) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (0,1,'Xóa sản phẩm','PRODUCT_DELETE'),(0,2,'Thêm sản phẩm','PRODUCT_ADD'),(0,3,'Sửa sản phẩm','PRODUCT_UPDATE'),(0,4,'Thêm quyền','ROLE_ADD'),(0,5,'Xóa quyền','ROLE_DELETE'),(0,6,'Phân quyền','ROLE_PERMISSION'),(0,7,'Xem quyền','ROLE_VIEW'),(0,8,'Sửa quyền','ROLE_UPDATE'),(0,9,'Xem sản phẩm','PRODUCT_VIEW'),(0,10,'Xem danh mục','CATEGORY_VIEW'),(0,11,'Thêm danh mục','CATEGORY_ADD'),(0,12,'Sửa danh mục','CATEGORY_UPDATE'),(0,13,'Xóa danh mục','CATEGORY_DELETE'),(0,15,'Xem tài khoản','ACCOUNT_VIEW'),(0,16,'Thêm tài khoản','ACCOUNT_ADD'),(0,17,'Sửa tài khoản','ACCOUNT_UPDATE'),(0,18,'Xóa tài khoản','ACCOUNT_DELETE'),(0,19,'Xóa đơn hàng','ORDER_DELETE'),(0,20,'Sửa đơn hàng','ORDER_UPDATE');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `deleted` bit(1) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (_binary '\0',320000,10,NULL,1,50,NULL,'Quần âu bò thương hiệu','Quần short bò nữ','quan-short-bo-nu'),(_binary '\0',200000,8,NULL,2,50,NULL,'Quần âu thời trang','Quần âu nam công sở','quan-au-nam-cong-so'),(_binary '\0',250000,8,NULL,3,50,NULL,'Quần âu thời trang','Quần âu nữ công sở','quan-au-nu-cong-so'),(_binary '\0',210000,8,NULL,4,50,NULL,'Quần âu thời trang','Quần âu nỉ nam ','quan-au-ni-nam'),(_binary '\0',190000,8,NULL,5,50,NULL,'Quần âu thời trang','Quần âu nữ ống rộng','quan-au-nu-ong-rong'),(_binary '\0',150000,8,NULL,6,50,NULL,'Quần âu thời trang','Quần âu vải nữ','quan-au-vai-nu'),(_binary '\0',300000,10,NULL,7,50,NULL,'Quần âu bò thương hiệu','Quần bò jeans nam','quan-bo-jeans-nam'),(_binary '\0',290000,10,NULL,8,50,NULL,'Quần âu bò thương hiệu','Quần bò nam ống rộng','quan-bo-nam-ong-rong'),(_binary '\0',400000,10,NULL,9,50,NULL,'Quần âu bò thương hiệu','Quần bò nữ ống rộng','quan-bo-nu-ong-rong'),(_binary '\0',430000,10,NULL,10,50,NULL,'Quần âu bò thương hiệu','Quần short bò nam','quan-short-bo-nam'),(_binary '\0',90000,4,NULL,11,50,NULL,'Áo thun chính hãng của nhà F5','Áo thun','ao-thun'),(_binary '\0',150000,5,NULL,12,50,NULL,'Áo sơ mi chính hãng của nhà F5','Áo sơ mi','ao-so-mi'),(_binary '\0',100000,6,NULL,13,50,NULL,'Áo polo chính hãng của nhà F5','Áo polo','ao-polo'),(_binary '\0',200000,7,NULL,14,50,NULL,'Áo hoodie chính hãng của nhà F5','Áo hoodie','ao-hoodie'),(_binary '\0',95000,18,NULL,15,50,NULL,'Áo len cổ lọ chính hãng của nhà F5','Áo len cổ lọ','ao-len-co-lo'),(_binary '\0',500000,19,NULL,16,50,NULL,'Áo blazer chính hãng của nhà F5','Áo blazer','ao-blazer'),(_binary '\0',90000,20,NULL,17,50,NULL,'Áo ba lỗ chính hãng của nhà F5','Áo ba lỗ','ao-balo'),(_binary '\0',300000,21,NULL,18,50,NULL,'Áo khoác chính hãng của nhà F5','Áo khoác','ao-khoac'),(_binary '\0',200000,22,NULL,19,50,NULL,'Áo croptop chính hãng của nhà F5','Áo croptop','ao-croptop'),(_binary '\0',150000,23,NULL,20,50,NULL,'Áo giữ nhiệt chính hãng của nhà F5','Áo giữ nhiệt','ao-giu-nhiet'),(_binary '\0',140000,17,NULL,21,50,NULL,'Giá thành rẻ, thiết kế chắc chắn, phù hợp với sân phủi và người mới chơi.','Giày Đá Banh Wika 3 Sọc Ct3','giay-da-banh-wika-3-soc-ct3'),(_binary '\0',2100000,17,NULL,22,50,NULL,'Chất liệu da mềm mại mang lại cảm giác bóng tốt, phù hợp với cầu thủ chơi ở vị trí tiền vệ.','Giày Đá Bóng Firm Ground Copa Pure 3 Elite','giay-da-bong-firm-ground-copa-pure-3-elite'),(_binary '\0',1600000,17,NULL,23,50,NULL,'Thiết kế đặc biệt ở phần thân giày giúp kiểm soát bóng tốt, phù hợp với cầu thủ thiên về kỹ thuật.','Giày đá Bóng Nam adidas Predator League Firm Ground','giay-da-bong-nam-adidas-predator-league-firm-ground'),(_binary '\0',320000,17,NULL,24,50,NULL,'Thiết kế hiện đại, ôm chân, hỗ trợ di chuyển linh hoạt trên sân cỏ nhân tạo.','Giày Đá Bóng Sân Cỏ Nhân Tạo Future Z 1.1','giay-da-bong-san-co-nhan-tao-future-z-11'),(_binary '\0',230000,17,NULL,25,50,NULL,'Chất liệu da PU với bề mặt vân nổi, đế TF bám sân tốt, thiết kế phối màu trắng xanh trẻ trung.','Giày đá bóng Wika Toni Kroos','giay-da-bong-wika-toni-kroos'),(_binary '\0',2700000,16,NULL,26,50,NULL,'Được thiết kế với công nghệ Air-Sole độc quyền của Nike, kết hợp kiểu dáng hầm hố, cá tính, và phong cách retro độc đáo, Air Jordan đã tạo nên cơn sốt toàn cầu suốt nhiều thập kỷ.','Giày thể thao Nike Air Jordan','giay-the-thao-nike-air-jordan'),(_binary '\0',3500000,16,NULL,27,50,NULL,'Ultraboost là dòng giày chạy bộ cao cấp của Adidas, nổi bật với công nghệ Boost siêu nhẹ, hoàn trả năng lượng tối đa. Thiết kế ôm chân với vải dệt Primeknit linh hoạt, tạo cảm giác như đi tất.','Giày thể thao Adidas Ultraboost','giay-the-thao-adidas-ultraboost'),(_binary '\0',800000,16,NULL,28,50,NULL,'Biti’s Hunter là sản phẩm giày thể thao quốc dân của Việt Nam. Thiết kế trẻ trung, đệm EVA êm nhẹ, phần upper thoáng khí và kiểu dáng dễ phối đồ. Giá thành hợp lý, phù hợp với học sinh, sinh viên.','Giày thể thao Biti’s Hunter','giay-the-thao-bitis-hunter'),(_binary '\0',2200000,16,NULL,29,50,NULL,'MLB Bigball Chunky là dòng giày thể thao thời trang từ thương hiệu MLB (Hàn Quốc), nổi bật với thiết kế đế dày, form \'hầm hố\' cá tính. Logo đội bóng Mỹ được in nổi bật tạo điểm nhấn phong cách đường phố.','Giày thể thao MLB Bigball','giay-the-thao-mlb-bigball'),(_binary '\0',1800000,16,NULL,30,50,NULL,'Nike Court là dòng giày tennis và lifestyle đơn giản nhưng cực kỳ thanh lịch. Đế cao su bền chắc, kiểu dáng cổ điển, dễ phối với quần jeans, kaki hay đồ thể thao.','Giày thể thao Nike Court','giay-the-thao-nike-court'),(_binary '\0',150000,14,NULL,31,50,NULL,'Mũ phớt thời trang hiện đại cá tính','Mũ phớt nỉ nam','mu-phot-ni-nam'),(_binary '\0',200000,14,NULL,32,50,NULL,'Mũ phớt thời trang hiện đại cá tính','Mũ phớt nỉ nữ','mu-phot-ni-nu'),(_binary '\0',50000,14,NULL,33,50,NULL,'Mũ phớt thời trang hiện đại cá tính','Mũ phớt cói nam','mu-phot-coi-nam'),(_binary '\0',400000,14,NULL,34,50,NULL,'Mũ phớt thời trang hiện đại cá tính','Mũ phớt da bò','mu-phot-da-bo'),(_binary '\0',70000,13,NULL,35,50,NULL,'Mũ lưỡi trai dành cho giới trẻ mẫu mới','Mũ lưỡi trai Nike','mu-luoi-trai-nike'),(_binary '\0',60000,13,NULL,36,50,NULL,'Mũ lưỡi trai dành cho giới trẻ mẫu mới','Mũ lưỡi trai Teelab','mu-luoi-trai-teelab'),(_binary '\0',80000,13,NULL,37,50,NULL,'Mũ lưỡi trai dành cho giới trẻ mẫu mới','Mũ lưỡi trai Jogarbola','mu-luoi-trai-jogarbola'),(_binary '\0',100000,13,NULL,38,50,NULL,'Mũ lưỡi trai dành cho giới trẻ mẫu mới','Mũ lưỡi trai nhung','mu-luoi-trai-nhung'),(_binary '\0',300000,13,NULL,39,50,NULL,'Mũ lưỡi trai dành cho giới trẻ mẫu mới','Mũ lưỡi trai da','mu-luoi-trai-da');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_discount`
--

DROP TABLE IF EXISTS `product_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_discount` (
  `discount_id` bigint DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_discount_discount_id` (`discount_id`),
  KEY `fk_product_discount_product_id` (`product_id`),
  CONSTRAINT `fk_product_discount_discount_id` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
  CONSTRAINT `fk_product_discount_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_discount`
--

LOCK TABLES `product_discount` WRITE;
/*!40000 ALTER TABLE `product_discount` DISABLE KEYS */;
INSERT INTO `product_discount` VALUES (2,'2025-06-08 00:00:00.000000',2,11,'2025-05-27 00:00:00.000000'),(3,'2025-06-08 00:00:00.000000',3,26,'2025-05-21 00:00:00.000000'),(5,'2025-06-28 00:00:00.000000',5,2,'2025-06-03 00:00:00.000000');
/*!40000 ALTER TABLE `product_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_favorite`
--

DROP TABLE IF EXISTS `product_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` bigint DEFAULT NULL,
  `stock` bigint DEFAULT NULL,
  `color_id` bigint DEFAULT NULL,
  `favorite_id` bigint DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK88k8gag826io7mf5jh04639li` (`color_id`),
  KEY `FKrcyhceyys9jlpydmqj1mclrmv` (`favorite_id`),
  KEY `FKomgt7x2gi4f2ov7o54sovb5fu` (`image_id`),
  KEY `FKartqjcvs73ddgj53p0g33lhvq` (`product_id`),
  KEY `FK98795aa8mjohgsb3vdqigcibu` (`size_id`),
  CONSTRAINT `FK88k8gag826io7mf5jh04639li` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `FK98795aa8mjohgsb3vdqigcibu` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`),
  CONSTRAINT `FKartqjcvs73ddgj53p0g33lhvq` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKomgt7x2gi4f2ov7o54sovb5fu` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `FKrcyhceyys9jlpydmqj1mclrmv` FOREIGN KEY (`favorite_id`) REFERENCES `favorite` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_favorite`
--

LOCK TABLES `product_favorite` WRITE;
/*!40000 ALTER TABLE `product_favorite` DISABLE KEYS */;
INSERT INTO `product_favorite` VALUES (7,1,10,1,3,40,2,5),(8,1,1,2,1,58,7,3);
/*!40000 ALTER TABLE `product_favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_shoppingcart`
--

DROP TABLE IF EXISTS `product_shoppingcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_shoppingcart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `shoppingcart_id` bigint DEFAULT NULL,
  `stock` bigint DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  `color_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_shoppingcart_size_id` (`size_id`),
  KEY `fk_product_shoppingcart_color_id` (`color_id`),
  KEY `fk_product_shoppingcart_image_id` (`image_id`),
  KEY `fk_product_shoppingcart_cart_id` (`shoppingcart_id`),
  KEY `fk_product_shoppingcart_product_id` (`product_id`),
  CONSTRAINT `fk_product_shoppingcart_cart_id` FOREIGN KEY (`shoppingcart_id`) REFERENCES `shoppingcart` (`id`),
  CONSTRAINT `fk_product_shoppingcart_color_id` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `fk_product_shoppingcart_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `fk_product_shoppingcart_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_shoppingcart_size_id` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_shoppingcart`
--

LOCK TABLES `product_shoppingcart` WRITE;
/*!40000 ALTER TABLE `product_shoppingcart` DISABLE KEYS */;
INSERT INTO `product_shoppingcart` VALUES (46,5,1,4,6,53,4,1),(47,2,1,4,10,40,5,1),(48,5,1,5,6,53,4,1);
/*!40000 ALTER TABLE `product_shoppingcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `deleted` tinyint(1) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (0,1,'ADMIN','admin','full quyền'),(0,2,'USER','user',NULL),(0,3,'PRODUCT_MANAGEMENT','Quản lý sản phẩm',NULL),(0,4,'ROLE_MANAGEMENT','Quản lý quyền',NULL),(0,5,'CATEGORY_MANAGEMENT','Quản lý danh mục',NULL),(0,6,'ACCOUNT_MANAGEMENT','Quản lý tài khoản',NULL),(0,7,'ORDER_MANAGEMENT','Quản lý đơn hàng',NULL),(1,8,'111','Backend deverloper','h');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `permission_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `fk_role_permissions_permission_id` (`permission_id`),
  KEY `fk_role_permissions_role_id` (`role_id`),
  CONSTRAINT `fk_role_permissions_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `fk_role_permissions_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES (9,1),(2,1),(3,1),(1,1),(7,1),(4,1),(8,1),(5,1),(6,1),(10,1),(11,1),(12,1),(13,1),(15,1),(16,1),(17,1),(18,1),(20,1),(19,1),(9,3),(2,3),(3,3),(1,3),(7,4),(4,4),(8,4),(5,4),(6,4),(10,5),(11,5),(12,5),(13,5),(15,6),(16,6),(17,6),(18,6),(20,7),(19,7);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingcart`
--

DROP TABLE IF EXISTS `shoppingcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoppingcart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_shoppingcart_user_id` (`user_id`),
  CONSTRAINT `fk_shoppingcart_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingcart`
--

LOCK TABLES `shoppingcart` WRITE;
/*!40000 ALTER TABLE `shoppingcart` DISABLE KEYS */;
INSERT INTO `shoppingcart` VALUES (5,1),(1,3),(2,4),(3,5),(4,6);
/*!40000 ALTER TABLE `shoppingcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'Phù hợp với người cao 1m50–1m55, nặng 40–45kg','XS'),(2,'Phù hợp với người cao 1m55–1m60, nặng 45–50kg','S'),(3,'Phù hợp với người cao 1m60–1m65, nặng 50–60kg','M'),(4,'Phù hợp với người cao 1m65–1m70, nặng 60–70kg','L'),(5,'Phù hợp với người cao 1m70–1m75, nặng 70–80kg','XL'),(6,'Phù hợp với người cao 1m75–1m80, nặng 80–90kg','XXL'),(7,'Phù hợp với người cao trên 1m80, nặng trên 90kg','XXXL');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `deleted` tinyint(1) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,_binary '',NULL,1,NULL,'https://ugc.production.linktr.ee/5e77e148-1b07-4429-8471-d0a13edc9fa3_boy.png','nguyenvana@gmail.com','Nguyễn Văn A','$2a$10$hJSftRyD11HMkc4QDwTYkeKlgAzWa646.qyaQRyWNY1WcX5jq4Y2.',NULL),(0,_binary '','2025-06-05 18:04:48.399110',2,'2025-06-05 18:04:48.399110',NULL,'admin@admin.com',NULL,'$2a$10$cI6DhnaJWpxkSkHk35HPyOIillmaLumiRHq2lB0LbEFa0xwVfXxSS',NULL),(0,_binary '','2025-06-06 02:53:36.740454',3,'2025-06-07 06:38:03.715377','https://lh3.googleusercontent.com/a/ACg8ocJ3OYWHsOuJFC44PkT0wsImSRwfPUTA_N_lV50IvTLLmiDD2g=s96-c','trinhduy107@gmail.com','duy trịnh',NULL,''),(0,_binary '','2025-06-06 15:16:32.799287',4,'2025-06-06 15:16:32.799813','https://lh3.googleusercontent.com/a/ACg8ocIZY5py8cMHGwzWiPzLZ0UfbzBpzougJoQh33ijGOTTJBanew=s96-c','duytrinhcong107@gmail.com','Duy Trinh',NULL,NULL),(0,_binary '','2025-06-06 15:16:45.485765',5,'2025-06-06 15:16:45.485765','https://lh3.googleusercontent.com/a/ACg8ocIiXqQjm-zKVgE37Euc-d7p2qWIQH9yD72XwwdP0ZM6Gg_wu4hT=s96-c','nhudinhchien12345@gmail.com','Chien123 Nhudinh',NULL,NULL),(0,_binary '','2025-06-06 15:21:00.536624',6,'2025-06-06 15:21:00.536624','https://lh3.googleusercontent.com/a/ACg8ocJAkuVZy2ea1Nq5CLZeMktQZHfb3eOJvjvJSXC1RUPwSDiepg=s96-c','khuongvanhiep04@gmail.com','Khương Hiệp',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `is_default` bit(1) DEFAULT NULL,
  `address_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_address_address_id` (`address_id`),
  KEY `fk_user_address_user_id` (`user_id`),
  CONSTRAINT `fk_user_address_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `fk_user_address_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` VALUES (_binary '',1,1,3),(_binary '',2,2,6);
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `fk_user_role_role_id` (`role_id`),
  KEY `fk_user_role_user_id` (`user_id`),
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,2),(2,5),(2,6),(2,1),(3,3),(4,4);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-12  8:46:44
