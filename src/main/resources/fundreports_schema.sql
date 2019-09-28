-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: FundReports
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

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
-- Table structure for table `Cusip`
--

DROP TABLE IF EXISTS `Cusip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cusip` (
  `cusip` varchar(16) NOT NULL,
  `stock` varchar(16) NOT NULL,
  `company_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`cusip`),
  UNIQUE KEY `StockCusip_cusip_uindex` (`cusip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Filing`
--

DROP TABLE IF EXISTS `Filing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Filing` (
  `filingId` int(11) NOT NULL AUTO_INCREMENT,
  `fundId` int(11) NOT NULL COMMENT 'from Fund table',
  `filingDate` date NOT NULL,
  `filingType` varchar(64) NOT NULL COMMENT '13-F or something else',
  `reportDate` date DEFAULT NULL,
  PRIMARY KEY (`filingId`),
  KEY `Filing_Fund_fundId_fk` (`fundId`),
  CONSTRAINT `Filing_Fund_fundId_fk` FOREIGN KEY (`fundId`) REFERENCES `Fund` (`fundId`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1 COMMENT='Details on the filing for a fund (whale)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Fund`
--

DROP TABLE IF EXISTS `Fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Fund` (
  `fundId` int(11) NOT NULL AUTO_INCREMENT,
  `fundName` varchar(128) NOT NULL,
  PRIMARY KEY (`fundId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COMMENT='Description for a Hedge Fund (Whale) that files 13-F reports';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Holding`
--

DROP TABLE IF EXISTS `Holding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Holding` (
  `fundId` int(11) NOT NULL,
  `filingId` int(11) NOT NULL,
  `holdingId` int(11) NOT NULL AUTO_INCREMENT,
  `cusip` varchar(64) NOT NULL,
  `stock` varchar(64) NOT NULL,
  `position` double NOT NULL,
  `numshares` int(11) NOT NULL,
  PRIMARY KEY (`holdingId`),
  UNIQUE KEY `Holding_fundId_filingId_cusip_pk` (`fundId`,`filingId`,`cusip`),
  KEY `Holding_Filing_filingId_fk` (`filingId`),
  CONSTRAINT `Holding_Filing_filingId_fk` FOREIGN KEY (`filingId`) REFERENCES `Filing` (`filingId`),
  CONSTRAINT `Holding_Fund_fundId_fk` FOREIGN KEY (`fundId`) REFERENCES `Fund` (`fundId`)
) ENGINE=InnoDB AUTO_INCREMENT=10845 DEFAULT CHARSET=latin1 COMMENT='Lists the various holdings in the filing';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-28 14:35:04
