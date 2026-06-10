CREATE DATABASE db_alquiler_peliculas;
GO
USE db_alquiler_peliculas;
GO

CREATE TABLE categoria (
    id_categoria    INT IDENTITY(1,1) PRIMARY KEY,
    nombre          VARCHAR(50)   NOT NULL,
    descripcion     VARCHAR(200)
);

CREATE TABLE pelicula (
    id_pelicula     INT IDENTITY(1,1) PRIMARY KEY,
    id_categoria    INT           NOT NULL,
    titulo          VARCHAR(100)  NOT NULL,
    director        VARCHAR(100),
    anio_estreno    INT,
    precio_alquiler DECIMAL(10,2) NOT NULL,
    stock           INT           NOT NULL DEFAULT 0,
    CONSTRAINT fk_pelicula_cat FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
);

CREATE TABLE cliente (
    id_cliente      INT IDENTITY(1,1) PRIMARY KEY,
    cedula          VARCHAR(13)   NOT NULL UNIQUE,
    nombres         VARCHAR(60)   NOT NULL,
    apellidos       VARCHAR(60)   NOT NULL,
    telefono        VARCHAR(15),
    email           VARCHAR(100),
    direccion       VARCHAR(200),
    fecha_reg       DATETIME      DEFAULT GETDATE()
);

CREATE TABLE alquiler (
    id_alquiler     INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente      INT           NOT NULL,
    fecha_alquiler  DATETIME      DEFAULT GETDATE(),
    fecha_devolucion DATE         NOT NULL,
    total           DECIMAL(10,2) NOT NULL DEFAULT 0,
    estado          VARCHAR(20)   DEFAULT 'ACTIVO',
    -- Estados: ACTIVO, DEVUELTO, VENCIDO
    CONSTRAINT fk_alquiler_cli FOREIGN KEY (id_cliente)
        REFERENCES cliente(id_cliente)
);

CREATE TABLE detalle_alquiler (
    id_detalle      INT IDENTITY(1,1) PRIMARY KEY,
    id_alquiler     INT           NOT NULL,
    id_pelicula     INT           NOT NULL,
    cantidad        INT           NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal        AS (cantidad * precio_unitario) PERSISTED,
    CONSTRAINT fk_detalle_alq FOREIGN KEY (id_alquiler)
        REFERENCES alquiler(id_alquiler),
    CONSTRAINT fk_detalle_pel FOREIGN KEY (id_pelicula)
        REFERENCES pelicula(id_pelicula)
);
GO

INSERT INTO categoria (nombre, descripcion) VALUES
('Acción', 'Películas de acción y aventura'),
('Comedia', 'Películas de humor'),
('Drama', 'Películas dramáticas'),
('Terror', 'Películas de terror y suspenso'),
('Ciencia Ficción', 'Películas de ciencia ficción');

INSERT INTO pelicula (id_categoria, titulo, director, anio_estreno,
                      precio_alquiler, stock) VALUES
(1, 'John Wick 4', 'Chad Stahelski', 2023, 3.50, 5),
(2, 'Barbie', 'Greta Gerwig', 2023, 3.00, 8),
(3, 'Oppenheimer', 'Christopher Nolan', 2023, 4.00, 3),
(4, 'Saw X', 'Kevin Greutert', 2023, 3.50, 4),
(5, 'Dune: Part Two', 'Denis Villeneuve', 2024, 4.50, 6);
GO