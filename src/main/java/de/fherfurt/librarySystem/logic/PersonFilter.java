package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonFilter {

    private List<Book> books;
    private double minOpenFees;
    private double maxOpenFees;
    private int minBooksBorrowed;
    private int maxBooksBorrowed;

    public PersonFilter() {
    }

    public PersonFilter(List<Book> books, double minOpenFees, double maxOpenFees, int minBooksBorrowed, int maxBooksBorrowed) {
        this.books = books;
        this.minOpenFees = minOpenFees;
        this.maxOpenFees = maxOpenFees;
        this.minBooksBorrowed = minBooksBorrowed;
        this.maxBooksBorrowed = maxBooksBorrowed;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getMinOpenFees() {
        return minOpenFees;
    }

    public void setMinOpenFees(double minOpenFees) {
        this.minOpenFees = minOpenFees;
    }

    public double getMaxOpenFees() {
        return maxOpenFees;
    }

    public void setMaxOpenFees(double maxOpenFees) {
        this.maxOpenFees = maxOpenFees;
    }

    public int getMinBooksBorrowed() {
        return minBooksBorrowed;
    }

    public void setMinBooksBorrowed(int minBooksBorrowed) {
        this.minBooksBorrowed = minBooksBorrowed;
    }

    public int getMaxBooksBorrowed() {
        return maxBooksBorrowed;
    }

    public void setMaxBooksBorrowed(int maxBooksBorrowed) {
        this.maxBooksBorrowed = maxBooksBorrowed;
    }
}
