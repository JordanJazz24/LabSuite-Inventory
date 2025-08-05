# Inventory System for Laboratory Instruments

Este proyecto implementa un sistema de escritorio con interfaz gráfica para la gestión de instrumentos de laboratorio. El desarrollo está realizado en Java, utilizando la arquitectura **Model-View-Controller (MVC)** y almacenamiento de datos en **MySQL**.

## Arquitectura General

El sistema se estructura en tres principales módulos:

- **Frontend**: Interfaz gráfica de usuario, desarrollada en Java Swing, que permite la interacción con el usuario para gestionar los instrumentos, calibraciones y mediciones.
- **Backend**: Lógica de negocio y servidor, encargado de procesar las solicitudes del frontend y comunicarse con la base de datos.
- **Protocol**: Define los contratos de comunicación entre frontend y backend, incluyendo códigos de operación y la interfaz de servicios (`IService`).

### Diagrama General

```
+--------------+         +--------------+         +--------------+
|   Frontend   | <--->   |   Backend    | <--->   |   MySQL DB   |
+--------------+         +--------------+         +--------------+
    (Swing)             (Servidor Java)          (Persistencia)
```

## Lógica Implementada

### 1. Comunicación Cliente-Servidor

- La comunicación entre frontend y backend se realiza mediante **Sockets**.
- El protocolo de intercambio está definido en `Protocol.java`, con códigos específicos para cada operación (crear, leer, actualizar, eliminar, buscar) sobre entidades como instrumentos, calibraciones y mediciones.

### 2. Backend (Servidor)

- El servidor se inicializa en la clase `Application.java` y ejecuta el ciclo principal en `Server.java`, aceptando conexiones y delegando a instancias de `Worker`.
- Cada `Worker` gestiona una conexión de cliente, procesando solicitudes mediante el protocolo y llamando a la capa de servicios.
- El backend implementa la lógica CRUD sobre las entidades principales: `Instrumento`, `TipoInstrumento`, `Calibracion`, y `Medicion`.
- La clase `Service` es el punto central de acceso, utilizando los DAO correspondientes para interactuar con la base de datos MySQL.

### 3. Frontend (Cliente)

- El frontend implementa la interfaz gráfica, conectando al servidor y enviando solicitudes mediante el protocolo definido.
- La clase principal del frontend también implementa la lógica para recibir mensajes del servidor y actualizar la interfaz de usuario.

### 4. Protocol

- `Protocol.java` define constantes para cada acción posible (ej. `createInstrumento`, `readCalibracion`, etc.) y los parámetros de conexión.
- `IService.java` describe la interfaz de servicios que debe implementar tanto el backend como el frontend para una comunicación transparente.

## Entidades Principales

- **TipoInstrumento**: Categoría de los instrumentos gestionados.
- **Instrumento**: Representa cada instrumento concreto, con su respectiva serie y descripción.
- **Calibracion**: Registros de calibraciones realizadas sobre los instrumentos.
- **Medicion**: Mediciones asociadas a cada calibración.

## Funcionalidades

- **Gestión completa de instrumentos**: Crear, leer, actualizar, eliminar y buscar instrumentos.
- **Gestión de tipos de instrumento**: CRUD sobre las categorías.
- **Gestión de calibraciones y mediciones**: Registro, consulta, modificación y eliminación.
- **Comunicación multiusuario**: El servidor soporta múltiples clientes conectados simultáneamente.
- **Persistencia en MySQL**: Todos los registros se almacenan y recuperan desde la base de datos.

## Ejecución

1. Configura la base de datos MySQL con las tablas requeridas.
2. Inicia el backend (`Application.java`) para levantar el servidor.
3. Ejecuta el frontend para interactuar con el sistema.

## Consideraciones Técnicas

- El proyecto sigue el patrón **MVC** para separar la lógica de presentación, negocio y datos.
- Utiliza **DAO (Data Access Object)** para abstraer las operaciones sobre la base de datos.
- La comunicación cliente-servidor está sincronizada utilizando mensajes serializados y un protocolo propio.
- El diseño permite la extensión de funcionalidades y la integración de otros tipos de entidades.

## Autor

Desarrollado por **JordanJazz24**.

---
