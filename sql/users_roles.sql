CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role`),
  CONSTRAINT `FKbgxdad7cyo2u7c8kqt4waorm1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci