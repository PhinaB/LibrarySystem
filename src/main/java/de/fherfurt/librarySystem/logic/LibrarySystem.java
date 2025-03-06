package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Address;
import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

/**
 * The LibrarySystem class manages books, people, and book lending in a library.
 *
 * @author  Stephanie Wachs, Josephina Burger, Lucas-Manfred Herpe
 * @version 1.0
 */
public class LibrarySystem {
    private final Map<Integer, Integer> borrowBookPersons;
    private final List<Person> persons;
    private final List<Book> books;
    private boolean errorLogged = false;

    /**
     * Constructor for creating a LibrarySystem object.
     */
    public LibrarySystem () {
        this.borrowBookPersons = new HashMap<>();
        this.persons = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Map<Integer, Integer> getBorrowBookPersons() {
        return borrowBookPersons;
    }

    /**
     * Adds a book to the library system if it is not already present.
     *
     * @param book  the book to be added
     * @return      the added book if successful, or null if the book is null or already exists in the LibrarySystem
     */
    public Book addBook(Book book) {
        if(book==null) {
            System.out.println("Book is null.");
            return null;
        }
        if(books.contains(book)) {
            System.out.println("Book is already in the list.");
            return null;
        }
        books.add(book);
        return book;
    }

    /**
     * Updates the details of a book, identified by its ID.
     *
     * @param bookId    the ID of the book to edit
     * @param title     the new title of the book
     * @param author    the new author of the book
     * @param genre     the new genre of the book
     * @return true if the book was found and updated, false otherwise
     */
    public boolean editBook(int bookId, String title, String author, String genre) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                if (title != null && !title.isBlank()) {
                    book.setTitle(title);
                }
                if (author != null && !author.isBlank()) {
                    book.setAuthor(author);
                }
                if (genre != null && !genre.isBlank()) {
                    book.setGenre(genre);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a book from the library system if it is not borrowed.
     *
     * @param book the book to be removed
     */
    public void deleteBook(Book book) {
        if (book == null) {
            System.out.println("Das Buch darf nicht null sein.");
            return;
        }
        if(book.isBorrowed() && borrowBookPersons.containsKey(book.getId())){
            System.out.println("The book cannot be deleted because it is still borrowed.");
            return;
        }
        books.remove(book);
    }

    /**
     * Adds a new person to the LibrarySystem.
     *
     * @param person    the person to be added
     * @return          the added person, or null if the person is already in the system or is null
     */
    public Person addPerson(Person person) {
        if(person==null){
            System.out.println("Person is null.");
            return null;
        }
        if(persons.contains(person)){
            System.out.println("Person is already in the list.");
            return null;
        }
        persons.add(person);
        return person;
    }

    /**
     * Updates the details of a person, identified by their ID.
     *
     * @param personId  the ID of the person to edit
     * @param firstName the new first name
     * @param lastName  the new last name
     * @param birthDate the new birthdate
     * @param address   the new address
     * @return          true if the person was found and updated, false otherwise
     */
    public boolean editPerson(int personId, String firstName, String lastName, LocalDate birthDate, Address address) {
        for (Person person : persons) {
            if(person.getId() == personId) {
                if(firstName != null && !firstName.isBlank()) {
                    person.setFirstName(firstName);
                }
                if(lastName != null && !lastName.isBlank()) {
                    person.setLastName(lastName);
                }
                if(birthDate != null) {
                    person.setBirthDate(birthDate);
                }
                if(address != null) {
                    person.setAddress(address);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a person from the system if they have no outstanding fees and no borrowed books.
     *
     * @param person the person to be removed
     */
    public void deletePerson (Person person) {
        if(person == null) {
            System.out.println("Person is null.");
            return;
        }

        int personId = person.getId();
        if(borrowBookPersons.containsValue(personId)) {
            System.out.println("Person with the Id " + personId + " cannot be delete because following books are still borrowed: " + person.getBorrowedBooks());
            return;
        }
        if(person.getOpenFees() > 0) {
            System.out.println("Person with Id " + personId + " cannot be delete due to the following unpaid: " + person.getOpenFees());
            return;
        }
        persons.remove(person);
    }

    /**
     * Allows a person to borrow a book if it is available.
     *
     * @param book      the book to be borrowed
     * @param person    the person borrowing the book
     * @return          true if the book was successfully borrowed, false otherwise
     */
    public boolean borrowBook(Book book, Person person) {
        errorLogged = false;

        if (!books.contains(book)) {
            return false;
        }
        if (!persons.contains(person)) {
            return false;
        }
        if (borrowBookPersons.containsKey(book.getId())) {
            return false;
        }

        try {
            person.addNewBorrowedBook(book);
        } catch (IllegalArgumentException e) {
            errorLogged = true;
            System.out.println("An error has occurred: "+e.getMessage());
            return false;
        }

        book.setBorrow(person);
        borrowBookPersons.put(book.getId(), person.getId());
        return true;
    }

    /**
     * Handles the return of a borrowed book and applies fees if necessary.
     *
     * @param book          the book being returned
     * @param person        the person returning the book
     * @param isNowDamaged  true if the book is returned damaged, false otherwise
     * @return              true if the return was successful, false otherwise
     */
    public boolean gaveBookBack(Book book, Person person, boolean isNowDamaged) {
        errorLogged = false;

        if (borrowBookPersons.containsValue(person.getId()) && borrowBookPersons.get(book.getId()).equals(person.getId())) {
            try {
                person.removeBorrowedBook(book);
            } catch (IllegalArgumentException e) {
                errorLogged = true;
                System.out.println("An error has occurred: "+e.getMessage());
                return false;
            }

            book.removeBorrow();
            double newFee = this.calculateFeeForBook(book, isNowDamaged);
            book.setBorrowed(false);
            person.addFee(newFee);
            return true;
        }
        return false;
    }

    /**
     * Checks if an error was logged during the borrowing or returning process.
     *
     * @return true if an error occurred, false otherwise
     */
    public boolean wasErrorLogged() {
        return errorLogged;
    }

    /**
     * Calculates the late fee for a returned book based on its return date and condition.
     *
     * @param book          the book being returned
     * @param isNowDamaged  true if the book is now damaged, false otherwise
     * @return              the calculated fee
     */
    public double calculateFeeForBook(Book book, boolean isNowDamaged) {
        double fee = 0.0;
        if (isNowDamaged && !book.isDamaged()) {
            book.setDamaged(true);
            fee += Book.getFeeForDamagedBook();
        }

        int daysLate = (int) ChronoUnit.DAYS.between(book.getBorrowDate().orElse(LocalDate.now()), LocalDate.now());
        if (daysLate <= 30) {
            return fee;
        }

        double weeksLate = Math.ceil(daysLate / 7.0);
        fee += weeksLate * Book.getFeeForOneWeek();

        return fee;
    }

    /**
     * Filters persons based on the given criteria.
     *
     * @param personFilter  the filter containing criteria for filtering persons
     * @return              a list of persons matching the filter criteria
     */
    public List<Person> filterPersons(PersonFilter personFilter) {
        if(!personFilter.hasFilter()){
            return persons;
        }
        else {
            return persons.stream()
                    .filter(person -> personFilter.getMinOpenFees() == 0.0 || person.getOpenFees() >= personFilter.getMinOpenFees())
                    .filter(person -> personFilter.getMaxOpenFees() == 0.0 || person.getOpenFees() <= personFilter.getMaxOpenFees())
                    .filter(person -> personFilter.getMinBooksBorrowed() == 0 || person.getBorrowedBooks().size() >= personFilter.getMinBooksBorrowed())
                    .filter(person -> personFilter.getMaxBooksBorrowed() == 0 || person.getBorrowedBooks().size() <= personFilter.getMaxBooksBorrowed())
                    .filter(person -> personFilter.getBooks() == null || person.getBorrowedBooks().stream().anyMatch(book -> personFilter.getBooks().contains(book)))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Filters books based on the given criteria.
     *
     * @param bookFilter    the filter containing criteria for filtering books
     * @return              a list of books matching the filter criteria
     */
    public List<Book> filterBooks(BookFilter bookFilter) {
        if (bookFilter == null || !bookFilter.hasFilter()) {
            return books;
        }
        return books.stream()
                .filter(book -> bookFilter.getGenre() == null || book.getGenre().equalsIgnoreCase(bookFilter.getGenre()))
                .filter(book -> bookFilter.getAuthor() == null || book.getAuthor().equalsIgnoreCase(bookFilter.getAuthor()))
                .filter(book -> bookFilter.getTitle() == null ||
                        (book.getTitle() != null && book.getTitle().toLowerCase().contains(bookFilter.getTitle().toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LibrarySystem{")
          .append(" persons:");

        if (persons.isEmpty()) {
            sb.append(" No people registered.");
        } else {
            for (Person person : persons) {
                sb.append("  ").append(person.toString()).append(";\n\n");
            }
        }

        sb.append("\n\n books: ");
        if (books.isEmpty()) {
            sb.append(" No books in inventory.");
        } else {
            for (Book book : books) {
                sb.append("  ").append(book.toString()).append(";\n\n"); // Zeilenumbrüche für mehr Übersicht
            }
        }

        sb.append("}\n");
        return sb.toString();
    }
}
