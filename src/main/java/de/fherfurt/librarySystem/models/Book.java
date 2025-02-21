package de.fherfurt.librarySystem.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Book implements Comparable<Book> {
    private static int nextId = 1;

    private final int id;
    private String title;
    private String author;
    private String genre;
    private boolean isDamaged;
    private Person personBorrowed;
    private LocalDate borrowDate;
    static final double feeForOneWeek = 5.00;
    static final double feeForDamagedBook = 10.00;

    public Book(String title, String author, String genre, boolean isDamaged) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isDamaged = isDamaged;

        this.id = nextId++;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public Optional<Person> getPersonBorrowed() {
        return Optional.ofNullable(personBorrowed);
    }

    public Optional<LocalDate> getBorrowDate() {
        return Optional.ofNullable(borrowDate);
    }

    public static double getFeeForOneWeek() {
        return feeForOneWeek;
    }

    public static double getFeeForDamagedBook() {
        return feeForDamagedBook;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBorrow(Person personBorrowed) {
        this.borrowDate = LocalDate.now();
        this.personBorrowed = personBorrowed;
    }

    public void removeBorrow() {
        this.personBorrowed = null;
        this.borrowDate = null;
    }

    @Override
    public String toString() {
        return String.format(
            "Buch-Details:\n" +
            " ID: %d\n" +
            " Titel: %s\n" +
            " Autor: %s\n" +
            " Genre: %s\n" +
            " Zustand: %s\n" +
            " Ausgeliehen von: %s\n" +
            " Ausleihdatum: %s",
            id,
            title,
            author,
            genre,
            isDamaged ? "Beschädigt" : "Gut",
            personBorrowed != null ? personBorrowed.toString() : "Niemand",
            borrowDate != null ? borrowDate.toString() : "Nicht ausgeliehen"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && isDamaged == book.isDamaged && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(personBorrowed, book.personBorrowed) && Objects.equals(borrowDate, book.borrowDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, isDamaged, personBorrowed, borrowDate);
    }

    @Override
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }
}
