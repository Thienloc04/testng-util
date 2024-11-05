package com.Group03.core;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PaymentServiceTestTestNG {
    private PaymentService paymentService;

    @BeforeMethod
    public void setUp() {
        paymentService = new PaymentService();
        System.out.println("Setup for test");
    }

    @AfterMethod
    public void tearDown() {
        // Thực hiện các thao tác dọn dẹp cần thiết
        paymentService = null; // Giải phóng đối tượng paymentService
        System.out.println("Tear down after test");
    }

    @Test
    public void testCalculateTotalPaymentSuccess() {
        double discountedPrice = 100.0;
        double taxRate = 0.1; // 10%
        double expectedTotal = 110.0; // 100.0 + 10% = 110.0

        double result = paymentService.calculateTotalPayment(discountedPrice, taxRate);
        Assert.assertEquals(result, expectedTotal, 0.0001); // Adding a tolerance for precision
    }


    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Giá sau giảm giá không thể là số âm")
    public void testCalculateTotalPaymentWithNegativeDiscountedPrice() {
        paymentService.calculateTotalPayment(-50.0, 0.1);
    }


    @Test(enabled = false) // Bài kiểm thử này sẽ bị bỏ qua
    public void testCalculateTotalPaymentWithZeroDiscountedPrice() {
        double discountedPrice = 0.0;
        double taxRate = 0.2; // 20%
        double expectedTotal = 0.0; // 0.0 + 20% = 0.0

        double result = paymentService.calculateTotalPayment(discountedPrice, taxRate);
        Assert.assertEquals(result, expectedTotal);
    }

    @Test(enabled = false, description = "Bài kiểm thử chưa sẵn sàng") // Bài kiểm thử này chưa sẵn sàng
    public void testCalculateTotalPaymentNotReady() {
        // Bài kiểm thử này chưa được thực hiện
        System.out.println("This test is ignored for now.");
    }
}
