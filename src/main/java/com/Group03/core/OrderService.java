package com.Group03.core;

public class OrderService {
    public double calculateDiscountedPrice(double originalPrice, int quantity, String customerType, String discountCode) {
        double discount = 0.0;

        // Validate inputs
        if (originalPrice < 0) {
            throw new IllegalArgumentException("Giá gốc không thể là số âm");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng không thể là số âm");
        }
        validateCustomerType(customerType);

        // Apply discounts
        if ("VIP".equals(customerType)) {
            discount += 0.2; // VIP discount
        } else if ("Regular".equals(customerType)) {
            discount += 0.1; // Regular customer discount
        }

        if ("DISCOUNT10".equals(discountCode)) {
            discount += 0.1; // Additional discount for DISCOUNT10
        }

        if (quantity > 10) {
            discount += 0.05; // Bulk purchase discount
        }

        // Cap the discount at 1.0 (100%)
        discount = Math.min(discount, 1.0);

        // Calculate final price
        double finalPrice = originalPrice * quantity * (1 - discount);
        return Math.max(finalPrice, 0); // Ensure no negative prices
    }

    public boolean isValidCustomerType(String customerType) {
        return "VIP".equals(customerType) || "Regular".equals(customerType) || "Other".equals(customerType);
    }

    public void validateCustomerType(String customerType) {
        if (!isValidCustomerType(customerType)) {
            throw new IllegalArgumentException("Loại khách hàng không hợp lệ");
        }
    }
}