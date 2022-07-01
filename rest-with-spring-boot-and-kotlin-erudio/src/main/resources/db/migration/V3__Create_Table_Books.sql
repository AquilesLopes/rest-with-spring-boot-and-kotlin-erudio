CREATE TABLE `books` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` longtext NOT NULL,
  `launch_date` datetime(6),
  `price` decimal(65,2) NOT NULL,
  `title` longtext  NOT NULL,
   PRIMARY KEY (`id`)
)
