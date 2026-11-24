package com.rsgp.fp.dam.ad.ejercicio02;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

import org.xml.sax.SAXException;

/*
 *  Lee el fichero libros.xml usando DOM y muestra por consola los datos de cada libro.
 */

public class LeerXmlDesdeDom {

    public static void main(String[] args) {

        try {
            Document document = cargarDocumento(Helper.RUTA_LIBROS_XML);
            mostrarLibros(document);

        } catch (ParserConfigurationException e) {
            System.err.println("Ha ocurrido algún error parseando el documento: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Ha ocurrido algún error al manipular el documento: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ha ocurrido algún error al leer el documento XML: " + e.getMessage());
        }
    }

    private static Document cargarDocumento(Path ruta) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(ruta.toFile());
        return document;
    }

    private static void mostrarLibros(Document document) {
        NodeList listaLibros = document.getElementsByTagName("libro");
        for (int i = 0; i < listaLibros.getLength(); i++) {
            Node nodo = listaLibros.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                mostrarLibro((Element) nodo);
            }
        }
    }

    private static void mostrarLibro(Element libro) {

        String año = libro.getAttribute("año");
        String titulo = obtenerTexto(libro, "titulo");
        String editorial = obtenerTexto(libro, "editorial");
        String precio = obtenerTexto(libro, "precio");

        System.out.println("Año: " + año);
        System.out.println("Título:    " + titulo);
        System.out.println("Editorial: " + editorial);
        System.out.println("Precio:    " + precio);

        // Un libro puede tener varios autores, por lo que se escanea uno a uno
        NodeList listaAutores = libro.getElementsByTagName("autor");
        for (int j = 0; j < listaAutores.getLength(); j++) {
            Element autor = (Element) listaAutores.item(j);
            String apellido = obtenerTexto(autor, "apellido");
            String nombre = obtenerTexto(autor, "nombre");
            System.out.println("Autor:     " + nombre + " " + apellido);
        }

        System.out.println();
    }

    // Devuelve el texto tal cual aparece en el XML del primer hijo con ese nombre
    private static String obtenerTexto(Element padre, String nombreEtiqueta) {
        NodeList lista = padre.getElementsByTagName(nombreEtiqueta);
        if (lista.getLength() == 0) {
            return "";
        }
        return lista.item(0).getTextContent();
    }
}
