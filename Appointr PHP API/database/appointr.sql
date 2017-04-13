-- MySQL dump 10.13  Distrib 5.5.54, for debian-linux-gnu (x86_64)
--
-- Host: 0.0.0.0    Database: appointr
-- ------------------------------------------------------
-- Server version	5.5.54-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `appointr`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `appointr` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `appointr`;

--
-- Table structure for table `accounts_faculty`
--

DROP TABLE IF EXISTS `accounts_faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts_faculty` (
  `user_id` int(6) NOT NULL AUTO_INCREMENT,
  `departments` enum('CSE','ECE','MME','MTH','PHY','HSS','OTHERS') NOT NULL,
  `faculty_name` varchar(50) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `room_no` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1017 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts_faculty`
--

LOCK TABLES `accounts_faculty` WRITE;
/*!40000 ALTER TABLE `accounts_faculty` DISABLE KEYS */;
INSERT INTO `accounts_faculty` VALUES (1000,'OTHERS','Test Faculty','ex','ex','0000'),(1007,'CSE','Mukesh K Jadon','jadon@lnmiit.ac.in','jadon','C1002'),(1008,'CSE','Nirmal Kumar Sivaraman','nirmal.sivaraman@lnmiit.ac.in','nirmal','C1003'),(1009,'CSE','Preety Singh','preety@lnmiit.ac.in','preety','C1004'),(1010,'CSE','Ravi Prakash Gorti','rgorthi@lnmiit.ac.in','ravi','C1005'),(1011,'CSE','Vikas Bajpai','vikas@lnmiit.ac.in','vikas','C1007'),(1012,'ECE','Sandeep Saini','sandeep.saini@lnmiit.ac.in','sandeep','E1002'),(1013,'PHY','Anjishnu Sarkar','anjishnu@lnmiit.ac.in','anjishnu','P1005'),(1014,'MTH','Ajit Patel','apatel@lnmiit.ac.in','ajit','M1004'),(1015,'HSS','Narendra Kumar','narendra@lnmiit.ac.in','narendra','H1004'),(1016,'MME','Vikram Sharma','vikram.sharma@lnmiit.ac.in','vikram','M1054');
/*!40000 ALTER TABLE `accounts_faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts_student`
--

DROP TABLE IF EXISTS `accounts_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts_student` (
  `user_id` int(6) NOT NULL AUTO_INCREMENT,
  `student_name` varchar(50) NOT NULL,
  `roll_no` varchar(8) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `roll_no` (`roll_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1015 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts_student`
--

LOCK TABLES `accounts_student` WRITE;
/*!40000 ALTER TABLE `accounts_student` DISABLE KEYS */;
INSERT INTO `accounts_student` VALUES (1000,'Test Student','SH0000','ex','ex'),(1012,'Shubham Mangal','15UCS138','15UCS138@lnmiit.ac.in','shubham'),(1013,'Arunima Mangal','15UCS025','15UCS025@lnmiit.ac.in','arunima'),(1014,'Neha Kumari','14UCC021','14UCC021@lnmiit.ac.in','neha');
/*!40000 ALTER TABLE `accounts_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_request`
--

DROP TABLE IF EXISTS `appointment_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment_request` (
  `request_id` int(6) NOT NULL AUTO_INCREMENT,
  `faculty_id` int(6) NOT NULL,
  `student_id` int(6) NOT NULL,
  `request_date` double NOT NULL,
  `request_time` varchar(5) DEFAULT NULL,
  `reason` varchar(100) DEFAULT NULL,
  `urgent` tinyint(1) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `details` varchar(100) DEFAULT NULL,
  `modify_request` tinyint(1) NOT NULL DEFAULT '0',
  `isClose` int(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `uq_1` (`faculty_id`,`student_id`,`isClose`),
  KEY `fk_2` (`student_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`faculty_id`) REFERENCES `accounts_faculty` (`user_id`),
  CONSTRAINT `fk_2` FOREIGN KEY (`student_id`) REFERENCES `accounts_student` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1033 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_request`
--

LOCK TABLES `appointment_request` WRITE;
/*!40000 ALTER TABLE `appointment_request` DISABLE KEYS */;
INSERT INTO `appointment_request` VALUES (1027,1000,1000,1493085960000,NULL,'Project Work',1,-1,NULL,0,1),(1028,1000,1000,1493169180000,NULL,'reason ',1,-1,NULL,0,1028),(1029,1000,1000,1493206920000,NULL,'hhb',1,0,NULL,0,0),(1031,1011,1012,1493157600000,NULL,'Project Verification',1,0,NULL,0,0),(1032,1008,1012,1493286480000,NULL,'Oops end term discussion',0,0,NULL,0,0);
/*!40000 ALTER TABLE `appointment_request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-24 18:53:15
