package com.MiguelBarrios;

public class TopGainersStats extends Statistics
{
    private static final double SELL_FACTOR = -0.01;

    private static final double INCREASE_FACTOR = 0.05;

    private static double ADDITIONAL_PURCHASE_FACTOR = 0.05;

    private final double initialPrice;

    private double absoluteMax;

    private double absoluteMin;

    private double sellPrice;

    public TopGainersStats(Trade initialTrade)
    {
        this(initialTrade.getPrice(), initialTrade.getOrderType());
    }

    public TopGainersStats(double initialPrice, OrderType type)
    {
        this(initialPrice, type, SELL_FACTOR);
    }

    public TopGainersStats(double initialPrice, OrderType type, double factor)
    {
        this.initialPrice =  absoluteMax = absoluteMin = initialPrice;

    }

    @Override
    public OrderType update(double updatedPrice)
    {
        double change = Util.percentChange(absoluteMax, updatedPrice);
        if(change <= SELL_FACTOR)
            return OrderType.SELL;

        if(change >= ADDITIONAL_PURCHASE_FACTOR)
        {
            ADDITIONAL_PURCHASE_FACTOR += INCREASE_FACTOR;
            return OrderType.BUY;
        }

        if(updatedPrice > absoluteMax)
            absoluteMax = updatedPrice;
        if(updatedPrice < absoluteMin)
            absoluteMin = updatedPrice;

        return OrderType.HOLD;
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
