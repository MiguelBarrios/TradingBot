package com.MiguelBarrios;

public class Trade
{
    private String symbol;

    private OrderType orderType;

    private int numberOfShares;

    private double price;

    private double totalPrice;

    private long time;

    public Trade(OrderType type, int numberOfShares, double price, String symbol)
    {
        this.orderType = type;
        this.numberOfShares = numberOfShares;
        this.symbol = symbol;
        this.price = price;
        totalPrice = price * numberOfShares;
        time = System.currentTimeMillis();
    }

    public String getSymbol()
    {
        return symbol;
    }

    public OrderType getOrderType()
    {
        return orderType;
    }

    public int getNumberOfShares()
    {
        return numberOfShares;
    }

    public double getPrice()
    {
        return price;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public long getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return "Trade{" +
                "symbol = '" + symbol + '\'' +
                ", orderType = " + orderType +
                ", numberOfShares = " + numberOfShares +
                ", price = " + price +
                ", totalPrice = " + totalPrice +
                ", time = " + time +
                '}';
    }
}
