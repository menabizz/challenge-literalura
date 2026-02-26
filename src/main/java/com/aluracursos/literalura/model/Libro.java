package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(){}

//    public static Libro fromDatosLibro(DatosLibro datosLibro){
//        var libro = new Libro();
//        libro.id = Long.valueOf(datosLibro.id());
//        libro.titulo = datosLibro.titulo();
//        libro.numeroDescargas = datosLibro.numeroDescargas();
//
//        if(!datosLibro.idiomas().isEmpty()) {
//            libro.idioma = datosLibro.idiomas().get(0);
//        }
//
//        if(!datosLibro.autores().isEmpty()) {
//            libro.autor = Autor.fromDatosAutor(datosLibro.autores().get(0));
//        }
//
//        return libro;
//    }

    public Libro (DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.numeroDescargas = datosLibro.numeroDescargas();

        if(!datosLibro.idiomas().isEmpty()) {
            this.idioma = datosLibro.idiomas().get(0);
        }

        if(!datosLibro.autores().isEmpty()) {
            this.autor = Autor.fromDatosAutor(datosLibro.autores().get(0));
        }

    }






    // GETTERS Y SETTERS


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdiomas(String idiomas) {
        this.idioma = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {
        return "\n-------- LIBRO --------\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor == null ? "-" : autor.getNombreAutor()) + "\n" +
                "Idioma: " + (idioma == null ? "-" : idioma) + "\n" +
                "Número de descargas: " + numeroDescargas + "\n" +
                "-----------------------\n";
    }
}



