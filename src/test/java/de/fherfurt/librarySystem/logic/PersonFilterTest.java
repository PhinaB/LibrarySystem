package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PersonFilterTest {

    private PersonFilter personFilter;

    @BeforeEach
    void setUp() {
        personFilter = new PersonFilter();
    }

    // TODO: Konstruktor testen

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