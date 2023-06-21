-- MariaDB dump 10.19  Distrib 10.6.12-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: prodaja_artikala_2
-- ------------------------------------------------------
-- Server version	10.6.12-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `artikal`
--

DROP TABLE IF EXISTS `artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artikal` (
  `idA` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) DEFAULT NULL,
  `opis` varchar(45) DEFAULT NULL,
  `cena` int(11) DEFAULT NULL,
  `popust` int(11) DEFAULT NULL,
  `idKat` int(11) DEFAULT NULL,
  `idKor` int(11) DEFAULT NULL,
  PRIMARY KEY (`idA`),
  KEY `FK_idKor_idx` (`idKor`),
  KEY `FK_idKat_idx` (`idKat`),
  CONSTRAINT `FK_idKat` FOREIGN KEY (`idKat`) REFERENCES `kategorija` (`idK`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_idKor` FOREIGN KEY (`idKor`) REFERENCES `korisnik` (`idK`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikal`
--

LOCK TABLES `artikal` WRITE;
/*!40000 ALTER TABLE `artikal` DISABLE KEYS */;
INSERT INTO `artikal` VALUES (1,'ves masina','dobra ves masina',20000,10,3,1),(2,'cips','dobar cips',100,0,1,2),(3,'koka kola','dobra koka kola',150,0,2,4),(4,'pepsi','dobar pepsi',120,0,2,3);
/*!40000 ALTER TABLE `artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kategorija` (
  `idK` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) DEFAULT NULL,
  `idNatK` int(11) DEFAULT NULL,
  PRIMARY KEY (`idK`),
  KEY `FK_idNatK_idx` (`idNatK`),
  CONSTRAINT `FK_idNatK` FOREIGN KEY (`idNatK`) REFERENCES `kategorija` (`idK`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (1,'hrana',NULL),(2,'pice',NULL),(3,'bela tehnika',NULL),(4,'posudje',NULL);
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korisnik` (
  `idK` int(11) NOT NULL AUTO_INCREMENT,
  `korisnickoIme` varchar(45) NOT NULL,
  `sifra` varchar(45) NOT NULL,
  `ime` varchar(45) DEFAULT NULL,
  `prezime` varchar(45) DEFAULT NULL,
  `adresa` varchar(45) DEFAULT NULL,
  `novac` int(11) DEFAULT NULL,
  `idG` int(11) DEFAULT NULL,
  PRIMARY KEY (`idK`),
  UNIQUE KEY `korisnickoIme_UNIQUE` (`korisnickoIme`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'luka','luka123','Luka','Sotra','adresa1',59000,1),(2,'isi','isi123','Isidora','Sotra','adresa1',20600,1),(3,'filip','filip123','Filip','Sotra','adresa3',4600,1),(4,'dejan','dejan123','Dejan','Sotra','adresa4',11800,1);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korpa` (
  `idKorpe` int(11) NOT NULL AUTO_INCREMENT,
  `ukupnaCena` int(11) DEFAULT NULL,
  `idKorisnik` int(11) DEFAULT NULL,
  PRIMARY KEY (`idKorpe`),
  KEY `FK_idKoris_idx` (`idKorisnik`),
  CONSTRAINT `FK_idKoris` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idK`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korpa`
--

LOCK TABLES `korpa` WRITE;
/*!40000 ALTER TABLE `korpa` DISABLE KEYS */;
INSERT INTO `korpa` VALUES (1,0,1),(2,0,2),(3,0,3),(4,0,4);
/*!40000 ALTER TABLE `korpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavka`
--

DROP TABLE IF EXISTS `stavka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stavka` (
  `idS` int(11) NOT NULL AUTO_INCREMENT,
  `kolicina` int(11) DEFAULT NULL,
  `jedinicnaCena` int(11) DEFAULT NULL,
  `idA` int(11) DEFAULT NULL,
  `idKorpe` int(11) DEFAULT NULL,
  `idN` int(11) DEFAULT NULL,
  PRIMARY KEY (`idS`),
  KEY `FK_idKorpe_idx` (`idKorpe`),
  KEY `FK_idArtikla_idx` (`idA`),
  CONSTRAINT `FK_idArtikla` FOREIGN KEY (`idA`) REFERENCES `artikal` (`idA`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_idKorpe` FOREIGN KEY (`idKorpe`) REFERENCES `korpa` (`idKorpe`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavka`
--

LOCK TABLES `stavka` WRITE;
/*!40000 ALTER TABLE `stavka` DISABLE KEYS */;
INSERT INTO `stavka` VALUES (16,3,100,2,3,1),(17,1,100,2,3,2),(18,1,18000,1,4,3),(19,2,100,2,4,3);
/*!40000 ALTER TABLE `stavka` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-27  0:51:02
