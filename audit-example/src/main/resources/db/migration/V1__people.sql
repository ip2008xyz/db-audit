CREATE TABLE people(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) DEFAULT NULL,
    last_name VARCHAR(255) DEFAULT NULL,
    office VARCHAR(255) DEFAULT NULL,
    user_name VARCHAR(255) DEFAULT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;