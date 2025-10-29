package org.example.discount;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RuleBuyAGetBFreeTest {

    private RuleBuyAGetBFree discountRule;
    private Product coffee;
    private Product milk;
    private Product coke;

    @BeforeEach
    void setUp() {
        coffee = new Product("1", "Coffee", 10.0f, "BGN");
        milk = new Product("2", "Milk", 5.0f, "BGN");
        coke = new Product("3", "Coke", 7.0f, "BGN");
    }

    @Test
    void applyBuy2Get1FreeWithEligibleItems() {
        discountRule = new RuleBuyAGetBFree(2, 1, Arrays.asList("Coffee", "Milk"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 4), // 2 free
                new OrderItem(coke, 2)    // 0 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(20.0f, result.getSum());
    }

    @Test
    void applyBuy1Get1FreeWithMultipleEligibleProducts() {
        discountRule = new RuleBuyAGetBFree(1, 1, Arrays.asList("Coffee", "Milk"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 3), // 3 free
                new OrderItem(milk, 2),   // 2 free
                new OrderItem(coke, 1)    // 0 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(40.0f, result.getSum());
    }

    @Test
    void applyNoDiscountWhenQuantityBelowThreshold() {
        discountRule = new RuleBuyAGetBFree(3, 1, Arrays.asList("Coffee"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 2), // 0 frree
                new OrderItem(milk, 1) // 0 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(0.0f, result.getSum());
        assertEquals("No discount!", result.getDescription());
    }

    @Test
    void applyBuy3Get2FreeWithExactQuantity() {
        discountRule = new RuleBuyAGetBFree(3, 2, Arrays.asList("Coffee"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 3) // 2 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(20.0f, result.getSum());
    }

    @Test
    void applyBuy3Get2FreeWithMultipleBatches() {
        discountRule = new RuleBuyAGetBFree(3, 2, Arrays.asList("Coffee"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 7) // 4 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(40.0f, result.getSum());
    }

    @Test
    void applyDiscountIsCaseInsensitive() {
        discountRule = new RuleBuyAGetBFree(2, 1, Arrays.asList("COFFEE", "milk"));

        Product upperCoffee = new Product("10", "COFFEE", 10.0f, "BGN");
        Product mixedMilk = new Product("11", "MiLk", 5.0f, "BGN");

        Order order = new Order(Arrays.asList(
                new OrderItem(upperCoffee, 4), // 2 free
                new OrderItem(mixedMilk, 3)    // 1 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(25.0f, result.getSum());
    }

    @Test
    void applyNoDiscountWithNoEligibleProducts() {
        discountRule = new RuleBuyAGetBFree(2, 1, Arrays.asList("Coffee", "Milk"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coke, 5) // 0 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(0.0f, result.getSum());
        assertEquals("No discount!", result.getDescription());
    }

    @Test
    void applyBuy4Get1FreeWithPartialBatch() {
        discountRule = new RuleBuyAGetBFree(4, 1, Arrays.asList("Milk"));

        Order order = new Order(Arrays.asList(
                new OrderItem(milk, 7) // 1 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(5.0f, result.getSum());
    }

    @Test
    void applyBuy1Get2FreeSpecialOffer() {
        discountRule = new RuleBuyAGetBFree(1, 2, Arrays.asList("Coffee"));

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 2) // 4 free
        ));

        DiscountResult result = discountRule.apply(order);

        assertEquals(40.0f, result.getSum());
    }
}