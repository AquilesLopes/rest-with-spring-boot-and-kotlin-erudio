CREATE TABLE IF NOT EXISTS `person` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `address` varchar(255) NOT NULL,
    `first_name` varchar(80) NOT NULL,
    `gender` varchar(50) NOT NULL,
    `last_name` varchar(80) NOT NULL,
    `birth_day` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
    )


