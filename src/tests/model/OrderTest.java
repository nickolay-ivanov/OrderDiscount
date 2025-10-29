package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Product product1;
    private Product product2;
    private OrderItem item1;
    private OrderItem item2;
    private Order order;

    @BeforeEach
    void setUp() {
        product1 = new Product("1", "Milk", 1.5f, "BGN");
        product2 = new Product("2", "Coke", 2.0f, "BGN");

        item1 = new OrderItem(product1, 2); // 3
        item2 = new OrderItem(product2, 3); // 6

        order = new Order(List.of(item1, item2)); // 9
    }

    @Test
    void constructorShouldGenerateIdAndCalculateTotals() {
        assertNotNull(order.getId());
        assertEquals(9.0f, order.getSubtotal());
        assertEquals(0.0f, order.getDiscountAmount());
        assertEquals(9.0f, order.getTotal());
    }

    @Test
    void recalculateShouldResetDiscountAndRecomputeTotals() {
        order.applyDiscount(2.0f);
        assertEquals(7.0f, order.getTotal());

        order.recalculate();
        assertEquals(9.0f, order.getSubtotal());
        assertEquals(0.0f, order.getDiscountAmount());
        assertEquals(9.0f, order.getTotal());
    }

    @Test
    void applyDiscountShouldReduceTotalButNotBelowZero() {
        order.applyDiscount(3.0f);
        assertEquals(6.0f, order.getTotal());

        order.applyDiscount(10.0f);
        assertEquals(0.0f, order.getTotal());
    }

    @Test
    void toStringShouldContainBasicOrderInfo() {
        String output = order.toString();
        assertTrue(output.contains("Order id:"));
        assertTrue(output.contains("Subtotal:"));
        assertTrue(output.contains("Total:"));
    }
}
