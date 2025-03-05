package de.fherfurt.librarySystem.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    public void testAddressConstructor(){
        //Arrange
        String street1 ="TestStreet1";
        int houseNumber1 = 1;
        String postalCode1 = "PostalCode1";
        String city1 ="TestCity1";
        String country1 = "TestCountry1";

        String street2 ="TestStreet2";
        int houseNumber2 = 2;
        String postalCode2 = "PostalCode2";
        String city2 ="TestCity2";
        String country2 = "TestCountry2";


        // Act
        Address address1= new Address(street1, houseNumber1, postalCode1, city1, country1);
        Address address2= new Address(street2, houseNumber2, postalCode2, city2, country2);

        //Assert
        assertEquals(street1, address1.getStreet());
        assertEquals(houseNumber1, address1.getHouseNumber());
        assertEquals(postalCode1, address1.getPostalCode());
        assertEquals(city1, address1.getCity());
        assertEquals(country1, address1.getCountry());

        assertEquals(street2, address2.getStreet());
        assertEquals(houseNumber2, address2.getHouseNumber());
        assertEquals(postalCode2, address2.getPostalCode());
        assertEquals(city2, address2.getCity());
        assertEquals(country2, address2.getCountry());
    }
}