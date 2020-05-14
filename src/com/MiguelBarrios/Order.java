package com.MiguelBarrios;

public class Order
{
    private String symbol;

    private OrderType orderType;

    //requested price
    private double price;

    public Order(String symbol, OrderType orderType, double price)
    {
        this.symbol = symbol;
        this.orderType = orderType;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return  orderType + " " + symbol + " " + price;
    }
}
