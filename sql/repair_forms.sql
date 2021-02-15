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
  KEY `FK4fw9r433k9a04xpuee1c8lb19` (`author_id`),
  KEY `FK7yc6uxu9jifd4wbmk1dpe673s` (`repairman_id`),
  CONSTRAINT `FK4fw9r433k9a04xpuee1c8lb19` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK7yc6uxu9jifd4wbmk1dpe673s` FOREIGN KEY (`repairman_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci