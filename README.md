# W2M Test 
## Alejandro Cerrato Espejo
### Spaceship Test

Hola reviewer! Espero podamos conocernos pronto!

## Como usar en local
1. Levanta el `docker-compose` para levantar el servicio en rabbit con `docker-compose up -d`
2. Levanta la aplicación a través de maven con el comando `mvn spring-boot:run` o a través de tu IDE favorito, o a través de un docker haciendo un build de este mismo con `docker build -t session . && docker run session`

## Cosas que hacer
### Importante
* LTS de Java, Spring, Maven y cualquier libreria ✔
* Desarrollar usando mvn spring y java ✔
* CRUD Naves espaciales de series y peliculas ✔

### Este mantenimiento debe permitir:
* Consultar todas las naves utilizando paginación.  ✔
* Consultar una única nave por id.  ✔
* Consultar todas las naves que contienen, en su nombre, el valor de un parámetro enviado en
  la petición. Por ejemplo, si enviamos “wing” devolverá “x-wing”.  ✔
* Crear una nueva nave.  ✔
* Modificar una nave.  ✔
* Eliminar una nave.  ✔
* Test unitario de como mínimo de una clase. ✔ (ConvertersTest)
* Desarrollar un @Aspect que añada una línea de log cuando nos piden una nave con un id
  negativo. ✔
* Gestión centralizada de excepciones. ✔
* Utilizar cachés de algún tipo.  ✔

### Puntos a tener en cuenta:
* Las naves se deben guardar en una base de datos. Puede ser, por ejemplo, H2 en memoria.  ✔
* La prueba se debe presentar en un repositorio de Git. No hace falta que esté publicado. Se
  puede enviar comprimido en un único archivo.  ✔

### Puntos opcionales de mejora:
* Utilizar alguna librería que facilite el mantenimiento de los scripts DDL de base de datos.  ✔
* Test de integración.  ✔
* Presentar la aplicación dockerizada.  ✔
* Documentación de la API. ✔
* Seguridad del API. ✔
* Implementar algún consumer/producer para algún broker (Rabbit, Kafka, etc). ✔