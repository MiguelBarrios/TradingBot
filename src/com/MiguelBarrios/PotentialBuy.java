package com.MiguelBarrios;

public class PotentialBuy
{
    private static final double FACTOR = 0.005;

    public final String symbol;

    public final double initialPrice;

    private double currentPrice;

    private double buyPrice;// = initialPrice + (initialPrice * INITIAL_PRICE_FACTOR * isShort);

    public PotentialBuy(String symbol, double initialPrice)
    {
        this.symbol = symbol;
        this.initialPrice = initialPrice;
        currentPrice = initialPrice;

    }

    
}
