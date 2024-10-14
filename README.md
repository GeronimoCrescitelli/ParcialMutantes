# TRABAJO PARCIAL PROGRAMACIÓN 3 - MUTANTES
## Consigna
Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Mens.

Te ha contratado a ti para que desarrolles un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN. Para eso te ha pedido crear un programa con un método o función con la siguiente firma:

```java
boolean isMutant(String[] dna);
```

## Como funciona ?
Esta API recibira como parametro un array de Strings que representaran cada fila de una tabla de NxN con la secuencia de ADN. Las letras de los Strings solo pueden ser (A, T, C, G), las cuales representa cada base nitrogenada del ADN.

Se sabra si un humano es mutante, si se encuentra **MAS DE UNA SECUENCIA** de **CUATRO LETRAS IGUALES**, de forma horizontal, vertical o en cualquiera de sus diagonales.

El usuario debe de ingresar en formato JSON un array de Strings que formen una matriz de NxN.


## Despliegue
El proyecto se encuentra alojado en Render y podran acceder a través del siguiente link.

https://parcialmutantes.onrender.com

Al mismo tiempo podras ingresar al siguiente link para ver la base de datos en memoria de H2 donde se cargan los ADN.

http://localhost:8080/h2-console/

Credenciales de acceso a la base de datos en memoria:
* URL: jdbc:h2:file:/data/mut-db;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
* USERNAME: sa
* NO INGRESE PASSWORD

## Endpoints y Ejemplos
* ### Endpoint /mutant
Este endpoint de tipo **POST** recibe como parametro un JSON con la matriz que contiene el ADN. 
#### Criterios de aceptacion
- **Clave `adn`**: Debe ser un arreglo de strings representando cada fila de la matriz de ADN.
#### Respuestas del Endpoint
- **Mutante detectado**: Devuelve HTTP 200 OK.
- **No es mutante**: Devuelve HTTP 403 Forbidden.
- **Ingreso inválido**: Devuelve un HTTP 400 Bad Request, si ingresamos una secuencia no valida como por podria ser una matriz que **NO** sea de NxN, un arreglo vacio, una letra no valida, un array de null o una fila null.
###### Request de un ADN mutante
```json
{
  "adn": [
    "AACGTT",
    "ATGTCC",
    "ACGGGG",
    "ATTGCC",
    "CCGTCC",
    "CCGTCC"
  ] 
}
```
Esta matriz tiene mas de una secuencia de cuatro letras iguales, esto quiere decir que el **ADN** ingresado es de un **_MUTANTE_**.

###### Response del ADN anterior

```json
{
  
  "mutant": true
}
```
###### Request de un ADN no valido
```json
{
  "dna": [
    "AACGTT",
    "ATGTCC",
    "ACGGGG",
    "ATTGCC",
    "CCGTCC",
    "CC"
  ] 
}
```
###### Response de un ADN no valido
```json
{
    "status": 400,
    "error": "Bad Request",
    "trace": ....
}
```


* ### Endpoint /stats
Este endpoint de tipo **GET** nos devuelve un JSON con la cantidad de ADN mutantes verificados, la cantidad de ADN humanos verificados y un ratio de cada cuantos humanos cuantos mutantes hay.

###### Respons del endpoint /stats

```json
{
    "ratio": 3.0,
    "count_mutant_adn": 3,
    "count_human_adn": 1
}
```
