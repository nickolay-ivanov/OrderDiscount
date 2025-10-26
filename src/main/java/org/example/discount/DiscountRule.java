package org.example.discount;

import org.example.model.Order;
public interface DiscountRule
{
    DiscountResult apply(Order order);
}
