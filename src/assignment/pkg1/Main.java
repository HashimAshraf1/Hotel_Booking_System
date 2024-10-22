/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;



import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Grand Hotel");

        // Adding sample rooms to the hotel
        hotel.addRoom(new SingleRoom(101));
        hotel.addRoom(new DoubleRoom(102));
        hotel.addRoom(new SuiteRoom(103));

        // Create the controller and view
        HotelBookingController controller = new HotelBookingController(hotel, null);
        HotelBookingSystemGUI view = new HotelBookingSystemGUI(controller);
        controller.setView(view);

        // Show the GUI
        SwingUtilities.invokeLater(() -> view.setVisible(true));
    }
}
