/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Hotel_Booking_System_GUI;

import assignment.pkg1.Booking;
import assignment.pkg1.Customer;
import assignment.pkg1.Hotel;
import assignment.pkg1.Payment;
import assignment.pkg1.CreditCardPayment;
import assignment.pkg1.SingleRoom;
import assignment.pkg1.DoubleRoom;
import assignment.pkg1.Room;
import assignment.pkg1.SuiteRoom;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class HotelTest {

    private Hotel hotel;

    @Before
    public void setUp() {
        hotel = new Hotel("Test Hotel");
        hotel.addRoom(new SingleRoom(101)); 
        hotel.addRoom(new DoubleRoom(102));  
        hotel.addRoom(new SuiteRoom(103));  

    @Test
    public void testAddRoom() {
        int initialRoomCount = hotel.getAvailableRooms().size();
        hotel.addRoom(new SuiteRoom(104));
        assertEquals(initialRoomCount + 1, hotel.getAvailableRooms().size());
    }

    @Test
    public void testFindAvailableRoom() {
        Room room = hotel.findAvailableRoomByType("Single");
        assertNotNull(room);
        assertEquals(101, room.getRoomNumber());
    }

    @Test
    public void testMakeBookingSuccess() {
        Customer customer = new Customer("John Doe", "john@example.com");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 5);
        Payment payment = new CreditCardPayment(500.0, "123456789", "John Doe");  

        hotel.makeBooking(customer, "Single", startDate, endDate, payment);

        assertEquals(1, hotel.getBookings().size());
    }

    @Test
public void testMakeBookingFail_NoRoomAvailable() {
    Customer customer = new Customer("Jane Doe", "jane@example.com");
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 5);
    Payment payment = new CreditCardPayment(500.0, "987654321", "Jane Doe");

    // Ensure that no Suite rooms are available
    Room suiteRoom = hotel.findAvailableRoomByType("Suite");
    if (suiteRoom != null) {
        suiteRoom.setAvailable(false);  
    }

    // Attempt to make a booking for a room type that is not available
    hotel.makeBooking(customer, "Suite", startDate, endDate, payment);

    // The booking list should still be empty
    assertEquals(0, hotel.getBookings().size());
}

    @Test
    public void testCancelBooking() {
        Customer customer = new Customer("John Doe", "john@example.com");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 5);
        Payment payment = new CreditCardPayment(500.0, "123456789", "John Doe"); 

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
    Payment invalidPayment = new CreditCardPayment(-100.0, "invalid", "Invalid Customer");

    // This should throw an IllegalArgumentException
    hotel.makeBooking(customer, "Single", startDate, endDate, invalidPayment);
}

}
