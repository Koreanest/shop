-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: shop
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `__init_log`
--

DROP TABLE IF EXISTS `__init_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `__init_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ran_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `__init_log`
--

LOCK TABLES `__init_log` WRITE;
/*!40000 ALTER TABLE `__init_log` DISABLE KEYS */;
INSERT INTO `__init_log` VALUES (1,'2026-02-25 11:21:24','01_schema.sql executed'),(2,'2026-02-25 11:21:24','02_seed.sql executed');
/*!40000 ALTER TABLE `__init_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ë¸Œëžœë“œ PK',
  `name` varchar(50) NOT NULL COMMENT 'ë¸Œëžœë“œëª…',
  `slug` varchar(60) NOT NULL COMMENT 'ë¸Œëžœë“œ ìŠ¬ëŸ¬ê·¸',
  `logo_url` varchar(300) DEFAULT NULL COMMENT 'ë¸Œëžœë“œ ë¡œê³  URL',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ìƒì„±ì¼ì‹œ',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ìˆ˜ì •ì¼ì‹œ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_brands_name` (`name`),
  UNIQUE KEY `uk_brands_slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='brands';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (3,'YONEX','yonex','/uploads/brands/yonex.png','2026-03-03 10:34:35','2026-03-03 10:34:35');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ìž¥ë°”êµ¬ë‹ˆì•„ì´í…œ PK',
  `cart_id` bigint NOT NULL COMMENT 'ìž¥ë°”êµ¬ë‹ˆ FK',
  `sku_id` bigint NOT NULL COMMENT 'SKU FK',
  `quantity` int NOT NULL DEFAULT '1' COMMENT 'ìˆ˜ëŸ‰',
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cart_item_cart_sku` (`cart_id`,`sku_id`),
  KEY `idx_cart_items_cart_id` (`cart_id`),
  KEY `idx_cart_items_sku_id` (`sku_id`),
  CONSTRAINT `fk_cart_items_cart` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_items_sku` FOREIGN KEY (`sku_id`) REFERENCES `skus` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='cart_items';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (108,6,2001,1,'2026-03-17 09:25:23.254819','2026-03-17 09:25:27.935748');
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ìž¥ë°”êµ¬ë‹ˆ PK',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ìƒì„±ì¼ì‹œ',
  `member_id` bigint NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_carts_member_id` (`member_id`),
  CONSTRAINT `fk_carts_member` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='carts';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (2,'2026-03-13 23:57:51',1,'2026-03-13 23:57:51.344018'),(6,'2026-03-17 09:25:16',11,'2026-03-17 09:25:16.402821'),(7,'2026-03-17 10:52:12',12,'2026-03-17 10:52:12.499372');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts_backup_20260313`
--

DROP TABLE IF EXISTS `carts_backup_20260313`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts_backup_20260313` (
  `id` bigint NOT NULL DEFAULT '0' COMMENT 'ìž¥ë°”êµ¬ë‹ˆ PK',
  `user_id` bigint NOT NULL COMMENT 'ìœ ì € FK(1:1)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ìƒì„±ì¼ì‹œ',
  `member_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts_backup_20260313`
--

LOCK TABLES `carts_backup_20260313` WRITE;
/*!40000 ALTER TABLE `carts_backup_20260313` DISABLE KEYS */;
INSERT INTO `carts_backup_20260313` VALUES (1,10,'2026-03-03 10:34:35',0);
/*!40000 ALTER TABLE `carts_backup_20260313` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `footer_categories`
--

DROP TABLE IF EXISTS `footer_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footer_categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `sort_order` int DEFAULT NULL,
  `title` varchar(80) NOT NULL,
  `visible_yn` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `footer_categories`
--

LOCK TABLES `footer_categories` WRITE;
/*!40000 ALTER TABLE `footer_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `footer_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `footer_links`
--

DROP TABLE IF EXISTS `footer_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footer_links` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `label` varchar(80) NOT NULL,
  `sort_order` int DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `visible_yn` varchar(1) DEFAULT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK295m3cpvjqdnot7no6d9rbdwv` (`category_id`),
  CONSTRAINT `FK295m3cpvjqdnot7no6d9rbdwv` FOREIGN KEY (`category_id`) REFERENCES `footer_categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `footer_links`
--

LOCK TABLES `footer_links` WRITE;
/*!40000 ALTER TABLE `footer_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `footer_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `footer_text`
--

DROP TABLE IF EXISTS `footer_text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footer_text` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `paragraph1` tinytext,
  `paragraph2` tinytext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `footer_text`
--

LOCK TABLES `footer_text` WRITE;
/*!40000 ALTER TABLE `footer_text` DISABLE KEYS */;
/*!40000 ALTER TABLE `footer_text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `sku_id` bigint NOT NULL COMMENT 'PK=FK (1:1) skus.id',
  `stock_qty` int NOT NULL DEFAULT '0' COMMENT 'ìž¬ê³  ìˆ˜ëŸ‰',
  `safety_stock_qty` int NOT NULL DEFAULT '0' COMMENT 'ì•ˆì „ìž¬ê³  ìˆ˜ëŸ‰',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ìˆ˜ì •ì¼ì‹œ',
  PRIMARY KEY (`sku_id`),
  CONSTRAINT `fk_inventory_sku` FOREIGN KEY (`sku_id`) REFERENCES `skus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='inventory';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (2001,4,2,'2026-03-18 23:55:51'),(2005,8,2,'2026-03-17 17:21:26'),(2006,7,2,'2026-03-18 01:28:54');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `main_banner`
--

DROP TABLE IF EXISTS `main_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `main_banner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `image_url` varchar(500) NOT NULL,
  `link_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `main_banner`
--

LOCK TABLES `main_banner` WRITE;
/*!40000 ALTER TABLE `main_banner` DISABLE KEYS */;
/*!40000 ALTER TABLE `main_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `main_video`
--

DROP TABLE IF EXISTS `main_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `main_video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `btn1_link` varchar(500) DEFAULT NULL,
  `btn1_text` varchar(100) DEFAULT NULL,
  `btn2_link` varchar(500) DEFAULT NULL,
  `btn2_text` varchar(100) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `subtitle` varchar(500) NOT NULL,
  `title` varchar(200) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `video_url` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `main_video`
--

LOCK TABLES `main_video` WRITE;
/*!40000 ALTER TABLE `main_video` DISABLE KEYS */;
/*!40000 ALTER TABLE `main_video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `position` varchar(100) DEFAULT NULL,
  `tel` varchar(30) DEFAULT NULL,
  `zip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9d30a9u1qpg8eou0otgkwrp5d` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,'2026-03-13 18:15:00.116890','2026-03-13 18:15:00.116890','Seoul','SHOP','101','test01@qwer.com','Bella','F','Kim','$2a$10$AzFy2y/3o2oHwWGJdBuqPeXLzlo9KRTrD1Tb9OFxcDpL5Kmf6iAwG','Developer','010-1234-5678',NULL),(2,'2026-03-13 18:25:52.744270','2026-03-13 18:25:52.744270','Seoul','SHOP','101','tesst01@qwer.com','Bella','F','Kim','$2a$10$VdHKDCwm.33DrbA7TBGNmOvV41ZeFx1CA1Zg9M.3D3dpoXu5cBSxu','Developer','010-1234-5678',NULL),(5,'2026-03-17 01:35:18.337152','2026-03-17 01:35:18.337152','서울 노원구 공릉로 97','csd','afdfaaa','asdf@asdf.com','아지','male','망','$2a$10$bJq62h4nOY0tFV24lOLNNuByfTOlR6KOWmKghBRhkadp4Qp9aaqBy','fa','sdas',NULL),(6,'2026-03-17 01:37:45.927625','2026-03-17 01:37:45.927625','서울 용산구 두텁바위로 2','','sdq','test@test.com','라니','F','고','$2a$10$fVjaoBYATm63lwut7GouFeIxDxfGPgq5bJsBPxJf02MzmREBwtpOS','','01012341234','04323'),(7,'2026-03-17 01:40:44.667322','2026-03-17 01:40:44.667322','서울 광진구 강변북로 2160','qwe','sda','234@45.com','ㅈㄷㄱ','male','ㅁㄴ','$2a$10$ZLZ9iBX/aucS6jPFY5xudOsW18bKfT5HmY89LgWhmy5uIrE6L681y','qrwr','123',NULL),(9,'2026-03-17 01:56:52.616481','2026-03-17 01:56:52.616481','서울 관악구 장군봉길 8','234','2314','e23@er.com','ds','male','2t3','$2a$10$sROiF9QCWBPrHUduU8AWA.GeV1DecV78a6munPTrg.Bvy4uy9rWKK','442','124',NULL),(10,'2026-03-17 09:20:35.615590','2026-03-17 09:20:35.615590','서울 동대문구 약령중앙로 7','sd','sdf','qw@er.com','asd','male','qw','$2a$10$DmQvEaHLBK8hJJMS2eTdeuUBSMvaUthrZpZ.gGO4byTwJFTzGtxRC','asd','1',NULL),(11,'2026-03-17 09:25:08.644977','2026-03-17 09:25:08.644977','대전 유성구 라온1길 61','erw','qwe','wer@dfwdf.com','ㅁㄴㅇ','male','ㅂㅈ','$2a$10$eUcQnU6bl.JP3FlMdOLw8OrgccbR8tIGc1rYECZQk8UDgFALfBrOm','wt','123',NULL),(12,'2026-03-17 10:52:03.469238','2026-03-17 10:52:03.469238','인천 강화군 길상면 마니산로 8','sda','qwer','qwer@qwer.com','라니','male','고','$2a$10$8mx3WsWqwtBloNxbWudaheLpIG31/hOOtuTyGxwWdMWmHmOg7fSmK','a','1234','23050');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nav_menu`
--

DROP TABLE IF EXISTS `nav_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nav_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ë„¤ë¹„/ì¹´í…Œê³ ë¦¬ PK',
  `name` varchar(100) NOT NULL COMMENT 'ì¹´í…Œê³ ë¦¬ëª…',
  `path` varchar(255) DEFAULT NULL COMMENT 'ë¼ìš°íŒ… ê²½ë¡œ',
  `parent_id` bigint DEFAULT NULL COMMENT 'ë¶€ëª¨ ì¹´í…Œê³ ë¦¬',
  `depth` int NOT NULL DEFAULT '1' COMMENT 'ë©”ë‰´ ê¹Šì´(1~3)',
  `sort_order` int NOT NULL DEFAULT '1' COMMENT 'ì •ë ¬ ìˆœì„œ',
  `visible_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT 'ë…¸ì¶œ ì—¬ë¶€(Y/N)',
  PRIMARY KEY (`id`),
  KEY `idx_nav_menu_parent_id` (`parent_id`),
  KEY `idx_nav_menu_depth` (`depth`),
  KEY `idx_nav_menu_sort_order` (`sort_order`),
  KEY `idx_nav_menu_parent` (`parent_id`),
  KEY `idx_nav_menu_sort` (`sort_order`),
  CONSTRAINT `fk_nav_menu_parent` FOREIGN KEY (`parent_id`) REFERENCES `nav_menu` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='nav_menu';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nav_menu`
--

LOCK TABLES `nav_menu` WRITE;
/*!40000 ALTER TABLE `nav_menu` DISABLE KEYS */;
INSERT INTO `nav_menu` VALUES (100,'Rackets',NULL,NULL,1,1,'Y');
/*!40000 ALTER TABLE `nav_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ì£¼ë¬¸ì•„ì´í…œ PK',
  `order_id` bigint NOT NULL COMMENT 'ì£¼ë¬¸ FK',
  `sku_id` bigint NOT NULL COMMENT 'SKU FK',
  `product_name_snapshot` varchar(120) NOT NULL,
  `brand_name_snapshot` varchar(50) NOT NULL COMMENT 'ë¸Œëžœë“œ ìŠ¤ëƒ…ìƒ·',
  `grip_snapshot` varchar(4) NOT NULL COMMENT 'ê·¸ë¦½ ìŠ¤ëƒ…ìƒ·',
  `unit_price` int NOT NULL DEFAULT '0' COMMENT 'ë‹¨ê°€',
  `quantity` int NOT NULL DEFAULT '1' COMMENT 'ìˆ˜ëŸ‰',
  `line_total` int NOT NULL DEFAULT '0' COMMENT 'ë¼ì¸í•©ê³„',
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_items_order_id` (`order_id`),
  KEY `idx_order_items_sku_id` (`sku_id`),
  CONSTRAINT `fk_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_items_sku` FOREIGN KEY (`sku_id`) REFERENCES `skus` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='order_items';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (301,203,2001,'VCORE 100 2023','YONEX','G2',289000,2,578000,'2026-03-14 16:15:24.911896','2026-03-14 16:15:24.911896'),(302,204,2001,'VCORE 100 2023','YONEX','G2',289000,2,578000,'2026-03-14 16:43:36.295505','2026-03-14 16:43:36.295505'),(303,210,2001,'VCORE 100 2023','YONEX','G2',289000,7,2023000,'2026-03-17 12:31:42.545816','2026-03-17 12:31:42.545816'),(304,211,2001,'VCORE 100 2023','YONEX','G2',289000,2,578000,'2026-03-17 16:19:39.129934','2026-03-17 16:19:39.129934'),(305,211,2005,'VCORE 100 2023','YONEX','G3',289000,1,289000,'2026-03-17 16:19:39.141562','2026-03-17 16:19:39.141562'),(306,212,2005,'VCORE 100 2023','YONEX','G3',289000,1,289000,'2026-03-17 17:19:57.971862','2026-03-17 17:19:57.971862'),(307,213,2005,'VCORE 100 2023','YONEX','G3',289000,1,289000,'2026-03-17 17:21:25.858848','2026-03-17 17:21:25.858848'),(308,214,2006,'VCORE 100 2023','YONEX','G4',289000,3,867000,'2026-03-18 01:28:53.997833','2026-03-18 01:28:53.997833'),(309,215,2001,'VCORE 100 2023','YONEX','G2',289000,3,867000,'2026-03-18 22:22:27.580846','2026-03-18 22:22:27.580846'),(310,216,2001,'VCORE 100 2023','YONEX','G2',289000,1,289000,'2026-03-18 23:55:50.582859','2026-03-18 23:55:50.582859');
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ì£¼ë¬¸ PK',
  `member_id` bigint NOT NULL,
  `order_no` varchar(40) NOT NULL COMMENT 'ì£¼ë¬¸ë²ˆí˜¸',
  `status` varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT 'ì£¼ë¬¸ìƒíƒœ',
  `total_price` int NOT NULL DEFAULT '0' COMMENT 'ì´ì•¡',
  `receiver_name` varchar(60) NOT NULL COMMENT 'ìˆ˜ë ¹ì¸',
  `receiver_phone` varchar(30) NOT NULL COMMENT 'ì—°ë½ì²˜',
  `zip` varchar(10) DEFAULT NULL COMMENT 'ìš°íŽ¸ë²ˆí˜¸',
  `address1` varchar(200) NOT NULL COMMENT 'ì£¼ì†Œ1',
  `address2` varchar(200) DEFAULT NULL COMMENT 'ì£¼ì†Œ2',
  `memo` varchar(200) DEFAULT NULL COMMENT 'ë©”ëª¨',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ìƒì„±ì¼ì‹œ',
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_orders_order_no` (`order_no`),
  KEY `idx_orders_member_id` (`member_id`),
  CONSTRAINT `fk_orders_member` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='orders';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (203,1,'ORD-20260314-0000000203','CANCELLED',578000,'홍길동','010-1234-5678','06236','서울 강남구 테헤란로 1','101동 1001호','문 앞에 놓아주세요','2026-03-14 16:15:25','2026-03-14 16:16:40.175943'),(204,1,'ORD-20260314-0000000204','CANCELLED',578000,'홍길동','010-1234-5678','06236','서울 강남구 테헤란로 1','101동 1001호','문 앞에 놓아주세요','2026-03-14 16:43:36','2026-03-14 16:44:35.352977'),(210,12,'ORD-20260317-0000000210','PENDING',2023000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-17 12:31:43','2026-03-17 12:31:42.618070'),(211,12,'ORD-20260317-0000000211','PENDING',867000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-17 16:19:39','2026-03-17 16:19:39.172467'),(212,12,'ORD-20260317-0000000212','CANCELED',289000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-17 17:19:58','2026-03-17 17:20:18.066227'),(213,12,'ORD-20260317-0000000213','PENDING',289000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-17 17:21:26','2026-03-17 17:21:25.870712'),(214,12,'ORD-20260318-0000000214','PENDING',867000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-18 01:28:54','2026-03-18 01:28:54.040568'),(215,12,'ORD-20260318-0000000215','PENDING',867000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-18 22:22:28','2026-03-18 22:22:27.609879'),(216,12,'ORD-20260318-0000000216','PENDING',289000,'고라니','1234','23050','인천 강화군 길상면 마니산로 8','qwer','문 앞에 놓아주세요','2026-03-18 23:55:51','2026-03-18 23:55:50.628439');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ì´ë¯¸ì§€ PK',
  `product_id` bigint NOT NULL COMMENT 'ìƒí’ˆ FK',
  `url` varchar(300) NOT NULL COMMENT 'ì´ë¯¸ì§€ URL',
  `sort_order` int NOT NULL DEFAULT '1' COMMENT 'ì •ë ¬ìˆœì„œ',
  `is_main` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ëŒ€í‘œ ì—¬ë¶€',
  PRIMARY KEY (`id`),
  KEY `idx_product_images_product_id` (`product_id`),
  CONSTRAINT `fk_product_images_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='product_images';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_notice_items`
--

DROP TABLE IF EXISTS `product_notice_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_notice_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(100) NOT NULL,
  `sort_order` int NOT NULL,
  `value` varchar(255) NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb4xvqmpd1br18t40mbgp34yq2` (`product_id`),
  CONSTRAINT `FKb4xvqmpd1br18t40mbgp34yq2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_notice_items`
--

LOCK TABLES `product_notice_items` WRITE;
/*!40000 ALTER TABLE `product_notice_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_notice_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_specs`
--

DROP TABLE IF EXISTS `product_specs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_specs` (
  `product_id` bigint NOT NULL COMMENT 'PK=FK (1:1) products.id',
  `head_size_sq_in` int DEFAULT NULL COMMENT 'í—¤ë“œì‚¬ì´ì¦ˆ(in^2)',
  `unstrung_weight_g` int DEFAULT NULL COMMENT 'ë¬´ê²Œ(ì–¸ìŠ¤íŠ¸ë§, g)',
  `balance_mm` int DEFAULT NULL COMMENT 'ë°¸ëŸ°ìŠ¤(mm)',
  `length_in` decimal(4,1) DEFAULT NULL COMMENT 'ê¸¸ì´(inch)',
  `pattern_main` int DEFAULT NULL COMMENT 'ìŠ¤íŠ¸ë§ íŒ¨í„´(main)',
  `pattern_cross` int DEFAULT NULL COMMENT 'ìŠ¤íŠ¸ë§ íŒ¨í„´(cross)',
  `stiffness_ra` int DEFAULT NULL COMMENT 'ê°•ì„±(RA)',
  PRIMARY KEY (`product_id`),
  CONSTRAINT `fk_product_specs_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='product_specs';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_specs`
--

LOCK TABLES `product_specs` WRITE;
/*!40000 ALTER TABLE `product_specs` DISABLE KEYS */;
INSERT INTO `product_specs` VALUES (1203,100,300,320,27.0,16,19,65);
/*!40000 ALTER TABLE `product_specs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ìƒí’ˆ PK',
  `brand_id` bigint NOT NULL COMMENT 'ë¸Œëžœë“œ FK',
  `title` varchar(100) NOT NULL COMMENT 'ìƒí’ˆëª…/ëª¨ë¸ëª…',
  `series` varchar(80) DEFAULT NULL COMMENT 'ì‹œë¦¬ì¦ˆ',
  `description` text COMMENT 'ì„¤ëª…',
  `price` int NOT NULL DEFAULT '0' COMMENT 'íŒë§¤ê°€(ì›)',
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ìƒíƒœ(DRAFT/ACTIVE/HIDDEN)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ìƒì„±ì¼ì‹œ',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ìˆ˜ì •ì¼ì‹œ',
  `slug` varchar(150) NOT NULL COMMENT 'ìŠ¬ëŸ¬ê·¸(ê³ ìœ )',
  `category_id` bigint NOT NULL COMMENT 'ì¹´í…Œê³ ë¦¬ FK(1~3 depth)',
  `image_url` varchar(300) NOT NULL COMMENT 'ëŒ€í‘œ ì´ë¯¸ì§€ URL',
  `image_path` varchar(300) NOT NULL COMMENT 'ëŒ€í‘œ ì´ë¯¸ì§€ ì„œë²„ê²½ë¡œ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_products_slug` (`slug`),
  KEY `idx_products_brand_id` (`brand_id`),
  KEY `idx_products_category_id` (`category_id`),
  CONSTRAINT `fk_products_brand` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_products_category` FOREIGN KEY (`category_id`) REFERENCES `nav_menu` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1208 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='products';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1203,3,'VCORE 100 2023','VCORE','...',289000,'ACTIVE','2026-03-03 10:34:35','2026-03-17 16:39:36','vcore-100-2023',100,'/uploads/yonex_ezone.png','D:/dev_yn/workspace/shop/uploads/yonex_ezone.png');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scroll_banners`
--

DROP TABLE IF EXISTS `scroll_banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scroll_banners` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `button_link_url` varchar(255) DEFAULT NULL,
  `button_text` varchar(50) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `sort_order` int DEFAULT NULL,
  `title` varchar(120) NOT NULL,
  `visible_yn` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scroll_banners`
--

LOCK TABLES `scroll_banners` WRITE;
/*!40000 ALTER TABLE `scroll_banners` DISABLE KEYS */;
/*!40000 ALTER TABLE `scroll_banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skus`
--

DROP TABLE IF EXISTS `skus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skus` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SKU PK',
  `product_id` bigint NOT NULL COMMENT 'ìƒí’ˆ FK',
  `price` int NOT NULL COMMENT 'ì˜µì…˜ ê°€ê²©(ì›)',
  `sku_code` varchar(80) NOT NULL COMMENT 'SKU ì½”ë“œ(ê³ ìœ )',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'í™œì„± ì—¬ë¶€',
  `grip_size` varchar(4) NOT NULL COMMENT 'ê·¸ë¦½ ì‚¬ì´ì¦ˆ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_skus_sku_code` (`sku_code`),
  KEY `idx_skus_product_id` (`product_id`),
  KEY `idx_skus_grip_size` (`grip_size`),
  CONSTRAINT `fk_skus_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='skus';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skus`
--

LOCK TABLES `skus` WRITE;
/*!40000 ALTER TABLE `skus` DISABLE KEYS */;
INSERT INTO `skus` VALUES (2001,1203,289000,'VCORE100-2023-G2',1,'G2'),(2005,1203,289000,'VCORE100-2023-G3',5,'G3'),(2006,1203,289000,'VCORE100-2023-G4',5,'G4');
/*!40000 ALTER TABLE `skus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_item`
--

DROP TABLE IF EXISTS `spot_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spot_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `sort_order` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `visible_yn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_item`
--

LOCK TABLES `spot_item` WRITE;
/*!40000 ALTER TABLE `spot_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `spot_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_banners`
--

DROP TABLE IF EXISTS `text_banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `text_banners` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `link_url` varchar(500) DEFAULT NULL,
  `sort_order` int DEFAULT NULL,
  `title` varchar(200) NOT NULL,
  `visible_yn` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_banners`
--

LOCK TABLES `text_banners` WRITE;
/*!40000 ALTER TABLE `text_banners` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ìœ ì € PK',
  `email` varchar(120) NOT NULL COMMENT 'ì´ë©”ì¼(ë¡œê·¸ì¸)',
  `password_hash` varchar(255) NOT NULL COMMENT 'ë¹„ë°€ë²ˆí˜¸ í•´ì‹œ',
  `name` varchar(60) NOT NULL COMMENT 'ì´ë¦„',
  `role` varchar(20) NOT NULL DEFAULT 'ADMIN' COMMENT 'ê¶Œí•œ(ADMIN/USER)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ìƒì„±ì¼ì‹œ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='users';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (10,'admin@shop.com','CHANGE_ME','Admin','ADMIN','2026-03-03 10:34:35');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-19  9:10:20
