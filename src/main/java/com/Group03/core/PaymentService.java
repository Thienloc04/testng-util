package com.Group03.core;

public class PaymentService {
    public double calculateTotalPayment(double discountedPrice, double taxRate) {
        if (discountedPrice < 0) {
            throw new IllegalArgumentException("Giá sau giảm giá không thể là số âm");
        }
        return discountedPrice * (1 + taxRate); // Tính tổng tiền sau thuế
    }
}