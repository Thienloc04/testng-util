package com.Group03.core;

import org.testng.Assert;
import org.testng.annotations.*;

public class OrderServiceTestTestNG {
    private OrderService orderService;

//    @BeforeSuite
//    public void beforeSuite() {
//        orderService = new OrderService();
//        System.out.println("Thiết lập cho bài kiểm thử: Khởi tạo OrderService.");
//    }
//
//    @AfterSuite
//    public void afterSuite() {
//        System.out.println("Sau khi thực hiện bài kiểm thử: Dọn dẹp sau khi hoàn tất kiểm thử.");
//    }

//    @BeforeMethod
//    public void setUp() {
//        orderService = new OrderService(); // Khởi tạo orderService
//    }

    @BeforeClass
    public void setUpClass() {
        orderService = new OrderService();
        System.out.println("Thiết lập cho lớp: Khởi tạo OrderService cho tất cả các phương thức kiểm thử.");
    }

//    @AfterClass
//    public void tearDownClass() {
//        System.out.println("Dọn dẹp sau khi hoàn tất kiểm thử.");
//        // Nếu orderService cần dọn dẹp, thực hiện ở đây
//        orderService = null; // Giải phóng tài nguyên nếu cần
//    }

//    @BeforeGroups("validCases")
//    public void setUpValidCases() {
////        orderService = new OrderService();
//        System.out.println("Thiết lập cho các trường hợp kiểm thử hợp lệ.");
//    }
//
//    @BeforeGroups("exceptionCases")
//    public void setUpExceptionCases() {
////        orderService = new OrderService();
//        System.out.println("Thiết lập cho các trường hợp kiểm thử ngoại lệ.");
//    }

    @DataProvider(name = "validPriceData")
    public Object[][] validPriceData() {
        return new Object[][] {
                // Trường hợp giá gốc bằng 0, dù có giảm giá nào thì kết quả vẫn phải bằng 0.
                {0.0, 5, "VIP", "DISCOUNT10", 0.0}, // Kết quả: 0 * (bất kỳ discount nào) = 0.0
                // Trường hợp biên: Số lượng bằng 0, không ảnh hưởng đến giảm giá, giảm 30% từ VIP và DISCOUNT10.
                {100.0, 0, "VIP", "DISCOUNT10", 0}, // Kết quả: 100 * (1 - 0.3) = 70.0
                {100.0, 5, "VIP", null, 400.0}, // VIP 20%
                {100.0, 3, "Regular", null, 270.0}, // Regular 10%
                {200.0, 1, "Regular", "DISCOUNT10", 160.0}, // Regular 10% + 10% code
                //Khách hàng Regular có mã giảm giá và số lượng = 10, chỉ giảm 20% từ Regular và mã DISCOUNT10.
                {100.0, 10, "Regular", "DISCOUNT10", 800.0},
                {50.0, 15, "Regular", null, 637.5}, // Regular + bulk
                {50.0, 15, "Other", null, 712.5}    // Other + bulk
        };
    }

    @DataProvider(name = "exceptionData")
    public Object[][] exceptionData() {
        return new Object[][] {
                {-100.0, 2, "Regular"}, // Negative price
                {100.0, -2, "Regular"}, // Negative quantity
                {100.0, 1, "InvalidType"} // Invalid customer type
        };
    }

    @Test(dataProvider = "validPriceData", groups = "validCases")
    public void testCalculateDiscountedPrice(double originalPrice, int quantity, String customerType, String discountCode, double expectedPrice) {
        System.out.println("Chạy testCalculateDiscountedPrice...");
        double actualPrice = orderService.calculateDiscountedPrice(originalPrice, quantity, customerType, discountCode);
        Assert.assertEquals(actualPrice, expectedPrice, 0.0001);
    }

    @Test(dataProvider = "exceptionData", groups = "exceptionCases", expectedExceptions = IllegalArgumentException.class)
    public void testCalculateDiscountedPrice_ExceptionCases(double originalPrice, int quantity, String customerType) {
        System.out.println("Chạy testCalculateDiscountedPrice_ExceptionCases...");
        orderService.calculateDiscountedPrice(originalPrice, quantity, customerType, null);
    }

    @Test(groups = "validCases")
    public void testValidCustomerType() {
        System.out.println("Chạy testValidCustomerType...");
        Assert.assertTrue(orderService.isValidCustomerType("VIP"));
        Assert.assertTrue(orderService.isValidCustomerType("Regular"));
        Assert.assertTrue(orderService.isValidCustomerType("Other"));
    }

    @Test(groups = "exceptionCases", expectedExceptions = IllegalArgumentException.class)
    public void testInvalidCustomerType() {
        System.out.println("Chạy testInvalidCustomerType...");
        orderService.validateCustomerType("InvalidType");
    }
}