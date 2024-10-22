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
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public Hotel(String name) {
        // Constructor logic, if necessary
    }

    // Adds a new room to the hotel's room list
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Finds an available room of a certain type
    public Room findAvailableRoomByType(String type) {
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(type) && room.isAvailable()) {
                return room;
            }
        }
        return null; // Returns null if no available room is found
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

    // Method to create a booking
public void makeBooking(Customer customer, String roomType, LocalDate startDate, LocalDate endDate, Payment payment) {
    // invalid payments
    if (payment.getAmount() <= 0) {
        throw new IllegalArgumentException("Payment amount must be positive.");
    }

    Room room = findAvailableRoomByType(roomType);
    if (room != null) {
        double nightlyRate = 100.0; // Example rate per night
        Booking booking = new Booking(customer, room, startDate, endDate, nightlyRate, payment);
        bookings.add(booking);
        room.setAvailable(false); // Mark room as booked
        System.out.println("Booking successful: " + booking);
    } else {
        System.out.println("No available rooms of type " + roomType);
    }
}


    // Cancels a booking by its ID if it exists and is not already cancelled
    public boolean cancelBooking(int bookingID) {
        Booking booking = findBookingById(bookingID);
        if (booking != null && !booking.isCancelled()) {
            booking.cancelBooking();
            return true;
        } else {
            System.out.println("Booking not found or already cancelled.");
            return false;
        }
    }

    // List of active bookings (non-cancelled bookings)
    public List<Booking> getActiveBookings() {
        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                activeBookings.add(booking);
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

     // Method to return the bookings list
    public List<Booking> getBookings() {
        return bookings;
    }
    public Room findAvailableRoom(String single) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
