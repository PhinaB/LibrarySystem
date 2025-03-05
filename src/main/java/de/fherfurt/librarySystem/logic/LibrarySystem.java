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

public class LibrarySystem {
    private final Map<Integer, Integer> borrowBookPersons;
    private final List<Person> persons;
    private final List<Book> books;
    private boolean errorLogged = false;

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

    public void deletePerson (Person person) {
        if(person == null) {
            System.out.println("Person is null.");
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

    public boolean gaveBookBack(Book book, Person person, boolean isNowDamaged) {
        errorLogged = false;

        if (borrowBookPersons.containsValue(person.getId()) && borrowBookPersons.get(person.getId()).equals(book.getId())) {
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

    public boolean wasErrorLogged() {
        return errorLogged;
    }

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

    public List<Person> filterPersons(PersonFilter personFilter) {
        if(!personFilter.hasFilter()){
            return persons;
        }
        else {
            return persons.stream()
                    .filter(person -> personFilter.getMinOpenFees() == 0.0 || person.getOpenFees() >= personFilter.getMinOpenFees())
                    .filter(person -> personFilter.getMaxOpenFees() == 0.0 || person.getOpenFees() <= personFilter.getMaxOpenFees())
                    .filter(person -> personFilter.getMinBooksBorrowed() == 0 || person.countBorrowedBooks() >= personFilter.getMinBooksBorrowed())
                    .filter(person -> personFilter.getMaxBooksBorrowed() == 0 || person.countBorrowedBooks() <= personFilter.getMaxBooksBorrowed())
                    .filter(person -> personFilter.getBooks() == null || person.getBorrowedBooks().stream().anyMatch(book -> personFilter.getBooks().contains(book)))
                    .collect(Collectors.toList());
        }
    }

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

        for (Person person : persons) {
            sb.append(person.toString()).append(";\n");
        }

        sb.append("\n\n books: ");
        for (Book book : books) {
            sb.append(book.toString()).append(";\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
