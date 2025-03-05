package de.fherfurt.librarySystem.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BookFilter} class.
 * This test class ensures that the filtering logic behaves as expected.
 */

class BookFilterTest {

    private BookFilter bookFilter;
    /**
     * Sets up a new instance of {@link BookFilter} before each test.
     */
    @BeforeEach
    void setUp() {
        bookFilter = new BookFilter();
    }

    /**
     * Tests the constructor of {@link BookFilter} to ensure that the provided
     * title, author, and genre values are correctly assigned.
     */

    @Test
    void testBookFilterConstructor(){
        //Arrange
        String title1 = "title1";
        String author1= "author1";
        String genre1 = "genre1";

        String title2 = "title2";
        String author2 = "author2";
        String genre2 = "genre2";

        //Act
        BookFilter bookFilter1 = new BookFilter(title1, author1, genre1);
        BookFilter bookFilter2 = new BookFilter(title2, author2, genre2);

        //Assert
        assertEquals(title1, bookFilter1.getTitle(), "The title should be the same");
        assertEquals(author1, bookFilter1.getAuthor(), "The author should be the same");
        assertEquals(genre1, bookFilter1.getGenre(), "The genre should be the same");

        assertEquals(title2, bookFilter2.getTitle(), "The title should be the same");
        assertEquals(author2, bookFilter2.getAuthor(), "The author should be the same");
        assertEquals(genre2, bookFilter2.getGenre(), "The genre should be the same");
    }

    /**
     * Tests if {@link BookFilter#hasFilter()} correctly returns false when no filters are set.
     */

    @Test
    void testHasFilterNoFilters() {
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Assert
        assertFalse(resultHasFilter, "No filter should be set.");
    }

    /**
     * Tests if {@link BookFilter#hasFilter()} correctly returns true when the genre filter is set.
     */

    @Test
    void testHasFilterWithGenre() {
        //Arrange
        bookFilter.setGenre("Genre");
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Assert
        assertTrue(resultHasFilter, "A filter should be set.");
    }

    /**
     * Tests if {@link BookFilter#hasFilter()} correctly returns true when the author filter is set.
     */

    @Test
    void testHasFilterWithAuthor() {
        //Arrange
        bookFilter.setAuthor("Author");
        //Act
        boolean resultHasFilter = bookFilter.hasFilter();
        //Asert
        assertTrue(resultHasFilter, "A filter should be set.");
    }

    /**
     * Tests if {@link BookFilter#hasFilter()} correctly returns true when the title filter is set.
     */

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