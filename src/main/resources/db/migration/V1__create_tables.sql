CREATE TABLE `facility` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `is_vaccinated` bit(1) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `other_names` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_account_non_expired` bit(1) DEFAULT NULL,
  `is_account_non_locked` bit(1) DEFAULT NULL,
  `is_credentials_non_expired` bit(1) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_details_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3wsl4duq3n5imh005r68f3uar` (`user_details_id`),
  CONSTRAINT `FK3wsl4duq3n5imh005r68f3uar` FOREIGN KEY (`user_details_id`) REFERENCES `user_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `vaccine` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_date` date DEFAULT NULL,
  `manufacturer` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `vaccine_dose` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `visit` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `took_place` bit(1) DEFAULT NULL,
  `visit_date` date DEFAULT NULL,
  `visit_date_time` time DEFAULT NULL,
  `facility_id` bigint NOT NULL,
  `patient_id` bigint DEFAULT NULL,
  `vaccine_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd0jrm155ko61rtlcoq27rb2lv` (`facility_id`),
  KEY `FKjsynkvv3ftg9su1a6dwrs9sph` (`patient_id`),
  KEY `FKejspo2otu3nuunbax6hgxd0pf` (`vaccine_id`),
  CONSTRAINT `FKd0jrm155ko61rtlcoq27rb2lv` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
  CONSTRAINT `FKejspo2otu3nuunbax6hgxd0pf` FOREIGN KEY (`vaccine_id`) REFERENCES `vaccine` (`id`),
  CONSTRAINT `FKjsynkvv3ftg9su1a6dwrs9sph` FOREIGN KEY (`patient_id`) REFERENCES `user_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;