package com.MiguelBarrios;

public class Results
{
    private final String symbol;

    private final double buyPrice;

    private final double sellPrice;

    private final boolean isShortPosition;

    private final double profit;

    private static double totalShortProfit = 0;
    private static int numShortPosition = 0;

    private static double totalBullProfit = 0;
    private static int numRegPosition = 0;

    public Results(Mover mover, Quote quote)
    {
        symbol = quote.getSymbol();
        isShortPosition = mover.getDirection().equals("down");

        if(isShortPosition)
        {
            this.buyPrice = mover.getLast();
            this.sellPrice = quote.getAskPrice();
            this.profit = buyPrice - sellPrice;
            totalShortProfit += profit;
            ++numShortPosition;
        }
        else
        {
            this.buyPrice = mover.getLast();
            this.sellPrice = quote.getBidprice();
            this.profit = sellPrice - buyPrice;
            totalBullProfit += profit;
            ++numRegPosition;
        }
    }



    public String getSymbol()
    {
        return symbol;
    }

    public double getBuyPrice()
    {
        return buyPrice;
    }

    public double getSellPrice()
    {
        return sellPrice;
    }

    public boolean isShortPosition()
    {
        return isShortPosition;
    }

    public double getProfit()
    {
        return profit;
    }

    public static double getTotalShortProfit()
    {
        return totalShortProfit;
    }

    public static double getTotalBullProfit()
    {
        return totalBullProfit;
    }

    public static String getTotalProfits()
    {
        return String.format("TotalPositions = %d shortPositions = %d regularPosition = %d\nTotalProfits=%.2f shortProfit = %.2f regularProfit = %.2f",
                (numShortPosition + numRegPosition), numShortPosition, numRegPosition, (totalBullProfit + totalShortProfit), totalShortProfit, totalBullProfit);
    }

    @Override
    public String toString()
    {
        return "Results{" +
                "symbol='" + symbol + '\'' +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", isShortPosition=" + isShortPosition +
                ", profit=" + profit +
                '}';
    }

    public String[] csvFormat()
    {
        String[] arr = {symbol,
                String.valueOf(isShortPosition),
                String.valueOf(buyPrice),
                String.valueOf(sellPrice),
                String.valueOf(profit)};
        return arr;
    }
}
