package assignment.pkg1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HotelBookingSystemGUI extends JFrame {
    
    private HotelBookingController controller;
    private final Color backgroundColor = new Color(220, 220, 220); // Light gray for background
    private final Color buttonNormalColor = new Color(100, 149, 237); // Cornflower Blue for buttons
    private final Color buttonTextColor = Color.WHITE; // White text for contrast
    private final Color primaryTextColor = Color.BLACK; // Black text for the title

    public HotelBookingSystemGUI(HotelBookingController controller) {
        this.controller = controller;
        setTitle("Hotel Booking System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a main panel
        JPanel mainPanel = new JPanel(new GridLayout(9, 1, 10, 10)); // Added spacing between buttons
        mainPanel.setBackground(backgroundColor); // Light gray background for contrast
        add(mainPanel, BorderLayout.CENTER);

        // Create a title label
        JLabel titleLabel = new JLabel("The Hotel Booking System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(primaryTextColor); // Black text for contrast
        add(titleLabel, BorderLayout.NORTH);

        // Add buttons with improved text contrast
        addButton(mainPanel, "Add Room", e -> controller.addRoom());
        addButton(mainPanel, "Make Booking", e -> controller.makeBooking());
        addButton(mainPanel, "View Available Rooms", e -> controller.viewAvailableRooms());
        addButton(mainPanel, "View All Bookings", e -> controller.viewAllBookings());
        addButton(mainPanel, "Early Check-In", e -> controller.handleEarlyCheck(true));
        addButton(mainPanel, "Early Check-Out", e -> controller.handleEarlyCheck(false));
        addButton(mainPanel, "Cancel Booking", e -> controller.cancelBooking());
        addButton(mainPanel, "Exit", e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Improved addButton method with text color contrast and padding for better visibility
    private void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(buttonTextColor); // White text for better contrast
        button.setBackground(buttonNormalColor); // Cornflower blue background
        button.setOpaque(true); // Ensure the background color is painted
        button.setBorderPainted(false); // Remove the default button border
        button.setFocusPainted(false); // Remove focus painting for better look
        button.addActionListener(action);
        panel.add(button);
    }
}
