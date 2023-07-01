CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `CustomerId` varchar(45) DEFAULT NULL,
  `ShortName` varchar(45) DEFAULT NULL,
  `FullName` varchar(45) DEFAULT NULL,
  `Address1` varchar(80) DEFAULT NULL,
  `Address2` varchar(80) DEFAULT NULL,
  `Address3` varchar(80) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `PostalCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
