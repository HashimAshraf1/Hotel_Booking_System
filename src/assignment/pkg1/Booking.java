package assignment.pkg1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private static int idCounter = 1; // Static counter for unique booking IDs
    private final int bookingID = idCounter++; // Unique booking ID
    private final Customer customer; // Customer associated with the booking
    private final Room room; // Room associated with the booking
    private LocalDate startDate, endDate; // Booking start and end dates
    private boolean isCancelled = false; // Status to check if the booking is cancelled
    private final Payment payment; // Payment details for the booking
    private final double nightlyRate; // Rate per night for the room

    // Constructor to initialize a booking with necessary details
    public Booking(Customer customer, Room room, LocalDate startDate, LocalDate endDate, Payment payment, double nightlyRate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;
        this.nightlyRate = nightlyRate;
        room.setAvailable(false); // Mark the room as unavailable when booked
    }

    Booking(Customer customer, Room room, LocalDate startDate, LocalDate endDate, Payment payment) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
            room.setAvailable(true); // Room is available again
            isCancelled = true;
            System.out.println("Room is available again.");
        } else {
            System.out.println("Booking was already cancelled.");
        }
    }

    // Get Method
    public int getBookingID() {
        return bookingID;
    }

    public Customer getCustomer() {
        return customer;
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

    public boolean isCancelled() {
        return isCancelled;
    }

    public Payment getPayment() {
        return payment;
    }

    // Returns a string representation of the booking
    @Override
    public String toString() {
        String paymentMethodString = payment.getMethod().name().replace("_", " ").toLowerCase();
        paymentMethodString = Character.toUpperCase(paymentMethodString.charAt(0)) + paymentMethodString.substring(1);

        return String.format(
            "Booking Details:%n" +
            "----------------%n" +
            "Booking ID: %d%n" +
            "Customer: %s%n" +
            "Room: %d (%s)%n" +
            "Start Date: %s%n" +
            "End Date: %s%n" +
            "Cancelled: %b%n" +
            "Payment: %.2f (%s)",
            bookingID,
            customer.getName(),
            room.getRoomNumber(), room.getType(),
            startDate, endDate,
            isCancelled,
            payment.getAmount(), paymentMethodString
        );
    }
}
