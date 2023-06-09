package com.onclebob.videostore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoStoreTest {
    public final Movie newReleaseMovie1 = new Movie("New Release 1", Movie.NEW_RELEASE);
    private final Movie newReleaseMovie2 = new Movie("New Release 2", Movie.NEW_RELEASE);
    private final Movie newReleaseMovie3 = new Movie("New Release 3", Movie.REGULAR);
    private final Movie newReleaseMovie4 = new Movie("New Release 4", Movie.REGULAR);
    private final Movie newReleaseMovie5 = new Movie("New Release 5", Movie.REGULAR);
    private Statement statement;

    private static final double DELTA = 1e-15;

    @BeforeEach
    protected void setUp() {
        statement = new Statement("Fred");
        statement.setCustomerName("Customer");
    }

    @Test
    public void testSingleNewReleaseStatementTotals() {
        statement.addRental(new Rental(newReleaseMovie1, 3));
        statement.generate();
        assertEquals(9.0, statement.getTotalAmount(), DELTA);
        assertEquals(2, statement.getFrequentRenterPoints());
    }

    @Test
    public void testDualNewReleaseStatementTotals() {
        statement.addRental(new Rental(newReleaseMovie1, 3));
        statement.addRental(new Rental(newReleaseMovie2, 3));
        statement.generate();
        assertEquals(18.0, statement.getTotalAmount(), DELTA);
        assertEquals(4, statement.getFrequentRenterPoints());
    }

    @Test
    public void testSingleChildrensStatementTotals() {
        newReleaseMovie2.setPriceCode(Movie.CHILDRENS);
        statement.addRental(new Rental(newReleaseMovie2, 3));
        statement.generate();
        assertEquals(1.5, statement.getTotalAmount(), DELTA);
        assertEquals(1, statement.getFrequentRenterPoints());
    }

    @Test
    public void testMultipleRegularStatementTotals() {
        statement.addRental(new Rental(newReleaseMovie3, 1));
        statement.addRental(new Rental(newReleaseMovie4, 2));
        statement.addRental(new Rental(newReleaseMovie5, 3));
        statement.generate();
        assertEquals(7.5, statement.getTotalAmount(), DELTA);
        assertEquals(3, statement.getFrequentRenterPoints());
    }

    @Test
    public void testMultipleRegularStatementformat() {
        statement.addRental(new Rental(newReleaseMovie3, 1));
        statement.addRental(new Rental(newReleaseMovie4, 2));
        statement.addRental(new Rental(newReleaseMovie5, 3));
        assertEquals("""
                com.onclebob.videostore.Rental Record for Customer
                \tNew Release 3\t2,0
                \tNew Release 4\t2,0
                \tNew Release 5\t3,5
                You owed 7,5
                You earned 3 frequent renter points
                """, statement.generate());
    }
}