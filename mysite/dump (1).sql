USE vn03448xvg7isnn7;
-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: BlankDictionary
-- ------------------------------------------------------
-- Server version	8.0.16

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
-- Table structure for table `auth_group`
--

DROP TABLE IF EXISTS `auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group`
--

LOCK TABLES `auth_group` WRITE;
/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_group_permissions`
--

DROP TABLE IF EXISTS `auth_group_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_group_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_group_permissions`
--

LOCK TABLES `auth_group_permissions` WRITE;
/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_permission`
--

DROP TABLE IF EXISTS `auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`),
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add bhutia',1,'add_bhutia'),(2,'Can change bhutia',1,'change_bhutia'),(3,'Can delete bhutia',1,'delete_bhutia'),(4,'Can view bhutia',1,'view_bhutia'),(5,'Can add log entry',2,'add_logentry'),(6,'Can change log entry',2,'change_logentry'),(7,'Can delete log entry',2,'delete_logentry'),(8,'Can view log entry',2,'view_logentry'),(9,'Can add permission',3,'add_permission'),(10,'Can change permission',3,'change_permission'),(11,'Can delete permission',3,'delete_permission'),(12,'Can view permission',3,'view_permission'),(13,'Can add group',4,'add_group'),(14,'Can change group',4,'change_group'),(15,'Can delete group',4,'delete_group'),(16,'Can view group',4,'view_group'),(17,'Can add user',5,'add_user'),(18,'Can change user',5,'change_user'),(19,'Can delete user',5,'delete_user'),(20,'Can view user',5,'view_user'),(21,'Can add content type',6,'add_contenttype'),(22,'Can change content type',6,'change_contenttype'),(23,'Can delete content type',6,'delete_contenttype'),(24,'Can view content type',6,'view_contenttype'),(25,'Can add session',7,'add_session'),(26,'Can change session',7,'change_session'),(27,'Can delete session',7,'delete_session'),(28,'Can view session',7,'view_session'),(29,'Can add Bhutia Entry',8,'add_bhutia'),(30,'Can change Bhutia Entry',8,'change_bhutia'),(31,'Can delete Bhutia Entry',8,'delete_bhutia'),(32,'Can view Bhutia Entry',8,'view_bhutia'),(33,'Can add Token',9,'add_token'),(34,'Can change Token',9,'change_token'),(35,'Can delete Token',9,'delete_token'),(36,'Can view Token',9,'view_token'),(37,'Can add application',10,'add_application'),(38,'Can change application',10,'change_application'),(39,'Can delete application',10,'delete_application'),(40,'Can view application',10,'view_application'),(41,'Can add access token',11,'add_accesstoken'),(42,'Can change access token',11,'change_accesstoken'),(43,'Can delete access token',11,'delete_accesstoken'),(44,'Can view access token',11,'view_accesstoken'),(45,'Can add grant',12,'add_grant'),(46,'Can change grant',12,'change_grant'),(47,'Can delete grant',12,'delete_grant'),(48,'Can view grant',12,'view_grant'),(49,'Can add refresh token',13,'add_refreshtoken'),(50,'Can change refresh token',13,'change_refreshtoken'),(51,'Can delete refresh token',13,'delete_refreshtoken'),(52,'Can view refresh token',13,'view_refreshtoken'),(53,'Can add English Entry',14,'add_english'),(54,'Can change English Entry',14,'change_english'),(55,'Can delete English Entry',14,'delete_english'),(56,'Can view English Entry',14,'view_english');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(128) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) NOT NULL,
  `first_name` varchar(150) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `email` varchar(254) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'pbkdf2_sha256$150000$NzCvrrfz24J9$RFo/WZ4ByLtChnxa0NtZ4SBl/l17uTo2bjJSyRicrik=','2019-07-16 06:39:55.342307',1,'admin','','','williamchen@oxy.edu',1,1,'2019-07-03 22:32:01.305861'),(2,'pbkdf2_sha256$390000$mWYC6dXTrPhNFmOHXXOLWC$5PJJY0EWYj0C2llwaC8YI6ynX4+asAtGpX4D5pCwUWI=','2022-12-20 00:23:37.569243',1,'amy','','','williamchen@oxy.edu',1,1,'2019-09-16 20:55:20.600360'),(3,'pbkdf2_sha256$150000$cq2fDWI5MACI$jIA9VF1lNeaIpKAJ3WwB66h5TQAT6TmC4YznsNKSRPw=','2020-10-27 17:04:12.684765',1,'william','','','williamshengchen8@gmail.com',1,1,'2020-10-26 20:16:36.181409');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_groups`
--

DROP TABLE IF EXISTS `auth_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`),
  KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`),
  CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_groups`
--

LOCK TABLES `auth_user_groups` WRITE;
/*!40000 ALTER TABLE `auth_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_user_permissions`
--

DROP TABLE IF EXISTS `auth_user_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_user_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`),
  KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`),
  CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_user_permissions`
--

LOCK TABLES `auth_user_user_permissions` WRITE;
/*!40000 ALTER TABLE `auth_user_user_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authtoken_token`
--

DROP TABLE IF EXISTS `authtoken_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authtoken_token` (
  `key` varchar(40) NOT NULL,
  `created` datetime(6) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `authtoken_token_user_id_35299eff_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authtoken_token`
--

LOCK TABLES `authtoken_token` WRITE;
/*!40000 ALTER TABLE `authtoken_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `authtoken_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Bhutia`
--

DROP TABLE IF EXISTS `Bhutia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Bhutia` (
  `entry_id` mediumint(9) NOT NULL,
  `eng_trans` varchar(50) DEFAULT NULL,
  `bhut_rom_formal` varchar(50) DEFAULT NULL,
  `bhut_rom_informal` varchar(50) DEFAULT NULL,
  `bhut_script_formal` varchar(50) DEFAULT NULL,
  `bhut_script_informal` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bhutia`
--

LOCK TABLES `Bhutia` WRITE;
/*!40000 ALTER TABLE `Bhutia` DISABLE KEYS */;
INSERT INTO `Bhutia` VALUES (1,'thank you','thug-zee che, thukjeche','thug-zee che, thukjeche','ཐུགས་རྗེ་ཆེ་','ཐུགས་རྗེ་ཆེ་'),(2,'greetings','kuzu zangpo, kusu sangpo','kuzu zangpo, kusu sangpo','སྐུ་གཟུགས་བཟང་པོ་','སྐུ་གཟུགས་བཟང་པོ་'),(3,'farewell','shap-kyal','','ཞབས་སྐྱེལ་',''),(4,'where?','gana, ghana','gana, ghana','ག་ན་','ག་ན་'),(5,'what?','ghan bo, khamu','ghan bo, khamu','གན་འབོ་/ ག་འབོ་','གན་འབོ་/ ག་འབོ་'),(6,'when?','nam','nam','ནམ','ནམ'),(7,'who?','ka','ka','ཀ་','ཀ་'),(8,'how','katem, gha tem','Katem, gha tem','ཀ་ཐེམ, ག་ཏེམ་','ཀ་ཐེམ, ག་ཏེམ་'),(9,'sorry, apologize','gongmati','zhothsol','གོངས་མ་ཏེད','བཟོད་གསོལ་'),(10,'yes','lags, lei','lags, lei','ལགས ','ལགས་ཡིན་'),(11,'no','me','me','མེ','མེ'),(12,'name','tshan','ming','མཚན་','མིང'),(13,'my','nge ki, ngahi','nge ki, ngahi','ངེས་ཀྱི་/ ངེ་ཀི་','ངེས་ཀྱི་/ ངེ་ཀི་'),(14,'I','nga','nga','ང་','ང་'),(15,'you','rang, rang lengey','chhoth, chod','་རང, རང་ལྷན་རྒྱས་','ཆོད་'),(16,'good','leg, lem','leg, lem','ལེགམ་','ལེགམ་'),(17,'bad','nyes, ma-legs, ma ley, kyo kyo','nyes, ma-legs, ma ley, kyo kyo','ཉེས་, མ་ལེགས་, ཀྱོ་ཀྱོ་','ཉེས་, མ་ལེགས་, ཀྱོ་ཀྱོ་'),(18,'understand','ha-gho','shesshhor','ཧ་གོ ','ཤེས་ཚར་'),(19,'know','khen','shes, she','་མཁྱེན་','ཤེས་'),(20,'1','chig','chig','གཅིག་','གཅིག་'),(21,'2','nyid','nyid','གཉིས་','གཉིས་'),(22,'3','sum','sum','གསུམ་','གསུམ་'),(23,'4','shi','shi','བཞི་','བཞི་'),(24,'5','nga','nga','ལྔ་','ལྔ་'),(25,'6','drug','drug','དྲུག་','དྲུག་'),(26,'7','dun','dun','བདུན་','བདུན་'),(27,'8','ged, gyat','ged, gyat','བརྒྱད་','བརྒྱད་'),(28,'9','gu','gu','དགུ་','དགུ་'),(29,'10','chu/ chu tampa','chu','བཅུ་/ ཐམ་པ་','བཅུ་ཐམ་པ་'),(30,'11','chu-chig','chu-chig','བཅུ་གཅིག་','བཅུ་གཅིག་'),(31,'12','chungi','chungi','བཅུ་གཉིས་','བཅུ་གཉིས་'),(32,'13','chusum','chusum','བཅུ་གསུམ','བཅུ་གསུམ'),(33,'14','chuzee','chuzee','བཅུ་བཞི་','བཅུ་བཞི་'),(34,'15','chonga','chonga','བཅུ་ལྔ་','བཅུ་ལྔ་'),(35,'16','chu drug','chu drug','བཅུ་དྲུག་','བཅུ་དྲུག་'),(36,'17','chu dune','chu dune','་བཅུ་བདུན་','་བཅུ་བདུན་'),(37,'18','chupgey','chupgey','བཅོ་བརྒྱད་','བཅོ་བརྒྱད་'),(38,'19','chugu','chugu','བཅུ་དགུ་','བཅུ་དགུ་'),(39,'20','kechik, nyishu','kechik, nyishu','ཁེ་གཅིག་','ཁེ་གཅིག་'),(40,'100','gyacig','gyacig','བརྒྱ་གཅིག་','བརྒྱ་གཅིག་'),(41,'1000','tongtacig','tongtacig','སྟོང་ཕྲག་གཅིག་','སྟོང་ཕྲག་གཅིག་'),(42,'Mother','Yum','A ma','ཡུམ་','ཨ་མ་ལགས'),(43,'Father','Yab, Pa lags','A-ba','ཡབ་་, པ་ལགས་','ཨ་བ་'),(44,'Older sister','Aila','Ashi','ཨ་འི་ལགས་','ཨ་ཞི་'),(45,'Younger sister','','Num, Busem','','ནུམ, ་བུ་སིམ་'),(46,'Older brother','Agya','A chu','ཨ་རྒྱ་','ཨ་ཅུ་'),(47,'Younger brother','Pillo','Pilbo','སྤོན་ལོ་','སྤོན་ལོ་'),(48,'Maternal aunt (younger)','A nyi la','Ani','ཨ་ནི་ལགས་','ཨ་ཉི་'),(49,'Paternal aunt (younger)','Amchungla','Am chung','ཨམ་ཆུང་','ཨམ་ཆུང་'),(50,'Maternal uncle (younger)','A shangla, Ajala','A shangs, Aja','ཨ་ཞང་་ལགས་','ཨ་ཞང་'),(51,'Paternal uncle (younger)','Akula','Aku','ཨ་ཀུ་་ལགས་','ཨ་ཀུ་'),(52,'son','sres','bu','སྲས་','བུ་'),(53,'daughter','sem, srean','bumo ','སྲསམ་','སྲསམ་'),(54,'grandson','tsapyuk','tsapyuk','ཚ་ཕྱུག་','ཚ་ཕྱུག་'),(55,'granddaughter','tsam','tsam','ཚམ་','ཚམ་'),(56,'nephew','tsa-phyug','tsa-phyug','ཚ་ཕྱུག་','ཚ་ཕྱུག་'),(57,'niece','tsam','tsam','ཚམ་','ཚམ་'),(58,'great grandson','yangpuk','yangpuk','ཡང་ཕྱུག་','ཡང་ཕྱུག་'),(59,'','','','',''),(60,'dog','kyi','kyi','ཁྱི་','ཁྱི་'),(61,'cat','ali','ali','ཨ་ལུས་','ཨ་ལུས་'),(62,'cow','ngo, bha','ngo','ནོ་, ་བ་','ནོ་'),(63,'bird','pichung, bya','pichung','བྱི་ཅུང་, བྱ་','བྱི་ཅུང་'),(64,'pig','phag-ko','phag-ko','ཕག་ཀོ་','ཕག་ཀོ་'),(65,'rat','pitsi','pitsi','བྱི་ཙི་','བྱི་ཙི་'),(66,'animal','seam chen, semcan','seam chen, semcan','སེམས ཅན་','སེམས ཅན་'),(67,'fish','nya','nya','ཉ་','ཉ་'),(68,'horse','ta','ta','རྟ་','རྟ་'),(69,'yak','yak','yak','གཡག་','གཡག་'),(70,'car','motor','lhang khor','མོ་ཏྲོར་','རླངས་འཁོར་'),(71,'road','lam','lam','ལམ་','ལམ་'),(72,'boat','chudrul','chudrul','ཆུ་གྲུ་','ཆུ་གྲུ་'),(73,'hat','u-sha','shamo, sham-bu','དབུ་ཞྭ་','ཞྭ་མོ་, ཞམ་བུ་'),(74,'clothing','namsa','kola','ནམ་བཟའ་','གོས་ལགས་'),(75,'head','u','go','དབུ་','མགོ་'),(76,'nose','shangs','na gug','ཤངས་','སྣ་གུག་'),(77,'mouth','shal','kha','ཞལ་','ཁ་ '),(78,'eyes','chan, chyan','mig','སྤྱན་','མིག'),(79,'tongue','jags','choe','ལྗགས་','ལྕེ་'),(80,'hands','chyag, chak','lag-ko','ཕྱག་ ','ལག་ཀོ་'),(81,'feet','zhab','kang leb','ཞབས','རྐང་ལེབ་'),(82,'leg','zhab','khang po','ཞབས','རྐང་པོ་'),(83,'arms','','lag-nya','','ལག་ཉ་'),(84,'body','kuzu, kusu','lu','སྐུ་གཟུགས་','ལུས་'),(85,'brain','lhethpo, let po, le po','lhethpo, let po, le po','ཀླད་པོ་','ཀླད་པོ་'),(86,'heart','donying','donying','དོ་སྙིང་','དོ་སྙིང་'),(87,'blood','khyag','khyag','ཁྱག་','ཁྱག་'),(88,'sickness/illness','na','na','ན་','ན་'),(89,'shoulder','pungpo','pungpo','པུང་པོ་','པུང་པོ་'),(90,'elbow','kiling','kiling','ཀི་ལིང་','ཀི་ལིང་'),(91,'heel',' tingpo',' tingpo',' ཏིན་པོ་, ',' ཏིན་པོ་'),(92,'top','teng','teng','སྟེང་','སྟེང་'),(93,'down, lower, bottom','hog','hog','འོག་','འོག་'),(94,'left','yoen','yoen','གཡོན་','གཡོན་'),(95,'right','yas','yas','གཡས་','གཡས་'),(96,'north','jyang','jyang','བྱང་','བྱང་'),(97,'south','lho','lho','ལྷོ་','ལྷོ་'),(98,'east ','shar','shar','ཤར་','ཤར་'),(99,'west','Nub','Nub','ནུབ་','ནུབ་'),(100,'Monday','Zadawo','Zadawo','གཟའ་ཟླ་བ་','གཟའ་ཟླ་བ་'),(101,'Tuesday','Zamigmar','Zamigmar','གཟའ་མིག་དམར་','གཟའ་མིག་དམར་'),(102,'Wednesday','Zalhako','Zalhako','གཟའ་ལྷག་པ་','གཟའ་ལྷག་པ་'),(103,'Thursday','Zaphurbo','Zaphurbo','གཟའ་ཕུར་བུ་','གཟའ་ཕུར་བུ'),(104,'Friday','Zapasang','Zapasang','གཟའ་པ་སངས་','གཟའ་པ་སངས་'),(105,'Saturday','Zapenpo','Zapenpo','གཟའ་སྤེན་པ་','གཟའ་སྤེན་པ་'),(106,'Sunday','Zanyima','Zanyima','གཟའཉི་མ་','གཟའཉི་མ་'),(107,'question','dewa, kha di','','དྲི་བ་, ཁ་འདྲི་',''),(108,'again','yangkyar, log chen, log te ra','','ཡང་སྐྱར་, ལོག་ཅན་, ལོག་ཏེ་ར་',''),(109,'all','yongdom, yongzogs, thamcath','','ཡོངས་བསྡོམ་, ཡོངས་རྫོགས་, ཐམས་ཅད་',''),(110,'further, still, even','darong','ད་རང་/ ད་རུང་','ད་རང་/ ད་རུང་',''),(111,'any','gandrey','','རྒྱབ་ལས་','རྒྱབ་ལས་'),(112,'and','dang','','དང་',''),(113,'here','na','','ན་',''),(114,'there','ona','ona','ཨོ་ན་','ཨོ་ན་'),(115,'he, they (gender neutral pronoun)','khong','kho','ཁོང་','ཁོ་'),(116,'she','khong','mo','ཁོང་','མོ་'),(117,'they, them, themselves','khongtso, khocheg','','ཁོང་ཚོ་, གུ་ཅག་',''),(118,'his','khong ki','khoi','ཁོང་གི་','ཁོའི་'),(119,'hers','khong ki','moi','ཁོང་གི་','མོའི་'),(120,'theirs','khongtsho ki','khongtshay','ཁོང་ཚོ་གི་','ཁོང་ཚོའི་'),(121,'later','gyabley','','གྱབ་ལས་',''),(122,'earlier','haley','','ཧ་ལས་',''),(123,'maybe','yintro','','ཡིན་ཏྲོ་',''),(124,'many','key po, mang-po','key po','ཀེས་པོ་་, མང་པོ་','ཀེས་པོ་་'),(125,'few','nyoung','shulcig','ཉུང་','ཤུལ་ཅིག་'),(126,'close, shut','tsum','','བཙུམ་',''),(127,'only','chuko','','ཅུ་ཀོ་',''),(128,'others','jomey','shen','ཞོ་མེ་','གཞན་'),(129,'hard','tag drag, thig','tag drag','ཀྲག་དྲགས་, མཁྲེགས་','ཀྲག་དྲགས་'),(130,'soft','sop','','སོབ་',''),(131,'some','khashes','lala','ཁ་ཤས་','ལ་ལ་'),(132,'start','go','','འགོ་',''),(133,'finish','drup, dzog-po, lhag, droi','','སྒྲུབ་, རྫོགས་པོ་, ལྷག. སྒྲོས་་','སྒྲུབ་, རྫོགས་པོ་'),(134,'that person','odey mi','','ཨོ་འདི་མི་',''),(135,'this person','di mi','','འདི་མི་',''),(136,'those people','odey mitsho','','ཨོ་འདི་མི་ཙུ་',''),(137,'these people','di mitsho','','་འདི་མི་ཙུ་',''),(138,'turn it off','sim','','སེམས་',''),(139,'light blue','ngo-kya','','སྔོ་སྐྱ་',''),(140,'wait','zhu','gug','ཞུགས་','སྒུག་'),(141,'places','yul','','ཡུལ་',''),(142,'house, home','khyim','zim-khang','ཁྱིམ་','གཟིམ་ཁང་'),(143,'bank','ngul-khang','','དངུལ་ཁང་',''),(144,'school','lhopdra','','སློབ་གྲྭ་',''),(145,'hospital','men-khang','','སྨན་ཁང་',''),(146,'city','grongkher','','གྲོང་ཁྱེར་',''),(147,'village','gyong, kyong, gyang','','གྱོང་, གྱང་',''),(148,'hotel','zim-khang','nyal-khang','གཟིམ་ཁང་','ཉལ་ཁང་/ མགྲོན་ཁང་'),(149,'prison','tsan','','བཙན་',''),(150,'monastery','gonpo, gonpa, gompa','','དགོན་པོ་','དགོན་པོ་'),(151,'church','yishu lhakhang','','ཡི་ཤུ་ལྷ་ཁང་',''),(152,'temple','lha khang','','ལྷ་ཁང་',''),(153,'restaurant','za-khang','','ཟ་ཁང་',''),(154,'country','yule','','ཡུལ་',''),(155,'time','khap','khap','སྐབས་','སྐབས་'),(156,'year','gunglo','rab-lo (according to Tibetan calendar)','དགུང་ལོ་','རང་ལོ་'),(157,'month','dawa','','ཟླ་བ་',''),(158,'week','dun-thak','','བདུན་ཕྲག་',''),(159,'day','nyim','','ཉི་མ་',''),(160,'January','Yin lo Daw dangpo','','དབྱིན་ལོ་ཟླ་བ་་དང་པོ་',''),(161,'February','Yin lo Daw nyidpo','','དབྱིན་ལོ་ཟླ་བ་གཉིས་པོ་',''),(162,'March','Yin lo daw sumpo','','དབྱིན་ལོ་ཟླ་བ་་གསུམ་པོ་',''),(163,'April','Yin lo daw shipo','','དབྱིན་ལོ་ཟླ་བ་་བཞི་པོ་',''),(164,'May','Yin lo daw ngapo','','དབྱིན་ལོ་ཟླ་བ་་ལྔ་པོ་',''),(165,'June','Yin lo daw druggo','','དབྱིན་ལོ་ཟླ་བ་བ་དྲུག་པོ་',''),(166,'July','Yin lo daw dunpo','','དབྱིན་ལོ་ཟླ་བ་་བདུན་པོ་',''),(167,'August','Yin lo daw gedpo','','དབྱིན་ལོ་ཟླ་བ་་བགྱད་པོ',''),(168,'September','Yin lo daw gupo','','དབྱིན་ལོ་ཟླ་བ་་དགུ་པོ་',''),(169,'October','Yin lo daw chupo','','དབྱིན་ལོ་ཟླ་བ་་བཅུ་པོ་',''),(170,'November','Yin lo daw chupchi','','དབྱིན་ལོ་ཟླ་བ་བཅུ་གཅིག་པོ་',''),(171,'December','Yin lo daw chungi','','དབྱིན་ལོ་ཟླ་བ་་བཅུ་གཉིས་པོ་',''),(172,'The year 1000','Yishi di lo dongtra gcig','','ཡི་ཤུ་འདས་ལོ་སྟོང་ཕྲག་གཅིག་',''),(173,'The year 2000','Yishu di lo dongtra nyi','','ཡི་ཤུ་འདས་ལོ་སྟོང་ཕྲག་གཉིས་',''),(174,'The year 1910','Yishu di lo chigdong gubgya chu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་བཅུ་',''),(175,'The year 1920','Yishu di lo chigdong gubgya nyishu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་གཉིས་བཅུ་',''),(176,'The year 1930','Yishu di lo  chigdong gubgya sumchu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་གསུམ་བཅུ་',''),(177,'the year 1940','Yishu di lo chigdong gubgya zhibchu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་བཞི་བཅུ་',''),(178,'the year 1950','Yishu di lo chigdong gubgya ngachu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་ལྔ་བཅུ་',''),(179,'the year 1960','Yishu di lo chigdong gubgya  drugchu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་དྲུག་བཅུ་',''),(180,'the year 1970','Yishu di lo chigdong gubgya denchu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་བདུན་བཅུ་',''),(181,'the year 1980','Yishu di lo chigdong gubgya gechu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱད་བརྒྱད་བཅུ་',''),(182,'the year 1990','Yishu di lo chigdong gubgya gubchu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བརྒྱ་དགུ་བཅུ་',''),(183,'the year 2010','Yishu di lo nyidong chu','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་',''),(184,'the year 2020','Yishu di lo nyidong ngishu','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་གཉིས་ཤུ་',''),(185,'the year 1991','Yishu di lo chigdong gubgya gubchu go cig','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་གཅིག་',''),(186,'the year 1992','Yishu di lo chigdong gubgya gubchu go nyi','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་གཉིས་',''),(187,'the year1993','Yishu di lo chigdong gubgya gubchu go sum','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་གསུམ་',''),(188,'1994','Yishu di lo chigdong gubgya gubchu go shi','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་བཞི་',''),(189,'1995','Yishu di lo chigdong gubgya gubchu go nga','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་ལྔ་',''),(190,'1996','Yishu di lo chigdong gubgya gubchu go drug','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་འདྲུག་',''),(191,'1997','Yishu di lo chigdong gubgya gubchu go den','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་བདུན་',''),(192,'1998','Yishu di lo chigdong gubgya gubchu go gey','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་བཅུ་བརྒྱད་བཅུ་',''),(193,'1999','Yishu di lo chigdong gubgya gubchu go gu','','ཡི་ཤུ་འདས་ལོ་གཅིག་སྟོང་དགུ་གྱ་གོ་དགུ་',''),(194,'2001','Yishu di lo nyidong chig','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་གཅིག་',''),(195,'2002','Yishu di lo nyidong nyi','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་གཉིས་',''),(196,'2003','Yishu di lo nyidongsum','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་གསུམ་',''),(197,'2004','Yishu di lo nyidongshi','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཞི་',''),(198,'2005','Yishu di lo nyidong nga','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་ལྔ་',''),(199,'2006','Yishu di lo nyidong drug','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་འདྲུག་',''),(200,'2007','Yishu di lo nyidong den','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བདུན་',''),(201,'2008','Yishu di lo nyidong gyad','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བརྒྱད་',''),(202,'2009','Yishu di lo nyidong gu','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་དགུ་',''),(203,'2011','Yishu di lo nyidong zhupcig','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་གཅིག་་',''),(204,'2012','Yishu di lo nyidong zhupnyi','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་གཉིས་',''),(205,'2013','Yishu di lo nyidong zhupsum','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་གསུམ་',''),(206,'2014','Yishu di lo nyidong zhupshi','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་བཞི་',''),(207,'2015','Yishu di lo nyidong chenga','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅོ་ལྔ་',''),(208,'2016','Yishu di lo nyidong zhudrug','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་དྲུག་',''),(209,'2017','Yishu di lo nyidong zhupden','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་བདུན་་',''),(210,'2018','Yishu di lo nyidong zhupgey','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅོ་བརྒྱད་',''),(211,'2019','Yishu di lo nyidong zhupgu','','ཡི་ཤུ་འདས་ལོ་གཉིས་སྟོང་བཅུ་དགུ་',''),(212,'happened','tensa','','ཏེན་ས་',''),(213,'day','nyen','','ཉིན་',''),(214,'morning','ngaru','shogs','སྔ་རུ་','ཞོགས་'),(215,'afternoon','Nyim-ped','','ཉིམ་ཕྱད་',''),(216,'evening, dusk','thun-tsams','','ཐུན་མཚམས་','ཐུན་མཚམས་'),(217,'night','shag','','ཞག་',''),(218,'one o\'clock','chhu-tshoth chig','','ཆུ་ཚོད་གཅིག་',''),(219,'two o\'clock','chhu-tshoth nyid','','ཆུ་ཚོད་གཉིས་',''),(220,'three o\'clock','chhu-tshoth sum','','ཆུ་ཚོད་གསུམ་',''),(221,'four o\'clock','chhu-tshoth shi','','ཆུ་ཚོད་གཞི་',''),(222,'five o\'clock','chhu-tshoth nga','','ཆུ་ཚོད་ལྔ་',''),(223,'six o\'clock','chhu-tshoth drug','','ཆུ་ཚོད་དྲུག་',''),(224,'seven o\'clock','chhu-tshoth den','','ཆུ་ཚོད་བདུན་',''),(225,'eight o\'clock','chhu-tshoth gey','','ཆུ་ཚོད་བརྒྱད་ ',''),(226,'nine o \'clock','chhu-tshoth gu','','ཆུ་ཚོད་གུ་',''),(227,'ten o\'clock','chhu-tshoth chu','','ཆུ་ཚོད་བཅུ་',''),(228,'eleven o\'clock','chhu-tshoth chucig','','ཆུ་ཚོད་བཅུ་གཅིག་',''),(229,'twelve o\'clock','chhu-tshoth chunyi','','ཆུ་ཚོད་བཅུ་གཉིས་',''),(230,'directions','choks','','ཕྱོགས་',''),(231,'come','shog','shegs','ཤོག་','གཤེགས་'),(232,'go','chen','shegs','བྱོན་','གཤེགས་'),(233,'leave','gom shu she','log gyu she','གོམ་ཤུ་ཤད་','ལོག་འགྱུ་ཤད་'),(234,'take, lead, accept','len','khyig, thit','ལེན་','ཁྱིག་, འཁྲིད་'),(235,'eat','zhe','za','ཞེས་','ཟ་'),(236,'drink','zhe','thung','བཞེས་','འཐུང་'),(237,'get up','shangs','Long','བཞེངས་','ལོང་'),(238,'sleep','zim','nyal','གཟིམ་','ཉལ་'),(239,'','','','',''),(240,'hold','she ','she','ཤེས་','ཤེ་'),(241,'lie ',' zim','nyi','ས་ཁ་གཟིམ་','ས་ཁ་ཉལ་'),(242,'listen','nyenbo','senpo','ཉན་བོ་','གསན་པོ་'),(243,'speak','songbona','lap','གསུངས་བོ་ན་','ལབ་བོ་ན་'),(244,'be quiet','','ur ma gyab, kha tsum','','ཨུར་མ་ཀྱབ་,  ཁ་ཚུམ་'),(245,'run','Gyuk','Gyuk','གྱུག་','གྱུག་'),(246,'sit','zhu','den','བཞུགས་','གདན་'),(247,'stand','yarshangs','longs','ཡར་བཞེངས་','ལོངས་'),(248,'walk - to talk','gompo, ghom po','','  གོམ་པོ་','གོམ་པོ་'),(249,'tell','song','lap','གསུངས་','ལབ་'),(250,'mud','dam-pa','','འདམ་པག་','འདམ་པག་'),(251,'dirt','drag-ka','','དྲག་ཀ་',''),(252,'earth','sashi','','ས་གཞི་',''),(253,'dust','teou','','ཐེའོ་',''),(254,'fire','mea','','མེ་',''),(255,'grass','yusm','','ཡུས་མ་',''),(256,'plant','shing','','ཤིང་',''),(257,'hail','so','','སོ་',''),(258,'ice, snow','kawo, khawo','','ཁ་བོ་',''),(259,'metal','chak','','ལྕགས་',''),(260,'liquid','chu','','ཆུ་',''),(261,'water','chu','chhab','ཆུ་','ཆབ་'),(262,'moon','dawa','','ཟླ་བ་',''),(263,'sun','nyema','','ཉི་མ་',''),(264,'rock','do','','རྡོ་',''),(265,'rainbow','ja','','འཇའ་','འཇའ་'),(266,'sky','kha','','མཁའ་',''),(267,'star','karma','','ཀ་རྨ་',''),(268,'tree','shing','','ཤིང་',''),(269,'wind','lhung-ma','','རླུང་མ་',''),(270,'rain','chhar-po','','ཆར་པོ་',''),(271,'seasons, period of time, era','dhue-tsams','','དུས་ཚམས་',''),(272,'summer','byar','','དབྱར་',''),(273,'winter','guen, gun','souka','དགུན་','སེ་ཀ་'),(274,'autumn','toenka','','སྟོན་ཀ་','སྟོན་ཀ་'),(275,'spring','chika, cheka','','དཔྱིད་ཀ་',''),(276,'appearance','zhota, zob-tra','','བཟོ་ལྟ་',''),(277,'colour','dog','','མདོག་','མདོག་'),(278,'red','mar-po','','དམར་པོ་',''),(279,'black','nakku','','ནགས་གོ་',''),(280,'white','kar, karpo','kar, karpo','དཀར་, དཀར་པོ་','དཀར་, དཀར་པོ་'),(281,'yellow','searpo','','སེར་པོ་',''),(282,'blue','ngon po','','སྔོན་པོ་',''),(283,'orange','tsalum','','ཚ་ལུམ་',''),(284,'green','jang-ku','','ལྗང་ཀུ་',''),(285,'purple','gya-muk, gya mug','','རྒྱ་མུག་',''),(286,'pink','mar-khya','','དམར་སྐྱ་',''),(287,'silver','ngul','','དངུལ་',''),(288,'gold','ser','','གསེར་',''),(289,'brown','mug po','','སྨྱུག་པོ་',''),(290,'humans','mi','','མི་',''),(291,'progress, develop','yargay','','ཡར་རྒྱས་',''),(292,'same','dra-o','','འདྲ་བོ་',''),(293,'different','khet','','ཁྱད་',''),(294,'content','kar chhag','','དཀར་ཆག་',''),(295,'mouse','ding-jing','','དིང་ཇིང་',''),(296,'rat','byi-tsi','','བྱི་ཙི་',''),(297,'fox','wa, wam','','ཝ་, ལྦམ་, ལྦ',''),(298,'deer','sha-ow','','ཤ་བོ་',''),(299,'bear','dhom','','དོམ་',''),(300,'snake','byew','','སྦྱིའུ་',''),(301,'scorpion','dik sing, dig shing','','སྡིག་སྲིང་',''),(302,'spider','bag-rag','','བག་ཧྲག་',''),(303,'ant','gyuma','','གྱུ་མ་',' '),(304,'mosquito','cha phi, chafi, dhug-byam','','ཅ་ཕྱི་, དུག་སྦྱམ་',''),(305,'butterfly','byam lab','','སྦྱམ་ལྷབ་',''),(306,'cold','khyag, khyag','','ཁྱག་, ཁྱག་',''),(307,'hot','tsha drag','','ཚ་དྲགས་',''),(308,'rain','chab, char, charpo','','ཆབ་, ཆར་, ཆར་པོ་',''),(309,'snow','kawo, kao','','ཁ་བོ་',''),(310,'windy','lung-ma','','རླུང་མ་',''),(311,'sunny','nyim shar ','','ཉི་མ་ཤར་',''),(312,'hygiene, cleanliness, free from dirt, clean','tsang-mu, zangmo','','གཙང་མུ་',''),(313,'wash','sil','kyu','བསིལ་','ཁྱུ་'),(314,'small plate','lichung','','ལི་ཆུང་',''),(315,'couple, married couple','zatsang, zaku','','བཟའ་ཚང་, བཟའ་ཀོ་',''),(316,'shoes','zhab ja','lham','ཞབས་ཆ་','ལྷམ་'),(317,'bag','surgeb','geb','སུར་གྱབ་','གྱབ་, འགེབ་'),(318,'small','chung','','ཆུང་',''),(319,'mirror','me-long','','མེ་ལོང་',''),(320,'research center, institute','nyamship-khang','','ཉམ་ཞིབ་ཁང་',''),(321,'necessary, need[ed], want[ed]','kho, gopo','','མཁོ་, དགོས་པོ་',''),(322,'fragrance','','drim','དྲིམ་',''),(323,'ill motive, nlack hearted','nyingngag','','ཉིང་ནག་',''),(324,'cheese','chhus','','ཆུར་',''),(325,'towel, napkin, hankerchief','','toras','','ལྟོས་རས་'),(326,'cook, make food','tso','','བཙོ་',''),(327,'vegetables','tshom','','ཚོད་མ་',''),(328,'beans','chamta','tibig','ལྕམ་ཏ་','ཏི་བིག་'),(329,'corn','ken chong, kensong','','ཀེན་ཙོང་',''),(330,'cucumber','gha nyon, kanyu','','ག་ཉོན་',''),(331,'potato, edible root','kyeu, kihu','','ཀྱའུ་, ཀིའུ་',''),(332,'tomato','benda','','བེན་ད་',''),(333,'onion','tsong, o chong','tsong, o chong','ཨོ་ཙོང་','ཨོ་ཙོང་'),(334,'mole','meo','','རྨེ་བོ་',''),(335,'squash','iskus','','ཨིས་ཀུས་',''),(336,'rice','chum','do','ཆུམ་','དོ་'),(337,'noodle','gyathug','','རྒྱ་ཐུག་','རྒྱ་ཐུག་'),(338,'dumpling','zhe-mo','mo-mo','བཞེས་མོགས་','མོགས་མོགས་'),(339,'apple','kushu','','ཀུ་ཤུ་',''),(340,'sweet','gnam, nam','','མངརམ་',''),(341,'snacks','dzo, jo','','ཛ་བོ་',''),(342,'orangle','sa-lum','','ཚ་ལུམ་',''),(343,'beer','sui-chhang','chang','གསོལ་ཆང་','ཆང་'),(344,'tea','sui-ja','ja','གསོལ་ཇ་','ཇ་'),(345,'coffee','co-fi','','ཀོ་ཕི་','ཀོ་ཕི་'),(346,'wild roots','nangtsan kyu','','ནང་ཙན་ཀུའུ་',''),(347,'meat','sha','','ཤ་',''),(348,'chicken meat','bya sha','','བྱ་ཤ་',''),(349,'fish meat','nya  sha','','ཉ་ཤ་',''),(350,'mutton (goat meat)','rasha','',' ་ར་ཤ་',''),(351,'mutton (sheep) sha','lug-sha','','ལུག་ཤ་',''),(352,'beef','lang-sha','','གླང་ཤ་',''),(353,'vegetarian, green leafy vegetable','ngo-tshoth','','སྔོ་ཚོད་',''),(354,'food','shal lag','to, zan ','ཞལ་ལག་','ལྟོ་, ཟན་'),(355,'Buddhist monastery','Nang po\'i gonpa','','ནང་པོའི་དགོན་པ་',''),(356,'Christian church','Yishu lhakhang','','ཡི་ཤུ་ལྷ་ཁང་',''),(357,'Hindu mandir','Hindu lhakhang','','ཧིན་དུ་ལྷ་ཁང་',''),(358,'Sikh gurudwara','Sikh ki lhakhang','','སིགྷ་ལྷ་ཁང་',''),(359,'Muslim mosque','Khache Lhakhang','','ཁ་ཆེ་ལྷ་ཁང་',''),(360,'form of knowledge','rig gney','','རིག་གནས་',''),(361,'agriculture','sonam, zhing ley','','སོ་ནམ་, ཞིང་ལས་',''),(362,'archaeology','nangorigney','','གནའ་དངོས་རིག་གནས་',''),(363,'architecture','tsikdzorikney','','རྩིག་བཟོ་རིག་གནས་',''),(364,'astronomy','gyukarrikney','','རྒྱུ་དཀར་རིག་གནས་',''),(365,'biology','Kengorigney','','སྐྱེས་དངོ་རིག་གནས་',''),(366,'botany','tsishingrigney','','རྩི་ཤིང་རིག་གནས་',''),(367,'cosmology','','','',''),(368,'chemistry','dzey-gyur','','རྫས་གྱུར་',''),(369,'drama','do-gar','','ཟློས་གར་',''),(370,'ecology','khoryugrigney, rigney','','འཁོར་ཡུག་རིགས་གནས་,  རིགས་གནས',''),(371,'economics','paljorrigney','','དཔལ་འབྱོར་རིག་གནས་',''),(372,'ethics','tshultrimrigney, rigney','','ཚུལ་ཁྲིམས་རིགས་གནས་, རིགས་གནས',''),(373,'engineering','','','',''),(374,'geography','sazhirigney','','ས་གཞི་རིག་གནས་',''),(375,'geology','sashi','','ས་གཞི་',''),(376,'history','log-gues','','ལོ་རྒྱུས་',''),(377,'horticulture','ngotshalrigney, rigney','','སྔོ་ཚལ་རིགས་གནས་,  རིགས་གནས',''),(378,'law','chatrim','','བཅའ་ཁྲིམས་',''),(379,'literature','tsomrig','','རྩོམ་རིག་',''),(380,'mathematics','angtsi','','ཨང་རྩིས་',''),(381,'philosophy','tshen-rig','','མཚན་རིག་',''),(382,'physics','','','',''),(383,'psychology','seamkham','','སེམས་ཁམས་',''),(384,'sociology','milugrigney, rigney','','མི་ལུགས་རིགས་གནས་, ་རིགས་གནས',''),(385,'grammar','bya-ka-ran','','བྱ་ཀ་རན་',''),(386,'medicine','men-rik','','སྨན་རིགས་',''),(387,'zoology/ veterinary science','seams chen rigney, rigney','','སེམས་ཅན་རིགས་གནས་, ་རིགས་གནས',''),(388,'queen','gyal mo','','རྒྱལ་མོ་',''),(389,'king','gyal-po, chos-rgyal, zhung-kyang','','རྒྱལ་པོ་, ཆོས་རྒྱལ་, གཞུང་སྐྱང་',''),(390,'prime minister','srea-lon','','སྲིད་བློན་',''),(391,'minister','ka lon','lhon-po','བཀའ་བློན་','བློན་པོ་'),(392,'Judge','khrimdag/ khrimpon','','ཁྲིམ་བདག་/ ཁྲིམ་དཔོན་',''),(393,'advocate','','','',''),(394,'Director','','','',''),(395,'Governor','sreth kyong','','སྲིད་སྐྱོངས་',''),(396,'President','srhezing','','སྲིད་འཛིན་',''),(397,'pot, vessel','','dzama, dzam','','རྫ་མ་, རྫམ་'),(398,'writer/ clerk','tsom-poen/ drung-yig','','རྩོམ་དཔོན་, དྲུང་ཡིག་',''),(399,'household objects','','khyim ki charkar','','ཁྱིམ་གི་ཆ་ཀར་'),(400,'stove, fireplace','thab','','ཐབ་',''),(401,'cup','pob, kar-yol, zhekar','','པོབ་, དཀར་ཡོལ་, བཞེས་དཀར་',''),(402,'pan','lhanga','','སླ་རྔ་',''),(403,'gas','lungthab','','རླུང་ཐབ་',''),(404,'knife','gye-chung, gyi chung','','གྱི་ཅུང་',''),(405,'kettle','jam bim, bim ','','ཇམ་བི་, བིམ་',''),(406,'plate','li dir','','ལི་སྡིར་',''),(407,'spoon','zhe-thoom','thoom','ཞེས་མཐུམ་','མཐུམ་'),(408,'tea strainer','ja tsa','','ཇ་ཚགས་',''),(409,'water container','','dzama, dzam','','རྫ་མ་, རྫམ་'),(410,'time','kab, du tshu','','སྐབས་',''),(411,'tomorrow','thong-rangs','sang','ཐོ་རངས་','སང་'),(412,'before last year','shosning','','གཞོས་ཉིང་',''),(413,'last year','na-ning','','ན་སྙིང་',''),(414,'today','dha-ring','','ད་རིང་',''),(415,'this year','dhyu-chig','','དུ་ཅིག་',''),(416,'day before yesterday','kha-nup','','ཁ་ནུབ་',''),(417,'yesterday','kha-sang, dang','','ཁ་སང་, མདང་','ཁ་སང་, མདང་'),(418,'day after tomorrow','nan tsi','','གནངས་ཚེ་',''),(419,'in the future','gyabley','','རྒྱབ་ལས་',''),(420,'a long time a go','dang po ngon lay','','དང་པོ་སྔོན་ལས་',''),(421,'hour','chu tsod','','ཆུ་ཚོད་',''),(422,'minute','karmo','karmo','སྐར་མོ་','སྐར་མོ་'),(423,'second','karcha','','སྐར་ཆ་',''),(424,'same','chig ko','','གཅིག་ཀོ་',''),(425,'next year','sangphoth','','སང་ཕོད་, སང་སྤོད་',''),(426,'right now','da tho','','ད་ལྟོ་',''),(427,'knowledge','khyenbo','yea-shes','མཁྱེན་བོ་','ཡེ་ཤེས་'),(428,'custom, system, way, procedure','gyen-rab, do lug','','འགྲོ་ལུགས་',''),(429,'story','sungtam','','གསུང་གཏམ་',''),(430,'book','deb','','དེབ་',''),(431,'classes','dzintra','','འཛིན་ཁྲ་',''),(432,'learn, study, read','chos dok','','ཆོས་སྐྲོགས་',''),(433,'internet','drawa','','དྲྭ་སྒྱ་',''),(434,'place','lung-po, ne','','ལུང་པོ་, གནས་',''),(435,'environment','khor yog','','འཁོར་ཡུག་',''),(436,'ocean','gyatso','','རྒྱ་མཚོ་','རྒྱ་མཚོ་'),(437,'river','tsang-chhu','','གཙང་ཆུ་',''),(438,'stream','kyong, gyun chu, chumig','','ཀྱོང་, རྒྱུག་ཆུ་, ཆུ་མིག་',''),(439,'mountain','ghang, ghangri','ghang, ghangri','གངས་, གངས་རི་','གངས་, གངས་རི་'),(440,'hills','ri','ri','རི་','རི་'),(441,'plains','thang','thang','ཐང་','ཐང་'),(442,'wetland','dam jab, dam job, sajenpu ','','འདམ་རྫོབ་, ས་རྗེན་པུ་',''),(443,'forest, jungle','nagstsan','shingnags','ནགས་ཙན་','ཤིང་ནགས་'),(444,'tree','shingdongyul, shingdongu','','ཤིངས་སྡོང་ཡུལ་, ཤིངས་སྡོང་དབུས་',''),(445,'sand','bem','','བྱེམ་',''),(446,'flower','me-tog','','མེ་ཏོག་',''),(447,'actions','ley','yog','ལས་','གཡོག་'),(448,'think','gongpo, nosam','gongpo, nosam','དགོངས་པོ་, མནོ་བསམ་','དགོངས་པོ་, མནོ་བསམ་'),(449,'see','thong-po','','མཐོང་པོ་',''),(450,'look','zhigs','ta','གཟིགས་','བལྟ་'),(451,'talk','sung','lap','གསུང་','ལབ་'),(452,'sit','shughs','den','བཞུགས་','སྡོད་'),(453,'walk','chen','gyu','བྱོན་','འགྱུ་, རྒྱུ་'),(454,'stand','shang','long','བཞེང་','ལོང་'),(455,'lie down','nyal','','ཉལ་བོ་',''),(456,'run','gyug','','རྒྱུགས་',''),(457,'sleep','zim','nyal','གཟིམ་','ཉལ་'),(458,'practice','laglen','','ལག་ལེན་',''),(459,'be concerned with','thu dug','sem dug','ཐུགས་སྡུག་','སེམས་སྡུག་'),(460,'care about','gatsho','','དགའ་ཚོ་',''),(461,'happy','gawa, ga','',' དགའ་བ་, དགའ་,',''),(462,'like/ be pleased','gakyith','','དགའ་སྐྱིད་',''),(463,'love','chaepo','shen','གཅེས་པོ་','གཅེས་པོ་, ཞེན་'),(464,'hate','she-dhang','','ཞེ་སྡང་',''),(465,'funny (amusing)','gyalrolong cici','','གད་བྲོ་ལོང་ཅི་ཅི་',''),(466,'sad','kyo','yeth-khyo, seamkhyo','སྐྱོ་','ཡིད་སྐྱོ་, སེམས་སྐྱོ་'),(467,'despair','kyom, yimugpo, reche mepo','','སྐྱོམ་, ཡིད་མུག་པོ་, རེ་ཆེ་མེད་པོ་',''),(468,'annoying','tsingcici','','ཙིང་ཅི་ཅི་',''),(469,'anger','tsigko, thong tho, thawo','','ཚིག་ཀོ་, ཁོང་ཁྲོ་, ཁྲོ་བོ་',''),(470,'peaceful','shi-wa','','ཞི་བ་',''),(471,'examine','cheyth, shipchath','','དཔྱད་, ཞིབ་དཔྱད་',''),(472,'explore','tshur she','','ཚོལ་ཤད་',''),(473,'ask, ask to do','lhongpo, kul bo, driwo','','སློང་པོ་, སྐུལ་བོ་, དྲི་བོ་',''),(474,'look for/ seek','tshole','','ཚོལ་',''),(475,'to obtain','drup','lone','གྲུབ་','ལོན་'),(476,'throw away/ cast off','tokko, ko she','','རྟོག་ཀོ་ , གོ་ཤེས་',''),(477,'common','thunmong','','ཐུན་མོངས་',''),(478,'rare','kon po','','དཀོན་པོ་',''),(479,'beautiful','dze po, dza cici','','མཛེས་པོ་, ཛ་ཅི་ཅི་',''),(480,'intelligent','rig-chen, khenbo, gyugchen, rigo gyungdrug','','རིག་ཅན་, མཁས་པོ་ོ་, རྒྱུགས་ཅན་, རིག་ཀོ་གྱུང་དྲུགས་',''),(481,'ugly','machangbo, zugney','','མ་ཅང་པོ་, གཟུགས་ཉེས་',''),(482,'unpleasant, evil','ngen po','','ངན་པོ་',''),(483,'fat','gyag, gyatgtom','','རྒྱགས་, རྒྱགས་ཏོམ་',''),(484,'thin','biko, supsup, labo','','བི་ཀོ་, སྲབ་སྲབ་, སླ་བོ་',''),(485,'plain','drang po, thang, gyathang','','དྲང་པོ་, ཐང་, རྒྱ་ཐང་',''),(486,'flashy','a ci ci, o trawo','','འོད་ཅི་ཅི་, འོད་འཕྲ་བོ་',''),(487,'level, floor','thog','','ཐོག་',''),(488,'fair, simple','drang-po','','དྲང་པོ་',''),(489,'not fair','drang-po me','','དྲང་པོ་མེད་',''),(490,'produce, make','dzo, thonkey','','བཟོ་བོ་, ཐོན་བསྐྱེད་',''),(491,'weak','lingling, tobchung','','ལིང་ལིང་, སྟོབས་ཆུང་',''),(492,'strong','dingding, tobchen, shukchen','','དིང་དིང་, སྟོབས་ཆེན་, ཤུགས་ཆེན་',''),(493,'unproductive, lazy','shel-lo','','ཤེས་ལོགས་',''),(494,'labour, work','lea','','ལས་',''),(495,'hardworking','u-tshog','','ཨུ་ཙུགས་',''),(496,'lazy','she-log','','ཞེ་ལོག་',''),(497,'live','tsozhen, sonpo','','འཚོ་བཞེན་, གསོན་པོ་',''),(498,'travel','due','',' འགྲུལ་',''),(499,'die','drong','chi, shi','གྲོངས་','འཆི་, ཤི་'),(500,'be born','kyiwa','khrung, thung','སྐྱེ་བ་','འཁྲུངས་'),(501,'inspire','salba','','གསལ་བ་',''),(502,'wander, roam','','khyam','','ཁྱམ་, འཁྱམས་'),(503,'insist','nyakya','','ཉག་ཀྱང་',''),(504,'frequent','atara','','ཨ་ཏང་ར་',''),(505,'rare','kentra','','དཀོན་དྲགས་',''),(506,'skill, expertise, talent','keytra','','རྩལ་',''),(507,'poor','byangka','','བྱང་ཀ་',''),(508,'rich','pyiku','','པྱི་ཀུ་',''),(509,'spiritual','chos sem','','ཆོས་སེམས་',''),(510,'short temper','tsinglug','','ཙིང་ལུགས་',''),(511,'good temper','shiwa','','ཞི་བ་',''),(512,'point of view/ perspective','dazang, ngosam','','བལྟ་ཟངས་་, ངོ་ཙམ་',''),(513,'mind/ heart','seam, sem','seam ngith','སེམས་','སེམས་ཉིད་'),(514,'opinion','samcha, gongpo, ngosam','','བསམ་ཆ་, དགོངས་པོ་, མནོ་བསམ་',''),(515,'idea','samchar','','བསམ་འཆར་',''),(516,'friend','drogko (male), drome (female)','','གྲོགས་ཀོ་ / གྲོགམ་',''),(517,'enemy','dra','','དགྲ་',''),(518,'know (a person)','ngo-khen','ngo-shes','ངོ་མཁྱེན་','ངོ་ཤེས་'),(519,'not know (a person)','ngo-mi-khen','ngo-mi-shes','ངོ་མི་མཁྱེན་','ངོ་མི་ཤེས་'),(520,'understanding','gotog','sam shes','གོ་རྟོགས་','བསམ་ཤེས་'),(521,'not understand','shu she me','ha-min-go','ཞུ་ཤད་མེད་','ཧ་མིང་གོ་'),(522,'equal','nyamchung','','ཉམ་ཆུང་',''),(523,'bored','she-log','','ཞེ་ལོག་',''),(524,'arrogant, prideful','nga-gyal','','ང་རྒྱལ་','ང་རྒྱལ་'),(525,'excited','gatrashe','','དགའ་དྲགས་ཤེས་',''),(526,'interested','shesdoth, seamsa','','ཤེས་འདོད་',''),(527,'attitude','sam chog','','བསམ་ཕྱོགས་',''),(528,'weigh','je','','ལྗིད་',''),(529,'fit','shong','','ཤོང་',''),(530,'not fit','mi-shong','','མི་ཤོང་',''),(531,'appropriate','tshathag choth, tshung-po','','ཚ་ཐག་ཆོད, མཚུངས་པོ་་',''),(532,'not appropriate','','tshung-po med','','ཚུངས་པོ་མེད་'),(533,'oppress','nem','','ནེམ་',''),(534,'liberate','thar','','མཐར་',''),(535,'complain','zhulo gyabpo','','ཞུ་ལོག་རྒྱབས་པོ་',''),(536,'boast','nga-gyal','','ང་རྒྱལ་',''),(537,'praise','toeth','','བསྟོད་',''),(538,'celebrate','sung','','སྲུང་',''),(539,'give, bestow','pe she, chinshe','','པེ་ཤེས་, སྦྱིན་ཤད་',''),(540,'provoke','nye-ta','','ཉེ་ཏ་',''),(541,'not take responsibility','ke ma ley','','ཀེ་མ་ལེགས་',''),(542,'take responsibility, responsibility, duty','ke, gen','','ཀེ་, འགན་',''),(543,'expand','par che','','པར་ཤད་',''),(544,'destroy','lhags-sheth, chag-sheth, lhag','sig','རླགས་ཤད་, ཀླག་','ཤིགས་'),(545,'laugh','goet','','དགོད་, རྒོད་, གོད་',''),(546,'cry','ngu, gnu','shums','ངུ་','ཤུམས་, བཤུམས་'),(547,'respect','kur, tsi, ghue','gue-shap, she-sa','བཀུར་, བརྩི་, གུས་','གུས་ཞབས་, ཞེ་ས་'),(548,'disrespect','tsi meth','','རྩི་མེད་',''),(549,'help','rogsram','','རོགས་རམ་',''),(550,'hungry','trespo','trespo','བཀྲེས་པོ་','བཀྲེས་པོ་'),(551,'throat','kea, koem, gul','','སྐེ་, སྐོསམ་, མགུལ་',''),(552,'about/ topic','kor','kor','སྐོར་','སྐོར་'),(553,'hair','u-ta','kya','བདུ་སྐྲ་','སྐྱ་'),(554,'poop, stool, excrement','kham sang','kyako','ཁམས་སང་','སྐྱག་ཀོ་'),(555,'thirsty','','khom, kha khom','','སྐོམ་, ཁ་སྐོམ་'),(556,'gift','khyom, khyum','','འཁྱོམས་, འཁྱོམ་','འཁྱོམས་, འཁྱོམ་'),(557,'important','gay-chi-tag, gay-chen, ghael chen','','གལ་ཆེ་དྲགས་',''),(558,'yourself, you all','gu-chag, ghu chag','','གུ་ཅག་',''),(559,'defilement, contamination, pollution','drip','','གྲིབ་',''),(560,'change, increase','gyur-wa, phar','','འགྱུར་བ་, འཕར་','འགྱུར་བ་'),(561,'money, silver, currency','ngul','','དངུལ་','དངུལ་'),(562,'forget','jeth po','','བརྗེད་པོ་','བརྗེད་པོ་'),(563,'obstacle, blunder, problem','nyogtra','','རྙོག་ཁྲ་',''),(564,'compassion, love, kindness','nyining zie, nyinje, kadin','','སྙིང་རྗེ་, བཀའ་དྲིན་','སྙིང་རྗེ་'),(565,'strength, forve','top, khoe ka','','སྟོབས་, འཁོས་ཀ་',''),(566,'weaken/ powerless','topchung','','སྟོབས་ཆུང་',''),(567,'all, totality of things, plural','tham-chath, kun','','ཐམས་ཅད་, ཀུན་','ཐམས་ཅད་, ཀུན་'),(568,'now, at the moment','da-ta','','ད་ལྟ་',''),(569,'further, still','dha-rung','','ད་རུང་','ད་རུང་'),(570,'first, before','dhang-po','','དང་པོ་','དང་པོ་'),(571,'monsoon','dhues lhungs, chardu','','དུས་རླུངས་, ཆར་དུས་',''),(572,'these days','dhing sang','','དེང་སང་',''),(573,'smell','drim','','དྲིམ་',''),(574,'this','di','','འདི་',''),(575,'to have, is','dug','','འདུག་',''),(576,'clothes','nam za','','ནམ་བཟའ་',''),(577,'ear','nam chog','','རྣམ་ཅོག་',''),(578,'obstacle, obstruction','bar-cheth, barchay','','བར་ཆད་',''),(579,'dictionary, glossary','tsigzoth','','ཚིག་མཛོད་',''),(580,'business','tshong','','ཚོང་',''),(581,'lake','tsho','','མཚོ་',''),(582,'excellent','zick-dak','','རྫིགས་དྲགས་',''),(583,'cautious, attentive','zhap zhap, drokzon','','བཟབ་བཟབ་, དྲོགས་ཟོན་',''),(584,'power, force','shugs','','ཤུགས་',''),(585,'grandfather','a zoe','','ཨ་ཇོ་བོ་',''),(586,'grandmother','a nyo','','ཨ་ཉོ་',''),(587,'offering bowl/ plate','katora','','ཀ་ཏོ་ར་',''),(588,'pillar, column','kawa','','ཀ་བ་',''),(589,'cotton fibre','kawo','','ཀ་བོ་',''),(590,'alphabet [for Bhutia/Lhokyed and Tibetan)','kayig sum chu','','ཀ་ཡིག་སུམ་ཅུ་, ཀ་ཡིག་གསུམ་བཅུ་',''),(591,'dry','kam, kampo','','ཀམ་, སྐམ་པོ་',''),(592,'tick, lice','kishig','','ཀི་ཤིག་',''),(593,'equality, even','kun nyam','','ཀུན་མཉམ་',''),(594,'benefit all','kun phen','','ཀུན་ཕན་',''),(595,'to reach, re return','kesong','','སྐེ་སོང་',''),(596,'banana','kedong','','ཀེ་སྡོང་',''),(597,'fern','kem','','ཀེམ་',''),(598,'fern shoot','ketchot','','ཀེས་ཚོད་',''),(599,'fern plant','keshing','','ཀེས་ཤིང་',''),(600,'cunning, clever, naughty','kya hu','','ཀྱག་ཧུ་, སྐྱ་ཧུ་',''),(601,'irritating, bad, agitating','kyom, kha khyom','','ཀྱོམ་, ཁ་སྐྱོམ་',''),(602,'read, study','log','','ཀློག་',''),(603,'difficult','ka , ka le, ghag shog, ghah dag','','དཀའ་, དཀའ་ལས་, གག་ཤོག་, གག་དྲགས་',''),(604,'to fill up, to pour','kang, kong','','བཀང་, སྐོང་',''),(605,'consent, approve','ka se, dig ko','','བཀའ་གསེས་, འགྲིགས་གོ་',''),(606,'meeting, discussion','kamol','','བཀའ་མོལ་',''),(607,'advice, instruction','kalob, kotpo','','བཀའ་སློབ་, བཀོད་པོ་',''),(608,'good luck, congratulations, good wishes','tashi delek','','བཀྲ་ཤིས་བདེ་ལེགས་',''),(609,'reason, cause','kyen','','རྐྱེན་',''),(610,'speech, voice, sound','ket','','སྐད་',''),(611,'body','ku, ku jug','','སྐུ་, སྐུ་གཟུགས་',''),(612,'sour','kyum','','སྐྱུམ་',''),(613,'saying, idiom','khatam','','ཁ་གཏམ་',''),(614,'bad speech','kha nen','','ཁ་ངན་',''),(615,'teeth','khaso','','ཁ་སོ་',''),(616,'personality, character','kham shi','','ཁམ་གཤིས་',''),(617,'special','khyet chen','','ཁྱད་ཅན་',''),(618,'blood pressure','tha lung','','ཁྲ་རླུང་',''),(619,'teach, lecture','thit','','ཁྲིད་',''),(620,'laws, rules','thim','','ཁྲིམས་',''),(621,'market','thom, throm','','ཁྲོམ་',''),(622,'why','ghan bya, kumbeya','','གན་བྱ་',''),(623,'[to] become','dub','','གྲུབ་',''),(624,'move, go','chen','gyu','བྱོན་','འགྱུ་');
/*!40000 ALTER TABLE `Bhutia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_admin_log`
--

DROP TABLE IF EXISTS `django_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) unsigned NOT NULL,
  `change_message` longtext NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`),
  CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_admin_log`
--

LOCK TABLES `django_admin_log` WRITE;
/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
INSERT INTO `django_admin_log` VALUES (1,'2019-07-03 22:42:22.664643','Chik','Chik',1,'[{\"added\": {}}]',1,1),(2,'2019-07-03 22:43:24.025737','Kuzu','Kuzu',1,'[{\"added\": {}}]',1,1),(3,'2019-07-06 21:58:09.296077','Chikia','Chikia',1,'[{\"added\": {}}]',1,1),(4,'2019-10-22 00:55:17.811026','lol','lol',1,'[{\"added\": {}}]',8,2),(5,'2019-11-21 00:38:38.303429','Pulchritude','Pulchritude',1,'[{\"added\": {}}]',14,2),(6,'2020-10-27 18:30:41.811584','4','test',1,'[{\"added\": {}}]',5,2),(7,'2020-10-27 18:31:54.306856','4','test',3,'',5,2);
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_content_type`
--

DROP TABLE IF EXISTS `django_content_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_content_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_content_type`
--

LOCK TABLES `django_content_type` WRITE;
/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
INSERT INTO `django_content_type` VALUES (2,'admin','logentry'),(4,'auth','group'),(3,'auth','permission'),(5,'auth','user'),(9,'authtoken','token'),(1,'bhutia','bhutia'),(6,'contenttypes','contenttype'),(8,'dictionaries','bhutia'),(14,'dictionaries','english'),(11,'oauth2_provider','accesstoken'),(10,'oauth2_provider','application'),(12,'oauth2_provider','grant'),(13,'oauth2_provider','refreshtoken'),(7,'sessions','session');
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_migrations`
--

DROP TABLE IF EXISTS `django_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_migrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'contenttypes','0001_initial','2019-07-03 22:30:46.722164'),(2,'auth','0001_initial','2019-07-03 22:30:46.858779'),(3,'admin','0001_initial','2019-07-03 22:30:47.226693'),(4,'admin','0002_logentry_remove_auto_add','2019-07-03 22:30:47.297599'),(5,'admin','0003_logentry_add_action_flag_choices','2019-07-03 22:30:47.308591'),(6,'contenttypes','0002_remove_content_type_name','2019-07-03 22:30:47.385156'),(7,'auth','0002_alter_permission_name_max_length','2019-07-03 22:30:47.456154'),(8,'auth','0003_alter_user_email_max_length','2019-07-03 22:30:47.488334'),(9,'auth','0004_alter_user_username_opts','2019-07-03 22:30:47.500570'),(10,'auth','0005_alter_user_last_login_null','2019-07-03 22:30:47.544093'),(11,'auth','0006_require_contenttypes_0002','2019-07-03 22:30:47.546870'),(12,'auth','0007_alter_validators_add_error_messages','2019-07-03 22:30:47.559996'),(13,'auth','0008_alter_user_username_max_length','2019-07-03 22:30:47.691075'),(14,'auth','0009_alter_user_last_name_max_length','2019-07-03 22:30:47.752112'),(15,'auth','0010_alter_group_name_max_length','2019-07-03 22:30:47.775587'),(16,'auth','0011_update_proxy_permissions','2019-07-03 22:30:47.786330'),(17,'bhutia','0001_initial','2019-07-03 22:30:47.790884'),(18,'bhutia','0002_auto_20190703_2221','2019-07-03 22:30:47.798568'),(19,'sessions','0001_initial','2019-07-03 22:30:47.823311'),(20,'bhutia','0003_auto_20190918_0640','2019-09-18 06:40:28.870645'),(21,'dictionaries','0001_initial','2019-10-22 00:36:04.963068'),(22,'dictionaries','0002_auto_20190703_2221','2019-10-22 00:36:05.025401'),(23,'dictionaries','0003_auto_20190918_0640','2019-10-22 00:36:05.034096'),(24,'dictionaries','0004_auto_20191022_0035','2019-10-22 00:36:05.038880'),(25,'authtoken','0001_initial','2019-11-07 17:11:54.441407'),(26,'authtoken','0002_auto_20160226_1747','2019-11-07 17:11:54.728975'),(27,'oauth2_provider','0001_initial','2019-11-07 17:47:06.175646'),(28,'oauth2_provider','0002_08_updates','2019-11-07 17:47:06.918658'),(29,'oauth2_provider','0003_auto_20160316_1503','2019-11-07 17:47:07.059516'),(30,'oauth2_provider','0004_auto_20160525_1623','2019-11-07 17:47:07.213280'),(31,'oauth2_provider','0005_auto_20170514_1141','2019-11-07 17:47:08.708398'),(32,'oauth2_provider','0006_auto_20171214_2232','2019-11-07 17:47:08.961668'),(33,'dictionaries','0005_english','2019-11-21 00:00:42.213370'),(34,'auth','0012_alter_user_first_name_max_length','2022-12-19 23:14:49.012715');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_session`
--

DROP TABLE IF EXISTS `django_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`),
  KEY `django_session_expire_date_a5c62663` (`expire_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_session`
--

LOCK TABLES `django_session` WRITE;
/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
INSERT INTO `django_session` VALUES ('7hzyq0dxeladqj23a8cl5hdo2zuuzmjn','ZWZmNGY4MjZiOGM2ODdjMGEwODM2ZmZmZGRiY2FhYzg0NTgwMDllODp7Il9hdXRoX3VzZXJfaWQiOiIzIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiI2M2M2ZDMyNGRkMjY1NzcxMTI5NzA3ZTY5ZWYwNjNiMzdlNWM0YmQyIn0=','2020-11-09 22:02:29.263729'),('auw54jplqhxwjy8fhqjfr3hzu236f0mi','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2019-12-05 00:01:02.888451'),('bieao2tcjzgetxyhqqf83dap98dxjwzn','MDZkODY4N2YzNmZiZGM4OTUxZjMyYzUwZmU5MGFiNTE3MWVlNDc1MDp7Il9hdXRoX3VzZXJfaWQiOiIxIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiIyMjI1ZTYyMGIxZTkyZDQyZGIxNTgxZmEwNzdlOWFiMjFlMzVmODhiIn0=','2019-07-20 21:57:39.835215'),('bmydpt0lqefq1hremtuc1d2tugfb2082','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2020-11-30 05:35:28.972426'),('cekaqhfwjj6puwjb4htwz38wkxyau417','MDZkODY4N2YzNmZiZGM4OTUxZjMyYzUwZmU5MGFiNTE3MWVlNDc1MDp7Il9hdXRoX3VzZXJfaWQiOiIxIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiIyMjI1ZTYyMGIxZTkyZDQyZGIxNTgxZmEwNzdlOWFiMjFlMzVmODhiIn0=','2019-07-17 22:41:57.975824'),('cumri0scz6yfel44j05x8gw369zr6kl3','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2019-12-04 22:03:57.513152'),('fn04nlsrpf6lezu619ey7lrbzmhiyui8','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2019-09-30 20:55:33.412483'),('gbp4x32o4v0xa3a67q0n1g9p55xjvnhs','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2019-12-18 01:11:48.886570'),('gynh2dnd8p61wrzsk8qkojdkg6gwdakb','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2019-10-04 06:32:50.603750'),('k4libefn3vpeio341vf27l1qv8ln50w5','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2019-11-05 00:55:06.160355'),('t146r4r9ladepm2j2xychrxik6hazsx4','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2021-08-16 18:06:56.019445'),('t5sipm0kpq3h6f095gji131ry4lmnpmn','MDZkODY4N2YzNmZiZGM4OTUxZjMyYzUwZmU5MGFiNTE3MWVlNDc1MDp7Il9hdXRoX3VzZXJfaWQiOiIxIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiIyMjI1ZTYyMGIxZTkyZDQyZGIxNTgxZmEwNzdlOWFiMjFlMzVmODhiIn0=','2019-07-20 21:14:59.399030'),('u8qi8gemvsi1sjbugv7p0q0vhepl1i5o','MDZkODY4N2YzNmZiZGM4OTUxZjMyYzUwZmU5MGFiNTE3MWVlNDc1MDp7Il9hdXRoX3VzZXJfaWQiOiIxIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiIyMjI1ZTYyMGIxZTkyZDQyZGIxNTgxZmEwNzdlOWFiMjFlMzVmODhiIn0=','2019-07-30 06:39:55.347650'),('ukkz0u32jt6j8hro4a26t81lor02kx3x','MDZkODY4N2YzNmZiZGM4OTUxZjMyYzUwZmU5MGFiNTE3MWVlNDc1MDp7Il9hdXRoX3VzZXJfaWQiOiIxIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiIyMjI1ZTYyMGIxZTkyZDQyZGIxNTgxZmEwNzdlOWFiMjFlMzVmODhiIn0=','2019-07-17 22:32:32.905115'),('xalbrluqtjibbksjcddo26yh3rk5cpal','ZWZmNGY4MjZiOGM2ODdjMGEwODM2ZmZmZGRiY2FhYzg0NTgwMDllODp7Il9hdXRoX3VzZXJfaWQiOiIzIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiI2M2M2ZDMyNGRkMjY1NzcxMTI5NzA3ZTY5ZWYwNjNiMzdlNWM0YmQyIn0=','2020-11-10 17:04:12.691852'),('ztpy4aio4jpdogfndo0qqnde2ljhq472','MzE5YWNmOTc3MjgxMTk4ZGEzNWZlZTRiN2M5NTcxM2JjMWQ2YzQ3ZDp7Il9hdXRoX3VzZXJfaWQiOiIyIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiJmMTNmYWU1Y2Y4MWFkMzQxYzlhNmZlOTcyMjFiMmU4YmE0NTRlYjhkIn0=','2020-11-10 18:31:29.815411');
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `English`
--

DROP TABLE IF EXISTS `English`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `English` (
  `entry_id` mediumint(9) NOT NULL,
  `word` varchar(50) DEFAULT NULL,
  `definition` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `English`
--

LOCK TABLES `English` WRITE;
/*!40000 ALTER TABLE `English` DISABLE KEYS */;
INSERT INTO `English` VALUES (1,'rencontre','Same as Rencounter, n.'),(2,'anomura','A group of decapod Crustacea, of which the hermit crab in an example.'),(3,'teneriffe','A white wine resembling Madeira in taste, but more tart, produced in Teneriffe, one of the Canary Islands; -- called also Vidonia.'),(4,'wrongless','Not wrong; void or free from wrong. [Obs.] -- Wrong\"less*ly, adv. [Obs.] Sir P. Sidney.'),(5,'egriot','A kind of sour cherry. Bacon.'),(6,'spectatrix','A female beholder or looker-on. \"A spectatress of the whole scene.\" Jeffrey.'),(7,'expurgatorial','Tending or serving to expurgate; expurgatory. Milman.'),(8,'butcher','1. One who slaughters animals, or dresses their flesh for market; one whose occupation it is to kill animals for food. '),(9,'apogeal','Apogean.'),(10,'quinic','Pertaining to, derived from, or connected with, quinine and related compounds; specifically, designating a nonnitrogenous acid obtained from cinchona bark, coffee, beans, etc., as a white crystalline substance. [Written also chinic, kinic.]'),(11,'automatical','1. Having an inherent power of action or motion. Nothing can be said to be automatic. Sir H. Davy. 2. Pertaining to, or produced by, an automaton; of the nature of an automaton; self-acting or self-regulating under fixed conditions; -- esp. applied to machinery or devices in which certain things formerly or usually done by hand are done by the machine or device itself; as, the automatic feed of a lathe; automatic gas lighting; an automatic engine or switch; an automatic mouse. 3. Not voluntary; not depending on the will; mechanical; as, automatic movements or functions. Unconscious or automatic reasoning. H. Spenser. Automatic arts, such economic arts or manufacture as are carried on by self-acting machinery. Ure.'),(12,'neritina','A genus including numerous species of shells resembling Nerita in form. They mostly inhabit brackish water, and are often delicately tinted.'),(13,'flabellation','The act of keeping fractured limbs cool by the use of a fan or some other contrivance. Dunglison.'),(14,'windgall','A soft tumor or synovial swelling on the fetlock joint of a horse; -- so called from having formerly been supposed to contain air.'),(15,'membranaceous','1. Same as Membranous. Arbuthnot. 2. (Bot.)  Thin and rather soft or pliable, as the leaves of the rose, peach tree, and aspen poplar.'),(16,'tithingman','1. (O. Eng. Law)  The chief man of a tithing; a headborough; one elected to preside over the tithing. 2. (Law)  A peace officer; an under constable. 3. A parish officer elected annually to preserve good order in the church during divine service, to make complaint of any disorderly conduct, and to enforce the observance of the Sabbath. [Local, U.S.]'),(17,'prosenchyma','A general term applied to the tissues formed of elongated cells, especially those with pointed or oblique extremities, as the principal cells of ordinary wood.'),(18,'pneumatometer','An instrument for measuring the amount of force exerted by the lungs in respiration.'),(19,'expeller','One who. or that which, expels.'),(20,'platy','Like a plate; consisting of plates.'),(21,'sea purse','The horny egg case of a skate, and of certain sharks.'),(22,'tchawytcha','The quinnat salmon. [Local, U.S.]'),(23,'shuffler','1. One who shuffles. 2. (Zoöl.)  Either one of the three common American scaup ducks. See Scaup duck, under Scaup.'),(24,'quinaldine','A colorless liquid of a slightly pungent odor, C9H6N.CH3, first obtained as a condensation product of aldehyde and aniline, and regarded as a derivative of quinoline; -- called also methyl quinoline. [Written also chinaldine.]'),(25,'interdependency','Mutual dependence; as, interdependency of interests. De Quincey.'),(26,'bursarship','The office of a bursar.'),(27,'palewise','In the manner of a pale or pales; by perpendicular lines or divisions; as, to divide an escutcheon palewise.'),(28,'sond','That which is sent; a message or messenger; hence, also, a visitation of providence; an affliction or trial. [Obs.] Ye have enough, parde, of Goddes sond. Chaucer.'),(29,'unacceptability','The quality of being unacceptable; unacceptableness.'),(30,'underpay','To pay inadequately.'),(31,'ophelic','Of, pertaining to, or designating, a substance (called ophelic acid) extracted from a plant (Ophelia) of the Gentian family as a bitter yellowish sirup, used in India as a febrifuge and tonic.'),(32,'aft','Near or towards the stern of a vessel; astern; abaft.'),(33,'tetragonal','1. (Geom.)  Of or pertaining to a tetragon; having four angles or sides; thus, the square, the parallelogram, the rhombus, and the trapezium are tetragonal fingers. 2. (Bot.)  Having four prominent longitudinal angles. 3. (Crystallog.)  Designating, or belonging to, a certain system of crystallization; dimetric. See Tetragonal system, under Crystallization.'),(34,'baccara','A French game of cards, played by a banker and punters.'),(35,'anencephalic','Without a brain; brainless. Todd & B.'),(36,'attle','Rubbish or refuse consisting of broken rock containing little or no ore. Weale.'),(37,'unprejudiced','1. Not prejudiced; free from undue bias or prepossession; not preoccupied by opinion; impartial; as, an unprejudiced mind; an unprejudiced judge. 2. Not warped or biased by prejudice; as, an unprejudiced judgment. -- Un*prej\"u*diced*ness, n. V. Knox.'),(38,'gyrostatics','The doctrine or theory of the gyrostat, or of the phenomena of rotating bodies.'),(39,'defoliation','The separation of ripened leaves from a branch or stem; the falling or shedding of the leaves.'),(40,'fragile','Easily broken; brittle; frail; delicate; easily destroyed. The state of ivy is tough, and not fragile. Bacon. Syn. -- Brittle; infirm; weak; frail; frangible; slight. -- Frag\"ile*ly, adv.'),(41,'marcobrunner','A celebrated Rhine wine.'),(42,'insufficiently','In an insufficient manner or degree; unadequately.'),(43,'pelerine','A woman\'s cape; especially, a fur cape that is longer in front than behind. PELE\'S HAIR Pe\"le\'s hair. [After a Hawaiian goddess associated with the crater Kilauea.]  Glass threads or fibers formed by the wind from bits blown from frothy lava or from the tips of lava jets or from bits of liquid lava thrown into the air. It often collects in thick masses resembling tow.'),(44,'bepurple','To tinge or dye with a purple color.'),(45,'biograph','1.  An animated picture machine for screen projection; a cinematograph. 2.  [Cf. Biography.] A biographical sketch. [Rare]'),(46,'suprascalpulary','Situated above, or on the anterior side of, the scapula.'),(47,'pectorally','As connected with the breast.'),(48,'larghetto','Somewhat slow or slowly, but not so slowly as largo, and rather more so than andante.'),(49,'choric','Of or pertaining to a chorus. I remember a choric ode in the Hecuba. Coleridge.'),(50,'sheathless','Without a sheath or case for covering; unsheathed.'),(51,'kind-heartedness','The state or quality of being kind-hearted; benevolence.'),(52,'reluctivity','Specific reluctance.'),(53,'obviation','The act of obviating, or the state of being obviated.'),(54,'inapt','Unapt; not apt; unsuitable; inept. -- In*apt\"ly, adv. -- In*apt\"ness, n.'),(55,'endomorph','A crystal of one species inclosed within one of another, as one of rutile inclosed in quartz.'),(56,'inexertion','Want of exertion; want of effort; defect of action; indolence; laziness.'),(57,'lowish','Somewhat low. [Colloq.] Richardson.'),(58,'unconsequential','Inconsequential. Johnson.'),(59,'taglock','An entangled lock, as of hair or wool. Nares.'),(60,'bauble','1. A trifling piece of finery; a gewgaw; that which is gay and showy without real value; a cheap, showy plaything. The ineffective bauble of an Indian pagod. Sheridan. 2. The fool\'s club. [Obs.] \"A fool\'s bauble was a short stick with a head ornamented with an ass\'s ears fantastically carved upon it.\" Nares.'),(61,'postcommunion','1. (Ch. of Eng. & Prot. Epis. Ch.)  The concluding portion of the communion service. 2. (R. C. Ch.)  A prayer or prayers which the priest says at Mass, after the ablutions.'),(62,'antimonite','1. (Chem.)  A compound of antimonious acid and a base or basic radical. 2. (Min.)  Stibnite.'),(63,'diprotodon','An extinct Quaternary marsupial from Australia, about as large as the hippopotamus; -- so named because of its two large front teeth. See Illustration in Appendix.'),(64,'volary','See Volery. [Obs.]'),(65,'insanable','Not capable of being healed; incurable; irremediable.'),(66,'appeaser','One who appeases; a pacifier.'),(67,'statically','In a statical manner.'),(68,'forcibleness','The quality of being forcible.'),(69,'fingle-fangle','A trifle. [Low] Hudibras.'),(70,'particulate','To particularize. [Obs.]\n\n1. Having the form of a particle. 2. Referring to, or produced by, particles, such as dust, minute germs, etc. [R.] The smallpox is a particulate disease. Tyndall.'),(71,'abjudge','To take away by judicial decision. [R.]'),(72,'buskin','1. A strong, protecting covering for the foot, coming some distance up the leg. The hunted red deer\'s undressed hide Their hairy buskins well supplied. Sir W. Scott. 2. A similar covering for the foot and leg, made with very thick soles, to give an appearance of elevation to the stature; -- worn by tragic actors in ancient Greece and Rome. Used as a symbol of tragedy, or the tragic drama, as distinguished from comedy. Great Fletcher never treads in buskins here, No greater Jonson dares in socks appear. Dryden.'),(73,'drabbler','A piece of canvas fastened by lacing to the bonnet of a sail, to give it a greater depth, or more drop.'),(74,'undecane','A liquid hydrocarbon, C11H24, of the methane series, found in petroleum; -- so called from its containing eleven carbon atoms in the molecule.'),(75,'averment','1. The act of averring, or that which is averred; affirmation; positive assertion. Signally has this averment received illustration in the course of recent events. I. Taylor. 2. Verification; establishment by evidence. Bacon. 3. (Law)  A positive statement of facts; an allegation; an offer to justify or prove what is alleged. Note: In any stage of pleadings, when either party advances new matter, he avers it to be true, by using this form of words: \"and this he is ready to verify.\" This was formerly called an averment. It modern pleading, it is termed a verification. Blackstone.'),(76,'coadunate','United at the base, as contiguous lobes of a leaf.'),(77,'arithmetical','Of or pertaining to arithmetic; according to the rules or method of arithmetic. Arithmetical complement of a logarithm. See Logarithm. -- Arithmetical mean. See Mean. -- Arithmetical progression. See Progression. -- Arithmetical proportion. See Proportion.'),(78,'postage','The price established by law to be paid for the conveyance of a letter or other mailable matter by a public post. Postage stamp, a government stamp required to be put upon articles sent by mail in payment of the postage, esp. an adhesive stamp issued and sold for that purpose.'),(79,'insafety','Insecurity; danger. [Obs.]'),(80,'prudential','1. Proceeding from, or dictated or characterized by, prudence; prudent; discreet; sometimes, selfish or pecuniary as distinguished from higher motives or influences; as, prudential motives. \" A prudential line of conduct.\" Sir W. Scott. 2. Exercising prudence; discretionary; advisory; superintending or executive; as, a prudential committee.\n\nThat which relates to or demands the exercise of, discretion or prudence; -- usually in the pl. Many stanzas, in poetic measures, contain rules relating to common prudentials as well as to religion. I. Watts.'),(81,'ubiquitariness','Quality or state of being ubiquitary, or ubiquitous. [R.] Fuller.'),(82,'superfineness','The state of being superfine.'),(83,'needlework','1. Work executed with a needle; sewed work; sewing; embroidery; also, the business of a seamstress. 2. The combination of timber and plaster making the outside framework of some houses.'),(84,'mantis','Any one of numerous species of voracious orthopterous insects of the genus Mantis, and allied genera. They are remarkable for their slender grotesque forms, and for holding their stout anterior legs in a manner suggesting hands folded in prayer. The common American species is M. Carolina. Mantis shrimp. (Zoöl.) See Sguilla.'),(85,'progenitorship','The state of being a progenitor.'),(86,'hydroid','Related to, or resembling, the hydra; of or pertaining to the Hydroidea. -- n.  One of the Hydroideas.'),(87,'cryptographer','One who writes in cipher, or secret characters.'),(88,'crosshatch','To shade by means of crosshatching.'),(89,'wailful','Sorrowful; mournful. \" Like wailful widows.\" Spenser. \"Wailful sonnets.\" Shak.'),(90,'vexed','1. Annoyed; harassed; troubled. 2. Much debated or contested; causing discussion; as, a vexed question.'),(91,'acquaintedness','State of being acquainted; degree of acquaintance. [R.] Boyle.'),(92,'scabrousness','The quality of being scabrous.'),(93,'helminthite','One of the sinuous tracks on the surfaces of many stones, and popularly considered as worm trails.'),(94,'sol','1. The sun. 2. (Alchem.)  Gold; -- so called from its brilliancy, color, and value. Chaucer.\n\n(a) A syllable applied in solmization to the note G, or to the fifth tone of any diatonic scale. (b) The tone itself.\n\n1. A sou. 2. A silver and gold coin of Peru. The silver sol is the unit of value, and is worth about 68 cents.'),(95,'procreate','To generate and produce; to beget; to engender.'),(96,'aliped','Wing-footed, as the bat. -- n.  An animal whose toes are connected by a membrane, serving for a wing, as the bat.'),(97,'bucranium','A sculptured ornament, representing an ox skull adorned with wreaths, etc.'),(98,'brusk','Same as Brusque.'),(99,'beckon','To make a significant sign to; hence, to summon, as by a motion of the hand. His distant friends, he beckons near. Dryden. It beckons you to go away with it. Shak.\n\nA sign made without words; a beck. \"At the first beckon.\" Bolingbroke. BECK\'S SCALE Beck\'s scale.  A hydrometer scale on which the zero point corresponds to sp. gr. 1.00, and the 30º-point to sp. gr. 0.85. From these points the scale is extended both ways, all the degrees being of equal length.'),(100,'homogamous','Having all the flowers alike; -- said of such composite plants as Eupatorium, and the thistels.'),(101,'pod','1. A bag; a pouch. [Obs. or Prov. Eng.] Tusser. 2. (Bot.)  A capsule of plant, especially a legume; a dry dehiscent fruit. See Illust. of Angiospermous. 3. (Zoöl.)  A considerable number of animals closely clustered together; -- said of seals. Pod auger, or pod bit, an auger or bit the channel of which is straight instead of twisted.\n\nTo swell; to fill; also, to produce pods.'),(102,'subsellium','One of the stalls of the lower range where there are two ranges. See Illust. of Stall.'),(103,'kholah','The Indian jackal.'),(104,'isonitroso-','A combining from (also used adjectively), signifying: Pertaining to, or designating, the characteristic, nitrogenous radical, NOH, called the isonitroso group.'),(105,'villanel','A ballad. [Obs.] Cotton.'),(106,'calced','Wearing shoes; calceated; -- in distintion from discalced or barefooted; as the calced Carmelites.'),(107,'disinteress','To deprive or rid of interest in, or regard for; to disengage. [Obs.]'),(108,'theologize','To render theological; to apply to divinity; to reduce to a system of theology. School divinity was but Aristotle\'s philosophy theologized. Glanvill.\n\nTo frame a system of theology; to theorize or speculate upon theological subjects.'),(109,'cornuted','1. Bearing horns; horned; horn-shaped. 2. Cuckolded. [R.] \"My being cornuted.\" LEstrange.'),(110,'stative','Of or pertaining to a fixed camp, or military posts or quarters. [Obs. or R.]'),(111,'there-anent','Concerning that. [Scot.]'),(112,'tenebrose','Characterized by darkness or gloom; tenebrous.'),(113,'omohyoid','Of or pertaining to the shoulder and the hyoid bone; as, the omohyoid muscle.'),(114,'impasture','To place in a pasture; to foster. [R.] T. Adams.'),(115,'perlitic','Relating to or resembling perlite, or pearlstone; as, the perlitic structure of certain rocks. See Pearlite.'),(116,'paramylum','A substance resembling starch, found in the green frothy scum formed on the surface of stagnant water.'),(117,'underproportioned','Of inadequate or inferior proportions; small; poor. Scanty and underproportioned returns of civility. Collier.'),(118,'ty-all','Something serving to tie or secure. [Obs.] Latimer.'),(119,'bombardo','Originally, a deep-toned instrument of the oboe or bassoon family; thence, a bass reed stop on the organ. The name bombardon is now given to a brass instrument, the lowest of the saxhorns, in tone resembling the ophicleide. Grove.'),(120,'unmechanize','1. To undo the mechanism of; to unmake; as, to unmechanize a structure. [Obs.] Sterne.'),(121,'liverwort','1. A ranunculaceous plant (Anemone Hepatica) with pretty white or bluish flowers and a three-lobed leaf; -- called also squirrel cups. 2. A flowerless plant (Marchantia polymorpha), having an irregularly lobed, spreading, and forking frond. Note: From this plant many others of the same order (Hepaticæ) have been vaguely called liverworts, esp. those of the tribe Marchantiaceæ. See Illust. of Hepatica.'),(122,'labellum','1. (Bot.)  The lower or apparently anterior petal of an orchidaceous flower, often of a very curious shape. 2. (Zoöl.)  A small appendage beneath the upper lip or labrum of certain insects.'),(123,'melodrame','Melodrama.'),(124,'disgraduate','To degrade; to reduce in rank. [Obs.] Tyndale.'),(125,'parasitical','1. Of the nature of a parasite; fawning for food or favors; sycophantic. \"Parasitic preachers.\" Milton. 2. (Bot. & Zoöl.)  Of or pertaining to parasites; living on, or deriving nourishment from, some other living animal or plant. See Parasite, 2 & 3. Parasitic gull, Parasitic jager. (Zoöl.) See Jager. -- Par`a*sit\"ic*al*ly, adv. -- Par`a*sit\"ic*al*ness, n.'),(126,'were','To wear. See 3d Wear. [Obs.] Chaucer.\n\nA weir. See Weir. [Obs.] Chaucer. Sir P. Sidney.\n\nTo guard; to protect. [Obs.] Chaucer.\n\nThe imperfect indicative plural, and imperfect subjunctive singular and plural, of the verb be. See Be.\n\n1. A man. [Obs.] 2. A fine for slaying a man; the money value set upon a man\'s life; weregild. [Obs.] Every man was valued at a certain sum, which was called his were. Bosworth.'),(127,'chevet','The extreme end of the chancel or choir; properly the round or polygonal part.'),(128,'whitebeam','The common beam tree of England (Pyrus Aria); -- so called from the white, woolly under surface of the leaves.'),(129,'tlinkit','The Indians of a seafaring group of tribes of southern Alaska comprising the Koluschan stock. Previous to deterioration from contact with the whites they were the foremost traders of the northwest. They built substantial houses of cedar adorned with  totem poles, and were expert stone carvers and copper workers. Slavery, the potlatch, and the use of immense labrets were characteristic. Many now work in the salmon industry.'),(130,'bivouac','(a) The watch of a whole army by night, when in danger of surprise or attack. (b) An encampment for the night without tents or covering.\n\n(a) To watch at night or be on guard, as a whole army. (b) To encamp for the night without tents or covering.'),(131,'permeate','1. To pass through the pores or interstices of; to penetrate and pass through without causing rupture or displacement; -- applied especially to fluids which pass through substances of loose texture; as, water permeates sand. Woodward. 2. To enter and spread through; to pervade. God was conceived to be diffused throughout the whole world, to permeate and pervade all things. Cudworth.'),(132,'zincograph','A zinc plate prepared for printing by zincography; also, a print from such a plate.'),(133,'preceptory','Preceptive. \"A law preceptory.\" Anderson (1573).\n\nA religious house of the Knights Templars, subordinate to the temple or principal house of the order in London. See Commandery, n., 2.'),(134,'albugineous','Of the nature of, or resembling, the white of the eye, or of an egg; albuminous; -- a term applied to textures, humors, etc., which are perfectly white.'),(135,'romant','A romaunt. [Obs.]'),(136,'eu','A prefix used frequently in composition, signifying well, good, advantageous; -- the opposite of dys-.'),(137,'deletive','Adapted to destroy or obliterate. [R.] Evelyn.'),(138,'replicated','Folded over or backward; folded back upon itself; as, a replicate leaf or petal; a replicate margin of a shell.'),(139,'squamozygomatic','Of or pertaining to both the squamosal and zygomatic bones; -- applied to a bone, or a center of ossification, in some fetal skulls. -- n.  A squamozygomatic bone.'),(140,'breton','Of or relating to Brittany, or Bretagne, in France. -- n.  A native or inhabitant of Brittany, or Bretagne, in France; also, the ancient language of Brittany; Armorican.'),(141,'lid','1. That which covers the opening of a vessel or box, etc. ; a movable cover; as, the lid of a chest or trunk. 2. The cover of the eye; an eyelid. Shak. Tears, big tears, gushed from the rough soldier\'s lid. Byron. 3. (Bot.) (a) The cover of the spore cases of mosses. (b) A calyx which separates from the flower, and falls off in a single piece, as in the Australian Eucalypti. (c) The top of an ovary which opens transversely, as in the fruit of the purslane and the tree which yields Brazil nuts.'),(142,'slimsy','Flimsy; frail. [Colloq. U.S.]'),(143,'sociableness','The quality of being sociable.'),(144,'frogged','Provided or ornamented with frogs; as, a frogged coat. See Frog, n., 4. Ld. Lytton.'),(145,'fondant','A kind of soft sweetmeat made by boiling solutions to the point of crystallization, usually molded; as, cherry fondant.'),(146,'isomeride','An isomer. [R.]'),(147,'ethiopic','Of or relating to Ethiopia or the Ethiopians.\n\nThe language of ancient Ethiopia; the language of the ancient Abyssinian empire (in Ethiopia), now used only in the Abyssinian church. It is of Semitic origin, and is also called Geez.'),(148,'entrant edge','#NAME?'),(149,'gemmulation','See Gemmation.'),(150,'acidification','The act or process of acidifying, or changing into an acid.'),(151,'daddock','The rotten body of a tree. [Prov. Eng.] Wright.'),(152,'vegetation','1. The act or process of vegetating, or growing as a plant does; vegetable growth. 2. The sum of vegetable life; vegetables or plants in general; as, luxuriant vegetation. 3. (Med.)  An exuberant morbid outgrowth upon any part, especially upon the valves of the heart. Vegetation of salts (Old Chem.), a crystalline growth of an arborescent form.'),(153,'rathskeller','Orig., in Germany, the cellar or basement of the city hall, usually rented for use as a restaurant where beer is sold; hence, a beer saloon of the German type below the street level, where, usually, drinks are served only at tables and simple food may also be had; -- sometimes loosely used, in English, of what are essentially basement restaurants where liquors are served.'),(154,'maying','The celebrating of May Day. \"He met her once a-Maying.\" Milton.'),(155,'subpurchaser','A purchaser who buys from a purchaser; one who buys at second hand.'),(156,'rightfully','According to right or justice.'),(157,'iceland moss','A kind of lichen (Cetraria Icelandica) found from the Arctic regions to the North Temperate zone. It furnishes a nutritious jelly and other forms of food, and is used in pulmonary complaints as a demulcent.'),(158,'renovator','One who, or that which, renovates. Foster.'),(159,'gyreful','Abounding in gyres. [Obs.]'),(160,'topsman','1. The chief drover of those who drive a herd of cattle. P. Cyc. 2. The uppermost sawyer in a saw pit; a topman. Simmonds.'),(161,'aeriality','The state of being aërial; [R.] De Quincey.'),(162,'dormer window','A window pierced in a roof, and so set as to be vertical while the roof slopes away from it. Also, the gablet, or houselike structure, in which it is contained.'),(163,'expound','1. To lay open; to expose to view; to examine. [Obs.] He expounded both his pockets. Hudibras. 2. To lay open the meaning of; to explain; to clear of obscurity; to interpret; as, to expound a text of Scripture, a law, a word, a meaning, or a riddle. Expound this matter more fully to me. Bunyan.'),(164,'marrubium','A genus of bitter aromatic plants, sometimes used in medicine; hoarhound.'),(165,'pickpocket','One who steals purses or other articles from pockets. Bentley.'),(166,'silkness','Silkiness. [Obs.] B. Jonson.'),(167,'caul','1. A covering of network for the head, worn by women; also, a net. Spenser. 2. (Anat.)  The fold of membrane loaded with fat, which covers more or less of the intestines in mammals; the great omentum See Omentum. The caul serves for warming of the lower belly. Ray. 3. A part of the amnion, one of the membranes enveloping the fetus, which sometimes is round the head of a child at its birth. It is deemed lucky to be with a caul or membrane over the face. This caul is esteemed an infallible preservative against drowning . . . According to Chysostom, the midwives frequently sold it for magic uses. Grose. I was born with a caul, which was advertised for sale, in the newspapers, at the low price of fifteen guineas. Dickens.'),(168,'yive','To give. [Obs.] Chaucer.'),(169,'sparrowwort','An evergreen shrub of the genus Erica (E. passerina).'),(170,'abomasus','The fourth or digestive stomach of a ruminant, which leads from the third stomach omasum. See Ruminantia.');
/*!40000 ALTER TABLE `English` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_provider_accesstoken`
--

DROP TABLE IF EXISTS `oauth2_provider_accesstoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_provider_accesstoken` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `expires` datetime(6) NOT NULL,
  `scope` longtext NOT NULL,
  `application_id` bigint(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created` datetime(6) NOT NULL,
  `updated` datetime(6) NOT NULL,
  `source_refresh_token_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oauth2_provider_accesstoken_token_8af090f8_uniq` (`token`),
  UNIQUE KEY `source_refresh_token_id` (`source_refresh_token_id`),
  KEY `oauth2_provider_accesstoken_user_id_6e4c9a65_fk_auth_user_id` (`user_id`),
  KEY `oauth2_provider_accesstoken_application_id_b22886e1_fk` (`application_id`),
  CONSTRAINT `oauth2_provider_acce_source_refresh_token_e66fbc72_fk_oauth2_pr` FOREIGN KEY (`source_refresh_token_id`) REFERENCES `oauth2_provider_refreshtoken` (`id`),
  CONSTRAINT `oauth2_provider_accesstoken_application_id_b22886e1_fk` FOREIGN KEY (`application_id`) REFERENCES `oauth2_provider_application` (`id`),
  CONSTRAINT `oauth2_provider_accesstoken_user_id_6e4c9a65_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_provider_accesstoken`
--

LOCK TABLES `oauth2_provider_accesstoken` WRITE;
/*!40000 ALTER TABLE `oauth2_provider_accesstoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_provider_accesstoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_provider_application`
--

DROP TABLE IF EXISTS `oauth2_provider_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_provider_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) NOT NULL,
  `redirect_uris` longtext NOT NULL,
  `client_type` varchar(32) NOT NULL,
  `authorization_grant_type` varchar(32) NOT NULL,
  `client_secret` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `skip_authorization` tinyint(1) NOT NULL,
  `created` datetime(6) NOT NULL,
  `updated` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id` (`client_id`),
  KEY `oauth2_provider_application_client_secret_53133678` (`client_secret`),
  KEY `oauth2_provider_application_user_id_79829054_fk_auth_user_id` (`user_id`),
  CONSTRAINT `oauth2_provider_application_user_id_79829054_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_provider_application`
--

LOCK TABLES `oauth2_provider_application` WRITE;
/*!40000 ALTER TABLE `oauth2_provider_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_provider_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_provider_grant`
--

DROP TABLE IF EXISTS `oauth2_provider_grant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_provider_grant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `expires` datetime(6) NOT NULL,
  `redirect_uri` varchar(255) NOT NULL,
  `scope` longtext NOT NULL,
  `application_id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime(6) NOT NULL,
  `updated` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oauth2_provider_grant_code_49ab4ddf_uniq` (`code`),
  KEY `oauth2_provider_grant_application_id_81923564_fk` (`application_id`),
  KEY `oauth2_provider_grant_user_id_e8f62af8_fk_auth_user_id` (`user_id`),
  CONSTRAINT `oauth2_provider_grant_application_id_81923564_fk` FOREIGN KEY (`application_id`) REFERENCES `oauth2_provider_application` (`id`),
  CONSTRAINT `oauth2_provider_grant_user_id_e8f62af8_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_provider_grant`
--

LOCK TABLES `oauth2_provider_grant` WRITE;
/*!40000 ALTER TABLE `oauth2_provider_grant` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_provider_grant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_provider_refreshtoken`
--

DROP TABLE IF EXISTS `oauth2_provider_refreshtoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_provider_refreshtoken` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `access_token_id` bigint(20) DEFAULT NULL,
  `application_id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime(6) NOT NULL,
  `updated` datetime(6) NOT NULL,
  `revoked` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `access_token_id` (`access_token_id`),
  UNIQUE KEY `oauth2_provider_refreshtoken_token_revoked_af8a5134_uniq` (`token`,`revoked`),
  KEY `oauth2_provider_refreshtoken_application_id_2d1c311b_fk` (`application_id`),
  KEY `oauth2_provider_refreshtoken_user_id_da837fce_fk_auth_user_id` (`user_id`),
  CONSTRAINT `oauth2_provider_refr_access_token_id_775e84e8_fk_oauth2_pr` FOREIGN KEY (`access_token_id`) REFERENCES `oauth2_provider_accesstoken` (`id`),
  CONSTRAINT `oauth2_provider_refreshtoken_application_id_2d1c311b_fk` FOREIGN KEY (`application_id`) REFERENCES `oauth2_provider_application` (`id`),
  CONSTRAINT `oauth2_provider_refreshtoken_user_id_da837fce_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_provider_refreshtoken`
--

LOCK TABLES `oauth2_provider_refreshtoken` WRITE;
/*!40000 ALTER TABLE `oauth2_provider_refreshtoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_provider_refreshtoken` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-02 11:05:34
