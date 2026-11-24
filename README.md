# Acceso a Datos 02

---

Este repositorio corresponde al ejercicio 2 de la asignatura Acceso a Datos.

El proyecto es *Gradle* por lo que el archivo libros.xml se ha puesto en la carpeta *resources* que es la carpeta que corresponde con la raíz de la salida del proyecto. Para acceder al archivo tanto en su versión compilada como sin compilar, la carga del mismo se hace desde el classpath, no desde ninguna ruta concreta. Para evitar indicarlo manualmente en java, lo más sencillo es ejecutarlo desde el IDE, ya que cada archivo es un punto de entrada en sí mismo (main).

Los archivos de salida, de los ejercicios 01 y 02 se crean dentro de una carpeta en el directorio del usuario (home); la ruta se puede ver desde el código, concretamente en el archivo *Helper.java*.