package de.fherfurt.librarySystem;

import de.fherfurt.librarySystem.models.Book;

public class Main {
    public static void main(String[] args) {
        Book book1 = new Book("Titel des Buchs", "Xander", "Komödie", false);
        Book book2 = new Book("Titel des Buchssss", "Zeranski", "Horror", true);

        System.out.println(book1);
        System.out.println(book2);
    }
}
