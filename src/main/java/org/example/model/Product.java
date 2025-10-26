package org.example.model;

import java.util.Objects;
public class Product
{
    private final String id;
    private final String name;
    private final float price;
    private final String currency;

    public Product(String id,String name, float price, String currency)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public float getPrice()
    {
    return price;
    }
    public String getCurrency()
    {
    return currency;
    }
    @Override
    public String toString()
    {
        return "Product " + name + ", ID: " + id + ", Price: " + price + currency;
    }

}
