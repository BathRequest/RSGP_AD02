package com.rsgp.fp.dam.ad.ejercicio02;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {

    public static final Path RUTA_ARCHIVO_DAT = Paths.get(System.getProperty("user.home"),"accesodatos-02","EMPLEADOS.DAT");
    public static final Path RUTA_ARCHIVO_XML = Paths.get(System.getProperty("user.home"),"accesodatos-02","EMPLEADOS.XML");

    // libros.xml está en src/main/resources, así que Gradle lo copia al classpath
    public static InputStream abrirLibrosXml() throws IOException {
        InputStream in = Helper.class.getResourceAsStream("/libros.xml");
        if (in == null) {
            throw new FileNotFoundException("No se encuentra libros.xml en el classpath");
        }
        return in;
    }
}
