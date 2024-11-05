package com.Group03.core;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class OrderServiceTest {

    private OrderService orderService;

    @BeforeMethod
    public void setUp() {
        orderService = new OrderService();
    }

    // Sử dụng DataProvider để cung cấp dữ liệu cho nhiều trường hợp kiểm thử
    @DataProvider(name = "validDiscountDataProvider")
    public Object[][] validDiscountDataProvider() {
        return new Object[][]{
                // { originalPrice, quantity, customerType, discountCode, expectedFinalPrice }

                // Trường hợp khách hàng VIP với mã giảm giá và số lượng > 10, được áp dụng 35% tổng giảm giá:
                // 20% VIP + 10% DISCOUNT10 + 5% số lượng > 10.
                //{100.0, 12, "VIP", "DISCOUNT10", 65.0}, // Kết quả: 100 * (1 - 0.35) = 65.0



                // Trường hợp giá gốc bằng 0, dù có giảm giá nào thì kết quả vẫn phải bằng 0.
                {0.0, 5, "VIP", "DISCOUNT10", 0.0}, // Kết quả: 0 * (bất kỳ discount nào) = 0.0

                // Khách hàng Regular có mã giảm giá và số lượng = 10, chỉ giảm 20% từ Regular và mã DISCOUNT10.
                //{100.0, 10, "Regular", "DISCOUNT10", 80.0}, // Kết quả: 100 * (1 - 0.2) = 80.0

                // Khách hàng Regular có mã giảm giá và số lượng > 10, giảm tổng cộng 25%.
                //{100.0, 11, "Regular", "DISCOUNT10", 75.0}, // Kết quả: 100 * (1 - 0.25) = 75.0

                // Khách hàng Regular không có mã giảm giá, số lượng > 10, chỉ giảm 15% từ Regular và số lượng.
                //{300.0, 12, "Regular", "", 255.0}, // Kết quả: 300 * (1 - 0.15) = 255.0

                // Khách hàng VIP với mã giảm giá và số lượng lớn, giảm tổng cộng 35%.
                //{10000.0, 20, "VIP", "DISCOUNT10", 6500.0}, // Kết quả: 10000 * (1 - 0.35) = 6500.0

                // Trường hợp biên: Số lượng bằng 0, không ảnh hưởng đến giảm giá, giảm 30% từ VIP và DISCOUNT10.
                {100.0, 0, "VIP", "DISCOUNT10", 70.0}, // Kết quả: 100 * (1 - 0.3) = 70.0

                // Trường hợp biên: VIP có mã giảm giá, tổng giảm giá là 35%, nhưng kết quả tính ra là 64.99999999999999
                // Thay vì 0 vì discount tính toán cho kết quả dương rất nhỏ do phép tính floating-point:
                // Kết quả 100 * (1 - 0.35) = 64.99999999999999 (không phải 0).
                //{100.0, 15, "VIP", "DISCOUNT10", 65.0}  // Thực tế không phải 0, mà là 64.99999999999999
        };
    }

    @Test(dataProvider = "validDiscountDataProvider")
    public void testCalculateDiscountedPrice_ValidCases(double originalPrice, int quantity, String customerType, String discountCode, double expectedFinalPrice) {
        double actualFinalPrice = orderService.calculateDiscountedPrice(originalPrice, quantity, customerType, discountCode);
        Assert.assertEquals(actualFinalPrice, expectedFinalPrice, 0.001, "Final price mismatch");
    }

    @DataProvider(name = "invalidDiscountDataProvider")
    public Object[][] invalidDiscountDataProvider() {
        return new Object[][]{
                // { originalPrice, quantity, customerType, discountCode, expectedExceptionMessage }
                {-100.0, 5, "VIP", "DISCOUNT10", "Giá gốc không thể là số âm"},
                {100.0, -5, "VIP", "DISCOUNT10", "Số lượng không thể là số âm"},
                {100.0, 5, "InvalidType", "DISCOUNT10", "Loại khách hàng không hợp lệ"}
        };
    }

    @Test(dataProvider = "invalidDiscountDataProvider", expectedExceptions = IllegalArgumentException.class)
    public void testCalculateDiscountedPrice_InvalidCases(double originalPrice, int quantity, String customerType, String discountCode, String expectedExceptionMessage) {
        try {
            orderService.calculateDiscountedPrice(originalPrice, quantity, customerType, discountCode);
            Assert.fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals(ex.getMessage(), expectedExceptionMessage, "Exception message mismatch");
            throw ex; // Rethrow to satisfy TestNG expectedExceptions
        }
    }
}
