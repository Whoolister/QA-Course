CREATE DATABASE IF NOT EXISTS solvd;
USE solvd;

CREATE TABLE IF NOT EXISTS products (
	product_id INT AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL UNIQUE,
    manufacturer VARCHAR(20),
    stock INT,
    price DOUBLE(6,2),
	PRIMARY KEY (product_id)
);