CREATE DATABASE IF NOT EXISTS footflex;
USE footflex;

CREATE TABLE IF NOT EXISTS roles (
    id_role INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS suscripcion (
    id_suscription INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    duration INT,
    total_price INT
);

CREATE TABLE IF NOT EXISTS users (
    id_user INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    lastname VARCHAR(50),
    email VARCHAR(50),
    phone VARCHAR(50),
    address VARCHAR(50),
    password VARCHAR(50),
    rol_id INT DEFAULT '1',
    suscription_id INT DEFAULT '1',
    start_suscription DATE,
    end_suscription DATE DEFAULT '3000-01-30', 
    provider VARCHAR(15),
    FOREIGN KEY (rol_id) REFERENCES roles(id_role),
    FOREIGN KEY (suscription_id) REFERENCES suscripcion(id_suscription)
);

CREATE TABLE IF NOT EXISTS categories (
    id_category INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS products (
    id_product INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(50) ,
    description VARCHAR(50),
    brand VARCHAR(50),
    sku VARCHAR(50),
    price INT,
    stock INT,
    image VARCHAR(500),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id_category)
);

CREATE TABLE IF NOT EXISTS orders (
    id_order INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    user_id INT,
    date DATE,
    total_price INT,
    FOREIGN KEY (user_id) REFERENCES users(id_user)
);

CREATE TABLE IF NOT EXISTS orders_products (
    id_order INT,
    id_product INT,
    quantity INT,
    PRIMARY KEY (id_order, id_product),
    FOREIGN KEY (id_order) REFERENCES orders(id_order),
    FOREIGN KEY (id_product) REFERENCES products(id_product)
);

CREATE TABLE IF NOT EXISTS proveedores (
    id_proveedor INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    phone VARCHAR(50),
    address VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS products_proveedores (
    id_product INT,
    id_proveedor INT,
    PRIMARY KEY (id_product, id_proveedor),
    FOREIGN KEY (id_product) REFERENCES products(id_product),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor)
);

CREATE TABLE IF NOT EXISTS orders_proveedores (
    id_order INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    proveedor_id INT,
    date DATE,
    total_price INT,
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id_proveedor)
);


CREATE TABLE IF NOT EXISTS devoluciones (
    id_devolucion INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    order_id INT,
    date DATE,
    total_price INT,
    FOREIGN KEY (order_id) REFERENCES orders(id_order)
);

CREATE TABLE IF NOT EXISTS devoluciones_products (
    id_devolucion INT,
    id_product INT,
    quantity INT,
    PRIMARY KEY (id_devolucion, id_product),
    FOREIGN KEY (id_devolucion) REFERENCES devoluciones(id_devolucion),
    FOREIGN KEY (id_product) REFERENCES products(id_product)
);


CREATE TABLE IF NOT EXISTS comentarios (
    id_comentario INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    user_id INT,
    product_id INT,
    date DATE,
    comment VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id_user),
    FOREIGN KEY (product_id) REFERENCES products(id_product)
);

CREATE TABLE IF NOT EXISTS valoraciones (
    id_valoracion INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    user_id INT,
    product_id INT,
    date DATE,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    FOREIGN KEY (user_id) REFERENCES users(id_user),
    FOREIGN KEY (product_id) REFERENCES products(id_product)
);

CREATE TABLE IF NOT EXISTS carrito (
    id_carrito INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
    user_id INT,
    product_id INT,
    quantity INT,
    total INT,
    FOREIGN KEY (user_id) REFERENCES users(id_user),
    FOREIGN KEY (product_id) REFERENCES products(id_product)
);

ALTER TABLE `footflex`.`users` 
DROP FOREIGN KEY `users_ibfk_2`,
DROP FOREIGN KEY `users_ibfk_1`;
ALTER TABLE `footflex`.`users` 
DROP COLUMN `provider`,
DROP COLUMN `end_suscription`,
DROP COLUMN `start_suscription`,
DROP COLUMN `suscription_id`,
DROP COLUMN `rol_id`,
DROP COLUMN `lastname`,
CHANGE COLUMN `name` `name` VARCHAR(50) NOT NULL ,
CHANGE COLUMN `password` `password` VARCHAR(500) NOT NULL ,
DROP INDEX `suscription_id` ,
DROP INDEX `rol_id` ;
;

DROP TABLE `footflex`.`roles`;
DROP TABLE `footflex`.`suscripcion`;

ALTER TABLE `footflex`.`users` 
ADD COLUMN `role` ENUM('ADMIN', 'USER') NOT NULL AFTER `password`;

ALTER TABLE `footflex`.`products` 
DROP FOREIGN KEY `products_ibfk_1`;
ALTER TABLE `footflex`.`products` 
DROP COLUMN `category_id`,
DROP INDEX `category_id` ;
;

DROP TABLE `footflex`.`categories`;

ALTER TABLE `footflex`.`users` 
CHANGE COLUMN `email` `email` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `phone` `phone` INT(9) NULL DEFAULT NULL ,
CHANGE COLUMN `address` `address` VARCHAR(100) NULL DEFAULT NULL ;

ALTER TABLE `footflex`.`users` 
CHANGE COLUMN `role` `role` ENUM('ADMIN', 'USER') NULL ;

ALTER TABLE `footflex`.`users`
MODIFY COLUMN `role` ENUM('ADMIN', 'USER') DEFAULT 'USER' NULL;

ALTER TABLE `footflex`.`products` 
ADD COLUMN `created_at` DATETIME NULL AFTER `image`;

ALTER TABLE `footflex`.`users` 
ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE;
;

ALTER TABLE `footflex`.`users` 
CHANGE COLUMN `email` `email` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `phone` `phone` INT NOT NULL ,
CHANGE COLUMN `address` `address` VARCHAR(100) NOT NULL ;

## INSERT DE USUARIO ADMIN

INSERT INTO users (name, email, password, role, phone, address)
VALUES ('admin', 'admin@example.com', '$2a$10$qe5G3K9aewfcErvR.Z8oye09dFDhlH4qeYGA1k0pYyWBJLGKSXTIi', 'ADMIN', 1234567890, 'Dirección de Admin');

