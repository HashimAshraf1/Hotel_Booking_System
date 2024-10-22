package assignment.pkg1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AvailableRoomsDialog extends JDialog {

    public AvailableRoomsDialog(JFrame parent, List<Room> rooms) {
        super(parent, "Available Rooms", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Panel to contain room details
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS)); // Stack vertically

        if (rooms.isEmpty()) {
            // Display a message if no rooms are available
            JLabel noRoomsLabel = new JLabel("No available rooms.");
            noRoomsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            roomPanel.add(noRoomsLabel);
        } else {
            // Populate the room panel with room details
            for (Room room : rooms) {
                // Add room details using HTML for better formatting
                JLabel roomLabel = new JLabel("<html><b>Room Number:</b> " + room.getRoomNumber() + "<br>" +
                        "<b>Type:</b> " + room.getRoomType() + "<br>" +
                        "<b>Available:</b> " + (room.isAvailable() ? "Yes" : "No") + "</html>");
                
                // Add padding to the room label
                roomLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                roomPanel.add(roomLabel);

                // Add a separator line between rooms
                roomPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            }
        }

        // Make the panel scrollable in case of too many rooms
        JScrollPane scrollPane = new JScrollPane(roomPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Add a Close button at the bottom
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
