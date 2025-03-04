package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;

public class BookFilter {
    private String title;
    private String author;
    private String genre;

    public BookFilter() {}

    public BookFilter(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public boolean hasFilter() {
        return this.genre != null || this.author != null || this.title != null;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
}
