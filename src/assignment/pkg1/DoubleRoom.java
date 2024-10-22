/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author hash
 */

public class DoubleRoom extends Room {
    public DoubleRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public String getRoomType() {
        return "Double";
    }
}

