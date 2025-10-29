package org.example.discount;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RuleAOffForOverBTest {

    private RuleAOffForOverB discountRule;
    private Product car;
    private Product ticket;
    private Product mouse;

    @BeforeEach
    void setUp() {
        car = new Product("1", "Car", 1000.0f, "BGN");
        ticket = new Product("2", "Ticket", 50.0f, "BGN");
        mouse = new Product("3", "Mouse", 80.0f, "BGN");
    }

    @Test
    void applyDiscountWhenOrderTotalExceedsRequirement() {
        discountRule = new RuleAOffForOverB(500.0f, 0.1f);

        Order order = new Order(Arrays.asList(
                new OrderItem(car, 1)
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(100.0f, result.getSum());
        assertEquals("10% off orders above 500.00", result.getDescription());
    }

    @Test
    void applyNoDiscountWhenOrderTotalBelowRequirement() {
        discountRule = new RuleAOffForOverB(500.0f, 0.1f);

        Order order = new Order(Arrays.asList(
                new OrderItem(ticket, 2),
                new OrderItem(mouse, 1)
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(0.0f, result.getSum());
        assertEquals("No discount!", result.getDescription());
    }

    @Test
    void applyNoDiscountWhenOrderTotalExactlyAtRequirement() {
        discountRule = new RuleAOffForOverB(500.0f, 0.1f);

        Product exactProduct = new Product("4", "Tablet", 500.0f, "BGN");
        Order order = new Order(Arrays.asList(
                new OrderItem(exactProduct, 1)
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(0.0f, result.getSum());
        assertEquals("No discount!", result.getDescription());
    }

    @Test
    void applyDiscountWithMultipleItemsExceedingRequirement() {
        discountRule = new RuleAOffForOverB(1000.0f, 0.15f);

        Order order = new Order(Arrays.asList(
                new OrderItem(car, 1),
                new OrderItem(ticket, 3),
                new OrderItem(mouse, 2)
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(196.5f, result.getSum(), 0.01);
        assertEquals("15% off orders above 1000.00", result.getDescription());
    }

    @Test
    void applyFullPercentageDiscount() {
        discountRule = new RuleAOffForOverB(100.0f, 1.0f);

        Order order = new Order(Arrays.asList(
                new OrderItem(car, 1)
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(1000.0f, result.getSum());
        assertEquals("100% off orders above 100.00", result.getDescription());
    }

    @Test
    void applyZeroPercentageDiscount() {
        discountRule = new RuleAOffForOverB(100.0f, 0.0f);

        Order order = new Order(Arrays.asList(
                new OrderItem(car, 1)
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(0.0f, result.getSum());
        assertEquals("0% off orders above 100.00", result.getDescription());
    }

    @Test
    void applyNoDiscountWithEmptyOrder() {
        discountRule = new RuleAOffForOverB(100.0f, 0.1f);

        Order order = new Order(Collections.emptyList());

        DiscountResult result = discountRule.apply(order);

        assertEquals(0.0f, result.getSum());
        assertEquals("No discount!", result.getDescription());
    }
}