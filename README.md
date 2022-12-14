# Spring Boot JPA MySQL - Ejemplo de orquestración con docker-compose


El ejemplo muestra una sencilla API Rest implementada en spring boot (spring mvc) junto con Hibernate como proveedor JPA (persistencia de datos).

Se usa como base de datos para persistir la información, Mysql.

El ejemplo pretende ilustrar las capacidades de docker-compose para orquestrar el despliegue de varias piezas o servicios, en concreto:

- inter-dependencias entre servicios
- mapeo de volúmenes contra disco host
- uso de volúmenes sin mapeo contra disco host
- mapeo de puertos
- expansión de variables de entorno
- uso de ficheros de configuración para proporcionar variables de entorno (env files).
- referencias entre hosts en docker, dentro de la misma red.





