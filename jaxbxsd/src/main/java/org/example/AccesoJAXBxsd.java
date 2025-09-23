/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.example;



/**
 *
 * @author Moncho
 */
public class AccesoJAXBxsd {

    public static void main(String[] args) {

        // Se crea una instancia de ObjectFactory, que es la fábrica de objetos generados a partir del esquema XSD.
        // Esta fábrica contiene métodos para crear instancias de las clases generadas por JAXB, como `Libros` y `Libro`.
        ObjectFactory factory = new ObjectFactory();

        // Se crea una instancia del elemento raíz `Libros` a partir de la fábrica. 
        // `Libros` es el contenedor principal que probablemente corresponde a la estructura XML definida en el XSD.
        Libros libros = factory.createLibros();

        // Se crea una instancia de `Libro` utilizando la fábrica. 
        // `Libro` representa un elemento individual dentro de la lista de libros.
        Libros.Libro libro = factory.createLibrosLibro();

        // Se establecen los valores de los atributos del libro. 
        // Aquí se setean el título, el autor y el año de publicación del libro.
        libro.setTitulo("El Señor de los Anillos");
        libro.setAutor("J.R.R. Tolkien");
        libro.setPublicadoEn(1968);

        // El libro recién creado se agrega a la lista de libros en el objeto `Libros`.
        libros.getLibro().add(libro);

        // Imprime un mensaje indicando que se ha añadido un nuevo libro.
        // El libro recién añadido se obtiene del final de la lista y se muestra como un objeto Java.
        System.out.println("\nAñadido un nuevo libro " + libros.getLibro().get(libros.getLibro().size() - 1) + "\n");

        // Imprime el título del primer libro en la lista, demostrando que el libro ha sido correctamente añadido.
        System.out.println("Título: " + libros.getLibro().get(0).getTitulo());

    }
}
