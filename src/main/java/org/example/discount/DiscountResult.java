package org.example.discount;

public class DiscountResult
{
    private final float sum;
    private final String description;

    public DiscountResult(float sum, String description)
    {
        this.sum = sum;
        this.description = description;
    }

    public float getSum()
    {
        return sum;
    }

    public String getDescription()
    {
        return description;
    }

    public static final DiscountResult NONE = new DiscountResult(0.0f, "No discount!");
}
