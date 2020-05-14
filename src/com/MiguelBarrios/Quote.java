package com.MiguelBarrios;

public class Quote
{
    private String symbol;

    private double bidprice;

    private int bidSize;

    private double askPrice;

    private int askSize;

    private double netChange;

    private boolean shortable;

    public Quote(String name, double bidprice, int bidSize, double askPrice, int askSize, double netChange, boolean shortable)
    {
        this.symbol = name;
        this.bidprice = bidprice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.netChange = netChange;
        this.shortable = shortable;
    }

    @Override
    public String toString()
    {
        return
                symbol +
                ", bidprice=" + bidprice +
                ", bidSize=" + bidSize +
                ", askPrice=" + askPrice +
                ", askSize=" + askSize +
                ", netChange=" + netChange +
                ", shortable=" + shortable;
    }
}
