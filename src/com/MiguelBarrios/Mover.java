package com.MiguelBarrios;

public class Mover
{
    private static String symbol;

    private static double lastPrice;

    private static double percentChange;

    public Mover(String symbol, double lastPrice, double percentChange)
    {
        this.symbol = symbol;
        this.lastPrice = lastPrice;
        this.percentChange = percentChange;
    }

    public static String getSymbol()
    {
        return symbol;
    }

    public static double getLastPrice()
    {
        return lastPrice;
    }

    public static double getPercentChange()
    {
        return percentChange;
    }
}
