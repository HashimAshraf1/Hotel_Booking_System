/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author hash
 */
public enum PaymentMethod {
    CREDIT_CARD {
        @Override
        public void processPayment(double amount) {
            System.out.println("Processing credit card payment of: " + amount);
        }
    },
    DEBIT_CARD {
        @Override
        public void processPayment(double amount) {
            System.out.println("Processing debit card payment of: " + amount);
        }
    },
    CASH {
        @Override
        public void processPayment(double amount) {
            System.out.println("Processing cash payment of: " + amount);
        }
    };

    public abstract void processPayment(double amount);
}

