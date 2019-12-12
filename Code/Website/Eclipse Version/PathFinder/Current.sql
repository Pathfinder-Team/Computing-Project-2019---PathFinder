-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 12, 2019 at 04:21 PM
-- Server version: 8.0.13-4
-- PHP Version: 7.2.24-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `4eyg55o51S`
--

-- --------------------------------------------------------

--
-- Table structure for table `point_to`
--

CREATE TABLE `point_to` (
  `point_from_id` int(11) NOT NULL,
  `point_to_id` int(11) NOT NULL,
  `point_weight` int(11) NOT NULL,
  `point_direction` varchar(500) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `point_to`
--

INSERT INTO `point_to` (`point_from_id`, `point_to_id`, `point_weight`, `point_direction`) VALUES
(1, 2, 3, 'left');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `point_to`
--
ALTER TABLE `point_to`
  ADD PRIMARY KEY (`point_from_id`),
  ADD KEY `fk_point_to_map points1_idx` (`point_from_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `point_to`
--
ALTER TABLE `point_to`
  ADD CONSTRAINT `fk_point_to_map points1` FOREIGN KEY (`point_from_id`) REFERENCES `map_points` (`current_point_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
