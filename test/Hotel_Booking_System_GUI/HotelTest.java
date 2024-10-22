/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Hotel_Booking_System_GUI;

import assignment.pkg1.Booking;
import assignment.pkg1.Customer;
import assignment.pkg1.Hotel;
import assignment.pkg1.Payment;
import assignment.pkg1.PaymentMethod;
import assignment.pkg1.Room;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class HotelTest {

    private Hotel hotel;

    @Before
    public void setUp() {
        hotel = new Hotel("Test Hotel");
        hotel.addRoom(new Room(101, "Single"));
        hotel.addRoom(new Room(102, "Double"));
    }

    @Test
    public void testAddRoom() {
        int initialRoomCount = hotel.getAvailableRooms().size();
        hotel.addRoom(new Room(103, "Suite"));
        assertEquals(initialRoomCount + 1, hotel.getAvailableRooms().size());
    }

    @Test
    public void testFindAvailableRoom() {
        Room room = hotel.findAvailableRoom("Single");
        assertNotNull(room);
        assertEquals(101, room.getRoomNumber());
    }

    @Test
    public void testMakeBookingSuccess() {
        Customer customer = new Customer("John Doe", "john@example.com");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 5);
        Payment payment = new Payment(500.0, PaymentMethod.CREDIT_CARD);

        hotel.makeBooking(customer, "Single", startDate, endDate, payment);

        assertEquals(1, hotel.getBookings().size());
    }

    @Test
    public void testMakeBookingFail_NoRoomAvailable() {
        Customer customer = new Customer("Jane Doe", "jane@example.com");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 5);
        Payment payment = new Payment(500.0, PaymentMethod.CREDIT_CARD);

        // No room of this type available
        hotel.makeBooking(customer, "Suite", startDate, endDate, payment);

        assertEquals(0, hotel.getBookings().size());
    }

    @Test
    public void testCancelBooking() {
        Customer customer = new Customer("John Doe", "john@example.com");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 5);
        Payment payment = new Payment(500.0, PaymentMethod.CREDIT_CARD);

        hotel.makeBooking(customer, "Single", startDate, endDate, payment);
        Booking booking = hotel.getBookings().get(0);

        // Cancel the booking
        boolean result = hotel.cancelBooking(booking.getBookingID());
        assertTrue(result);

        // Ensure the booking is cancelled
        assertTrue(booking.isCancelled());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeBookingInvalidPayment() {
    Customer customer = new Customer("Invalid Customer", "invalid@example.com");
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 5);
    
    // Invalid payment with negative amount
    Payment invalidPayment = new Payment(-100.0, PaymentMethod.CREDIT_CARD);

    hotel.makeBooking(customer, "Single", startDate, endDate, invalidPayment);
}
    
}

