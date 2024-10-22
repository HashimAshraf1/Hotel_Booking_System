/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author kyoun
 */
public abstract class Room {
    private final int roomNumber;
    private boolean isAvailable = true;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Abstract method to get the room type
    public abstract String getRoomType();

    @Override
    public String toString() {
        return String.format("Room Number: %d, Type: %s, Available: %b", roomNumber, getRoomType(), isAvailable);
    }
}
