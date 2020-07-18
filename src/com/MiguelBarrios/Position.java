package com.MiguelBarrios;

public class Position
{
    private final String symbol;
    private final String cusip;

    private final double currentDayProfitLoss;
    private final double currentDayProfitLossPercentage;

    private final int longQuantity;
    private final int settledLongQuantity;

    private final int settledShortQuantity;
    private final int shortQuantity;

    private final double marketValue;
    private final double averagePrice;

    public Position(String symbol, String cusip, double currentDayProfitLoss, double currentDayProfitLossPercentage, int longQuantity, int settledLongQuantity, int settledShortQuantity, int shortQuantity, double marketValue, double averagePrice)
    {
        this.symbol = symbol;
        this.cusip = cusip;
        this.currentDayProfitLoss = currentDayProfitLoss;
        this.currentDayProfitLossPercentage = currentDayProfitLossPercentage;
        this.longQuantity = longQuantity;
        this.settledLongQuantity = settledLongQuantity;
        this.settledShortQuantity = settledShortQuantity;
        this.shortQuantity = shortQuantity;
        this.marketValue = marketValue;
        this.averagePrice = averagePrice;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public String getCusip()
    {
        return cusip;
    }

    public double getCurrentDayProfitLoss()
    {
        return currentDayProfitLoss;
    }

    public double getcurrentDayProfitLossPercentage()
    {
        return currentDayProfitLossPercentage;
    }

    public int getLongQuantity()
    {
        return longQuantity;
    }

    public int getSettledLongQuantity()
    {
        return settledLongQuantity;
    }

    public int getSettledShortQuantity()
    {
        return settledShortQuantity;
    }

    public int getShortQuantity()
    {
        return shortQuantity;
    }

    public double getMarketValue()
    {
        return marketValue;
    }

    public double getAveragePrice()
    {
        return averagePrice;
    }

    @Override
    public String toString()
    {
        return "Position{" +
                "symbol='" + symbol + '\'' +
                ", cusip='" + cusip + '\'' +
                ", currentDayProfitLoss=" + currentDayProfitLoss +
                ", currentDayProfitLossPercentage=" + currentDayProfitLossPercentage +
                ", longQuantity=" + longQuantity +
                ", settledLongQuantity=" + settledLongQuantity +
                ", settledShortQuantity=" + settledShortQuantity +
                ", shortQuantity=" + shortQuantity +
                ", marketValue=" + marketValue +
                ", averagePrice=" + averagePrice +
                '}';
    }
}
