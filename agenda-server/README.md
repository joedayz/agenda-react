## Server Application

Spring Boot as RESTful backend, Maven, Hibernate, MySQL

**RESTful API Address:** http://localhost:8080

**Employee endpoints**
 * HTTP GET 	: "localhost:8080/empleados" getting all empleados.
 * HTTP GET 	: "localhost:8080/empleados/{id}" get an empleado by id.
 * HTTP POST	: "localhost:8080/empleados" create a new empleado.
 * HTTP PUT 	: "localhost:8080/empleados/{id}" update an existing empleado.
 * HTTP DELETE : "localhost:8080/empleados/{id}" delete an empleado by id.

**Configuration Files**

`WebConfiguration.java` : CORS methods("GET", "POST", "PUT", "DELETE") allowed for development.

`HibernateConfiguration.java` : Hibernate configurations

**Maven Run Configurations**

`clean spring-boot:run`: clean and run Spring Boot application

`clean test`: clean and run tests
