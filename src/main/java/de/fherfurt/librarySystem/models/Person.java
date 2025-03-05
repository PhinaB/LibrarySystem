package de.fherfurt.librarySystem.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.lang.CloneNotSupportedException;

/**
 * The Person class represents a person consisting of ID, first name, last name, birthdate, address,
 * borrowed books and open fees.
 * Also implements Comparable and Cloneable.
 *
 * @see     Comparable
 * @see     Cloneable
 * @author  Josephina Burger
 * @version 1.0
 */
public class Person implements Comparable<Person>, Cloneable {
    private static int idCounter = 1;
    private final int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private final List<Book> borrowedBooks;
    private double openFees;


    /**
     * Constructor to create a new Person with all fields initialized.
     * The book ID is automatically assigned based on a counter.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @param birthDate the birthdate of the person
     * @param address   the address of the person
     */
    public Person(String firstName, String lastName, LocalDate birthDate, Address address) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
        this.openFees = 0.0;
    }

    /**
     * Constructor to create a new Person without an address.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @param birthDate the birthdate of the person
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
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

    /**
     * Returns the address of the person wrapped in an Optional.
     * This ensures that the address can be safely checked for null.
     *
     * @return an Optional containing the address of the person or an empty Optional if no address is set
     */
    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Book> getBorrowedBooks() {
        return this.borrowedBooks;
    }

    public double getOpenFees() {
        return openFees;
    }

    private void setOpenFees(double openFees) {
        this.openFees = openFees;
    }

    /**
     * Adds a fee to the person's open fees. Throws an IllegalArgumentException if the fee is negative.
     *
     * @param newFee the fee amount to add
     */
    public void addFee(double newFee){
        if (newFee < 0) {
            throw new IllegalArgumentException("Fee cannot be negative.");
        }
        this.openFees += newFee;
    }

    /**
     * Adds a new borrowed book to the person's list of borrowed books.
     * Throws an IllegalArgumentException if the book is null.
     *
     * @param book the book to add to the list of borrowed books
     */
    public void addNewBorrowedBook(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book is null!");
        }
        if (!this.borrowedBooks.contains(book)) {
            this.borrowedBooks.add(book);
        }
    }

    /**
     * Removes a borrowed book from the person's list of borrowed books.
     * Throws an IllegalArgumentException if the book is null.
     * If the book is not found in the list of borrowed books, a message is printed.
     *
     * @param book the book to remove from the list of borrowed books
     */
    public void removeBorrowedBook(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book is null!");
        }
        if(!this.borrowedBooks.contains(book)){
            System.out.println("The Book is not part of the list of borrowed books.");
        }
        this.borrowedBooks.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Double.compare(openFees, person.openFees) == 0 && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(address, person.address) && Objects.equals(borrowedBooks, person.borrowedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, address, borrowedBooks, openFees);
    }

    /**
     * Compares this person to another person based on their unique identifier (ID).
     *
     * @param otherPerson   The person to compare this person with.
     * @return              A negative integer, zero, or a positive integer if this person's ID
     *                      is less than, equal to, or greater than the other person's ID.
     */
    @Override
    public int compareTo(Person otherPerson) {
        return Integer.compare(this.id, otherPerson.id);
    }

    @Override
    protected Person clone() throws CloneNotSupportedException {
        try{
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Person details:\n" + 
                        " ID: %d\n" +
                        " Name (Date of Birth): %s, %s (%s)\n" +
                        " Address: %s\n" +
                        " Borrowed books: %s\n" +
                        " Open fees: %.2f",
                id,
                firstName,
                lastName,
                birthDate,
                address != null ? address.toString() : "No address registered.",
                borrowedBooks != null ? borrowedBooks.toString() : "No book borrowed.",
                openFees);
    }
}
