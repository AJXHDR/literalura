package com.literalura.api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.literalura.Book;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BookApiClient {
    private static final String API_URL = "https://openlibrary.org/search.json?title=";

    public Book getBookByTitle(String title) {
        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            URI uri = URI.create(API_URL + encodedTitle);

            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(inputStream);

                if (rootNode.get("docs") != null && rootNode.get("docs").size() > 0) {
                    JsonNode firstBook = rootNode.get("docs").get(0);
                    String bookTitle = firstBook.get("title").asText();
                    String author = firstBook.has("author_name") ? firstBook.get("author_name").get(0).asText() : "Desconocido";
                    String language = firstBook.has("language") ? firstBook.get("language").get(0).asText() : "Desconocido";

                    // Verificamos las descargas y asignamos un valor predeterminado si no hay información
                    double downloads = firstBook.has("download_count") ?
                            firstBook.get("download_count").isNumber() ? firstBook.get("download_count").asDouble() : 0.0 : 0.0;
                    String downloadCount = (downloads > 0) ? String.valueOf(downloads) : "Desconocido";  // Si no hay descargas, lo mostramos como "Desconocido"

                    return new Book(bookTitle, author, language, downloadCount);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener libro de la API", e);
        }
    }

    // Metodo para formatear el autor en "Apellido, Nombre"
    private String formatAuthor(String author) {
        String[] parts = author.split(" ");
        if (parts.length > 1) {
            return parts[1] + ", " + parts[0];
        }
        return author;  // Si no hay apellido/nombre, devuelvelo tal cual
    }

    // Metodo para obtener el nombre completo del idioma
    private String getFullLanguageName(String languageCode) {
        switch (languageCode) {
            case "eng": return "Inglés";
            case "spa": return "Español";
            case "hun": return "Húngaro";
            default: return languageCode; // Si no lo encuentras, devuelves el código
        }
    }
}