package de.fherfurt.librarySystem.logic;

/**
 * The BookFilter class defines filter criteria for searching books in a library system based on title, author and genre.
 *
 * @author  Lucas-Manfred Herpe
 * @version 1.0
 */
public class BookFilter {
    private String title;
    private String author;
    private String genre;

    public BookFilter() {}

    /**
     * Constructor for creating a BookFilter object with specified criteria for title, author, and genre.
     *
     * @param title     the title filter criterion for searching books
     * @param author    the author filter criterion for searching books
     * @param genre     the genre filter criterion for searching books
     */
    public BookFilter(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    /**
     * Method to check if at least one filter criterion is set.
     *
     * @return true if any of the filter criteria (title, author, or genre) is set, false otherwise
     */
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
