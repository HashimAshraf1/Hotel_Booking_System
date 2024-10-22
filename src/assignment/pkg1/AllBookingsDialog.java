package assignment.pkg1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AllBookingsDialog extends JDialog {
    public AllBookingsDialog(JFrame parent, String title, boolean modal, List<Booking> bookings) {
        super(parent, title, modal);
        setLayout(new BorderLayout());
        
        // Create a panel for booking details with a scroll pane
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Stack elements vertically
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        panel.add(new JLabel("<html><h2>All Bookings:</h2></html>", SwingConstants.CENTER));  // Add title
        panel.add(Box.createVerticalStrut(10));  // Space between title and content
        panel.add(new JSeparator());

        if (bookings.isEmpty()) {
            panel.add(new JLabel("No bookings found."));
        } else {
            for (Booking booking : bookings) {
                JPanel bookingPanel = new JPanel(new GridLayout(0, 1));

                // Use HTML for cleaner formatting of booking details
                JLabel bookingDetails = new JLabel("<html>" +
                        "<b>Booking ID:</b> " + booking.getBookingID() + "<br>" +
                        "<b>Customer:</b> " + booking.getCustomer().getName() + "<br>" +
                        "<b>Room:</b> " + booking.getRoom().getRoomNumber() + " (" + booking.getRoom().getRoomType() + ")<br>" +
                        "<b>Start Date:</b> " + booking.getStartDate() + "<br>" +
                        "<b>End Date:</b> " + booking.getEndDate() + "<br>" +
                        "<b>Cancelled:</b> " + (booking.isCancelled() ? "Yes" : "No") + "<br>" +
                        "<b>Payment:</b> " + booking.getPayment().getAmount() + " (" + booking.getPayment().getClass().getSimpleName() + ")<br>" +
                        "</html>");

                bookingPanel.add(bookingDetails);
                bookingPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));  // Add padding around booking entries
                panel.add(bookingPanel);

                // Add separator between bookings
                panel.add(new JSeparator());
            }
        }

        // Make the panel scrollable
        add(new JScrollPane(panel), BorderLayout.CENTER);

        // Add OK button at the bottom
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set dialog size and location
        setSize(500, 400);  // Adjust size as needed
        setLocationRelativeTo(parent);
    }
}
