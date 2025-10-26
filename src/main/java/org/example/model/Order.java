package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order
{
    private final String id;
    private final List<OrderItem> items;
    private float subtotal;
    private float discountAmount;
    private float total;

    public Order(List<OrderItem> items)
    {
        this.id = UUID.randomUUID().toString();
        this.items = new ArrayList<>(items);
        recalculate();
    }

    public String getId()
    {
        return id;
    }

    public List<OrderItem>getItems()
    {
        return Collections.unmodifiableList(items);
    }

    public float getSubtotal()
    {
        return subtotal;
    }

    public float getDiscountAmount()
    {
        return discountAmount;

    }

    public float getTotal()
    {
        return total;
    }
    public void recalculate()
    {
        this.subtotal = items.stream().map(OrderItem::ItemTotal).reduce(0.0f, Float::sum);
        this.discountAmount = 0.0f;
        this.total = subtotal;
    }

    public void applyDiscount(float discount)
    {
        this.discountAmount += discount;
        this.total = Math.max(0, subtotal - discountAmount);
    }

    @Override
    public String toString()
    {
        StringBuilder print = new StringBuilder("Order id: " + id + "\n");
        for (OrderItem item : items)
        {
            print.append(" - ").append(item.getProduct().getName()).append(" x ").append(item.getQuantity()).append(" = ").append(item.ItemTotal()).append(" ").append(item.getProduct().getCurrency()).append("\n");
        }
        print.append("Subtotal: ").append(subtotal).append("\n");
        print.append("Discounts applied: ").append(discountAmount).append("\n");
        print.append("Total: ").append(total).append("\n");
        return print.toString();
    }
}
