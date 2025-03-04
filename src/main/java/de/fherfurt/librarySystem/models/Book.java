package de.fherfurt.librarySystem.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Book implements Comparable<Book> {
    private static int idCounter = 1;

    private final int id;
    private String title;
    private String author;
    private String genre;
    private boolean isDamaged;
    private boolean isBorrowed;
    private Person personBorrowed;
    private LocalDate borrowDate;
    static final double feeForOneWeek = 5.00;
    static final double feeForDamagedBook = 10.00;

    public Book(String title, String author, String genre, boolean isDamaged) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isDamaged = isDamaged;

        this.id = idCounter++;
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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDamaged(boolean damaged) {
        if(isDamaged) {
            return;
        }
        isDamaged = damaged;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the book as borrowed and saves the current date as the borrow date.
     *
     * @param personBorrowed The person who borrowed the book.
     */
    public void setBorrow(Person personBorrowed) {
        this.setBorrow(personBorrowed, LocalDate.now());
    }

    /**
     * Sets the book as borrowed and allows you to set a specific borrowing date.
     * This method is primarily intended for testing purposes.
     *
     * @param personBorrowed The person who borrowed the book.
     * @param borrowDate     The date on which the book should be marked as borrowed.
     */
    public void setBorrow(Person personBorrowed, LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        this.personBorrowed = personBorrowed;
        this.isBorrowed = true;
    }

    public void removeBorrow() {
        this.personBorrowed = null;
        this.borrowDate = null;
        this.isBorrowed = false;
    }

    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return String.format(
            "book details:\n" +
            " ID: %d\n" +
            " title: %s\n" +
            " author: %s\n" +
            " genre: %s\n" +
            " damaged: %s\n" +
            " borrowed by: %s\n" +
            " borrow date: %s",
            id,
            title,
            author,
            genre,
            isDamaged ? "damaged" : "not damaged",
                personBorrowed != null ? personBorrowed.getFirstName() + " " + personBorrowed.getLastName() : "no one",
                borrowDate != null ? borrowDate.toString() : "not borrowed"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && isDamaged == book.isDamaged && isBorrowed == book.isBorrowed && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(personBorrowed, book.personBorrowed) && Objects.equals(borrowDate, book.borrowDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, isDamaged, isBorrowed, personBorrowed, borrowDate);
    }

    @Override
    public int compareTo(Book otherBook) {
        return this.title.compareTo(otherBook.title);
    }
}
