package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MostrarXMLSAX {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("libro")) {
                        System.out.println("Libro:");
                        System.out.println(" ISBN: " + attributes.getValue("isbn"));
                        System.out.println(" Ejemplares: " + attributes.getValue("ejemplares"));
                        System.out.println(" Año edición: " + attributes.getValue("anioEdicion"));
                    }
                }

                public void characters(char[] ch, int start, int length) throws SAXException {
                    String contenido = new String(ch, start, length).trim();
                    if (!contenido.isEmpty()) {
                        System.out.println(" Contenido: " + contenido);
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("libro")) {
                        System.out.println("--------------------");
                    }
                }
            };

            parser.parse("libreria.xml", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
