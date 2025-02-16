package de.fherfurt.librarySystem.models;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testBookCreation(){
        Book book = new Book("Der Hobbit", "J.R.R. Tolkien", "Fantasy");

        assertEquals("Der Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals("Fantasy", book.getGenre());
        assertFalse(book.isDamaged());
    }

@Test
    public void testCompareBooks() {
    Book book1 = new Book("A Game of Thrones", "George R.R Martin", "Fantasy");
    Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy");

    assertTrue(book1.compareTo(book2) < 0); //Alphabetisch vorher
    assertTrue(book2.compareTo(book1) > 0); // Alphabetisch nachher
    }
}