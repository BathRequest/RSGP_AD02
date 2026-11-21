package com.rsgp.fp.dam.ad.ejercicio02;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
* Crear un fichero EMPLEADOS.DAT, de acceso aleatorio, que contenga al menos cinco empleados.
* Dicho fichero contendrá los campos siguientes: CODIGO (int), NOMBRE (string), DIRECCION (string), SALARIO (float) y COMISION (float).
*/

public class CrearEmpleadosDat {

    //Se usa el API nueva. Se usa como salida el directorio del usuario y la clase Paths para componer la ruta sin
    //usar caracteres dependientes del SO ni usar "file.separator" que lo ahce muy poco legible
    public static final Path path = Paths.get(System.getProperty("user.home"),"accesodatos-02","ejercicio-01","EMPLEADOS.DAT");

    public static void main(String[] args) {

        //Creamos los empleados
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado(12, "Rafael", "Calle Sol, 2, Denia", 1000.0f, 0.1f));
        empleados.add(new Empleado(12, "Inés", "Calle Luna, 5, Albacete", 1200.0f, 0.2f));
        empleados.add(new Empleado(12, "Arnoldo", "Calle Mercurio, 42, Madrid", 31000.0f, 0.3f));
        empleados.add(new Empleado(12, "Ulpiana", "Calle Antares, 56, Ciudad Real", 10500.0f, 0.4f));
        empleados.add(new Empleado(12, "Simplicio", "Calle Altair, 123, Barcelona", 11000.0f, 0.5f));

        try {
            EscribirAlArchivo(path, empleados);
            if (Files.exists(path)){
                System.out.println("El archivo se ha creado con éxito en " + path.toString());
                System.out.println("El tamaño total del archivo de salida es de " + Files.size(path) + " bytes.");
            }
        } catch (IOException e) {
            System.err.println("Ha ocurrido algún error al manipular el archivo: " + e.getMessage());
        }

    }

    private static void EscribirAlArchivo(Path path, List<Empleado> empleados) throws IOException{
        //Creamos el directorio de salida si no existe
        if (!Files.exists(path.getParent())) Files.createDirectories(path);

        //Si ya existe el archivo de una ejecución anterior, lo borramos
        if (Files.exists(path)) Files.delete(path);

        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw")) {
            for (Empleado e : empleados) {
                file.writeInt(e.getCodigo());
                file.writeUTF(e.getNombre());
                file.writeUTF(e.getDireccion());
                file.writeFloat(e.getSalario());
                file.writeFloat(e.getComision());
            }
        }
    }

}

