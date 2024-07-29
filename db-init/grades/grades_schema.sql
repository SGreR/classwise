-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: grades
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `abilities`
--

DROP TABLE IF EXISTS `abilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abilities` (
  `abilities_id` bigint NOT NULL AUTO_INCREMENT,
  `final_grade` double NOT NULL,
  `classperformance_id` bigint DEFAULT NULL,
  `speaking_id` bigint DEFAULT NULL,
  PRIMARY KEY (`abilities_id`),
  UNIQUE KEY `UK31emjaovsw76ryftd1csgb5n7` (`classperformance_id`),
  UNIQUE KEY `UKjn47ksy9i5n03c9g10wee6dba` (`speaking_id`),
  CONSTRAINT `FKkxnv4wouk51vtmtgk0kno9dek` FOREIGN KEY (`speaking_id`) REFERENCES `speaking` (`speaking_id`),
  CONSTRAINT `FKmo3cepl6wc0hgomp7pc9l9cd6` FOREIGN KEY (`classperformance_id`) REFERENCES `class_performance` (`classperformance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `abilities_skills`
--

DROP TABLE IF EXISTS `abilities_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abilities_skills` (
  `abilities_abilities_id` bigint NOT NULL,
  `skills_skill_id` bigint NOT NULL,
  PRIMARY KEY (`abilities_abilities_id`,`skills_skill_id`),
  UNIQUE KEY `UK9j1m143n7d1h6nyiva1lofyos` (`skills_skill_id`),
  CONSTRAINT `FK8ogtvxse0mexb5jk6nj5fae7` FOREIGN KEY (`abilities_abilities_id`) REFERENCES `abilities` (`abilities_id`),
  CONSTRAINT `FKnug5o77htbhbbwhubpwns3r66` FOREIGN KEY (`skills_skill_id`) REFERENCES `skill` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `class_performance`
--

DROP TABLE IF EXISTS `class_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_performance` (
  `classperformance_id` bigint NOT NULL AUTO_INCREMENT,
  `average_grade` double NOT NULL,
  `behavior_grade` int NOT NULL,
  `homework_grade` int NOT NULL,
  `participation_grade` int NOT NULL,
  `presence_grade` int NOT NULL,
  PRIMARY KEY (`classperformance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades` (
  `grades_id` bigint NOT NULL AUTO_INCREMENT,
  `course_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  `test_number` enum('FIRST','SECOND') DEFAULT NULL,
  `abilities_id` bigint DEFAULT NULL,
  PRIMARY KEY (`grades_id`),
  UNIQUE KEY `UKg23hv3y4835sh9y8414cnpmkq` (`abilities_id`),
  CONSTRAINT `FK6j6pmh4ccd6t5k4dl6nmr6cax` FOREIGN KEY (`abilities_id`) REFERENCES `abilities` (`abilities_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `skill_id` bigint NOT NULL AUTO_INCREMENT,
  `average_grade` double NOT NULL,
  `skill_name` enum('LISTENING','READING','USEOFENGLISH','WRITING') DEFAULT NULL,
  `teacher_grade` double NOT NULL,
  `test_grade` double NOT NULL,
  PRIMARY KEY (`skill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `speaking`
--

DROP TABLE IF EXISTS `speaking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `speaking` (
  `speaking_id` bigint NOT NULL AUTO_INCREMENT,
  `accuracy_grade` int NOT NULL,
  `average_grade` double NOT NULL,
  `language_range_grade` int NOT NULL,
  `language_use` int NOT NULL,
  `production_and_fluency_grade` int NOT NULL,
  `spoken_interaction_grade` int NOT NULL,
  PRIMARY KEY (`speaking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-28 16:05:48
