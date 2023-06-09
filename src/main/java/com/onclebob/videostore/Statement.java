package com.onclebob.videostore;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Vector;

public class Statement {
    private String customerName;
    private final Vector<Rental> rentals = new Vector<>();
    private int frequentRenterPoints;
    private double totalAmount;

    public Statement(String customerName) {
        this.customerName = customerName;
    }

    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public String generate() {
        StringBuilder statementText = new StringBuilder();

        clearTotals();
        statementText.append(createHeader());
        statementText.append(createRentalLines());
        statementText.append(createFooter());
        return statementText.toString();
    }

    private String createFooter() {
        return MessageFormat.format("""
                        You owed {0,number,#.#}
                        You earned {1} frequent renter points
                        """,
                totalAmount,
                frequentRenterPoints);
    }

    private String createHeader() {
        return MessageFormat.format("com.onclebob.videostore.Rental Record for {0}\n",
                this.customerName);
    }

    private String createRentalLines() {
        StringBuilder rentalLines = new StringBuilder();
        Enumeration<Rental> rentals = this.rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each = rentals.nextElement();
            rentalLines.append(createRentalLine(each));
        }
        return rentalLines.toString();
    }

    private String createRentalLine(Rental rental) {
        return formatRentalLines(rental);
    }

    private String formatRentalLines(Rental rental) {
        determineFrequentRenterPoints(rental);
        return MessageFormat.format("\t{0}\t{1,number,#0.0}\n", rental.getMovie().getTitle(), determineAmount(rental));
    }

    private void determineFrequentRenterPoints(Rental rental) {
        frequentRenterPoints++;
        boolean bonusIsEarned = (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE
                && rental.getDaysRented() > 1);
        if (bonusIsEarned) frequentRenterPoints++;
    }

    private double determineAmount(Rental rental) {
        double rentalAmount = 0;
        switch (rental.getMovie().getPriceCode()) {
            case Movie.REGULAR -> {
                rentalAmount += 2;
                if (rental.getDaysRented() > 2)
                    rentalAmount += (rental.getDaysRented() - 2) * 1.5;
            }
            case Movie.NEW_RELEASE -> rentalAmount += rental.getDaysRented() * 3;
            case Movie.CHILDRENS -> {
                rentalAmount += 1.5;
                if (rental.getDaysRented() > 3)
                    rentalAmount += (rental.getDaysRented() - 3) * 1.5;
            }
        }
        totalAmount += rentalAmount;
        return rentalAmount;
    }

    private void clearTotals() {
        totalAmount = 0;
        frequentRenterPoints = 0;
    }

    public int getFrequentRenterPoints() {
        return this.frequentRenterPoints;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}