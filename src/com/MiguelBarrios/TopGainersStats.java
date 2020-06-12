package com.MiguelBarrios;

public class TopGainersStats extends Statistics
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

    public TopGainersStats(Trade initialTrade)
    {
        this(initialTrade.getPrice(), initialTrade.getOrderType());
    }

    public TopGainersStats(double initialPrice, OrderType type)
    {
        this(initialPrice, type, DEFAULT_FACTOR);
    }

    public TopGainersStats(double initialPrice, OrderType type, double factor)
    {
        this.initialPrice = this.currentPrice =  absoluteMax = absoluteMin = initialPrice;

        this.factor = factor;

        percentChange = 0;

        shortPosition = (type == OrderType.SHORT);

        int isShort = (shortPosition) ? 1 : -1;

        sellPrice = absoluteMin  + (absoluteMin * factor * isShort);
        sellPriceInit = initialPrice + (initialPrice * INITIAL_PRICE_FACTOR * isShort);
    }

    @Override
    public OrderType update(double price)
    {
        return null;
    }

    @Override
    public double getPercentChange()
    {
        return 0;
    }

    @Override
    public double getAbsoluteMax()
    {
        return 0;
    }

    @Override
    public double getAbsoluteMin()
    {
        return 0;
    }

    @Override
    public double getCurrentPrice()
    {
        return 0;
    }

    @Override
    public double getInitialPrice()
    {
        return 0;
    }
}
