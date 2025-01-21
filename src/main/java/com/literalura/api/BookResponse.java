package com.literalura.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.literalura.literalura.Book;

import java.util.List;

public class BookResponse {
    @JsonProperty("results") // Vincula la propiedad "results" del JSON a esta lista
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}