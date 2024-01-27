CREATE DATABASE IF NOT EXISTS Inventario;

USE Inventario;

CREATE TABLE IF NOT EXISTS TipoInstrumento (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    unidad VARCHAR(20)
    );



CREATE TABLE IF NOT EXISTS Instrumento (
    serie VARCHAR(50) PRIMARY KEY,
    tipoInstrumento VARCHAR(10),
    descripcion TEXT,
    minimo INT,
    maximo INT,
    tolerancia INT,
    FOREIGN KEY (tipoInstrumento) REFERENCES TipoInstrumento(codigo)
    );

CREATE TABLE IF NOT EXISTS Calibracion (
    numCalibra INT AUTO_INCREMENT PRIMARY KEY,
    instrumento_serie VARCHAR(50),
    fecha VARCHAR(10),
    FOREIGN KEY (instrumento_serie) REFERENCES Instrumento(serie)
    );


CREATE TABLE IF NOT EXISTS Medicion (
    numero INT AUTO_INCREMENT, -- Número de la medición
    referencia VARCHAR(255),  -- Referencia de la medición
    lectura VARCHAR(255),     -- Valor de la medición
    calibracion INT,   -- Referencia a la calibración a la que pertenece esta medición
    PRIMARY KEY (numero, calibracion), -- Clave primaria compuesta por número y calibración
    FOREIGN KEY (calibracion) REFERENCES Calibracion(numCalibra) -- Llave foránea referenciando la tabla Calibracion
    );

-- Inserción de un tipo de instrumento
INSERT INTO TipoInstrumento (codigo, nombre, unidad)
VALUES ('TI001', 'Termómetro', '°C');

-- Inserción de un instrumento
INSERT INTO Instrumento (serie, tipoInstrumento, descripcion, minimo, maximo, tolerancia)
VALUES ('1122', 'TI001', 'Termómetro digital', -10, 100, 1);

-- Inserción de una calibración