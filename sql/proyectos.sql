-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 08-11-2018 a las 14:47:54
-- Versión del servidor: 5.7.22-0ubuntu0.16.04.1
-- Versión de PHP: 7.0.30-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Data Base: `proyects`
--

-- --------------------------------------------------------



CREATE TABLE `proyects` 
(
  `cod` varchar(10) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `state` varchar(20) DEFAULT NULL,

  PRIMARY KEY (`cod`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `proyects` (`cod`, `title`, `description`, `state`) VALUES
('P001', 'Redesign of the website', 'Update the design and functionality of the existing website', 'in progress'),
('P002', 'Launch of new product', 'Develop and launch a new product to the market', 'on hold'),
('P003', 'Implementation of accounting software', 'Install and configure accounting software for the company', 'completed'),
('P004', 'Organization of business event', 'Plan and coordinate a business event for the presentation of new products', 'in progress');



CREATE TABLE `tasks` 
(
  `cod` varchar(10) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `cod_proyect` varchar(10) NOT NULL,
  `state` varchar(20) NOT NULL,

  PRIMARY KEY (`cod`),
  CONSTRAINT `fk_tasks_projects` FOREIGN KEY (`cod_proyect`) REFERENCES `proyects` (`cod`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `tasks` (`cod`, `title`, `description`, `cod_proyect`, `state`)
VALUES
(1, 'Design wireframes', 'Create wireframes for the new website design', 'P001' , 'in progress'),
(2, 'Write content', 'Create content for the website pages', 'P001' , 'not started'),
(3, 'Code website frontend', 'Code the website frontend using HTML, CSS and JS', 'P001' , 'not started'),
(4, 'Code website backend', 'Code the website backend using PHP and MySQL', 'P001', 'not started'),
(5, 'Create product mockups', 'Design mockups for the new product', 'P002', 'in progress'),
(6, 'Develop product features', 'Develop features for the new product', 'P002', 'not started'),
(7, 'Perform testing', 'Perform testing of the new product', 'P002', 'not started'),
(8, 'Train employees', 'Provide training to employees on the new accounting software', 'P003', 'in progress'),
(9, 'Migrate data', 'Migrate existing data to the new accounting software', 'P003', 'not started'),
(10, 'Plan event agenda', 'Plan the agenda for the business event', 'P004', 'in progress'),
(11, 'Contact suppliers', 'Contact suppliers for event materials', 'P004', 'not started'),
(12, 'Book venue', 'Book the venue for the event', 'P004', 'not started');



CREATE TABLE `comments` 
(
  `cod` varchar(10) NOT NULL,
  `cod_task` varchar(10) NOT NULL,
  `content` varchar(200) NOT NULL,

  PRIMARY Key (`cod`),
  CONSTRAINT `fk_comments_tasks` FOREIGN KEY (`cod_task`) REFERENCES `tasks` (`cod`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `comments` (`cod`, `cod_task`, `content`) VALUES
('1', '1', 'Great wireframes!'),
('2', '2', 'I am working on the content for this task.'),
('3', '3', 'I started working on the frontend.'),
('4', '3', 'I am having some trouble with this task. Need help!');



CREATE TABLE `teams` 
(
  `cod` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,

  PRIMARY Key (`cod`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 INSERT INTO `teams` (`cod`, `name`) VALUES
 ('A', 'Team 1'),
 ('B', 'Team 2'),
 ('C', 'Team 3');



CREATE TABLE `employees`
(
  `dni` varchar(10) NOT NULL,
  `name` varchar(200) NOT NULL,
  `lastName`varchar(200) NOT NULL,
  `phoneNumber` varchar(9) NOT NULL,
  `address` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,

  PRIMARY Key (`dni`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `employees` (`dni`, `name`, `lastName`, `phoneNumber`, `address`, `email`) VALUES 
('Admin', 'Admin', '', '', '', 'Admin'),
('12345678A', 'John', 'Doe', '123456789', '123 Main St', 'admin@gmail.com'),
('87654321B', 'Jane', 'Doe', '987654321', '456 Elm St', 'janedoe@email.com'),
('11111111C', 'Alice', 'Smith', '111111111', '789 Oak St', 'alice.smith@email.com'),
('22222222D', 'Bob', 'Johnson', '222222222', '321 Pine St', 'bob.johnson@email.com');



CREATE TABLE `assigntask`
(
  dni varchar(10) NOT NULL, 
  cod_task varchar(10) NOT NULL,

  PRIMARY Key (`dni`, `cod_task`),
  CONSTRAINT `fk_assignTask_employees` FOREIGN KEY (`dni`) REFERENCES `employees` (`dni`) ON DELETE CASCADE,
  CONSTRAINT `fk_assignTask_tasks` FOREIGN KEY (`cod_task`) REFERENCES `tasks` (`cod`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `assigntask` (`dni`, `cod_task`) VALUES
('11111111C', '1'),
('11111111C', '2'),
('87654321B', '5');



CREATE TABLE `assignteam`
(
  cod_team varchar(10) NOT NULL, 
  cod_proyect varchar(10) NOT NULL,

  PRIMARY Key (`cod_team`, `cod_proyect`),
  CONSTRAINT `fk_assignTeam_team` FOREIGN KEY (`cod_team`) REFERENCES `teams` (`cod`) ON DELETE CASCADE,
  CONSTRAINT `fk_assignTeam_Project` FOREIGN KEY (`cod_proyect`) REFERENCES `proyects` (`cod`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `assignteam` (`cod_team`, `cod_proyect`) VALUES
('A', 'P001'),
('C', 'P001'),
('B', 'P002');



CREATE TABLE `assignemployee`
(
  dni varchar(10) NOT NULL, 
  cod_team varchar(10) NOT NULL,

  PRIMARY Key (`dni`, `cod_team`),
  CONSTRAINT `fk_assignEmployees_team` FOREIGN KEY (`cod_team`) REFERENCES `teams` (`cod`) ON DELETE CASCADE,
  CONSTRAINT `fk_assignemployess_employee` FOREIGN KEY (`dni`) REFERENCES `employees` (`dni`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `assignemployee` (`dni`, `cod_team`) VALUES
('11111111C', 'A'),
('87654321B', 'A'),
('22222222D', 'B');

INSERT INTO `assignemployee` (`dni`, `cod_team`) VALUES
('12345678A', 'A');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;