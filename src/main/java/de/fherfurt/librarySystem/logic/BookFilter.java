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

    //TODO: die Methode ist noch nicht ganz korrekt. Wie wollen hier nur schauen, ob ein Filter tatsächlich gesetzt wurde.
    //TODO: siehe auch hasFilter bei PersonFilter
    public boolean hasFilter(Book book) {
        if (book == null) return false;
        return (this.genre == null || this.genre.equalsIgnoreCase(book.getGenre())) &&
                (this.author == null || this.author.equalsIgnoreCase(book.getAuthor())) &&
                (this.title == null || book.getTitle().toLowerCase().contains(this.title.toLowerCase()));
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
