/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author kyoun
 */
public class Customer {
    private final String name; // Customer's name
    private final String contactInfo; // Customer's contact information

    // Constructor to initialize Customer with a name and contact information
    public Customer(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    
    public String getName() {
        return name;
    }

   
    public String getContactInfo() {
        return contactInfo;
    }

    
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}

