package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.DatosAutor;
import com.aluracursos.literalura.model.DatosLibro;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class GutendexService {
    private final String URL_BASE = "https://gutendex.com/books/";

    public List<DatosLibro> buscarLibros(String busqueda) {
        var json = ConsumoAPI.obtenerDatosAPI
                (URL_BASE + "?search=" + URLEncoder.encode(busqueda.trim().toLowerCase(), StandardCharsets.UTF_8)
        );
        return new ConvierteDatosAPI()
                .convertirDatos(json, RespuestaAPI.class)
                .librosData();
    }


    public List<DatosAutor> mostrarAutoresVivosAño(Integer año) {
        var endpoint = URL_BASE + "?author_year_start=" + año
                + "&author_year_end=" + año;

        var json = ConsumoAPI.obtenerDatosAPI(endpoint);

        return new ConvierteDatosAPI().convertirDatos(json, RespuestaAPI.class)
                .librosData()
                .stream()
                .flatMap(l -> l.autores().stream())
                .toList();
    }
}
