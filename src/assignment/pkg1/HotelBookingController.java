/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author hash
 */


import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class HotelBookingController {

    private Hotel hotel;
    private HotelBookingSystemGUI view;

    public HotelBookingController(Hotel hotel, HotelBookingSystemGUI view) {
        this.hotel = hotel;
        this.view = view;
    }

    // Setter for view
    public void setView(HotelBookingSystemGUI view) {
        this.view = view;
    }

    public void addRoom() {
        JTextField roomNumberField = new JTextField();
        JTextField roomTypeField = new JTextField();
        Object[] message = {
            "Room Number:", roomNumberField,
            "Room Type:", roomTypeField
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Add Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
                String roomType = roomTypeField.getText().trim();
                hotel.addRoom(new Room(roomNumber, roomType));
                JOptionPane.showMessageDialog(view, "Room added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid room number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void makeBooking() {
    // Create input fields for user to fill
    JTextField customerNameField = new JTextField();
    JTextField contactInfoField = new JTextField();
    JTextField roomTypeField = new JTextField();
    JTextField startDateField = new JTextField();
    JTextField endDateField = new JTextField();
    JTextField amountField = new JTextField();
    JComboBox<PaymentMethod> methodBox = new JComboBox<>(PaymentMethod.values());

    // Show the booking form dialog
    Object[] message = {
        "Customer Name:", customerNameField,
        "Contact Info:", contactInfoField,
        "Room Type:", roomTypeField,
        "Start Date (yyyy-mm-dd):", startDateField,
        "End Date (yyyy-mm-dd):", endDateField,
        "Payment Amount:", amountField,
        "Payment Method:", methodBox
    };

    int option = JOptionPane.showConfirmDialog(view, message, "Make Booking", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        // Retrieve input values and trim any extra spaces
        String customerName = customerNameField.getText().trim();
        String contactInfo = contactInfoField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String startDateStr = startDateField.getText().trim();
        String endDateStr = endDateField.getText().trim();
        String amountStr = amountField.getText().trim();

        // **1. Check if any field is left blank**
        if (customerName.isEmpty() || contactInfo.isEmpty() || roomType.isEmpty() ||
            startDateStr.isEmpty() || endDateStr.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if validation fails
        }

        try {
            // **2. Parse dates and amount**
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            double amount = Double.parseDouble(amountStr);
            PaymentMethod method = (PaymentMethod) methodBox.getSelectedItem();

            // **3. Check if end date is before start date**
            if (endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(view, "End date cannot be before the start date.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if validation fails
            }

            // **4. Check room availability (this logic should ensure the room is available for the selected dates)**
            List<Room> availableRooms = hotel.getAvailableRooms(); // Implement filtering by room type and dates
            if (availableRooms.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No available rooms for the selected dates.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if validation fails
            }

            // **5. Create booking if all validations pass**
            Customer customer = new Customer(customerName, contactInfo);
            Payment payment = new Payment(amount, method);
            hotel.makeBooking(customer, roomType, startDate, endDate, payment);
            
            // Booking success message
            JOptionPane.showMessageDialog(view, "Booking made successfully.");

        } catch (DateTimeParseException ex) {
            // Handle invalid date format
            JOptionPane.showMessageDialog(view, "Invalid date format. Please use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            // Handle invalid amount format
            JOptionPane.showMessageDialog(view, "Invalid payment amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    public void viewAvailableRooms() {
    // Fetch available rooms from the hotel
    List<Room> availableRooms = hotel.getAvailableRooms();

    // Create and display the AvailableRoomsDialog with the available rooms
    AvailableRoomsDialog dialog = new AvailableRoomsDialog(view, availableRooms);
    dialog.setVisible(true);  // Show the updated dialog
}


    public void viewAllBookings() {
    // Fetch the list of bookings
    List<Booking> bookings = hotel.getBookings();

    // Create and display the AllBookingsDialog
    AllBookingsDialog dialog = new AllBookingsDialog(view, "All Bookings", true, bookings);
    dialog.setVisible(true);  // Show the dialog
}


    public void handleEarlyCheck(boolean isCheckIn) {
        JTextField bookingIdField = new JTextField();
        JTextField newDateField = new JTextField();
        Object[] message = {
            "Booking ID:", bookingIdField,
            isCheckIn ? "New Start Date (yyyy-mm-dd):" : "New End Date (yyyy-mm-dd):", newDateField
        };

        int option = JOptionPane.showConfirmDialog(view, message, isCheckIn ? "Early Check-In" : "Early Check-Out", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int bookingId = Integer.parseInt(bookingIdField.getText().trim());
                LocalDate newDate = LocalDate.parse(newDateField.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                Booking booking = hotel.findBookingById(bookingId);
                boolean success;
                if (isCheckIn) {
                    success = booking != null && booking.earlyCheckIn(newDate);
                } else {
                    success = booking != null && booking.earlyCheckOut(newDate);
                }
                JOptionPane.showMessageDialog(view, success ? "Operation successful." : "Operation failed. Invalid booking ID or date.", "Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(view, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cancelBooking() {
    JTextField bookingIdField = new JTextField();
    Object[] message = {
        "Booking ID:", bookingIdField
    };

    int option = JOptionPane.showConfirmDialog(view, message, "Cancel Booking", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText().trim());
            boolean success = hotel.cancelBooking(bookingId);
            if (success) {
                JOptionPane.showMessageDialog(view, "Booking cancelled successfully.");

                // After cancellation, refresh the bookings dialog
                viewAllBookings();  // Re-open the updated bookings dialog
            } else {
                JOptionPane.showMessageDialog(view, "Cancellation failed. Invalid booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

}
