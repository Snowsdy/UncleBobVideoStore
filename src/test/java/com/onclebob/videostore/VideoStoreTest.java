package com.onclebob.videostore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VideoStoreTest {

    @BeforeEach
    protected void setUp() {
        customer = new Customer("Fred");
    }

    @Test
    public void testSingleNewReleaseStatement() {
        customer.addRental(new Rental(new Movie("The Cell", Movie.NEW_RELEASE), 3));
        assertEquals("com.onclebob.videostore.Rental Record for Fred\n\tThe Cell\t9.0\nYou owed 9.0\nYou earned 2 frequent renter points\n", customer.statement());
    }

    @Test
    public void testDualNewReleaseStatement() {
        customer.addRental(new Rental(new Movie("The Cell", Movie.NEW_RELEASE), 3));
        customer.addRental(new Rental(new Movie("The Tigger com.onclebob.videostore.Movie", Movie.NEW_RELEASE), 3));
        assertEquals("com.onclebob.videostore.Rental Record for Fred\n\tThe Cell\t9.0\n\tThe Tigger com.onclebob.videostore.Movie\t9.0\nYou owed 18.0\nYou earned 4 frequent renter points\n", customer.statement());
    }

    @Test
    public void testSingleChildrensStatement() {
        customer.addRental(new Rental(new Movie("The Tigger com.onclebob.videostore.Movie", Movie.CHILDRENS), 3));
        assertEquals("com.onclebob.videostore.Rental Record for Fred\n\tThe Tigger com.onclebob.videostore.Movie\t1.5\nYou owed 1.5\nYou earned 1 frequent renter points\n", customer.statement());
    }

    @Test
    public void testMultipleRegularStatement() {
        customer.addRental(new Rental(new Movie("Plan 9 from Outer Space", Movie.REGULAR), 1));
        customer.addRental(new Rental(new Movie("8 1/2", Movie.REGULAR), 2));
        customer.addRental(new Rental(new Movie("Eraserhead", Movie.REGULAR), 3));

        assertEquals("com.onclebob.videostore.Rental Record for Fred\n\tPlan 9 from Outer Space\t2.0\n\t8 1/2\t2.0\n\tEraserhead\t3.5\nYou owed 7.5\nYou earned 3 frequent renter points\n", customer.statement());
    }

    private Customer customer;
}