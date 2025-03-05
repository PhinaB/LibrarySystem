package de.fherfurt.librarySystem.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookFilterTest {

    private BookFilter bookFilter;

    @BeforeEach
    void setUp() {
        bookFilter = new BookFilter();
    }

    // TODO: Konstruktor testen

    @Test
    void testHasFilterNoFilters() {
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Assert
        assertFalse(resultHasFilter, "No filter should be set.");
    }
    @Test
    void testHasFilterWithGenre() {
        //Arrange
        bookFilter.setGenre("Genre");
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Assert
        assertTrue(resultHasFilter, "A filter should be set.");
    }

    @Test
    void testHasFilterWithAuthor() {
        //Arrange
        bookFilter.setAuthor("Author");
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Asert
        assertTrue(resultHasFilter, "A filter should be set.");
    }

    @Test
    void testHasFilterWithTitle() {
        //Arrange
        bookFilter.setTitle("Title");
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Assert
        assertTrue(resultHasFilter, "A filter should be set.");
    }
}