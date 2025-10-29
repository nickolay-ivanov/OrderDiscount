package org.example.discount;

import org.example.model.Order;
import org.example.model.OrderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleCombo implements DiscountRule {
    private final Map<Set<String>, Float> combos;

    public RuleCombo(Map<List<String>, Float> combosConfig) {
        this.combos = combosConfig.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().stream()
                                .map(String::toLowerCase)
                                .collect(Collectors.toSet()),
                        Map.Entry::getValue
                ));
    }

    @Override
    public DiscountResult apply(Order order) {
        float totalDiscount = 0f;
        StringBuilder description = new StringBuilder();

        for (Set<String> combo : combos.keySet())
        {
            Map<String, Integer> comboItemsCount = new HashMap<>();
            for (String item : combo)
            {
                for (OrderItem it : order.getItems())
                {
                    if (it.getProduct().getName().equalsIgnoreCase(item))
                    {
                        comboItemsCount.put(item, it.getQuantity());
                    }
                }
            }

            if (comboItemsCount.size() < combo.size())
            {
                continue;
            }

            int smallest = Integer.MAX_VALUE;
            for (int q : comboItemsCount.values())
            {
                if (q < smallest)
                {
                    smallest = q;
                }
            }

            if (smallest > 0)
            {
                float comboDiscount = combos.get(combo) * smallest;
                totalDiscount += comboDiscount;
                description.append("Combo discount for ")
                        .append(combo)
                        .append(" x")
                        .append(smallest)
                        .append(" = ")
                        .append(comboDiscount)
                        .append("; ");
            }
        }

        return new DiscountResult(totalDiscount, description.toString());
    }
}
