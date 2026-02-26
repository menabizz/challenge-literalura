package com.aluracursos.literalura.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "nombre_autor")
    private String nombreAutor;

    @Column(name = "año_nacimiento")
    private Integer añoNacimiento;

    @Column(name = "año_muerte")
    private Integer añoMuerte;


    // Mapeamos la relación de que cada serie tiene una lista de episodios, y que lo que cambiemos en uno cambie en el otro
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER) // Precarga los datos para traerlos sin pedirlos en métodos especificos
    private List<Libro> libros;

    public Autor(){}

    public static Autor fromDatosAutor(DatosAutor datosAutor) {
        Autor autor = new Autor();
        autor.nombreAutor = datosAutor.nombreAutor();
        autor.añoMuerte = datosAutor.añoMuerte();
        autor.añoNacimiento = datosAutor.añoNacimiento();
        return autor;
    }




    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(Integer añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public Integer getAñoMuerte() {
        return añoMuerte;
    }

    public void setAñoMuerte(Integer añoMuerte) {
        this.añoMuerte = añoMuerte;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        String nombresLibros = libros != null ? libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ")) : "-";

        return "\n-------- AUTOR --------\n" +
                "Nombre: " + nombreAutor + "\n" +
                "Fecha de nacimiento: " + (añoNacimiento == null ? "-" : añoNacimiento.toString()) + "\n" +
                "Fecha de Fallecimiento: " + (añoMuerte == null ? "-" : añoMuerte.toString()) + "\n" +
                "Libros: " + nombresLibros + "\n" +
                "-----------------------\n";
    }

}
