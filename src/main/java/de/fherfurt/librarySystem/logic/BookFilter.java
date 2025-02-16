package de.fherfurt.librarySystem.logic;
import java.util.List;
import java.util.stream.Collectors;

public class BookFilter {
public static List<Book> filterBooks(List<Book> books, String genre,String author, String title) {
    return books.stream()
            .filter(book -> genre == null || book.getGenre(). equalsIgnoreCase(genre))
            .filter(book -> author == null || book.getAuthor().equalsIgnoreCase(author))
            .filter(book -> title == null  || book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect(Collectors.toList());
}
}
