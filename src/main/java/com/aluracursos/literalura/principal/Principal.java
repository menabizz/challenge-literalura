package com.aluracursos.literalura.principal;


import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.GutendexService;
import java.util.List;
import java.util.Scanner;


public class Principal {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final GutendexService gutendexService;

    private Scanner teclado = new Scanner(System.in);


    public Principal(LibroRepository libroRepository,
                     AutorRepository autorRepository,
                     GutendexService gutendexService) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.gutendexService = gutendexService;
    }


    public void muestraMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    
                    *******************************************************
                    Escoja una opción:
                    
                    1 - Buscar libro por título.
                    2 - Mostrar lista de libros registrados.
                    3 - Mostrar lista de autores registrados.
                    4 - Buscar autores vivos en un determinado año.
                    5 - Listar libros registrados por idioma.
                    6 - Mostrar los 10 libros más descargados.
                    
                    0 - Salir
                    
                    *******************************************************
                    
                    """;
            System.out.println(menu);

            if (teclado.hasNextInt()) {
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresPorAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    listarLibrosMasDescargados();
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("\n[!] Opción inválida [!]");
            }

            } else {
                System.out.println("\n[!] Opción inválida [!]");
                teclado.nextLine();
            }
        }
    }



    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();

        var datosLibro = gutendexService.buscarLibros(nombreLibro).stream().findFirst();

        if (datosLibro.isEmpty()) {
            System.out.println("No se ha encontrado ningún libro con ese nombre.");
        } else {
            var libro = new Libro(datosLibro.get());
            var autor = libro.getAutor();

            // 1. Buscar el autor en la base de datos por nombre
            var autorExistente = autorRepository.findByNombreAutor(autor.getNombreAutor());

            // 2. Si el autor ya existe, usar el autor existente
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                // 3. Si el autor no existe, guardar el nuevo autor
                autorRepository.save(autor);
            }

            libro.setAutor(autor); // Asignar el autor (existente o nuevo) al libro

            // 4. Buscar el libro en la base de datos por título
            var libroExistente = libroRepository.findByTitulo(libro.getTitulo());

            // 5. Si el libro ya existe, no hacer nada
            if (libroExistente.isPresent()) {
                System.out.println("El libro ya existe en la base de datos.");
            } else {
                // 6. Si el libro no existe, guardar el nuevo libro
                libroRepository.save(libro);
                System.out.println(libro);
            }
        }
    }

    private void mostrarLibrosRegistrados() {
        var librosLista = libroRepository.findAll();
        if (librosLista.isEmpty())
            System.out.println("Ningún libro ha sido registrado aún.");

        librosLista.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        var autoresLista = autorRepository.findAll();

        if (autoresLista.isEmpty())
            System.out.println("Ningún autor ha sido registrado aún.");

        autoresLista.forEach(System.out::println);;

    }

    private void buscarAutoresPorAño() {
        System.out.println("Escriba el año deseado:");
        var año = teclado.nextInt();

        var autores = autorRepository.findByAñoNacimientoLessThanEqualAndAñoMuerteGreaterThanEqual(año, año);

        if (autores.isEmpty())
            System.out.println("Ningún autor ha sido encontrado.");
        autores.forEach(System.out::println);

    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                - es => Español
                - en => Inglés
                - fr => Francés
                - pt => Portugués
                
                """);

        String idioma = teclado.nextLine();
        boolean idiomaValido = false;

        List<String> posiblesIdiomas = List.of("es", "en", "fr", "pt");

        idiomaValido = posiblesIdiomas.stream().anyMatch(l -> l.equalsIgnoreCase(idioma));

        if (!idiomaValido) {
            System.out.println("Ese idioma no se encuentra disponible. Ingrese un idioma válido");
        } else {
            var libros = libroRepository.findByIdioma(idioma);
            var cantidad = libroRepository.countByIdioma(idioma);

            System.out.println("\nHay " + cantidad + " libro"
                    + (cantidad != 1 ? "s" : "") + " disponible" + (cantidad != 1 ? "s" : "") + ":\n");

            libros.forEach(System.out::println);

            if (libros.isEmpty())
                System.out.println("No se ha encontrado ningún libro.");

        }


    }

    private void listarLibrosMasDescargados() {
        var masDescargados = libroRepository.findTop10ByOrderByNumeroDescargasDesc();

        System.out.println("\n-------- TOP 10 --------\n");
        masDescargados.forEach(System.out::println);
        System.out.println("\n------------------------\n");

        if (masDescargados.isEmpty())
            System.out.println("No se ha encontrado ningún libro.");

    }



}