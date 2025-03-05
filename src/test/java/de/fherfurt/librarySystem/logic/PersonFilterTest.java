package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PersonFilterTest {

    private PersonFilter personFilter;

    @BeforeEach
    void setUp() {
        personFilter = new PersonFilter();
    }

    @Test
    void testPersonFilterConstructor() {
        // Arrange
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("a", "a", "a", false));
        bookList.add(new Book("b", "b", "b", false));

        double minOpenFees = 5.0;
        double maxOpenFees = 10.0;
        int minBooksBorrowed = 2;
        int maxBooksBorrowed = 8;

        // Act
        PersonFilter personFilter1 = new PersonFilter(bookList, minOpenFees, maxOpenFees, minBooksBorrowed, maxBooksBorrowed);

        // Assert
        assertEquals(bookList, personFilter1.getBooks(), "Books list should be correctly set.");
        assertEquals(minOpenFees, personFilter1.getMinOpenFees(), "Min open fees should be correctly set.");
        assertEquals(maxOpenFees, personFilter1.getMaxOpenFees(), "Max open fees should be correctly set.");
        assertEquals(minBooksBorrowed, personFilter1.getMinBooksBorrowed(), "Min books borrowed should be correctly set.");
        assertEquals(maxBooksBorrowed, personFilter1.getMaxBooksBorrowed(), "Max books borrowed should be correctly set.");
    }

    @Test
    void testHasFilterNoFilters() {
        // Act
        boolean result = personFilter.hasFilter();

        // Assert
        assertFalse(result, "Es sollten keine Filter gesetzt sein.");
    }

    @Test
    void testHasFilterWithBooks() {
        // Arrange
        personFilter.setBooks(List.of(new Book("Titel", "Autor", "Genre", false)));

        // Act
        boolean result = personFilter.hasFilter();

        // Assert
        assertTrue(result, "Der Filter sollte auf true setzen, wenn Bücher gesetzt sind.");
    }

    @Test
    void testHasFilterWithMinOpenFees() {
        // Arrange
        personFilter.setMinOpenFees(10.0);

        // Act
        boolean result = personFilter.hasFilter();

        // Assert
        assertTrue(result, "Der Filter sollte auf true setzen, wenn MinOpenFees gesetzt ist.");
    }

    @Test
    void testHasFilterWithMaxOpenFees() {
        // Arrange
        personFilter.setMaxOpenFees(10.0);

        // Act
        boolean result = personFilter.hasFilter();

        // Assert
        assertTrue(result, "Der Filter sollte auf true setzen, wenn MaxOpenFees gesetzt ist.");
    }

    @Test
    void testHasFilterWithMinBooksBorrowed() {
        // Arrange
        personFilter.setMinBooksBorrowed(5);

        // Act
        boolean result = personFilter.hasFilter();

        // Assert
        assertTrue(result, "Der Filter sollte auf true setzen, wenn MinBooksBorrowed gesetzt ist.");
    }

    @Test
    void testHasFilterWithMaxBooksBorrowed() {
        // Arrange
        personFilter.setMaxBooksBorrowed(5);

        // Act
        boolean result = personFilter.hasFilter();

        // Assert
        assertTrue(result, "Der Filter sollte auf true setzen, wenn MaxBooksBorrowed gesetzt ist.");
    }
}