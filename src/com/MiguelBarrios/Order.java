package com.MiguelBarrios;

public class Order
{
     private Trade bought;

     private Trade sold;

     private Stats stats;

    public Order(Trade bought)
    {
        this.bought = bought;
        sold = null;
    }

    public void close(Trade sell)
    {
        this.sold = sell;
    }

    public static Order placeOrder()
    {
        return null;
    }

    public static OrderType eval()
    {
        return null;
    }

    public String getSymbol()
    {
        return bought.getSymbol();
    }

    public int positionSize()
    {
        return bought.getNumberOfShares();
    }

    public double boughtFor()
    {
        return bought.getTotalPrice();
    }

    public double soldFor()
    {
        return sold.getTotalPrice();
    }
}
