/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author kyoun
 */
public class Room {
    // The room number (e.g., 101, 102)
    private final int roomNumber;
    
    // The type of room (e.g., Single, Double, Suite)
    private final String type;
    
    // Indicates whether the room is currently available for booking
    private boolean isAvailable = true;

    // Constructor to initialize the room number and type
    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }

 
    public int getRoomNumber() { 
        return roomNumber; 
    }

 
    public String getType() { 
        return type; 
    }

    // Checks if room is available for booking
    public boolean isAvailable() { 
        return isAvailable; 
    }

    // Sets the availability status of the room
    public void setAvailable(boolean available) { 
        isAvailable = available; 
    }

    // Overrides the toString method to provide a string representation of the room's details
    @Override
    public String toString() {
        return String.format("Room Number: %d%nType: %s%nAvailable: %b", roomNumber, type, isAvailable);
    }
}

