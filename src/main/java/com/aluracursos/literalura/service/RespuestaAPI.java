package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.DatosLibro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaAPI(
        @JsonAlias("count") Integer cantidad,
        @JsonAlias("previous") String linkPaginaAnterior,
        @JsonAlias("next") String linkPaginaPosterior,
        @JsonAlias("results") List<DatosLibro> librosData) {

}
