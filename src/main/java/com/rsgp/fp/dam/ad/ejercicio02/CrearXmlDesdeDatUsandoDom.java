package com.rsgp.fp.dam.ad.ejercicio02;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
 *  A partir de los datos del fichero EMPLEADOS.DAT crear un fichero llamado EMPLEADOS.XML usando DOM.
 *
 * IMPORTANTE: ESta clase dará error si no se ha ejecutado previamente "CrearEmpleadosDat"
 */

public class CrearXmlDesdeDatUsandoDom {

    public static void main (String[] args) throws ParserConfigurationException {

        try {
            //Recuperamos los empleados desde el archivo binario
            List <Empleado> listaEmpleados = recuperarEmpleadosDesdeDat(Helper.RUTA_ARCHIVO_DAT);

            //Creamos el documento DOM
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            Document document = documentBuilderFactory.newDocumentBuilder().newDocument();

            //Definimos el elemento reíz del document
            Element raiz = document.createElement("empleados");
            document.appendChild(raiz);

            //Añadimos los empleados al elemento raíz
            for (Empleado e : listaEmpleados){
                raiz.appendChild(crearElementoDesdeEmpleado(e,document));
            }

            //Preparamos el transformer
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //Añadimos sangría mediante configuración.
            //https://docs.oracle.com/javase/8/docs/api/javax/xml/transform/OutputKeys.html#INDENT
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            //Establecemos el DOM como origen y generamos la salida
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(Helper.RUTA_ARCHIVO_XML.toFile());
            transformer.transform(source, result);

        } catch (IOException e){
            System.err.println("Ha ocurrido algún error recuperando los usuarios deses el archivo DAT: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println("Ha ocurrido algún error manipulando el archivo DOM");
        } catch (TransformerException e) {
            System.err.println("Ha ocurrido algún error al generar la salida a XML");
        }

    }

    private static Element crearElementoDesdeEmpleado (Empleado empleado, Document documento){
        Element empleadoADevolver = documento.createElement("empleado");
        empleadoADevolver.setAttribute("codigo", String.valueOf(empleado.getCodigo()));
        empleadoADevolver.setAttribute("nombre", empleado.getNombre());
        empleadoADevolver.setAttribute("direccion", empleado.getDireccion());
        empleadoADevolver.setAttribute("salario", String.valueOf(empleado.getSalario()));
        empleadoADevolver.setAttribute("comision", String.valueOf(empleado.getComision()));

        return empleadoADevolver;
    }

    private static  List<Empleado> recuperarEmpleadosDesdeDat(Path path) throws IOException {

        List<Empleado> empleados = new ArrayList<>();

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), "r")){
            while (true){
                Empleado nuevo = new Empleado(
                        randomAccessFile.readInt(),
                        randomAccessFile.readUTF(),
                        randomAccessFile.readUTF(),
                        randomAccessFile.readFloat(),
                        randomAccessFile.readFloat()
                );
                empleados.add(nuevo);
            }
        } catch (EOFException e){
            return empleados;
        }
    }
}
