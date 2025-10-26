package org.example.discount;

import org.example.model.Order;
import org.example.model.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleCombo implements DiscountRule
{
    private final Map<Set<String>, Float> combos;

    public RuleCombo(Map<List<String>, Float> combosConfig)
    {
        this.combos = combosConfig.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().stream().map(String::toLowerCase).collect(Collectors.toSet()),
                        Map.Entry::getValue
                ));
    }

    @Override
    public DiscountResult apply(Order order)
    {
        Set<String> present = order.getItems().stream()
                .map(it -> it.getProduct().getName().toLowerCase())
                .collect(Collectors.toSet());
        for (Map.Entry<Set<String>, Float> combo : combos.entrySet())
        {
            if (present.containsAll(combo.getKey()))
            {
                return new DiscountResult(combo.getValue(), "Combo discount for " + combo.getKey());
            }
        }
        return DiscountResult.NONE;
    }
}
