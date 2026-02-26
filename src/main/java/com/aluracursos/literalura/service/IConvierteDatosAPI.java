package com.aluracursos.literalura.service;

import java.util.List;

public interface IConvierteDatosAPI {
    <T> T convertirDatos (String json, Class<T> clase);

    <T> List<T> convertirDatosLista(String json, Class<T> clase);
}
