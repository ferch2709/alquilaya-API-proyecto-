# PROYECTO API Sistema de Alquiler de Vehículos - AlquilaYa S.A.

## Descripción de la API
API REST y web desarrollada en **Java** con **Spring Boot** para la empresa "AlquilaYa S.A.". Diseñada bajo una Arquitectura Orientada a Servicios (SOA), moderniza la gestión de alquiler de vehículos permitiendo administrar la flota de unidades, el registro de clientes conductores, el ciclo de vida completo de los contratos de renta con cálculo automático de tarifas e integración con una interfaz gráfica en tiempo real.

---

## Tecnologías Utilizadas
* **Lenguaje:** Java 21
* **Framework:** Spring Boot
  * Spring Web MVC (Controladores REST y vistas)
  * Spring Data JPA / Hibernate (Persistencia relacional)
  * Thymeleaf (Renderizado de interfaz del lado del servidor)
* **Frontend:** HTML5, JavaScript moderno (Fetch API) y Bootstrap 5
* **Base de Datos:** MySQL Server (Driver `mysql-connector-j`)
* **Gestor de Compilación y Dependencias:** Apache Maven
* **Cliente de Pruebas:** Postman

---

## Variables de Entorno Necesarias
Para permitir el despliegue seguro en diferentes entornos sin exponer contraseñas locales en el repositorio:

* **PORT:** Puerto de escucha del servidor (Por defecto: 8000)
* **DB_URL:** Cadena de conexión JDBC a MySQL (Por defecto: jdbc:mysql://localhost:3306/alquilaya?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC)
* **DB_USERNAME:** Usuario de base de datos (Por defecto: root)
* **DB_PASSWORD:** Contraseña de base de datos (Por defecto: tu_contrasena_local)

---

## Pasos de Instalación y Ejecución Local

### Prerrequisitos
* Java JDK 21 instalado y configurado en el sistema
* Servidor MySQL activo en el puerto 3306
* Base de datos creada:
  CREATE DATABASE alquilaya;

### Pasos de Ejecución
* 1. Clonar el repositorio:
  git clone https://github.com/ferch2709/alquilaya-API.git
  cd alquilaya-API

* 2. Configuración local:
  Revisar en src/main/resources/application.properties que el usuario y la contraseña coincidan con los de tu MySQL local.

* 3. Compilar y levantar la aplicación:
  ./mvnw spring-boot:run
  O ejecutar la clase AlquilayaApplication.java desde tu IDE.

* 4. Acceso al sistema:
  * Interfaz Web (Frontend interactivo): http://localhost:8000/
  * Endpoints REST: http://localhost:8000/api/...

---

## Listado de Endpoints

### 1. Módulo Vehículos (/api/vehiculos)
* **GET /api/vehiculos:** Lista toda la flota vehicular
* **POST /api/vehiculos:** Da de alta un nuevo vehículo
* **GET /api/vehiculos/{id}:** Consulta un vehículo por su ID
* **GET /api/vehiculos/disponibles:** Filtra únicamente vehículos aptos para alquiler (estado DISPONIBLE)
* **PUT /api/vehiculos/{id}:** Actualiza las características de un vehículo
* **PUT /api/vehiculos/{id}/estado/{estado}:** Cambia el estado del auto (DISPONIBLE, ALQUILADO, MANTENIMIENTO)
* **DELETE /api/vehiculos/{id}:** Da de baja un vehículo de la flota

### 2. Módulo Clientes (/api/clientes)
* **GET /api/clientes:** Lista todos los clientes registrados
* **POST /api/clientes:** Registra un nuevo conductor (con licencia de conducir única)
* **GET /api/clientes/{id}:** Obtiene los datos de un cliente por su ID
* **PUT /api/clientes/{id}:** Actualiza la información de un cliente
* **DELETE /api/clientes/{id}:** Elimina un cliente del sistema

### 3. Módulo Alquileres (/api/alquileres)
* **GET /api/alquileres:** Retorna todos los contratos registrados
* **POST /api/alquileres:** Genera un alquiler, calculando el precio total según días y cambiando el auto a ALQUILADO
* **GET /api/alquileres/{id}:** Obtiene el detalle de un contrato por su ID
* **GET /api/alquileres/cliente/{idCliente}:** Retorna el historial de rentas de un cliente específico
* **PUT /api/alquileres/{id}:** Actualiza el contrato (ej. finalizar devolución y liberar el auto)
* **DELETE /api/alquileres/{id}:** Cancela/elimina un registro de alquiler

---

## Ejemplos de Peticiones y Respuestas

### 1. Registrar Cliente
* **Método:** POST
* **URL:** http://localhost:8000/api/clientes
* **Headers:** Content-Type: application/json
* **Cuerpo de la Petición:**
```json
{
  "firstName": "Juan",
  "lastName": "Pérez Gómez",
  "email": "juan.perez@alquilaya.com",
  "driversLicense": "LIC-987654",
  "phone": "5544332211"
}
```
* **Estado HTTP:** 201 Created
* **Cuerpo de la Respuesta:**
```json
{
  "idCliente": 1,
  "firstName": "Juan",
  "lastName": "Pérez Gómez",
  "email": "juan.perez@alquilaya.com",
  "driversLicense": "LIC-987654",
  "phone": "5544332211"
}
```

---

### 2. Registrar Vehículo en Flota
* **Método:** POST
* **URL:** http://localhost:8000/api/vehiculos
* **Headers:** Content-Type: application/json
* **Cuerpo de la Petición:**
```json
{
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2023,
  "matricula": "ABC-1234",
  "tarifaDiaria": 650.00,
  "estado": "DISPONIBLE"
}
```
* **Estado HTTP:** 201 Created
* **Cuerpo de la Respuesta:**
```json
{
  "idVehiculo": 1,
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2023,
  "matricula": "ABC-1234",
  "tarifaDiaria": 650.00,
  "estado": "DISPONIBLE"
}
```

---

### 3. Registrar un Contrato de Alquiler
* **Método:** POST
* **URL:** http://localhost:8000/api/alquileres
* **Headers:** Content-Type: application/json
* **Cuerpo de la Petición:**
```json
{
  "vehiculo": {
    "idVehiculo": 1
  },
  "cliente": {
    "idCliente": 1
  },
  "fechaAlquiler": "2026-10-15",
  "fechaDevolucion": "2026-10-20",
  "estado": "ACTIVO"
}
```
* **Estado HTTP:** 201 Created
* **Cuerpo de la Respuesta:**
```json
{
  "idAlquiler": 1,
  "vehiculo": {
    "idVehiculo": 1,
    "marca": "Toyota",
    "modelo": "Corolla",
    "tarifaDiaria": 650.00,
    "estado": "ALQUILADO"
  },
  "cliente": {
    "idCliente": 1,
    "firstName": "Juan",
    "lastName": "Pérez Gómez"
  },
  "fechaAlquiler": "2026-10-15",
  "fechaDevolucion": "2026-10-20",
  "precioTotal": 3250.00,
  "estado": "ACTIVO"
}
```

---

### 4. Consultar Vehículos Disponibles
* **Método:** GET
* **URL:** http://localhost:8000/api/vehiculos/disponibles
* **Estado HTTP:** 200 OK
* **Cuerpo de la Respuesta:**
```json
[
  {
    "idVehiculo": 1,
    "marca": "Toyota",
    "modelo": "Corolla",
    "anio": 2023,
    "matricula": "ABC-1234",
    "tarifaDiaria": 650.00,
    "estado": "DISPONIBLE"
  }
]
```

---

### 5. Finalizar Alquiler / Devolución
* **Método:** PUT
* **URL:** http://localhost:8000/api/alquileres/1
* **Headers:** Content-Type: application/json
* **Cuerpo de la Petición:**
```json
{
  "fechaDevolucion": "2026-10-20",
  "estado": "COMPLETADO"
}
```
* **Estado HTTP:** 200 OK

---

### 6. Enviar Vehículo a Taller (Mantenimiento)
* **Método:** PUT
* **URL:** http://localhost:8000/api/vehiculos/1/estado/MANTENIMIENTO
* **Estado HTTP:** 200 OK
* **Cuerpo de la Respuesta:**
```json
{
  "idVehiculo": 1,
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2023,
  "matricula": "ABC-1234",
  "tarifaDiaria": 650.00,
  "estado": "MANTENIMIENTO"
}
```
