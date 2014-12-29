-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 29, 2014 at 02:45 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `flight_scheduler`
--

-- --------------------------------------------------------

--
-- Table structure for table `airports`
--

CREATE TABLE IF NOT EXISTS `airports` (
  `airport_id` int(3) NOT NULL,
  `airport_name` varchar(50) NOT NULL,
  `mct` int(5) NOT NULL,
  `xpos` int(5) NOT NULL,
  `ypos` int(5) NOT NULL,
  PRIMARY KEY (`airport_id`),
  UNIQUE KEY `airport_name` (`airport_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `airports`
--

INSERT INTO `airports` (`airport_id`, `airport_name`, `mct`, `xpos`, `ypos`) VALUES
(1, 'LAGOS', 10, 70, 400),
(2, 'ILORIN', 10, 250, 150),
(3, 'IBADAN', 10, 250, 250),
(4, 'ABUJA', 10, 550, 150),
(5, 'ASABA', 10, 450, 350),
(6, 'AKURE', 10, 700, 200),
(7, 'BAUCHI', 10, 700, 50),
(8, 'MINNA', 10, 150, 50);

-- --------------------------------------------------------

--
-- Table structure for table `flights`
--

CREATE TABLE IF NOT EXISTS `flights` (
  `origin` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `departure` varchar(8) NOT NULL,
  `arrival` varchar(8) NOT NULL,
  KEY `origin` (`origin`,`destination`),
  KEY `destination` (`destination`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flights`
--

INSERT INTO `flights` (`origin`, `destination`, `departure`, `arrival`) VALUES
('LAGOS', 'ILORIN', '07:15:00', '08:00:00'),
('LAGOS', 'ILORIN', '15:00:00', '15:40:00'),
('ILORIN', 'LAGOS', '17:30:00', '18:10:00'),
('ILORIN', 'LAGOS', '10:00:00', '10:40:00'),
('IBADAN', 'ABUJA', '08:00:00', '09:10:00'),
('ABUJA', 'IBADAN', '16:00:00', '17:15:00'),
('ILORIN', 'ABUJA', '08:30:00', '09:30:00'),
('ILORIN', 'ABUJA', '16:20:00', '17:20:00'),
('ABUJA', 'ILORIN', '16:00:00', '17:00:00'),
('LAGOS', 'ASABA', '11:50:00', '12:50:00'),
('ASABA', 'LAGOS', '13:20:00', '14:20:00'),
('ABUJA', 'ASABA', '15:30:00', '16:30:00'),
('ASABA', 'ABUJA', '17:00:00', '18:00:00'),
('ABUJA', 'AKURE', '12:15:00', '13:15:00'),
('AKURE', 'ABUJA', '13:45:00', '14:45:00'),
('ABUJA', 'BAUCHI', '09:00:00', '10:00:00'),
('BAUCHI', 'ABUJA', '10:30:00', '11:30:00'),
('MINNA', 'LAGOS', '08:30:00', '09:30:00'),
('ABUJA', 'MINNA', '07:30:00', '08:00:00'),
('MINNA', 'ABUJA', '17:50:00', '18:20:00'),
('MINNA', 'ILORIN', '08:30:00', '09:30:00'),
('ILORIN', 'MINNA', '16:20:00', '17:20:00');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `flights`
--
ALTER TABLE `flights`
  ADD CONSTRAINT `flights_ibfk_1` FOREIGN KEY (`origin`) REFERENCES `airports` (`airport_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `flights_ibfk_2` FOREIGN KEY (`destination`) REFERENCES `airports` (`airport_name`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
