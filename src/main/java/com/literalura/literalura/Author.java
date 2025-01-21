package com.literalura.literalura;

import java.util.List;

public class Author {
    private String name;
    private int birthYear;
    private int deathYear;
    private List<Book> books;

    // Constructor
    public Author(String name, int birthYear, int deathYear, List<Book> books) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.books = books;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    // Para representar al autor en el formato requerido
    @Override
    public String toString() {
        String birth = (birthYear != 0) ? String.valueOf(birthYear) : "Desconocido";
        String death = (deathYear != 0) ? String.valueOf(deathYear) : "Desconocido";
        StringBuilder bookTitles = new StringBuilder();
        for (Book book : books) {
            bookTitles.append(book.getTitle()).append("\n");
        }

        return "Autor: " + name + "\n" +
                "Fecha de nacimiento: " + birth + "\n" +
                "Fecha de fallecimiento: " + death + "\n" +
                "Libros: \n" + bookTitles.toString();
    }
}