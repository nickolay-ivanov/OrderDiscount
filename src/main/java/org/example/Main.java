package org.example;

import org.example.model.*;
import org.example.discount.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        Product coffee = new Product("p1", "Coffee", 10f, "LEV");
        Product milk = new Product("p2", "Milk", 5f, "LEV");
        Product coke = new Product("p3", "Coke", 100f, "LEV");

        List<OrderItem> items = Arrays.asList
                (
                new OrderItem(coffee, 4),
                new OrderItem(milk, 1),
                new OrderItem(coke, 5)
                );

        Order order = new Order(items);
        System.out.println("Before discounts:\n" + order);

        DiscountEngine engine = new DiscountEngine(Arrays.asList
                (
                new RuleBuyAGetBFree(3, 1, Arrays.asList("Coffee")),
                new RuleCombo(Map.of(Arrays.asList("Coffee", "Milk"), 5f)),
                new RuleAOffForOverB(100f, 0.10f)
                ));

        List<DiscountResult> appliedDiscounts = engine.applyAll(order);

        System.out.println("Applied discounts:");
        for (DiscountResult d : appliedDiscounts)
        {
            System.out.printf(" - %s: %.2f\n", d.getDescription(), d.getSum());
        }

        System.out.println("\nAfter discounts:\n" + order);
    }
}