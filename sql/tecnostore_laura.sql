DROP DATABASE IF EXISTS tecnostore_Laura;
CREATE DATABASE tecnostore_Laura;
USE tecnostore_Laura;

CREATE TABLE IF NOT EXISTS tipo_cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    porcentaje_descuento DECIMAL(5,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE IF NOT EXISTS persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_documento ENUM('C.C', 'PASAPORTE', 'VISA') NOT NULL,
    documento VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
    id INT PRIMARY KEY,
    tipo_cliente_fk INT NOT NULL DEFAULT 1,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE,
    FOREIGN KEY (tipo_cliente_fk) REFERENCES tipo_cliente(id)
);

CREATE TABLE IF NOT EXISTS marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sistema_operativo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS celular (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca_fk INT NOT NULL,
    sistema_operativo_fk INT NOT NULL,
    modelo VARCHAR(60) NOT NULL,
    gama ENUM('Alta', 'Media', 'Baja') NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    FOREIGN KEY (marca_fk) REFERENCES marca(id),
    FOREIGN KEY (sistema_operativo_fk) REFERENCES sistema_operativo(id)
);

CREATE TABLE IF NOT EXISTS stock_minimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    celular_fk INT NOT NULL UNIQUE,
    cantidad_minima INT NOT NULL DEFAULT 5,
    FOREIGN KEY (celular_fk) REFERENCES celular(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_fk INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    descuento DECIMAL(10,2) DEFAULT 0.00,
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cliente_fk) REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_fk INT NOT NULL,
    celular_fk INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (venta_fk) REFERENCES venta(id) ON DELETE CASCADE,
    FOREIGN KEY (celular_fk) REFERENCES celular(id)
);


INSERT IGNORE INTO tipo_cliente (id, nombre, porcentaje_descuento) VALUES 
(1, 'REGULAR', 0.00),
(2, 'JUNIOR_MAYORISTA', 0.10),
(3, 'MAYORISTA', 0.20);

INSERT IGNORE INTO sistema_operativo (id, nombre) VALUES 
(1, 'Android'), 
(2, 'iOS');

INSERT INTO marca (id, nombre) VALUES 
(1, 'Samsung'), 
(2, 'Apple'), 
(3, 'Xiaomi'),
(4, 'Motorola');

INSERT INTO persona (id, nombre, tipo_documento, documento, correo, telefono) VALUES
(1, 'Laura Calvache', 'C.C', '1002962251', 'laura@gmail.com', '3165519131'),
(2, 'Carlos Gómez', 'C.C', '1098765432', 'carlos.g@gmail.com', '3151234567'),
(3, 'Distribuidora Mayorista SAS', 'VISA', '900123456', 'ventas@mayoristasas.com', '3209876543'),
(4, 'Andrea Mantilla', 'C.C', '1005432189', 'andrea.m@gmail.com', '3187654321');


INSERT INTO cliente (id, tipo_cliente_fk) VALUES 
(1, 1), -- Regular
(2, 2), -- Junior Mayorista (10%)
(3, 3), -- Mayorista (20%)
(4, 1); -- Regular


INSERT INTO celular (id, marca_fk, sistema_operativo_fk, modelo, gama, precio, stock) VALUES
(1, 1, 1, 'Galaxy S24', 'Alta', 3500000.00, 10),
(2, 3, 1, 'Redmi Note 13', 'Media', 900000.00, 3),
(3, 2, 2, 'iPhone 15', 'Alta', 4200000.00, 8),
(4, 1, 1, 'Galaxy A15', 'Baja', 650000.00, 2),
(5, 2, 2, 'iPhone 13', 'Media', 2800000.00, 0),
(6, 4, 1, 'Moto G84', 'Media', 1100000.00, 15);


INSERT INTO stock_minimo (celular_fk, cantidad_minima) VALUES 
(1, 5),
(2, 5),
(3, 5),
(4, 5),
(5, 3),
(6, 5);

-- Compra 1 Galaxy S24 ($3.500.000). Subtotal: 3.500.000, Descuento: 0, IVA (19%): 665.000, Total: 4.165.000
INSERT INTO venta (id, cliente_fk, fecha, descuento, iva, total) VALUES 
(1, 1, '2026-10-10 14:30:00', 0.00, 665000.00, 4165000.00);

INSERT INTO detalle_venta (venta_fk, celular_fk, cantidad, precio_unitario, subtotal) VALUES 
(1, 1, 1, 3500000.00, 3500000.00);

-- Venta 2: Cliente Junior Mayorista (id: 2, 10% de descuento)
-- Compra 2 Moto G84 ($1.100.000 c/u -> $2.200.000). 
-- Descuento 10%: 220.000 -> Base gravable: 1.980.000 -> IVA (19%): 376.200 -> Total: 2.356.200
INSERT INTO venta (id, cliente_fk, fecha, descuento, iva, total) VALUES 
(2, 2, '2026-10-11 10:15:00', 220000.00, 376200.00, 2356200.00);

INSERT INTO detalle_venta (venta_fk, celular_fk, cantidad, precio_unitario, subtotal) VALUES 
(2, 6, 2, 1100000.00, 2200000.00);


-- Venta 3: Cliente Mayorista (id: 3, 20% de descuento) con múltiples productos
-- Compra 1 iPhone 15 ($4.200.000) + 1 Redmi Note 13 ($900.000). Subtotal bruto: 5.100.000
-- Descuento 20%: 1.020.000 -> Base gravable: 4.080.000 -> IVA (19%): 775.200 -> Total: 4.855.200
INSERT INTO venta (id, cliente_fk, fecha, descuento, iva, total) VALUES 
(3, 3, '2026-10-12 16:45:00', 1020000.00, 775200.00, 4855200.00);

INSERT INTO detalle_venta (venta_fk, celular_fk, cantidad, precio_unitario, subtotal) VALUES 
(3, 3, 1, 4200000.00, 4200000.00),
(3, 2, 1, 900000.00, 900000.00);



