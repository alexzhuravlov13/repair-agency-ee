CREATE TABLE `repair_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `car` varchar(255) DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `repairman_id` int NOT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `author_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci