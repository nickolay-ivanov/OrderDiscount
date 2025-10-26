package org.example.discount;

import org.example.model.Order;
import java.util.ArrayList;
import java.util.List;

public class DiscountEngine
{
    private final List<DiscountRule> rules = new ArrayList<>();

    public DiscountEngine(List<DiscountRule> rules)
    {
        this.rules.addAll(rules);
    }

    public List<DiscountResult> applyAll(Order order)
    {
        List<DiscountResult> applied = new ArrayList<>();
        for (DiscountRule r : rules)
        {
            DiscountResult res = r.apply(order);
            if (res != null && res.getSum() > 0f)
            {
                order.applyDiscount(res.getSum());
                applied.add(res);
            }
        }
        return applied;
    }
}