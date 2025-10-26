package org.example.discount;

import org.example.model.Order;

public class RuleAOffForOverB implements DiscountRule
{
    private final float requirement;
    private final float percentage;

    public RuleAOffForOverB(float requirement, float percentage)
    {
        this.requirement = requirement;
        this.percentage = percentage;
    }

    @Override
    public DiscountResult apply(Order order)
    {
        if(order.getTotal() > requirement)
        {
            float sum = order.getTotal() * percentage;
            return new DiscountResult(sum, String.format("%.0f%% off orders above %.2f", percentage * 100, requirement));
        }
        return DiscountResult.NONE;
    }
}
