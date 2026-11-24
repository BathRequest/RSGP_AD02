package com.rsgp.fp.dam.ad.ejercicio02;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
 *  Lee el fichero libros.xml usando SAX y muestra por consola los datos de cada libro.
 *
 * IMPORTANTE: Este ejercicio es un proyecto gradle, por lo que busca el archivo libros.xml en el classpath.
 * Para evitar indicarlo manualmente en java, lo más sencillo es ejecutarlo desde el IDE, ya que cada archivo es un punto de entrada en sí mismo (main).
 */

public class LeerXmlDesdeSax {

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            try (InputStream in = Helper.abrirLibrosXml()) {
                parser.parse(in, new ManejadorLibros());
            }

        } catch (ParserConfigurationException e) {
            System.err.println("Ha ocurrido algún error parseando el documento: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Ha ocurrido algún error al manipular el documento: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ha ocurrido algún error al leer el documento XML: " + e.getMessage());
        }
    }

    // El parseo se hace completo cuando cierra el libro. Con SAX no se puede volver hacia atrás
    static class ManejadorLibros extends DefaultHandler {

        private StringBuilder texto = new StringBuilder();

        private String año;
        private String titulo;
        private String editorial;
        private String precio;
        private String nombre;
        private String apellido;
        private List<String> autores = new ArrayList<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            texto.setLength(0);

            if (qName.equals("libro")) {
                año = attributes.getValue("año");
                autores.clear();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            texto.append(ch, start, length);
        }

        //EndElement es el evento de cierre de una etiqueta. Se crea a mano la equivalencia entre etiquetas y elementos
        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("titulo")) {
                titulo = texto.toString();
            } else if (qName.equals("editorial")) {
                editorial = texto.toString();
            } else if (qName.equals("precio")) {
                precio = texto.toString();
            } else if (qName.equals("nombre")) {
                nombre = texto.toString();
            } else if (qName.equals("apellido")) {
                apellido = texto.toString();
            } else if (qName.equals("autor")) {
                autores.add(nombre + " " + apellido);
            } else if (qName.equals("libro")) {
                mostrarLibro();
            }
        }

        private void mostrarLibro() {
            System.out.println("Año: " + año);
            System.out.println("Título:    " + titulo);
            System.out.println("Editorial: " + editorial);
            System.out.println("Precio:    " + precio);

            for (String autor : autores) {
                System.out.println("Autor:     " + autor);
            }

            System.out.println();
        }
    }
}
