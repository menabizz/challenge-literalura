package com.aluracursos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConvierteDatosAPI implements IConvierteDatosAPI {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convertirDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            System.out.println("Error al convertir los datos JSON: " + e.getMessage());
            System.out.println("JSON recibido: " + json);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> convertirDatosLista(String json, Class<T> clase) {
        CollectionType list = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clase);
        try {
            return objectMapper.readValue(json, list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
