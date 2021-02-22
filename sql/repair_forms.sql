CREATE TABLE `repair_forms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `car` varchar(255) DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `rf_description` varchar(255) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `rf_s_description` varchar(255) DEFAULT NULL,
  `rf_status` varchar(255) DEFAULT NULL,
  `author_id` int DEFAULT NULL,
  `repairman_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkelnb5r07oipk4675ky2o4mi2` (`author_id`),
  CONSTRAINT `FKkelnb5r07oipk4675ky2o4mi2` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci