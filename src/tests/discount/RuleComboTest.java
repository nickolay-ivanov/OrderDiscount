package org.example.discount;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleComboTest {

    private RuleCombo ruleCombo;
    private Product coffee;
    private Product milk;
    private Product coke;

    @BeforeEach
    void setUp() {
        coffee = new Product("1", "Coffee", 10.0f, "USD");
        milk = new Product("2", "Milk", 5.0f, "USD");
        coke = new Product("3", "Coke", 7.0f, "USD");
    }

    @Test
    void applyComboDiscountWithEligibleItems() {
        Map<List<String>, Float> comboConfig = Map.of(
                List.of("Coffee", "Milk"), 2.0f
        );

        ruleCombo = new RuleCombo(comboConfig);

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 2),
                new OrderItem(milk, 2),
                new OrderItem(coke, 1)
        ));

        DiscountResult result = ruleCombo.apply(order);

        assertEquals(4.0f, result.getSum());
    }

    @Test
    void applyComboDiscountWithMissingItem() {
        Map<List<String>, Float> comboConfig = Map.of(
                List.of("Coffee", "Milk"), 2.0f
        );

        ruleCombo = new RuleCombo(comboConfig);

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 3),
                new OrderItem(coke, 2)
        ));

        DiscountResult result = ruleCombo.apply(order);

        assertEquals(0.0f, result.getSum());
    }

    @Test
    void applyComboDiscountWithMultipleCombos() {
        Map<List<String>, Float> comboConfig = Map.of(
                List.of("Coffee", "Milk"), 2.0f,
                List.of("Coke", "Milk"), 1.5f
        );

        ruleCombo = new RuleCombo(comboConfig);

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 1),
                new OrderItem(milk, 2),
                new OrderItem(coke, 1)
        ));

        DiscountResult result = ruleCombo.apply(order);

        assertEquals(3.5f, result.getSum());
    }

    @Test
    void applyComboDiscountIsCaseInsensitive() {
        Map<List<String>, Float> comboConfig = Map.of(
                List.of("coffee", "milk"), 2.0f
        );

        ruleCombo = new RuleCombo(comboConfig);

        Product upperCoffee = new Product("10", "COFFEE", 10.0f, "USD");
        Product mixedMilk = new Product("11", "Milk", 5.0f, "USD");

        Order order = new Order(Arrays.asList(
                new OrderItem(upperCoffee, 1),
                new OrderItem(mixedMilk, 1)
        ));

        DiscountResult result = ruleCombo.apply(order);

        assertEquals(2.0f, result.getSum());
    }

    @Test
    void applyComboDiscountWithNoCombosConfigured() {
        ruleCombo = new RuleCombo(Collections.emptyMap());

        Order order = new Order(Arrays.asList(
                new OrderItem(coffee, 2),
                new OrderItem(milk, 2)
        ));

        DiscountResult result = ruleCombo.apply(order);

        assertEquals(0.0f, result.getSum());
    }

    @Test
    void applyComboDiscountWithEmptyOrder() {
        Map<List<String>, Float> comboConfig = Map.of(
                List.of("Coffee", "Milk"), 2.0f
        );

        ruleCombo = new RuleCombo(comboConfig);

        Order order = new Order(Collections.emptyList());

        DiscountResult result = ruleCombo.apply(order);

        assertEquals(0.0f, result.getSum());
    }
}
