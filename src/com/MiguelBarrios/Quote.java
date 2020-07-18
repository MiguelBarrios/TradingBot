package com.MiguelBarrios;

import java.text.SimpleDateFormat;

public class Quote
{
    public static final SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    private final String symbol;

    private final  double bidprice;

    private final int bidSize;

    private final double askPrice;

    private final int askSize;

    private final double netChange;

    private final boolean shortable;

    private final int totalVolume;

    private final double volatility;

    private final double regularMarketNetChange;

    private final long time;

    public Quote(String symbol, double bidprice, int bidSize, double askPrice, int askSize, double netChange, int totalVolume, boolean shortable, double volitility, double regularMarketNetChange)
    {
        this.symbol = symbol;
        this.bidprice = bidprice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.netChange = netChange;
        this.totalVolume = totalVolume;
        this.shortable = shortable;
        this.volatility = volitility;
        this.regularMarketNetChange = regularMarketNetChange;
        this.time = System.currentTimeMillis();
    }

    public String[] csvFormat()
    {
        String[] arr = {symbol,
                String.valueOf(bidprice),
                String.valueOf(bidSize),
                String.valueOf(askPrice),
                String.valueOf(askSize),
                String.valueOf(netChange),
                String.valueOf(totalVolume),
                String.valueOf(shortable),
                String.valueOf(volatility),
                String.valueOf(regularMarketNetChange),
                String.valueOf(dft.format(time))
        };

        return arr;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public double getBidprice()
    {
        return bidprice;
    }

    public double getAskPrice()
    {
        return askPrice;
    }

    public boolean isShortable()
    {
        return shortable;
    }

    public int getTotalVolume()
    {
        return totalVolume;
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
