package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;

import java.util.List;

/**
 * The PersonFilter class defines filter criteria for searching people in a library system based on
 * borrowed books, outstanding fees, and the number of borrowed books.
 *
 * @author  Josephina Burger
 * @version 1.0
 */
public class PersonFilter {

    private List<Book> books;
    private double minOpenFees;
    private double maxOpenFees;
    private int minBooksBorrowed;
    private int maxBooksBorrowed;

    public PersonFilter() {
    }

    /**
     * Constructor for creating a PersonFilter object with specified criteria for borrowed books,
     * minimum and maximum outstanding fees, and the minimum and maximum number of borrowed books.
     *
     * @param books             the list of books to filter by
     * @param minOpenFees       the minimum outstanding fees to filter by
     * @param maxOpenFees       the maximum outstanding fees to filter by
     * @param minBooksBorrowed  the minimum number of borrowed books to filter by
     * @param maxBooksBorrowed  the maximum number of borrowed books to filter by
     */
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

    /**
     * Method to check if at least one filter criterion is set.
     *
     *  @return true if at least one filter criterion is set (books, open fees, or borrowed books),
     *  false if no criteria are set
     */
    public boolean hasFilter() {
        return (books != null && !books.isEmpty()) ||
                minOpenFees > 0.0 ||
                maxOpenFees > 0.0 ||
                minBooksBorrowed > 0 ||
                maxBooksBorrowed > 0;
    }
}
