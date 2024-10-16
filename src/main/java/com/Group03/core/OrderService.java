package com.Group03.core;

public class OrderService {
    public double calculateDiscountedPrice(double originalPrice, int quantity, String customerType, String discountCode) {
        double discount = 0.0;

        if (originalPrice < 0) {
            throw new IllegalArgumentException("Giá gốc không thể là số âm");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng không thể là số âm");
        }
        if (!"VIP".equals(customerType) && !"Regular".equals(customerType) && !"Other".equals(customerType)) {
            throw new IllegalArgumentException("Loại khách hàng không hợp lệ");
        }

        // Áp dụng giảm giá dựa trên loại khách hàng
        if ("VIP".equals(customerType)) {
            discount += 0.2; // Giảm giá 20% cho khách VIP
        } else if ("Regular".equals(customerType)) {
            discount += 0.1; // Giảm giá 10% cho khách thông thường
        }

        // Áp dụng mã giảm giá
        if ("DISCOUNT10".equals(discountCode)) {
            discount += 0.1; // Giảm thêm 10% nếu có mã DISCOUNT10
        }

        // Áp dụng giảm giá thêm nếu số lượng lớn hơn 10
        if (quantity > 10) {
            discount += 0.05; // Giảm thêm 5% nếu mua nhiều hơn 10 sản phẩm
        }

        // Tính giá sau giảm giá
        double finalPrice = originalPrice * (1 - discount);
        return finalPrice;
        //return finalPrice <= 0 ? 0 : finalPrice; // Đảm bảo giá không âm
    }
}
