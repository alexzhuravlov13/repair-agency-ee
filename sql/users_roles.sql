CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci