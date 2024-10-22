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

    // Constructor
    public HotelBookingController(Hotel hotel, HotelBookingSystemGUI view) {
        this.hotel = hotel;
        this.view = view;
    }

    // Setter for the view
    public void setView(HotelBookingSystemGUI view) {
        this.view = view;
    }

    // Method to add a room
    public void addRoom() {
        JTextField roomNumberField = new JTextField();
        JComboBox<String> roomTypeBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        Object[] message = {
            "Room Number:", roomNumberField,
            "Room Type:", roomTypeBox
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Add Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
                String roomType = (String) roomTypeBox.getSelectedItem();

                // Add room based on selected type
                Room room;
                switch (roomType) {
                    case "Single":
                        room = new SingleRoom(roomNumber);
                        break;
                    case "Double":
                        room = new DoubleRoom(roomNumber);
                        break;
                    case "Suite":
                        room = new SuiteRoom(roomNumber);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid room type");
                }

                hotel.addRoom(room);
                JOptionPane.showMessageDialog(view, "Room added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid room number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to make a booking
    public void makeBooking() {
        JTextField customerNameField = new JTextField();
        JTextField contactInfoField = new JTextField();
        JComboBox<String> roomTypeBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JTextField amountField = new JTextField();
        JComboBox<PaymentMethod> methodBox = new JComboBox<>(PaymentMethod.values());

        Object[] message = {
            "Customer Name:", customerNameField,
            "Contact Info:", contactInfoField,
            "Room Type:", roomTypeBox,
            "Start Date (yyyy-mm-dd):", startDateField,
            "End Date (yyyy-mm-dd):", endDateField,
            "Payment Amount:", amountField,
            "Payment Method:", methodBox
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Make Booking", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String customerName = customerNameField.getText().trim();
                String contactInfo = contactInfoField.getText().trim();
                String roomType = (String) roomTypeBox.getSelectedItem();
                String startDateStr = startDateField.getText().trim();
                String endDateStr = endDateField.getText().trim();
                String amountStr = amountField.getText().trim();

                if (customerName.isEmpty() || contactInfo.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() || amountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                double amount = Double.parseDouble(amountStr);
                PaymentMethod method = (PaymentMethod) methodBox.getSelectedItem();

                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(view, "End date cannot be before the start date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room room = hotel.findAvailableRoomByType(roomType); // Method should return a Room of the given type

                if (room == null) {
                    JOptionPane.showMessageDialog(view, "No available rooms for the selected type.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Customer customer = new Customer(customerName, contactInfo);
                Payment payment;
                switch (method) {
                    case CREDIT_CARD:
                        payment = new CreditCardPayment(amount, "1234-5678-9012-3456", customerName);
                        break;
                    case DEBIT_CARD:
                        payment = new DebitCardPayment(amount, "9876-5432-1098-7654", customerName);
                        break;
                    case PAYPAL:
                        payment = new PayPalPayment(amount, customerName + "@example.com");
                        break;
                    case CASH:
                    default:
                        payment = new CashPayment(amount);
                        break;
                }

                hotel.makeBooking(customer, roomType, startDate, endDate, payment);
                JOptionPane.showMessageDialog(view, "Booking made successfully.");

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(view, "Invalid date format. Please use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid payment amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to view available rooms
    public void viewAvailableRooms() {
        List<Room> availableRooms = hotel.getAvailableRooms();
        AvailableRoomsDialog dialog = new AvailableRoomsDialog(view, availableRooms);
        dialog.setVisible(true);
    }

    // Method to view all bookings
    public void viewAllBookings() {
        List<Booking> bookings = hotel.getActiveBookings(); // Ensure this method returns non-cancelled bookings
        AllBookingsDialog dialog = new AllBookingsDialog(view, "All Bookings", true, bookings);
        dialog.setVisible(true);
    }

    // Method for handling early check-in or check-out
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
                boolean success = booking != null && (isCheckIn ? booking.earlyCheckIn(newDate) : booking.earlyCheckOut(newDate));
                JOptionPane.showMessageDialog(view, success ? "Operation successful." : "Operation failed. Invalid booking ID or date.", "Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(view, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to cancel a booking
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
                    viewAllBookings();
                } else {
                    JOptionPane.showMessageDialog(view, "Cancellation failed. Invalid booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
