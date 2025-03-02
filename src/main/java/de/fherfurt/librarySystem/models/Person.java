package de.fherfurt.librarySystem.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Person implements Comparable<Person> {
    private static int idCounter=0;
    private final int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private final List<Book> borrowedBooks;
    private double openFees;

    public Person(String firstName, String lastName, LocalDate birthDate, Address address) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
        this.openFees = 1.0;
    }

    public Person(String firstName, String lastName, LocalDate birthDate){
        this(firstName, lastName, birthDate, null);
    }

    public int getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<List<Book>> getBorrowedBooks() {
        return Optional.ofNullable(borrowedBooks);
    }

    //TODO: Bücher werden glöscht und neue Liste hinzugefügt-> so gewollt? benötigt für die editPerson
    public void setBorrowedBooks(List<Book> borrowedBooks) {
        if (borrowedBooks != null) {
            this.borrowedBooks.clear();
            this.borrowedBooks.addAll(borrowedBooks);
        }
    }

    public double getOpenFees() {
        return openFees;
    }

    private void setOpenFees(double openFees) {
        this.openFees = openFees;
    }

    public void addFee(double newFee){
        this.openFees += newFee;
    }

    public void addNewBorrowedBook(Book book){
        // TODO übergebenes Buch könnte null sein
        if (!this.borrowedBooks.contains(book)) {
            this.borrowedBooks.add(book);
        }
    }

    public void removeBorrowedBook(Book book){
        // TODO: Bücher können nur entfernt werden, wenn sie vorher in der ArrayList waren -> Fehler muss abgefangen werden
        // TODO übergebenes Buch könnte null sein
        this.borrowedBooks.remove(book);
    }

    public int countBorrowedBooks() {return borrowedBooks == null ? 0 : borrowedBooks.size();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        // id- Prüfung reicht eigentlich
        return id == person.id && Double.compare(openFees, person.openFees) == 0 && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(address, person.address) && Objects.equals(borrowedBooks, person.borrowedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, address, borrowedBooks, openFees);
    }

    @Override
    public String toString() {
        return String.format(
                "Person Details:\n" +
                " Name (Date of Birth): %s, %s (%s)\n" +
                " Address: %s\n" + // TODO: soll das auch englisch sein?
                " Borrowed Books: %s\n" +
                " Open Fees: %.2f",
                firstName,
                lastName,
                birthDate,
                address != null ? address.toString() : "No address registered.",
                borrowedBooks != null ? borrowedBooks.toString() : "No borrowed books.",
                openFees);
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.id, o.id);
    }
}
