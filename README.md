#  Monitor sensors

**Monitor sensors** - This is an application for monitoring and control of sensors. The system provides functionality for creating, editing, deleting and viewing sensors, as well as managing their data. The application includes authentication and authorization with two access levels: Administrator and Viewer.

## Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security (JWT-base)**
- **Hibernate**
- **PostgreSQL**
- **Swagger (OpenAPI)**
- **MapStruct**
- **Lombok**
- **Apache Kafka**
- **Docker & Docker Compose**

## Description

The application provides a REST API for working with _**sensors**_, such as Pressure, Temperature, Humidity and others. Sensors data are stored in a relational database and can be viewed or changed by a user with appropriate rights. 
Services of sensors and statistics communicate with each other using **_Apache Kafka_**

### Main functionality:

1. Crud Operations for sensors
2. Authentication and authorization with two roles: **Administrator** and **Viewer**
3. Data validation when creating and editing sensors
4. Statistics on sensors with daily updates
5. The possibility of obtaining statistics in a given time period

### Roles
- Administrator (admin) - full access (creation, editing, deleting, viewing sensors).
- Viewer (user) - access only to viewing the list of sensors.

### Sensor structure
```json
{
  "name": "Manometer",
  "model": "APR-MD-06",
  "range": {
    "from": 1,
    "to": 10
  },
  "type": "Pressure",
  "unit": "bar",
  "location": "garage",
  "description": "A device for measuring pressure in the tires of the car. The measuring part has LED backlight"
}
```