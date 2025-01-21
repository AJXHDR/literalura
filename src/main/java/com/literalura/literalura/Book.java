package com.literalura.literalura;

public class Book {
    private String title;
    private String author;
    private String language;
    private String downloads;

    // Constructor
    public Book(String title, String author, String language, String downloads) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.downloads = downloads;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public String getDownloads() {
        return downloads;
    }

    // Para representar el libro en el formato que se requiere
    @Override
    public String toString() {
        // Formatear el autor en "Apellido, Nombre"
        String formattedAuthor = formatAuthor(author);

        return "----- LIBRO -----\n" +
                "Título: " + title + "\n" +
                "Autor: " + formattedAuthor + "\n" +
                "Idioma: " + language + "\n" +
                "Número de descargas: " + downloads + "\n" +
                "--------------------";
    }

    // Formatear el autor
    private String formatAuthor(String author) {
        String[] parts = author.split(" ");
        if (parts.length >= 2) {
            return parts[1] + ", " + parts[0];  // Cambiar a "Apellido, Nombre"
        } else {
            return author;  // Si no tiene al menos dos partes, devolverlo tal cual
        }
    }
}