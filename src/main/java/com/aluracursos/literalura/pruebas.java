package com.aluracursos.literalura;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatosAPI;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class pruebas {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        ConsumoAPI consumoAPI = new ConsumoAPI();
        String URL_API = "https://gutendex.com/books";
        ConvierteDatosAPI convierteDatosAPI = new ConvierteDatosAPI();



        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        String nombreLibroArreglado = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);

        var json = consumoAPI.obtenerDatosAPI(URL_API + "?search=" + nombreLibroArreglado);

        DatosLibro datosLibro = convierteDatosAPI.convertirDatos(json, DatosLibro.class);
        System.out.println(datosLibro);

        }
    }

