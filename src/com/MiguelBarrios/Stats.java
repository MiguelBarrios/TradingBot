package com.MiguelBarrios;

public class Stats
{
    private static double DEFAULT_FACTOR = 0.02;

    private double initialPrice;

    private double currentPrice;

    private double absoluteMax;

    private double absoluteMin;

    private double percentChange;

    private boolean shortPosition;

    private double sellPrice;

    private double factor;

    public Stats(double initialPrice, OrderType type)
    {
        this(initialPrice, type, DEFAULT_FACTOR);
    }

    public Stats(double initialPrice, OrderType type, double factor)
    {
        this.initialPrice = initialPrice;

        currentPrice = initialPrice;

        absoluteMax = initialPrice;

        absoluteMin = initialPrice;

        percentChange = 0;


        shortPosition = (type == OrderType.SHORT) ? true : false;

        if(shortPosition)
        {
            sellPrice = absoluteMin  + (absoluteMin * factor);
        }
        else
        {
            sellPrice = absoluteMax - (absoluteMax * factor);
        }

        this.factor = factor;
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

        percentChange =  Math.round((1 - initialPrice/currentPrice) * 1000) / 1000.0;

        if(shortPosition)
        {
            sellPrice = absoluteMin  + (absoluteMin * factor);
            if(currentPrice >= sellPrice)
                return OrderType.SELL;
        }
        else
        {
            sellPrice = absoluteMax - (absoluteMax * factor);
            if(currentPrice <= sellPrice || currentPrice < initialPrice)
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
