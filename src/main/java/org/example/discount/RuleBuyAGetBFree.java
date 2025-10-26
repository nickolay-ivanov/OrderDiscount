package org.example.discount;

import org.example.model.Order;
import org.example.model.OrderItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleBuyAGetBFree implements DiscountRule
{
    private final int a;
    private final int b;
    private final Set<String> productNames;

    public RuleBuyAGetBFree(int a, int b, List<String>productNames)
    {
        this.a = a;
        this.b = b;
        this.productNames = productNames.stream().map(String::toLowerCase).collect(Collectors.toSet());
    }
    
    @Override
    public DiscountResult apply(Order order)
    {
        float discount = 0f;
        StringBuilder description = new StringBuilder();
        for(OrderItem item : order.getItems())
        {
            if(productNames.contains(item.getProduct().getName().toLowerCase())) 
            {
                int freeItems = (item.getQuantity() / a) * b;
                float itemDiscount = item.getProduct().getPrice() * freeItems;
                if(itemDiscount > 0)
                {
                    discount += itemDiscount;
                    description.append(String.format("%d free %s: ", freeItems,item.getProduct().getName()));
                }
            }
        }
        return discount > 0 ? new DiscountResult(discount, "Buy A Get B Free: " + description) :
                DiscountResult.NONE;
    }
    
}
