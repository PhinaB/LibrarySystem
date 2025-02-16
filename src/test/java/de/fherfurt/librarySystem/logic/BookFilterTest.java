package de.fherfurt.librarySystem.logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class BookFilterTest {

    @Test
    public void testFilterByGenre() {
        List<Book> books = Arrays.asList(
                new Book("Der Hobbit", "J.R.R. Tolkien", "Fantasy"),
                new Book("1984", "George Orwell", "Dystopie"),
                new Book("Harry Potter", "J.K. Rowling", "Fantasy")
        );

        List<Book> fantasyBooks = BookFilter.filterBooks(books, "Fantasy",null, null);
        assertEquals(2, fantasyBooks.size()));
    }

    @Test
    public void testFilterByAuthor() {
        List<Book> books = Array.asList(
                new Book("Der Hobbit", "J.R.R. Tolkien", "Fantasy"),
                new Book("1984", "George Orwell", "Dystopie"),
                new Book("Animal Farm", "George Orwell", "Dystopie")
        );

        List<Book> orwellBooks = BookFilter.filterBooks(books, null, "George Orwell", null);
        assertEquals(2, orwellBooks.size()));
    }
    @Test
    public void testFilterByTitle() {
        List<Book> books = Arrays.asList(
                new Book("Der Hobbit", "J.R.R. Tolkien", "Fantasy"),
                new Book("1984", "George Orwell", "Dystopie"),
                new Book("Harry Potter", "J.K. Rowling", "Fantasy")
        );

        List<Book> hobbitBooks = BookFilter.filterBooks(books, null, null,"Hobbit");
        assertEquals(1, hobbitBooks.size());
        assertEquals("Der Hobbit", hobbitBooks.get(0).getTitle());
    }

}