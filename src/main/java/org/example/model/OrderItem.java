package org.example.model;

public class OrderItem
{
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity)
    {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct()
    {
        return product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public float ItemTotal()
    {
        return product.getPrice()*quantity;
    }

    @Override
    public String toString()
    {
        return String.format("%s x %d  = %.2f %s", product.getName(),
                quantity, product.getPrice(), product.getCurrency());
    }

}
