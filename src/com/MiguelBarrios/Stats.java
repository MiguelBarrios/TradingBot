package com.MiguelBarrios;

public class Stats
{
    private static final double DEFAULT_FACTOR = 0.02;

    private static final double INITIAL_PRICE_FACTOR = 0.015;

    private final double initialPrice;

    private final boolean shortPosition;

    private final double sellPriceInit;

    private final double factor;

    private double currentPrice;

    private double absoluteMax;

    private double absoluteMin;

    private double percentChange;

    private double sellPrice;

    public Stats(double initialPrice, OrderType type)
    {
        this(initialPrice, type, DEFAULT_FACTOR);
    }

    public Stats(double initialPrice, OrderType type, double factor)
    {
        this.initialPrice = this.currentPrice =  absoluteMax = absoluteMin = initialPrice;

        this.factor = factor;

        percentChange = 0;

        shortPosition = (type == OrderType.SHORT);

        int isShort = (shortPosition) ? 1 : -1;

        sellPrice = absoluteMin  + (absoluteMin * factor * isShort);
        sellPriceInit = initialPrice + (initialPrice * INITIAL_PRICE_FACTOR * isShort);
    }

    public OrderType update(double price)
    {
        currentPrice = price;

        //Update Absolute Max and Min
        if(currentPrice > absoluteMax) {
            absoluteMax = currentPrice;
        }
        else if(currentPrice < absoluteMin) {
            absoluteMin = currentPrice;
        }

        percentChange =  Math.round((1 - initialPrice/currentPrice) * 10000) / 10000.0;

        if(shortPosition)
        {
            sellPrice = absoluteMin  + (absoluteMin * factor);
            if(currentPrice >= sellPrice)
                return OrderType.SELL;
        }
        else
        {
            sellPrice = absoluteMax - (absoluteMax * factor);
            if(currentPrice <= sellPrice || currentPrice < sellPriceInit)
                return OrderType.SELL;
        }

        return OrderType.HOLD;
    }

    public double getPercentChange()
    {
        return percentChange;
    }

    public double getAbsoluteMax()
    {
        return absoluteMax;
    }

    public double getAbsoluteMin()
    {
        return absoluteMin;
    }

    public double getCurrentPrice()
    {
        return currentPrice;
    }

    public double getInitialPrice()
    {
        return initialPrice;
    }
}
