CREATE DATABASE  IF NOT EXISTS `application`;
USE `application`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `applicants`;

CREATE TABLE `applicants` (
                         `username` varchar(50) NOT NULL,
                         `password` char(68) NOT NULL,
                         `name` varchar(50) NOT NULL,
                         `surname` varchar(50) NOT NULL,
                         `age` INT NOT NULL,
                         `occupation` varchar(20) NOT NULL,
                         `email` varchar(50) NOT NULL,
                         `income` INT NOT NULL,
                         `comments` varchar(200),

                         `status` varchar(200) NOT NULL DEFAULT "On the review", -- Default set status of the application.
                         `enabled` tinyint NOT NULL DEFAULT 1, -- Default value set to 1 (enabled), 0 - is disable the row.
                         `deletion` BOOL NOT NULL DEFAULT false, -- Default value set to "false", "true" - is to delete the row.
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `authorities` (
                               `username` varchar(50) NOT NULL,
                               `authority` varchar(50) NOT NULL,
                               UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
                               CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `applicants` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
