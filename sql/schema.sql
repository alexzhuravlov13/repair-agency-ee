DROP TABLE IF EXISTS `repair_forms`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
);

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role`)
);

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
  CONSTRAINT `FK4fw9r433k9a04xpuee1c8lb19` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`)
);
