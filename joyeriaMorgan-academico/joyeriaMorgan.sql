CREATE DATABASE joyeriaMorgan;
USE joyeriaMorgan;

CREATE TABLE joyas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) UNIQUE,
    nombre VARCHAR(120) NOT NULL,
    tipo VARCHAR(50),
    material VARCHAR(50),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    stock_minimo INT DEFAULT 0,
    estado VARCHAR(20) DEFAULT 'ACTIVO'
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120),
    documento VARCHAR(20),
    telefono VARCHAR(30),
    email VARCHAR(120)
);

CREATE TABLE ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    cliente_id INT,
    subtotal DECIMAL(10,2),
    impuesto DECIMAL(10,2),
    total DECIMAL(10,2),
    metodo_pago VARCHAR(40),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT NOT NULL,
    joya_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2),
    descuento DECIMAL(10,2) DEFAULT 0,
    subtotal DECIMAL(10,2),
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (joya_id) REFERENCES joyas(id)
);