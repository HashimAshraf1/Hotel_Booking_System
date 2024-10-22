/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author kyoun
 */


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final List<Room> rooms = new ArrayList<>(); // List of rooms in the hotel
    private final List<Booking> bookings = new ArrayList<>(); // List of bookings

    // Constructor for Hotel, accepts the hotel name
    public Hotel(String name) {
    }

    // Adds a new room to the hotel's room list
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Finds an available room of certain type
    public Room findAvailableRoom(String type) {
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(type) && room.isAvailable()) {
                return room;
            }
        }
        return null; // Returns null if no available room is found
    }

 // Creates a booking for a customer if an available room is found
     public boolean makeBooking(Customer customer, String roomType, LocalDate startDate, LocalDate endDate, Payment payment) {
        // Input validation
        if (customer == null || roomType == null || startDate == null || endDate == null || payment == null) {
            throw new IllegalArgumentException("Booking details cannot be null.");
        }

        if (payment.getAmount() < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before the start date.");
        }

        Room room = findAvailableRoom(roomType);
        if (room != null) {
            double nightlyRate = 100.0; // Default nightly rate
            bookings.add(new Booking(customer, room, startDate, endDate, payment, nightlyRate));
            System.out.println("Booking successful: " + bookings.get(bookings.size() - 1));
            return true;
        } else {
            System.out.println("No available rooms of type " + roomType);
            return false;
        }
    }

    // Cancels a booking by its ID if it exists and is not already cancelled
    public boolean cancelBooking(int bookingID) {
        Booking booking = findBookingById(bookingID);
        if (booking != null && !booking.isCancelled()) {
            booking.cancelBooking();
            return true; // Indicate that the booking was successfully cancelled
        } else {
            System.out.println("Booking not found or already cancelled.");
            return false; // Indicate that the booking was not cancelled
        }
    }

    // List of available rooms
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

   // List of all non-cancelled bookings
public List<Booking> getBookings() {
    List<Booking> activeBookings = new ArrayList<>();
    for (Booking booking : bookings) {
        if (!booking.isCancelled()) {
            activeBookings.add(booking);  // Only add non-cancelled bookings
        }
    }
    return activeBookings;
}


    // Finds a booking by its ID
    public Booking findBookingById(int bookingID) {
        for (Booking booking : bookings) {
            if (booking.getBookingID() == bookingID) {
                return booking;
            }
        }
        return null; // Returns null if no booking with the given ID is found
    }
}
