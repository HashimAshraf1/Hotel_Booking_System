package assignment.pkg1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private static int idCounter = 1; 
    private final int bookingID; 
    private final Customer customer; 
    private final Room room; 
    private LocalDate startDate, endDate; 
    private boolean isCancelled = false; 
    private final Payment payment;
    private final double nightlyRate; 

    // Constructor to initialize a booking with necessary details
    public Booking(Customer customer, Room room, LocalDate startDate, LocalDate endDate, double nightlyRate, Payment payment) {
        this.bookingID = idCounter++;
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nightlyRate = nightlyRate;
        this.payment = payment;
        
        // Mark the room as unavailable once a booking is created
        this.room.setAvailable(false);
    }

    // Allow early check-in and update payment
    public boolean earlyCheckIn(LocalDate newStartDate) {
        if (newStartDate.isBefore(startDate)) {
            long extraDays = ChronoUnit.DAYS.between(newStartDate, startDate);
            double extraCharge = extraDays * nightlyRate;
            payment.setAmount(payment.getAmount() + extraCharge);
            startDate = newStartDate;
            return true;
        }
        return false;
    }

    // Allow early check-out and adjust payment
    public boolean earlyCheckOut(LocalDate newEndDate) {
        if (newEndDate.isAfter(startDate) && newEndDate.isBefore(endDate)) {
            long unusedDays = ChronoUnit.DAYS.between(newEndDate, endDate);
            double refund = unusedDays * nightlyRate;
            payment.setAmount(payment.getAmount() - refund);
            endDate = newEndDate;
            return true;
        }
        return false;
    }

    // Cancel the booking and mark room as available
    public void cancelBooking() {
        if (!isCancelled) {
            room.setAvailable(true);
            isCancelled = true;
            System.out.println("Room is available again.");
        } else {
            System.out.println("Booking was already cancelled.");
        }
    }

    // Getters
    public int getBookingID() {
        return bookingID;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    // Returns a string representation of the booking
    @Override
    public String toString() {
    return String.format(
        "Booking Details:%n" +
        "----------------%n" +
        "Booking ID: %d%n" +
        "Customer: %s%n" +
        "Contact Info: %s%n" +  
        "Room: %d (%s)%n" +
        "Start Date: %s%n" +
        "End Date: %s%n" +
        "Cancelled: %b%n" +
        "Payment: %.2f (%s)",
        bookingID,
        customer.getName(),
        customer.getContactInfo(), 
        room.getRoomNumber(), room.getRoomType(),
        startDate, endDate,
        isCancelled,
        payment.getAmount(), payment.getClass().getSimpleName()
    );
}
}
