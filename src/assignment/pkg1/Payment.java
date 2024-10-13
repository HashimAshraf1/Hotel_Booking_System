/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author hash
 */
public class Payment {
    private double amount; // Total amount for the payment
    private PaymentMethod method; // Method of payment (e.g., credit card, cash)

    // Constructor to initialize payment amount and method
    public Payment(double amount, PaymentMethod method) {
        this.amount = amount;
        this.method = method;
    }

    
    public double getAmount() {
        return amount;
    }

   
    public void setAmount(double amount) {
        this.amount = amount;
    }

    
    public PaymentMethod getMethod() {
        return method;
    }

    
    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    // Override toString method to provide a formatted string representation of the payment
    @Override
    public String toString() {
        return String.format(
            "Payment{%n" +
            "    amount=%.2f,%n" +
            "    method=%s%n" +
            "}",
            amount,
            method
        );
    }
}
